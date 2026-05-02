#!/bin/bash

set -e

echo "Waiting for backend..."

until curl -sf http://backend:8081/actuator/health/readiness | grep '"status":"UP"' >/dev/null; do
  sleep 2
done

echo "Backend is fully ready!"
echo "Running seed scripts..."

# ======================
# MinIO
# ======================
if [ -f /minio/.initialized ]; then
  echo "MinIO already initialized, skipping..."
else
  echo "Restoring MinIO..."
  if [ -f /seed/minio/minio_dump.tar ]; then
    tar xvf /seed/minio/minio_dump.tar -C /minio
    touch /minio/.initialized
    echo "MinIO restore completed"
  else
    echo "[!] No MinIO dump found, skipping..."
  fi
fi

# ======================
# PostgreSQL
# ======================
if [ -f /postgres/.initialized ]; then
  echo "PostgreSQL already initialized, skipping..."
else
  echo "Restoring Postgres..."

  if ls /seed/postgres/*.sql >/dev/null 2>&1; then
    apk add --no-cache postgresql-client
    for file in /seed/postgres/*.sql; do
      [ -e "$file" ] || continue
      echo "Applying $file..."
      PGPASSWORD="$POSTGRES_PASSWORD" psql -h postgres -U "$POSTGRES_USERNAME" -d "$POSTGRES_DB" -f "$file"
    done
    touch /postgres/.initialized
    echo "PostgreSQL restore completed"
  else
    echo "[!] No PostgreSQL dumps found, skipping..."
  fi
fi

echo "Seeding completed"
