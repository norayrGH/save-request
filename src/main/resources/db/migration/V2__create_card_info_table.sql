CREATE TABLE "card_info"
(
    id                          BIGSERIAL    NOT NULL
             CONSTRAINT card_info_pkey PRIMARY KEY,
    card_name varchar (255),
    card_number varchar (16),
    valid_month varchar (2),
    valid_year varchar (4),
    cvc2 varchar (8)

);