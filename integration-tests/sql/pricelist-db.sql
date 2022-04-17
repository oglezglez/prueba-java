create table pricelist (
    price_list varchar(255) not null, 
    brand_id varchar(255), 
    curr varchar(255), 
    end_date timestamp, 
    price double, 
    priority integer, 
    product_id varchar(255), 
    start_date timestamp, 
    primary key (price_list));
INSERT INTO PRICELIST (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES ('1', {ts '2020-06-14 00:00:00.00'}, {ts '2020-12-31 23:59:59.99'},'1','35455',0,35.50,'EUR');
INSERT INTO PRICELIST (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES ('1', {ts '2020-06-14 15:00:00.00'}, {ts '2020-06-14 18:30:00.00'},'2','35455',1,25.45,'EUR');
INSERT INTO PRICELIST (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES ('1', {ts '2020-06-15 00:00:00.00'}, {ts '2020-06-15 11:00:00.00'},'3','35455',1,30.50,'EUR');
INSERT INTO PRICELIST (BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURR)
VALUES ('1', {ts '2020-06-15 16:00:00.00'}, {ts '2020-12-31 23:59:59.99'},'4','35455',1,38.95,'EUR');
