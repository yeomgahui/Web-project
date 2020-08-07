create table product(
                        id varchar(20) not null primary key,
                        store varchar(20) not null,
                        category varchar(20) not null,
                        productName varchar(20) not null,
                        productPrice number not null,
                        productQty number not null,
                        productContent varchar(2000),
                        image varchar(2000),
);

CREATE SEQUENCE itemSEQ
    START WITH 1
    INCREMENT BY 1;



insert into product values(itemSEQ.nextval, 'emart','fruit','apple',1300 , 3, '맛있는 빨간 사과','c:/???') ;
insert into product values(itemSEQ.nextval, 'emart','fruit','banana',2000 , 2, '싱싱한 노란 바나나','c:/???') ;
insert into product values(itemSEQ.nextval, 'emart','dairy','cheese',3400 , 7, '치즈 치즈','c:/???') ;


insert into product values(itemSEQ.nextval, 'lotte','fruit','apple',1300 , 5, '맛있는 빨간 사과','c:/???' );
insert into product values(itemSEQ.nextval, 'lotte','meat','pork',6700 , 5, '삼겹살','c:/???' );
insert into product values(itemSEQ.nextval, 'lotte','meat','beef',9300 , 5, '안심','c:/???' );
insert into product values(itemSEQ.nextval, 'lotte','dairy','cheese',3400 , 7, '치즈 치즈','c:/???') ;

insert into product values(itemSEQ.nextval, 'homeplus','fruit','apple',1300 , 7, '맛있는 빨간 사과','c:/???') ;
insert into product values(itemSEQ.nextval, 'homeplus','meat','beef',9300 , 5, '안심','c:/???' );
insert into product values(itemSEQ.nextval, 'homeplus','dairy','cheese',3400 , 7, '치즈 치즈','c:/???') ;
