package d.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import b.ConnectionPool.ConnectionPool;
import e.Exceptions.CouponSystemException;

public class CompanyCouponDAOdb {
	
	
	public CompanyCouponDAOdb(){
		super();
	}
	
	
	/** Accepts Company ID, Coupon ID and amount of Coupons and writes this data to Company-Coupon secondary table in the database  */
	public void linkCoupon(int compID, int coupID, int amount) throws CouponSystemException {
		
		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to link Coupon to Company via prepared statement
			String linkCouponSQL = "insert into compcoupon (companyid, couponid, couponamount) values (?,?,?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(linkCouponSQL);
			// Fetching variables into SQL string from method input
			pstmt.setInt(1, compID);
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
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		
	}
	
	
	/** Removes all expired Coupons from the Company-Coupon secondary table in the database */
	public void deleteExpiredCoupons() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to remove expired Coupons from the Company-Coupon secondary table via prepared statement
			String deleteExpiredCouponsCompCoupSQL = "delete from compcoupon where couponid in (select id from coupon where expired = 'expired')";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteExpiredCouponsCompCoupSQL);
			// Executing prepared statement and using its result for post-execute check
			int resExpired = pstmt.executeUpdate();
			if (resExpired != 0) {
				// If result row count is not equal to zero - execution has been successful
				System.out.println("Expired coupons have been deleted from Company-Coupon table successfully");
			} else {
				// Otherwise execution failed due to expired Coupons not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
	}

	
	/** Removes all Coupons from the Company-Coupon table in the database related to Company ID received */
	public void deleteCouponsByCompanyID(int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to remove Coupons related to specified Company ID from the Company-Coupon secondary table via prepared statement
			String deleteCouponsByCompanyIDSQL = "delete from compcoupon where companyid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCompanyIDSQL);
			// Set Company ID from variable that method received
			pstmt.setInt(1, compID);
			// Executing prepared statement and using its result for post-execute check
			int resRemByComp = pstmt.executeUpdate();
			if (resRemByComp != 0) {
				// If result row count is not equal to zero - execution has been successful
				System.out.println("Coupons of specified Company have been deleted from Company-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons related to specified Company not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
	}
	
	
	/** Removes all Coupons from the Company-Coupon table in the database related to Customer ID received */
	public void deleteCouponsByCustomerID(int custID) throws CouponSystemException {
		
		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to remove Coupons related to specified Customer ID from the Company-Coupon secondary table via prepared statement
			String deleteCouponsByCustomerIDSQL = "delete from compcoupon where couponid in (select couponid from custcoupon where customerid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCustomerIDSQL);
			// Set Customer ID from variable that method received
			pstmt.setInt(1, custID);
			// Executing prepared statement and using its result for post-execute check
			int resRemByCust = pstmt.executeUpdate();
			if (resRemByCust != 0) {
				// If result row count is not equal to zero - execution has been successful
				System.out.println("Coupons of specified Customer have been deleted from Company-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons related to specified Customer not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
	}
	
	
	/** Removes all Coupons of selected ID from the Company-Coupon table in the database  */
	public void deleteCouponsByID(int coupID) throws CouponSystemException {
		
		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to remove Coupons of defined ID from the Company-Coupon secondary table via prepared statement
			String deleteCouponsByIDSQL = "delete from compcoupon where couponid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByIDSQL);
			// Set Coupon ID from variable that method received
			pstmt.setInt(1, coupID);
			// Executing prepared statement and using its result for post-execute check
			int resRemByID = pstmt.executeUpdate();
			if (resRemByID != 0) {
				// If result row count is not equal to zero - execution has been successful
				System.out.println("Coupons of specified ID has been deleted from Company-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupons of specified ID not found in the database
				System.out.println("Coupons removal has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupons removal has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
	}
	
	
	/** Receives Coupon ID and returns amount of coupons   */
	public int getCouponAmount(int coupID) throws CouponSystemException{

		// Predefining value for a return
		int amount = -1;
		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to fetch Coupon amount from DB
			String fetchCouponAmountSQL = "select couponamount from compcoupon where couponid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(fetchCouponAmountSQL);
			// Set Coupon ID from variable that method received
			pstmt.setInt(1, coupID);
			// Executing prepared statement and using its result for post-execute check
			ResultSet resAmount = pstmt.executeQuery();
			// If query result has data (if Amount value has been found) do
			if(resAmount.next()){
				amount = resAmount.getInt("couponamount");
//				// Write retrieval success confirmation to the console 							
//				System.out.println("Coupon amount has been fetched successfully: " + amount);
			} else {
				// If query result is empty (Amount hasn't been found) throw exception
				System.out.println("Coupon amount retrieve has been failed - Coupon ID link not found");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon amount fetch from the DB
			throw new CouponSystemException("SQL error - Coupon amount retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
		// Return coupon amount
		return amount;
	}
	
	
	/** Receives Coupon ID and New Amount and updates relevant entry in Company-Coupon secondary table   */
	public void updateCouponAmount(int coupID, int newAmount) throws CouponSystemException{

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		
		try {
			// Defining SQL string to update Coupon amount in DB
			String updateCouponAmountSQL = "update compcoupon set couponamount=? where couponid = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(updateCouponAmountSQL);
			// Set Coupon ID and New Amount from variables that method received
			pstmt.setInt(1, newAmount);
			pstmt.setInt(2, coupID);
			// Executing prepared statement and using its result for post-execute check
			int resUpdAmount = pstmt.executeUpdate();
			if (resUpdAmount != 0) {
				// If result row count is not equal to zero - execution has been successful
				System.out.println("Coupon amount of specified Coupon ID have been updated in Company-Coupon table successfully");
			} else {
				// Otherwise execution failed due to Coupon link in Company-Coupon secondary table has not been found
				System.out.println("Coupon amount update has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon amount update in DB
			throw new CouponSystemException("SQL error - Coupon amount update has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least we try to)
			ConnectionPool.getInstance().returnConnection(con);
		} 
	}


}
