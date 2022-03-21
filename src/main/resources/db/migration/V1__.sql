CREATE TABLE role  (
  id char(36) NOT NULL,
  name VARCHAR(255) NULL,
  CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE category (
  id char(36) NOT NULL,
  name VARCHAR(255) NULL,
  CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product (
  id char(36) NOT NULL,
  name VARCHAR(255) NULL,
  description  VARCHAR(255) NULL,
  price INT NULL,
  imageName VARCHAR(255) NULL,
  category_id char(36) NULL,
  CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE cart (
  id char(36) NOT NULL,
  user_id char(36) NULL,
  CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_product (
  id char(36) NOT NULL,
  price INT NULL,
  qty INT NULL,
  cart_id char(36) NULL,
  product_id char(36) NULL,
  CONSTRAINT pk_cartproduct PRIMARY KEY (id)
);

CREATE TABLE cupons (
  id char(36) NOT NULL,
  code VARCHAR(255) NULL,
  description  VARCHAR(255) NULL,
  active BIT(1) NULL,
  orders_id char(36) NULL,
  CONSTRAINT pk_cupons PRIMARY KEY (id)
);

CREATE TABLE order_product (
  id char(36) NOT NULL,
  price INT NULL,
  qty INT NULL,
  product_id char(36) NULL,
  orders_id char(36) NULL,
  CONSTRAINT pk_orderproduct PRIMARY KEY (id)
);

CREATE TABLE orders (
  id char(36) NOT NULL,
  order_date datetime NULL,
  total INT NULL,
  user_id char(36) NULL,
  CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE personalized_jewelry (
  id char(36) NOT NULL,
  text VARCHAR(255) NULL,
  product_id char(36) NULL,
  orders_id char(36) NULL,
  CONSTRAINT pk_personalizedjewelry PRIMARY KEY (id)
);

CREATE TABLE user (
  id char(36) NOT NULL,
  name VARCHAR(255) NULL,
  surname VARCHAR(255) NULL,
  email VARCHAR(255) NULL,
  password VARCHAR(255) NULL,
  CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_role (
  id char(36) NOT NULL,
  user_id char(36) NULL,
  role_id char(36) NULL,
  CONSTRAINT pk_userrole PRIMARY KEY (id)
);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);
ALTER TABLE cart ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE cart_product ADD CONSTRAINT FK_CARTPRODUCT_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);
ALTER TABLE cart_product ADD CONSTRAINT FK_CARTPRODUCT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);
ALTER TABLE cupons ADD CONSTRAINT FK_CUPONS_ON_ORDERS FOREIGN KEY (orders_id) REFERENCES orders (id);
ALTER TABLE order_product ADD CONSTRAINT FK_ORDERPRODUCT_ON_ORDERS FOREIGN KEY (orders_id) REFERENCES orders (id);
ALTER TABLE order_product ADD CONSTRAINT FK_ORDERPRODUCT_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);
ALTER TABLE orders ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE personalized_jewelry ADD CONSTRAINT FK_PERSONALIZEDJEWELRY_ON_ORDERS FOREIGN KEY (orders_id) REFERENCES orders (id);
ALTER TABLE personalized_jewelry ADD CONSTRAINT FK_PERSONALIZEDJEWELRY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);
ALTER TABLE user_role ADD CONSTRAINT FK_USERROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);
ALTER TABLE user_role ADD CONSTRAINT FK_USERROLE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);
