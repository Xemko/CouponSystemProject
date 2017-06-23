package d.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import b.ConnectionPool.ConnectionPool;
import c.Beans.Customer;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CustomerDAOdb implements CustomerDAO {

	public CustomerDAOdb() {
		super();
	}

	@Override
	/**
	 * Accepts predefined Customer object and writes it to Customer table in the
	 * database
	 */
	public int createCustomer(Customer customer) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		int custID = -1;
		try {
			// // Check if Customer name already exists in DB
			// String nameExist = "SELECT * FROM Customer where name = ?";
			// PreparedStatement pstmt1 = con.prepareStatement(nameExist);
			// pstmt1 = con.prepareStatement(nameExist);
			// pstmt1.setString(1, customer.getName());
			// ResultSet result = pstmt1.executeQuery();
			// if (result.next()) {
			// System.out.println("Customer name already exists in Database,
			// please choose another one.");
			// } else {

			// Defining SQL string to insert Customer via prepared statement
			String createCustomerSQL = "insert into customer (name, password) values (?,?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(createCustomerSQL);
			// Fetching variables into SQL string via Customer bean
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getPassword());
			// Executing prepared statement
			pstmt.executeUpdate();
			String gettingCustID = "select * from Customer where name = ?";

			PreparedStatement pstmt3 = con.prepareStatement(gettingCustID);
			pstmt3.setString(1, customer.getName());
			ResultSet result1 = pstmt3.executeQuery();
			if (result1.next()) {
				custID = result1.getInt("id");
				customer.setId(custID);
			}

			// Writing success confirmation to the console
			// System.out.println("Customer has been added successfully:" +
			// customer);
			// }
		} catch (SQLException e) {
			// Handling SQL exception during Customer writing into database
			throw new CouponSystemException("SQL error - Customer creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		return custID;
	}

	@Override
	/**
	 * Accepts predefined Customer object and deletes described entry from the
	 * Customer table in the database
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to delete Customer via prepared statement
			String removeCustomerSQL = "delete from customer where name = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(removeCustomerSQL);
			// Fetching variable into SQL string via Customer bean
			pstmt.setString(1, customer.getName());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Customer has been removed successfully:" + customer);
			} else {
				// Otherwise execution failed due to Customer not found in the
				// database
				throw new CouponSystemException("Customer removal has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer removal from the database
			throw new CouponSystemException("SQL error - Customer remove has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/**
	 * Accepts predefined Customer object and updates entry with relevant ID in
	 * the database with values from accepted Customer object
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to update Customer via prepared statement

			String updateCustomerSQL = "update customer set password=? where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(updateCustomerSQL);
			// Fetching variables into SQL string via Customer bean plus ID as
			// method accepted variable

			pstmt.setString(1, customer.getPassword());
			pstmt.setInt(2, customer.getId());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Customer has been updated successfully:" + customer);
			} else {
				// Otherwise execution failed due to Customer not found in the
				// database
				System.out.println("Customer update has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer update in the database
			throw new CouponSystemException("SQL error - Customer update has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/** Accepts Customer ID and returns related Customer object */
	public Customer getCustomer(int id) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Creating Customer object to fill it later with data from SQL query
		// result and for method to return it afterwards
		Customer customer = new Customer();

		try {
			// Defining SQL string to retrieve Customer via prepared statement
			String getCustomerSQL = "select * from customer where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCustomerSQL);
			// Putting method accepted ID into SQL string
			pstmt.setInt(1, id);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCust = pstmt.executeQuery();
			// If query result has data (if Customer has been found) do
			if (resCust.next()) {
				// Set Customer object ID to ID variable that method received
				customer.setId(id);
				// Set Customer attributes according to the results in the
				// ResultSet
				customer.setName(resCust.getString("name"));
				customer.setPassword(resCust.getString("password"));
				// Write retrieval success confirmation to the console
				System.out.println("Customer has been found successfully:" + customer);
			} else {
				// If query result is empty (Customer hasn't been found) throw
				// exception
				throw new CouponSystemException("Customer retrieve has been failed - Customer ID not found");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer retrieval from the
			// database
			throw new CouponSystemException("SQL error - Customer retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return Customer
		return customer;
	}

	@Override
	/** Returns all Customer as an array list */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Customers
		Collection<Customer> colCust = new ArrayList<>();

		try {
			// Defining SQL string to retrieve all Customers via prepared
			// statement
			String getAllCustomersSQL = "select * from customer";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getAllCustomersSQL);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCust = pstmt.executeQuery();
			// While there is result line in resCust do lines below
			while (resCust.next()) {
				// Creating Customer object to fill it later with data from SQL
				// query result and for method to add it to the list and return
				// it afterwards
				Customer customer = new Customer();
				// Set Customer attributes according to the results in the
				// ResultSet
				customer.setId(resCust.getInt("id"));
				customer.setName(resCust.getString("name"));
				customer.setPassword(resCust.getString("password"));
				// Add resulting Customer to the list
				colCust.add(customer);
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer retrieval from the
			// database
			throw new CouponSystemException("SQL error - Customer list retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return resulted list of Customers
		return colCust;
	}

	@Override
	/**
	 * Returns all Coupons related to Customer with ID entered as an array list
	 */
	public Collection<Coupon> getCoupons(int id) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupons with nested condition via
			// prepared statement
			String getCouponsSQL = "select * from coupon where id in (select couponid from custcoupon where customerid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsSQL);
			// Set Customer object ID to ID variable that method received
			pstmt.setInt(1, id);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCoup = pstmt.executeQuery();
			// While there is result line in resCoup do lines below
			while (resCoup.next()) {
				// Creating Coupon object to fill it later with data from SQL
				// query result and for method to add it to the list and return
				// it afterwards
				Coupon coupon = new Coupon();
				// Set Coupon attributes according to the results in the
				// ResultSet
				coupon.setId(resCoup.getInt("id"));
				coupon.setTitle(resCoup.getString("title"));
				coupon.setStart_date(resCoup.getDate("start_date"));
				coupon.setEnd_date(resCoup.getDate("end_date"));
				coupon.setExpired(resCoup.getString("expired"));
				coupon.setType(resCoup.getString("type"));
				coupon.setMessage(resCoup.getString("message"));
				coupon.setPrice(resCoup.getInt("price"));
				coupon.setImage(resCoup.getString("image"));
				// Add resulting Coupon to the list
				colCoup.add(coupon);
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon retrieval from the database
			throw new CouponSystemException("SQL error - Coupon list retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return resulted list of Coupons
		return colCoup;
	}

	@Override
	/**
	 * Checks if Customer+Password sequence exists in the database and returns
	 * Customer ID
	 */
	public int login(String customerName, String password) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Defining login initial state as non-valid Customer ID
		int loginID = -1;

		try {
			// Defining SQL string to retrieve Customer with selected name and
			// password via prepared statement
			String customerCredentialsSQL = "select * from customer where name = ? and password = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(customerCredentialsSQL);
			// Putting method accepted Customer name and password into SQL
			// string
			pstmt.setString(1, customerName);
			pstmt.setString(2, password);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCust = pstmt.executeQuery();
			// If query result has data (if Customer has been found) do
			if (resCust.next()) {
				// Set found Customer ID to loginID variable to return
				loginID = resCust.getInt("id");
				// Write login success confirmation to the console
				System.out.println("Login successful! Customer ID is: " + loginID);
			} else {
				// Otherwise if query result is empty (Customer hasn't been
				// found) set login state as failed;
				loginID = -1;
				// Write login failure confirmation to the console
				throw new CouponSystemException("Login failed!");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer retrieval from the
			// database
			throw new CouponSystemException("SQL error - Credentials retrieve has failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return ID of Customer logged in
		return loginID;
	}

	@Override
	public boolean customerNameExists(Customer customer) throws CouponSystemException {
		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		boolean exists = false;
		try {
			// Checking if Customer name already exists in DB
			String nameExist = "SELECT * FROM Customer where name = ?";
			PreparedStatement pstmt1 = con.prepareStatement(nameExist);
			pstmt1 = con.prepareStatement(nameExist);
			pstmt1.setString(1, customer.getName());
			ResultSet result = pstmt1.executeQuery();
			if (result.next()) {
				System.out.println("Customer name already exists in Database, please choose another one.");
				exists = true;
			}
		} catch (SQLException e) {
			// Handling SQL exception during Customer writing into database
			throw new CouponSystemException("SQL error - Customer creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		return exists;
	}

}
