FROM alpine:latest

RUN apk add --no-cache \
    bash \
    curl \
    postgresql-client

COPY . /seed

RUN chmod +x /seed/apply_by_seed_container.sh

ENTRYPOINT ["/seed/apply_by_seed_container.sh"]
