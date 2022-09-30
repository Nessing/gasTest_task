CREATE TABLE names
(
    id          bigserial primary key,
    first_name  VARCHAR(255),
    middle_name VARCHAR(255),
    last_name   VARCHAR(255)
);

CREATE TABLE paths
(
    id          bigserial primary key,
    path        VARCHAR(255)
);

CREATE TABLE intersections
(
    id          bigserial primary key,
    names_id    bigserial references names (id),
    paths_id    bigserial references paths (id)
);

CREATE TABLE intersections_names
(
    intersection_id   bigserial references intersections (id),
    name_id           bigserial references names (id)
);

CREATE TABLE results
(
    id                  bigserial primary key,
    intersection_id     bigserial references intersections (id),
    state               VARCHAR(50)
);

CREATE TABLE intersections_results
(
    result_id           bigserial references results (id),
    intersection_id     bigserial references intersections (id)
)
