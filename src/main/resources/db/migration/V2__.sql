
ALTER TABLE product_personalized_jewelries DROP FOREIGN KEY FK1av7lwx7rci8g1xeus3sh5tj1;

ALTER TABLE cart_pers DROP FOREIGN KEY FK4s37973op9m55kvcy22si14xw;

ALTER TABLE personalized_jewelry DROP FOREIGN KEY FK8iist0r4e9jbhw5r8pwf33pha;

ALTER TABLE product_personalized_jewelries DROP FOREIGN KEY FKlfg1vlc2l9fxrkfgbhmv9ftnj;

DROP TABLE cupons;

DROP TABLE product_personalized_jewelries;

ALTER TABLE cart_pers DROP COLUMN pers_id;

ALTER TABLE personalized_jewelry DROP COLUMN product_id;