FROM python:3.14-slim

RUN apt-get update && apt-get install -y \
    curl \
    python3-dev \
    python3-pip \
    postgresql-client

COPY . /seed

RUN pip3 install --no-cache-dir -r /seed/minio/requirements.txt; \
    chmod +x /seed/apply_by_seed_container.sh

ENTRYPOINT ["/seed/apply_by_seed_container.sh"]
