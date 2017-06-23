package d.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import b.ConnectionPool.ConnectionPool;
import c.Beans.Company;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CompanyDAOdb implements CompanyDAO {

	public CompanyDAOdb() {
		super();
	}

	@Override
	/**
	 * Accepts predefined Company object and writes it to Company table in the
	 * database
	 */
	public int createCompany(Company company) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		int compID = -1;
		try {
			// // Checking if Company name already exists in DB
			// String nameExist = "SELECT * FROM Company where name = ?";
			// PreparedStatement pstmt1 = con.prepareStatement(nameExist);
			// pstmt1 = con.prepareStatement(nameExist);
			// pstmt1.setString(1, company.getName());
			// ResultSet result = pstmt1.executeQuery();
			// if (result.next()) {
			// System.out.println("Company name already exists in Database,
			// please choose another one.");
			// } else {

			// Defining SQL string to insert Company via prepared statement
			String createCompanySQL = "insert into company (name, password, email) values (?,?,?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(createCompanySQL);
			// Fetching variables into SQL string via Company bean
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getPassword());
			pstmt.setString(3, company.getEmail());
			// Executing prepared statement
			pstmt.executeUpdate();

			// Adding Company ID to the object
			String getCompanyID = "select * from company where name = ?";
			PreparedStatement pstmt2 = con.prepareStatement(getCompanyID);
			pstmt2.setString(1, company.getName());
			ResultSet getID = pstmt2.executeQuery();
			getID.next();
			compID = getID.getInt("id");
			company.setId(compID);

			// Writing success confirmation to the console
//			System.out.println("Company has been added successfully : " + company);

			// }
		} catch (SQLException e) {
			// Handling SQL exception during Company writing into database
			throw new CouponSystemException("SQL error - Company creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		return compID;
	}

	@Override
	/**
	 * Accepts predefined Company object and deletes described entry from the
	 * Company table in the database
	 */
	public void removeCompany(Company company) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to delete Company via prepared statement
			String removeCompanySQL = "delete from company where name = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(removeCompanySQL);
			// Fetching variable into SQL string via Company bean
			pstmt.setString(1, company.getName());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Company has been removed successfully:" + company);
			} else {
				// Otherwise execution failed due to Company not found in the
				// database
				throw new CouponSystemException("Company removal has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company removal from the database
			throw new CouponSystemException("SQL error - Company remove has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/**
	 * Accepts predefined Company object and updates entry with relevant ID in
	 * the database with values from accepted Company object
	 */
	public void updateCompany(Company company) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to update Company via prepared statement
			String updateCompanySQL = "update company set password=?, email=? where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(updateCompanySQL);
			// Fetching variables into SQL string via Company bean plus ID as
			// method accepted variable
			pstmt.setString(1, company.getPassword());
			pstmt.setString(2, company.getEmail());
			pstmt.setInt(3, company.getId());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();

			// Adding Company ID to the object
			// company.setId(id);

			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Company has been updated successfully:" + company);
			} else {
				// Otherwise execution failed due to Company not found in the
				// database
				System.out.println("Company update has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company update in the database
			throw new CouponSystemException("SQL error - Company update has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/** Accepts Company ID and returns related Company object */
	public Company getCompany(int id) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Creating Company object to fill it later with data from SQL query
		// result and for method to return it afterwards
		Company company = new Company();

		try {
			// Defining SQL string to retrieve Company via prepared statement
			String getCompanySQL = "select * from company where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCompanySQL);
			// Putting method accepted ID into SQL string
			pstmt.setInt(1, id);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resComp = pstmt.executeQuery();
			// If query result has data (if Company has been found) do
			if (resComp.next()) {
				// Set Company object ID to ID variable that method received
				company.setId(id);
				// Set Company attributes according to the results in the
				// ResultSet
				company.setName(resComp.getString("name"));
				company.setPassword(resComp.getString("password"));
				company.setEmail(resComp.getString("email"));
				// Write retrieval success confirmation to the console
				System.out.println("Company has been found successfully:" + company);
			} else {
				// If query result is empty (Company hasn't been found) throw
				// exception
				throw new CouponSystemException("Company retrieve has been failed - Company ID not found");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company retrieval from the database
			throw new CouponSystemException("SQL error - Company retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		// Return Company object
		return company;
	}

	@Override
	/** Returns all Companies as an array list */
	public Collection<Company> getAllCompanies() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Companies
		Collection<Company> colComp = new ArrayList<>();

		try {
			// Defining SQL string to retrieve all Companies via prepared
			// statement
			String getAllCompaniesSQL = "select * from company";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getAllCompaniesSQL);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resComp = pstmt.executeQuery();
			// While there is result line in resComp do lines below
			while (resComp.next()) {
				// Creating Company object to fill it later with data from SQL
				// query result and for method to add it to the list and return
				// it afterwards
				Company company = new Company();
				// Set Company attributes according to the results in the
				// ResultSet
				company.setId(resComp.getInt("id"));
				company.setName(resComp.getString("name"));
				company.setPassword(resComp.getString("password"));
				company.setEmail(resComp.getString("email"));
				// Add resulting Company to the list
				colComp.add(company);
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company retrieval from the database
			throw new CouponSystemException("SQL error - Company list retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return resulted list of Companies
		return colComp;
	}

	@Override
	/**
	 * Returns all Coupons related to Company with ID entered as an array list
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
			String getCouponsSQL = "select * from coupon where id in (select couponid from compcoupon where companyid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsSQL);
			// Set Company object ID to ID variable that method received
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
	 * Checks if Company+Password sequence exists in the database and returns
	 * Company ID
	 */
	public int login(String companyName, String password) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Defining login initial state as non-valid Company ID
		int loginID = -1;

		try {
			// Defining SQL string to retrieve Company with selected name and
			// password via prepared statement
			String companyCredentialsSQL = "select * from company where name = ? and password = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(companyCredentialsSQL);
			// Putting method accepted Company name and password into SQL string
			pstmt.setString(1, companyName);
			pstmt.setString(2, password);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resComp = pstmt.executeQuery();
			// If query result has data (if Company has been found) do
			if (resComp.next()) {
				// Set found Company ID to loginID variable to return
				loginID = resComp.getInt("id");
				// Write login success confirmation to the console
				System.out.println("Login successful! Company ID is: " + loginID);
			} else {
				// Otherwise if query result is empty (Company hasn't been
				// found) set login state as failed;
				loginID = -1;
				// Write login failure confirmation to the console
				throw new CouponSystemException("Login failed!");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company retrieval from the database
			throw new CouponSystemException("SQL error - Credentials retrieve has failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return ID of Company logged in
		return loginID;
	}

	@Override
	public boolean companyNameExists(Company company) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		boolean exists = false;
		try {
			// Checking if Company name already exists in DB
			String nameExist = "SELECT * FROM Company where name = ?";
			PreparedStatement pstmt1 = con.prepareStatement(nameExist);
			pstmt1 = con.prepareStatement(nameExist);
			pstmt1.setString(1, company.getName());
			ResultSet result = pstmt1.executeQuery();
			if (result.next()) {
				System.out.println("Company name already exists in Database, please choose another one.");
				exists = true;
			}
		} catch (SQLException e) {
			// Handling SQL exception during Company writing into database
			throw new CouponSystemException("SQL error - Company creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		return exists;
	}
}
