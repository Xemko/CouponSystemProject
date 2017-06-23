Create table company(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(25), password varchar(10), email varchar(30));

Create table customer(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(25), password varchar(10));

Create table coupon(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), title varchar(25), start_date date, end_date date, expired varchar (10), type varchar (25), message varchar (150), price int, image varchar (100));

Create table custcoupon(CustomerID int, CouponID int, CouponAmount int);

Create table compcoupon(CompanyID int, CouponID int, CouponAmount int);

Insert into company(name, password, email) values('companyA', '123', 'emailA@mail.com'); 
Insert into company(name, password, email) values('companyB', '234', 'emailB@mail.com');
Insert into company(name, password, email) values('companyC', '345', 'emailC@mail.com');
Insert into company(name, password, email) values('companyD', '678', 'emailD@mail.com');
Insert into company(name, password, email) values('companyE', '648', 'GmailD@mail.com');



Insert into customer(name, password) values('customerA', '12345');		
Insert into customer(name, password) values('customerB', '23456');		
Insert into customer(name, password) values('customerC', '34567');
Insert into customer(name, password) values('customerD', '12365');
Insert into customer(name, password) values('customerE', '09876');


Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponA', '2016-01-01', '2016-12-31', 'no', 'Food', 'Bakery sales 50 percent off', 50, 'image1');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponB', '2016-12-01', '2018-06-30', 'no', 'Alcohol', 'Wine sale for dedicated customers', 120, 'image2');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponC', '2016-10-01', '2018-05-30', 'no', 'Video', 'Classic movies from 80s', 15, 'image3');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponD', '2016-10-01', '2018-05-30', 'no', 'Games', 'Third person shooter', 25, 'image4');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponE', '2016-10-01', '2018-04-30', 'no', 'Travel', 'Flight ticket', 200, 'image5');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponF', '2016-10-01', '2018-03-30', 'no', 'Study', 'Java for beginners', 75, 'image6');
Insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('couponG', '2017-02-01', '2020-03-30', 'no', 'XXX', 'Adult', 400, 'image7');


Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(1,1,5);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(1,2,5);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(4,3,5);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(3,4,5);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(3,6,5);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(2,5,10);
Insert into compcoupon(CompanyID, CouponID, CouponAmount) values(5,5,10);





Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(1,1,1);
Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(2,2,1);
Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(3,3,1);
Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(4,4,1);
Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(5,6,1);
Insert into custcoupon(CustomerID, CouponID, CouponAmount) values(5,7,1);



