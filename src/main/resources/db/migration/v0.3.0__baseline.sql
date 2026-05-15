CREATE TABLE households (
    id   VARCHAR(36)  NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id           VARCHAR(36)  NOT NULL,
    provider     VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    displayName  VARCHAR(255) NOT NULL DEFAULT '',
    household_id VARCHAR(36),
    PRIMARY KEY (id),
    CONSTRAINT fk_users_household FOREIGN KEY (household_id) REFERENCES households (id)
);

CREATE TABLE recipes (
    id           VARCHAR(36)  NOT NULL,
    name         VARCHAR(255) NOT NULL,
    tags         TEXT         NOT NULL,
    household_id VARCHAR(36)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_recipes_household FOREIGN KEY (household_id) REFERENCES households (id)
);

CREATE TABLE recipe_lists (
    id           VARCHAR(36)  NOT NULL,
    name         VARCHAR(255) NOT NULL,
    created      DATETIME     NOT NULL,
    household_id VARCHAR(36)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_recipe_lists_household FOREIGN KEY (household_id) REFERENCES households (id)
);

CREATE TABLE recipe_list_liked_recipes (
    recipe_list_id VARCHAR(36) NOT NULL,
    recipe_id      VARCHAR(36) NOT NULL,
    PRIMARY KEY (recipe_list_id, recipe_id),
    CONSTRAINT fk_liked_recipe_list FOREIGN KEY (recipe_list_id) REFERENCES recipe_lists (id),
    CONSTRAINT fk_liked_recipe      FOREIGN KEY (recipe_id)      REFERENCES recipes (id)
);

CREATE TABLE recipe_list_disliked_recipes (
    recipe_list_id VARCHAR(36) NOT NULL,
    recipe_id      VARCHAR(36) NOT NULL,
    PRIMARY KEY (recipe_list_id, recipe_id),
    CONSTRAINT fk_disliked_recipe_list FOREIGN KEY (recipe_list_id) REFERENCES recipe_lists (id),
    CONSTRAINT fk_disliked_recipe      FOREIGN KEY (recipe_id)      REFERENCES recipes (id)
);
