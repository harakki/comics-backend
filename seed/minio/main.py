import mimetypes
import json
import os
import psycopg2
import uuid6
from datetime import datetime

from minio import Minio

MINIO_HOST = os.getenv("MINIO_HOST", "minio:9000")
MINIO_ACCESS_KEY = os.getenv("MINIO_ACCESS_KEY")
MINIO_SECRET_KEY = os.getenv("MINIO_SECRET_KEY")
MINIO_BUCKET = os.getenv("MINIO_BUCKET")

POSTGRES_HOST = os.getenv("POSTGRES_HOST", "postgres")
POSTGRES_PORT = os.getenv("POSTGRES_PORT", "5432")
POSTGRES_DB = os.getenv("POSTGRES_DB")
POSTGRES_USER = os.getenv("POSTGRES_USERNAME")
POSTGRES_PASSWORD = os.getenv("POSTGRES_PASSWORD")

BASE_PATH = "/seed/minio/files"
MAPPING_FILE = "/seed/minio/mapping.json"

minio = Minio(
    MINIO_HOST, access_key=MINIO_ACCESS_KEY, secret_key=MINIO_SECRET_KEY, secure=False
)

db = psycopg2.connect(
    host=POSTGRES_HOST,
    port=POSTGRES_PORT,
    dbname=POSTGRES_DB,
    user=POSTGRES_USER,
    password=POSTGRES_PASSWORD,
)

if not minio.bucket_exists(MINIO_BUCKET):
    minio.make_bucket(MINIO_BUCKET)


def generate_uuid_v7():
    return uuid6.uuid7()


def detect_mime(path):
    return mimetypes.guess_type(path)[0]


def get_file_size(path):
    return os.path.getsize(path)


def upload_to_minio(file_path, s3_key):
    minio.fput_object(MINIO_BUCKET, s3_key, file_path)


def insert_media(cur, media_id, s3_key, filename, mime, size):
    cur.execute(
        """
                INSERT INTO media (id, bucket, s3_key, original_filename, status, content_type, size, created_at,
                                   version)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
                """,
        (
            str(media_id),
            MINIO_BUCKET,
            s3_key,
            filename,
            "COMMITTED",
            mime,
            size,
            datetime.utcnow(),
            0,
        ),
    )


def link_entity(cur, entity_type, entity_id, media_id):
    if entity_type == "authors":
        cur.execute(
            """
                    UPDATE authors
                    SET main_cover_media_id = %s
                    WHERE id = %s
                    """,
            (str(media_id), entity_id),
        )

    elif entity_type == "publishers":
        cur.execute(
            """
                    UPDATE publishers
                    SET logo_media_id = %s
                    WHERE id = %s
                    """,
            (str(media_id), entity_id),
        )

    elif entity_type == "titles":
        cur.execute(
            """
                    UPDATE titles
                    SET main_cover_media_id = %s
                    WHERE id = %s
                    """,
            (str(media_id), entity_id),
        )


def process_file(cur, entity_type, filename, entity_id):
    file_path = os.path.join(BASE_PATH, entity_type, filename)

    if not os.path.exists(file_path):
        print(f"[WARN] Missing file: {file_path}")
        return

    media_id = generate_uuid_v7()

    s3_key = f"uploads/{media_id}/{filename}"

    mime = detect_mime(file_path)
    size = get_file_size(file_path)

    upload_to_minio(file_path, s3_key)

    insert_media(cur, media_id, s3_key, filename, mime, size)

    link_entity(cur, entity_type, entity_id, media_id)

    print(f"[OK] {entity_type}/{filename}")


def main():
    print(f"Loading mapping from: {MAPPING_FILE}")

    with open(MAPPING_FILE, "r") as f:
        mapping = json.load(f)

    with db:
        with db.cursor() as cur:

            for entity_type in ["authors", "publishers", "titles"]:
                for filename, entity_id in mapping.get(entity_type, {}).items():

                    try:
                        process_file(cur, entity_type, filename, entity_id)
                        db.commit()

                    except Exception as e:
                        db.rollback()
                        print(f"[ERROR] {filename}: {e}")

    print("MinIO seeding completed successfully")


if __name__ == "__main__":
    main()
