package a.DBcreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import e.Exceptions.CouponSystemException;

public class CreateDB {

	public static void main(String[] args) throws CouponSystemException {

		// // Defining a database URL with private schema user and password
		String url = "jdbc:derby://localhost:1527/cs;create=true;user=project;password=12345";

		// Defining three main tables
		String sqlCompanyTable = "create table company(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(25), password varchar(10), email varchar(30))";
		String sqlCustomerTable = "create table customer(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name varchar(25), password varchar(10))";
		String sqlCouponTable = "create table coupon(id bigint primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), title varchar(25), start_date date, end_date date, expired varchar (10), type varchar (25), message varchar (150), price int, image varchar (100))";

		// Defining two secondary relations tables
		String sqlCustomerCoupon = "create table custcoupon(CustomerID int, CouponID int, CouponAmount int)";
		String sqlCompanyCoupon = "create table compcoupon(CompanyID int, CouponID int, CouponAmount int)";

		// Defining initial values for the Company table
		String sqlCompany1 = "insert into company(name, password, email) values('companyA', '123', 'emailA@mail.com')";
		String sqlCompany2 = "insert into company(name, password, email) values('companyB', '234', 'emailB@mail.com')";
		String sqlCompany3 = "insert into company(name, password, email) values('companyC', '345', 'emailC@mail.com')";

		// Defining initial values for the Customer table
		String sqlCustomer1 = "insert into customer(name, password) values('customerA', '12345')";
		String sqlCustomer2 = "insert into customer(name, password) values('customerB', '23456')";
		String sqlCustomer3 = "insert into customer(name, password) values('customerC', '34567')";

		// Defining initial values for the Coupon table
		String sqlCoupon1 = "insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('CouponA', '2016-01-01', '2016-12-31', 'no', 'Food', 'Bakery sales 50 percent off', 50, 'image1')";
		String sqlCoupon2 = "insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('CouponB', '2016-12-01', '2017-06-30', 'no', 'Alcohol', 'Wine sale for dedicated customers', 120, 'image2')";
		String sqlCoupon3 = "insert into coupon(title, start_date, end_date, expired, type, message, price, image) values('CouponC', '2016-10-01', '2017-05-30', 'no', 'Video', 'Classic movies from 80s', 15, 'image3')";

		// Defining initial values for secondary table CompCoupon
		String sqlCompCoup1 = "insert into compcoupon(CompanyID, CouponID, CouponAmount) values(1,1,5)";
		String sqlCompCoup2 = "insert into compcoupon(CompanyID, CouponID, CouponAmount) values(1,2,3)";
		String sqlCompCoup3 = "insert into compcoupon(CompanyID, CouponID, CouponAmount) values(1,3,2)";

		// Defining initial values for secondary table CustCoupon
		String sqlCustCoup1 = "insert into custcoupon(CustomerID, CouponID, CouponAmount) values(1,1,1)";
		String sqlCustCoup2 = "insert into custcoupon(CustomerID, CouponID, CouponAmount) values(2,2,1)";
		String sqlCustCoup3 = "insert into custcoupon(CustomerID, CouponID, CouponAmount) values(3,3,1)";

		// Defining maintenance drop tables strings - uncomment these only if
		// you want to delete existing tables
		// String sqlDropTableCompany = "drop table company";
		// String sqlDropTableCustomer = "drop table customer";
		// String sqlDropTableCoupon = "drop table coupon";
		// String sqlDropTableCustomerCoupon = "drop table custcoupon";
		// String sqlDropTableCompanyCoupon = "drop table compcoupon";

		try (Connection con = DriverManager.getConnection(url); Statement stmt = con.createStatement();) {

			// Creating three main tables
			stmt.executeUpdate(sqlCompanyTable);
			System.out.println("Company table has been created successfully!");
			stmt.execute(sqlCustomerTable);
			System.out.println("Customer table has been created successfully!");
			stmt.execute(sqlCouponTable);
			System.out.println("Coupon table has been created successfully!");
			// Creating two secondary tables
			stmt.executeUpdate(sqlCustomerCoupon);
			System.out.println("Customer-Coupon table has been created successfully!");
			stmt.execute(sqlCompanyCoupon);
			System.out.println("Company-Coupon table has been created successfully!");

			// Fill Company table with automatic ID generation - First entry
			stmt.executeUpdate(sqlCompany1, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys1 = stmt.getGeneratedKeys();
			rsKeys1.next(); // Go to the first line of ResultSet
			int compId1 = rsKeys1.getInt(1);
			System.out.println("Company inserted with ID = " + compId1);

			// Fill Company table with automatic ID generation - Second entry
			stmt.executeUpdate(sqlCompany2, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys2 = stmt.getGeneratedKeys();
			rsKeys2.next(); // Go to the first line of ResultSet
			int compId2 = rsKeys2.getInt(1);
			System.out.println("Company inserted with ID = " + compId2);

			// Fill Company table with automatic ID generation - Third entry
			stmt.executeUpdate(sqlCompany3, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys3 = stmt.getGeneratedKeys();
			rsKeys3.next(); // Go to the first line of ResultSet
			int compId3 = rsKeys3.getInt(1);
			System.out.println("Company inserted with ID = " + compId3);

			// Fill Customer table with automatic ID generation - First entry
			stmt.executeUpdate(sqlCustomer1, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys4 = stmt.getGeneratedKeys();
			rsKeys4.next(); // Go to the first line of ResultSet
			int custId1 = rsKeys4.getInt(1);
			System.out.println("Customer inserted with ID = " + custId1);

			// Fill Customer table with automatic ID generation - Second entry
			stmt.executeUpdate(sqlCustomer2, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys5 = stmt.getGeneratedKeys();
			rsKeys5.next(); // Go to the first line of ResultSet
			int custId2 = rsKeys5.getInt(1);
			System.out.println("Customer inserted with ID = " + custId2);

			// Fill Customer table with automatic ID generation - Third entry
			stmt.executeUpdate(sqlCustomer3, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys6 = stmt.getGeneratedKeys();
			rsKeys6.next(); // Go to the first line of ResultSet
			int custId3 = rsKeys6.getInt(1);
			System.out.println("Customer inserted with ID = " + custId3);

			// Fill Coupon table with automatic ID generation - First entry
			stmt.executeUpdate(sqlCoupon1, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys7 = stmt.getGeneratedKeys();
			rsKeys7.next(); // Go to the first line of ResultSet
			int coupId1 = rsKeys7.getInt(1);
			System.out.println("Coupon inserted with ID = " + coupId1);

			// Fill Coupon table with automatic ID generation - First entry
			stmt.executeUpdate(sqlCoupon2, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys8 = stmt.getGeneratedKeys();
			rsKeys8.next(); // Go to the first line of ResultSet
			int coupId2 = rsKeys8.getInt(1);
			System.out.println("Coupon inserted with ID = " + coupId2);

			// Fill Coupon table with automatic ID generation - First entry
			stmt.executeUpdate(sqlCoupon3, Statement.RETURN_GENERATED_KEYS);
			// Get the result set with the auto generated company ID
			ResultSet rsKeys9 = stmt.getGeneratedKeys();
			rsKeys9.next(); // Go to the first line of ResultSet
			int coupId3 = rsKeys9.getInt(1);
			System.out.println("Coupon inserted with ID = " + coupId3);

			// Fill secondary table Company-Coupon with predefined queries
			stmt.executeUpdate(sqlCompCoup1);
			System.out.println("Company_Coupon entry 1 has been created successfully!");
			stmt.executeUpdate(sqlCompCoup2);
			System.out.println("Company_Coupon entry 2 has been created successfully!");
			stmt.executeUpdate(sqlCompCoup3);
			System.out.println("Company_Coupon entry 3 has been created successfully!");

			// Fill secondary table Customer-Coupon with predefined queries
			stmt.executeUpdate(sqlCustCoup1);
			System.out.println("Customer_Coupon entry 1 has been created successfully!");
			stmt.executeUpdate(sqlCustCoup2);
			System.out.println("Customer_Coupon entry 2 has been created successfully!");
			stmt.executeUpdate(sqlCustCoup3);
			System.out.println("Customer_Coupon entry 3 has been created successfully!");

			// Drop tables execution for maintenance purposes - uncomment these
			// only if you want to delete existing tables
			// stmt.executeUpdate(sqlDropTableCompany);
			// System.out.println("Company table has been dropped
			// successfully!");
			// stmt.executeUpdate(sqlDropTableCustomer);
			// System.out.println("Customer table has been dropped
			// successfully!");
			// stmt.executeUpdate(sqlDropTableCoupon);
			// System.out.println("Coupon table has been dropped
			// successfully!");
			// stmt.executeUpdate(sqlDropTableCustomerCoupon);
			// System.out.println("Customer-Coupon table has been dropped
			// successfully!");
			// stmt.executeUpdate(sqlDropTableCompanyCoupon);
			// System.out.println("Company-Coupon table has been dropped
			// successfully!");

		} catch (SQLException e) {
			throw new CouponSystemException("Database initialization has been failed - see console output for details",
					e);
		}

	}

}
