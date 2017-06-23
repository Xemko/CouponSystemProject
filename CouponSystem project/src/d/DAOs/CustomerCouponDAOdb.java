package d.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import b.ConnectionPool.ConnectionPool;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CustomerCouponDAOdb {

	private CouponDAOdb couponDAOdb = new CouponDAOdb();

	public CustomerCouponDAOdb() {
		super();
	}

	/**
	 * Accepts Customer ID, Coupon ID and amount of Coupons and writes this data
	 * to Customer-Coupon secondary table in the database
	 */
	public void linkCoupon(int custID, int coupID, int amount) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to link Coupon to Customer via prepared
			// statement
			String linkCouponSQL = "insert into custcoupon (customerid, couponid, couponamount) values (?,?,?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(linkCouponSQL);
			// Fetching variables into SQL string from method input
			pstmt.setInt(1, custID);
			pstmt.setInt(2, coupID);
			pstmt.setInt(3, amount);
			// Executing prepared statement
			pstmt.executeUpdate();
			// Writing success confirmation to the console
			System.out.println("Coupon has been linked successfully!");

		} catch (SQLException e) {
			// Handling SQL exception during data writing into database
			throw new CouponSystemException("SQL error - Coupon linking has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	/**
	 * Removes all expired Coupons from the Customer-Coupon secondary table in
	 * the database
	 */
	public void deleteExpiredCoupons() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove expired Coupons from the
			// Customer-Coupon secondary table via prepared statement
			String deleteExpiredCouponsCustCoupSQL = "delete from custcoupon where couponid in (select id from coupon where expired = 'expired')";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteExpiredCouponsCustCoupSQL);
			// Executing prepared statement and using its result for
			// post-execute check
			int resExpired = pstmt.executeUpdate();
			if (resExpired != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Expired coupons have been deleted from Customer-Coupon table successfully");
			} else {
				// Otherwise execution failed due to expired Coupons not found
				// in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	/**
	 * Removes all Coupons from the Customer-Coupon table in the database
	 * related to Company ID received
	 */
	public void deleteCouponsByCompanyID(int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove Coupons related to specified
			// Company ID from the Customer-Coupon secondary table via prepared
			// statement
			String deleteCouponsByCompanyIDSQL = "delete from custcoupon where couponid in (select couponid from compcoupon where companyid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCompanyIDSQL);
			// Set Company ID from variable that method received
			pstmt.setInt(1, compID);
			// Executing prepared statement and using its result for
			// post-execute check
			int resRemByComp = pstmt.executeUpdate();
			if (resRemByComp != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println(
						"Coupons of specified Company have been deleted from Customer-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons related to
				// specified Company not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	/**
	 * Removes all Coupons from the Customer-Coupon table in the database
	 * related to Customer ID received
	 */
	public void deleteCouponsByCustomerID(int custID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove Coupons related to specified
			// Customer ID from the Customer-Coupon secondary table via prepared
			// statement
			String deleteCouponsByCustomerIDSQL = "delete from custcoupon where customerid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCustomerIDSQL);
			// Set Customer ID from variable that method received
			pstmt.setInt(1, custID);
			// Executing prepared statement and using its result for
			// post-execute check
			int resRemByCust = pstmt.executeUpdate();
			if (resRemByCust != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println(
						"Coupons of specified Customer have been deleted from Customer-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons related to
				// specified Customer not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	/**
	 * Removes all Coupons of selected ID from the Customer-Coupon table in the
	 * database
	 */
	public void deleteCouponsByID(int coupID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove Coupons of defined ID from the
			// Customer-Coupon secondary table via prepared statement
			String deleteCouponsByIDSQL = "delete from custcoupon where couponid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByIDSQL);
			// Set Coupon ID from variable that method received
			pstmt.setInt(1, coupID);
			// Executing prepared statement and using its result for
			// post-execute check
			int resRemByID = pstmt.executeUpdate();
			if (resRemByID != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Coupons of specified ID have been deleted from Customer-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons of specified ID not
				// found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	/** Returns all Coupons purchased by specified Customer */
	public Collection<Coupon> getPurchasedCouponsByCustomer(int custID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupon IDs via prepared statement
			String getCouponIDsByCustomerSQL = "select couponid from custcoupon where customerid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponIDsByCustomerSQL);
			// Set Customer ID variable that method received into prepared
			// statement
			pstmt.setInt(1, custID);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCustCoup1 = pstmt.executeQuery();
			// While there is result line in resCustCoup1 do lines below
			while (resCustCoup1.next()) {
				// Creating and defining Coupon object as per Coupon ID from
				// ResultSet and for method to add it to the list and return it
				// afterwards
				Coupon coupon = new Coupon();
				coupon = couponDAOdb.getCoupon(resCustCoup1.getInt("couponid"));
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

	/** Returns all Coupons of specified type purchased by specified Customer */
	public Collection<Coupon> getPurchasedCouponsByCustomerByType(int custID, String coupType)
			throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupon IDs via prepared statement
			String getCouponIDsByCustomerByTypeSQL = "select couponid from custcoupon where customerid = ? and couponid in (select id from coupon where type = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponIDsByCustomerByTypeSQL);
			// Set Customer ID and Coupon type variables that method received
			// into prepared statement
			pstmt.setInt(1, custID);
			pstmt.setString(2, coupType);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCustCoup2 = pstmt.executeQuery();
			// While there is result line in resCustCoup2 do lines below
			while (resCustCoup2.next()) {
				// Creating and defining Coupon object as per Coupon ID from
				// ResultSet and for method to add it to the list and return it
				// afterwards
				Coupon coupon = new Coupon();
				coupon = couponDAOdb.getCoupon(resCustCoup2.getInt("couponid"));
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

	/**
	 * Returns all Coupons of specified price and below purchased by specified
	 * Customer
	 */
	public Collection<Coupon> getPurchasedCouponsByCustomerByPrice(int custID, int price) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupon IDs via prepared statement
			String getCouponIDsByCustomerByPriceSQL = "select couponid from custcoupon where customerid = ? and couponid in (select id from coupon where price < ? order by price desc)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponIDsByCustomerByPriceSQL);
			// Set Customer ID and Price variables that method received into
			// prepared statement
			pstmt.setInt(1, custID);
			pstmt.setInt(2, price);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCustCoup3 = pstmt.executeQuery();
			// While there is result line in resCustCoup3 do lines below
			while (resCustCoup3.next()) {
				// Creating and defining Coupon object as per Coupon ID from
				// ResultSet and for method to add it to the list and return it
				// afterwards
				Coupon coupon = new Coupon();
				coupon = couponDAOdb.getCoupon(resCustCoup3.getInt("couponid"));
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

	public boolean couponAlreadyPurchased(int coupID, int custID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		boolean exists = false;
		try {
			// Defining SQL string to check if Coupon has been already purchased
			// by the Customer via prepared statement
			String checkCouponSQL = "select * from custcoupon where couponid = ? and customerid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(checkCouponSQL);
			// Fetching variables into SQL string from method input
			pstmt.setInt(1, coupID);
			pstmt.setInt(2, custID);
			// Executing prepared statement
			ResultSet result = pstmt.executeQuery();
			// If result for query is not a null, Coupon exists in DB
			if (result.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			// Handling SQL exception during data writing into database
			throw new CouponSystemException("SQL error - Coupon not found", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return final exists status of Coupon ID
		return exists;
	}

}
