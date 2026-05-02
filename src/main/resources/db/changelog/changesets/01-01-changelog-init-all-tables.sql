-- liquibase formatted sql

-- changeset harakki:1777647337557-1
CREATE TABLE authors
(
    id                  UUID                        NOT NULL,
    name                VARCHAR(255)                NOT NULL,
    slug                VARCHAR(255)                NOT NULL,
    description         TEXT,
    website_urls        TEXT[],
    country_iso_code    VARCHAR(2),
    main_cover_media_id UUID,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          UUID,
    updated_by          UUID,
    version             BIGINT,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-2
CREATE TABLE chapter_pages
(
    id         UUID    NOT NULL,
    chapter_id UUID    NOT NULL,
    page_order INTEGER NOT NULL,
    media_id   UUID    NOT NULL,
    CONSTRAINT pk_chapter_pages PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-3
CREATE TABLE chapters
(
    id         UUID                        NOT NULL,
    title_id   UUID                        NOT NULL,
    volume     INTEGER,
    number     INTEGER                     NOT NULL,
    sub_number INTEGER                     NOT NULL,
    name       VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by UUID,
    updated_by UUID,
    version    BIGINT,
    CONSTRAINT pk_chapters PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-4
CREATE TABLE collection_titles
(
    collection_id UUID    NOT NULL,
    title_id      UUID,
    sort_order    INTEGER NOT NULL,
    CONSTRAINT pk_collection_titles PRIMARY KEY (collection_id, sort_order)
);

-- changeset harakki:1777647337557-5
CREATE TABLE library_entries
(
    id                   UUID                        NOT NULL,
    user_id              UUID                        NOT NULL,
    title_id             UUID                        NOT NULL,
    status               VARCHAR(255)                NOT NULL,
    vote                 VARCHAR(255),
    last_read_chapter_id UUID,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE,
    version              BIGINT,
    CONSTRAINT pk_library_entries PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-6
CREATE TABLE media
(
    id                UUID                        NOT NULL,
    bucket            VARCHAR(255),
    s3_key            VARCHAR(255)                NOT NULL,
    original_filename VARCHAR(255),
    status            VARCHAR(255),
    content_type      VARCHAR(255),
    size              BIGINT,
    width             INTEGER,
    height            INTEGER,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by        UUID,
    version           BIGINT,
    CONSTRAINT pk_media PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-7
CREATE TABLE publishers
(
    id               UUID                        NOT NULL,
    name             VARCHAR(255)                NOT NULL,
    slug             VARCHAR(255)                NOT NULL,
    description      TEXT,
    website_urls     TEXT[],
    country_iso_code VARCHAR(2),
    logo_media_id    UUID,
    created_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    created_by       UUID,
    updated_by       UUID,
    version          BIGINT,
    CONSTRAINT pk_publishers PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-8
CREATE TABLE slug_sequences
(
    slug_prefix VARCHAR(255) NOT NULL,
    counter     BIGINT       NOT NULL,
    CONSTRAINT pk_slug_sequences PRIMARY KEY (slug_prefix)
);

-- changeset harakki:1777647337557-9
CREATE TABLE tags
(
    id          UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    slug        VARCHAR(255)                NOT NULL,
    type        VARCHAR(255)                NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_by  UUID,
    updated_by  UUID,
    version     BIGINT,
    CONSTRAINT pk_tags PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-10
CREATE TABLE title_authors
(
    id         UUID         NOT NULL,
    title_id   UUID         NOT NULL,
    author_id  UUID,
    role       VARCHAR(255) NOT NULL,
    sort_order INTEGER,
    version    BIGINT,
    CONSTRAINT pk_title_authors PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-11
CREATE TABLE title_publishers
(
    id           UUID NOT NULL,
    title_id     UUID NOT NULL,
    publisher_id UUID,
    sort_order   INTEGER,
    version      BIGINT,
    CONSTRAINT pk_title_publishers PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-12
CREATE TABLE title_tags
(
    tag_id   UUID NOT NULL,
    title_id UUID NOT NULL,
    CONSTRAINT pk_title_tags PRIMARY KEY (tag_id, title_id)
);

-- changeset harakki:1777647337557-13
CREATE TABLE titles
(
    id                  UUID                        NOT NULL,
    name                VARCHAR(255)                NOT NULL,
    slug                VARCHAR(255)                NOT NULL,
    description         TEXT,
    type                VARCHAR(255),
    title_status        VARCHAR(255),
    release_year        INTEGER,
    content_rating      VARCHAR(255)                NOT NULL,
    is_licensed         BOOLEAN                     NOT NULL,
    country_iso_code    VARCHAR(2),
    main_cover_media_id UUID,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          UUID,
    updated_by          UUID,
    version             BIGINT,
    CONSTRAINT pk_titles PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-14
CREATE TABLE user_collections
(
    id          UUID                        NOT NULL,
    author_id   UUID                        NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description TEXT,
    is_public   BOOLEAN                     NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    version     BIGINT,
    CONSTRAINT pk_user_collections PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-15
CREATE TABLE user_interactions
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id     UUID                                    NOT NULL,
    type        VARCHAR(255)                            NOT NULL,
    target_id   UUID                                    NOT NULL,
    metadata    JSONB,
    occurred_at TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    CONSTRAINT pk_user_interactions PRIMARY KEY (id)
);

-- changeset harakki:1777647337557-16
CREATE TABLE user_tag_profiles
(
    score      DOUBLE PRECISION            NOT NULL,
    count      INTEGER                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    user_id    UUID                        NOT NULL,
    tag_id     UUID                        NOT NULL,
    CONSTRAINT pk_user_tag_profiles PRIMARY KEY (user_id, tag_id)
);

-- changeset harakki:1777647337557-17
ALTER TABLE title_authors
    ADD CONSTRAINT uc_717ac20bfc36a13de70651a84 UNIQUE (title_id, author_id, role);

-- changeset harakki:1777647337557-18
ALTER TABLE authors
    ADD CONSTRAINT uc_authors_slug UNIQUE (slug);

-- changeset harakki:1777647337557-19
ALTER TABLE chapters
    ADD CONSTRAINT uc_chapter_title_number_subnumber UNIQUE (title_id, number, sub_number);

-- changeset harakki:1777647337557-20
ALTER TABLE title_publishers
    ADD CONSTRAINT uc_d3e1ba6449f92bfae68c9cb40 UNIQUE (title_id, publisher_id);

-- changeset harakki:1777647337557-21
ALTER TABLE library_entries
    ADD CONSTRAINT uc_library_user_title UNIQUE (user_id, title_id);

-- changeset harakki:1777647337557-22
ALTER TABLE publishers
    ADD CONSTRAINT uc_publishers_name UNIQUE (name);

-- changeset harakki:1777647337557-23
ALTER TABLE publishers
    ADD CONSTRAINT uc_publishers_slug UNIQUE (slug);

-- changeset harakki:1777647337557-24
ALTER TABLE tags
    ADD CONSTRAINT uc_tags_name UNIQUE (name);

-- changeset harakki:1777647337557-25
ALTER TABLE tags
    ADD CONSTRAINT uc_tags_slug UNIQUE (slug);

-- changeset harakki:1777647337557-26
ALTER TABLE titles
    ADD CONSTRAINT uc_titles_slug UNIQUE (slug);

-- changeset harakki:1777647337557-27
CREATE INDEX idx_chapter_number ON chapters (number);

-- changeset harakki:1777647337557-28
CREATE INDEX idx_chapter_title_id ON chapters (title_id);

-- changeset harakki:1777647337557-29
CREATE INDEX idx_collection_author ON user_collections (author_id);

-- changeset harakki:1777647337557-30
CREATE UNIQUE INDEX idx_collection_author_name ON user_collections (author_id, name);

-- changeset harakki:1777647337557-31
CREATE INDEX idx_interactions_type ON user_interactions (type);

-- changeset harakki:1777647337557-32
CREATE INDEX idx_interactions_user_target ON user_interactions (user_id, target_id);

-- changeset harakki:1777647337557-33
CREATE INDEX idx_library_title ON library_entries (title_id);

-- changeset harakki:1777647337557-34
CREATE INDEX idx_library_user ON library_entries (user_id);

-- changeset harakki:1777647337557-35
CREATE INDEX idx_library_user_status ON library_entries (user_id, status);

-- changeset harakki:1777647337557-37
CREATE INDEX idx_title_created_at ON titles (created_at);

-- changeset harakki:1777647337557-39
CREATE INDEX idx_title_release_year ON titles (release_year);

-- changeset harakki:1777647337557-40
CREATE INDEX idx_title_status ON titles (title_status);

-- changeset harakki:1777647337557-41
CREATE INDEX idx_user_tag_profiles_user_id ON user_tag_profiles (user_id);

-- changeset harakki:1777647337557-42
ALTER TABLE chapter_pages
    ADD CONSTRAINT FK_CHAPTER_PAGES_ON_CHAPTER FOREIGN KEY (chapter_id) REFERENCES chapters (id);

-- changeset harakki:1777647337557-43
ALTER TABLE title_authors
    ADD CONSTRAINT FK_TITLE_AUTHORS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES authors (id);
CREATE INDEX idx_title_author_author_id ON title_authors (author_id);

-- changeset harakki:1777647337557-44
ALTER TABLE title_authors
    ADD CONSTRAINT FK_TITLE_AUTHORS_ON_TITLE FOREIGN KEY (title_id) REFERENCES titles (id);

-- changeset harakki:1777647337557-45
ALTER TABLE title_publishers
    ADD CONSTRAINT FK_TITLE_PUBLISHERS_ON_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES publishers (id);
CREATE INDEX idx_title_publishers_publisher_id ON title_publishers (publisher_id);

-- changeset harakki:1777647337557-46
ALTER TABLE title_publishers
    ADD CONSTRAINT FK_TITLE_PUBLISHERS_ON_TITLE FOREIGN KEY (title_id) REFERENCES titles (id);

-- changeset harakki:1777647337557-47
ALTER TABLE collection_titles
    ADD CONSTRAINT fk_collection_titles_on_collection FOREIGN KEY (collection_id) REFERENCES user_collections (id);

-- changeset harakki:1777647337557-48
ALTER TABLE title_tags
    ADD CONSTRAINT fk_tittag_on_tag FOREIGN KEY (tag_id) REFERENCES tags (id);

-- changeset harakki:1777647337557-49
ALTER TABLE title_tags
    ADD CONSTRAINT fk_tittag_on_title FOREIGN KEY (title_id) REFERENCES titles (id);

-- changeset harakki:1777647337557-50
CREATE TABLE event_publication
(
    id                     UUID                        NOT NULL,
    completion_attempts    INTEGER                     NOT NULL,
    completion_date        TIMESTAMP(6) WITH TIME ZONE,
    event_type             VARCHAR(255)                NOT NULL,
    last_resubmission_date TIMESTAMP(6) WITH TIME ZONE,
    listener_id            VARCHAR(255)                NOT NULL,
    publication_date       TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    serialized_event       VARCHAR(255)                NOT NULL,
    status                 VARCHAR(255),
    CONSTRAINT pk_event_publication PRIMARY KEY (id),
    CONSTRAINT event_publication_status_check CHECK ((status)::TEXT = ANY
                                                     ((ARRAY ['PUBLISHED'::CHARACTER VARYING, 'PROCESSING'::CHARACTER VARYING, 'COMPLETED'::CHARACTER VARYING, 'FAILED'::CHARACTER VARYING, 'RESUBMITTED'::CHARACTER VARYING])::TEXT[]))
);
