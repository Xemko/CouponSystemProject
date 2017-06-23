package d.DAOs;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import b.ConnectionPool.ConnectionPool;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CouponDAOdb implements CouponDAO {

	private static java.sql.Date sqlDate;

	public CouponDAOdb() {
		sqlDate = new java.sql.Date(new Date().getTime());
	}

	@Override
	/**
	 * Accepts predefined Coupon object and writes it to Coupon table in the
	 * database
	 */
	public int createCoupon(Coupon coupon) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Defining Coupon ID variable to return
		int couponID = -1;

		try {
			// Checking if Coupon title already exists in DB
			// PreparedStatement pstmt1 = null;
			// String nameExist = "SELECT * FROM Coupon where Title = ?";
			// pstmt1 = con.prepareStatement(nameExist);
			// pstmt1.setString(1, coupon.getTitle());
			// ResultSet result = pstmt1.executeQuery();
			// if (result.next()) {
			// System.out.println("Coupon title already exists in Database,
			// please choose another one.");
			// } else {

			// Defining SQL string to insert Coupon via prepared statement
			String createCouponSQL = "insert into coupon (title, start_date, end_date, expired, type, message, price, image) values (?,?,?,?,?,?,?,?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(createCouponSQL);
			// Fetching variables into SQL string via Coupon bean
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, getSqlDate(coupon.getStart_date()));
			pstmt.setDate(3, getSqlDate(coupon.getEnd_date()));
			pstmt.setString(4, coupon.getExpired());
			pstmt.setString(5, coupon.getType());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setInt(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			// Executing prepared statement
			pstmt.executeUpdate();
			String fetchingID = "select id from coupon where title = ? ";
			PreparedStatement pstmtID = con.prepareStatement(fetchingID);
			pstmtID.setString(1, coupon.getTitle());
			ResultSet resCoupon = pstmtID.executeQuery();
			resCoupon.next();
			// Fetching newly created Coupon ID for a return

			couponID = resCoupon.getInt(1);
			coupon.setId(couponID);
			// Writing success confirmation to the console
			System.out.println("Coupon has been added successfully:" + coupon);

			// }
		} catch (SQLException e) {
			// Handling SQL exception during Coupon writing into database
			throw new CouponSystemException("SQL error - Coupon creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		// Return Coupon ID for future use
		return couponID;
	}

	@Override
	/**
	 * Accepts predefined Coupon object and deletes described entry from the
	 * Coupon table in the database
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to delete Coupon via prepared statement
			String removeCouponSQL = "delete from coupon where title = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(removeCouponSQL);
			// Fetching variable into SQL string via Coupon bean
			pstmt.setString(1, coupon.getTitle());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Coupon has been removed successfully:" + coupon);
			} else {
				// Otherwise execution failed due to Coupon not found in the
				// database
				System.out.println("Coupon removal has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon removal from the database
			throw new CouponSystemException("SQL error - Coupon remove has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/**
	 * Accepts predefined Coupon object and updates entry with relevant ID in
	 * the database with values from accepted Coupon object
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to update Coupon via prepared statement
			String updateCouponSQL = "update coupon set title=?, start_date=?, end_date=?, expired=?, type=?, message=?, price=?, image=? where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(updateCouponSQL);
			// Fetching variables into SQL string via Coupon bean plus ID as
			// method accepted variable
			pstmt.setString(1, coupon.getTitle());
			pstmt.setDate(2, getSqlDate(coupon.getStart_date()));
			pstmt.setDate(3, getSqlDate(coupon.getEnd_date()));
			pstmt.setString(4, coupon.getExpired());
			pstmt.setString(5, coupon.getType());
			pstmt.setString(6, coupon.getMessage());
			pstmt.setInt(7, coupon.getPrice());
			pstmt.setString(8, coupon.getImage());
			pstmt.setInt(9, coupon.getId());
			// Executing prepared statement and using its result for
			// post-execute check
			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Coupon has been updated successfully:" + coupon);
			} else {
				// Otherwise execution failed due to Coupon not found in the
				// database
				System.out.println("Coupon update has been failed - subject entry not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon update in the database
			throw new CouponSystemException("SQL error - Coupon update has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/** Accepts Coupon ID and returns related Coupon object */
	public Coupon getCoupon(int id) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Creating Coupon object to fill it later with data from SQL query
		// result and for method to return it afterwards
		Coupon coupon = new Coupon();

		try {
			// Defining SQL string to retrieve Coupon via prepared statement
			String getCouponSQL = "select * from coupon where id = ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponSQL);
			// Putting method accepted ID into SQL string
			pstmt.setInt(1, id);
			// Executing prepared statement and using its result for
			// post-execute manipulations
			ResultSet resCoup = pstmt.executeQuery();
			// If query result has data (if Coupon has been found) do
			if (resCoup.next()) {
				// Set Coupon object ID to ID variable that method received
				coupon.setId(id);
				// Set Coupon attributes according to the results in the
				// ResultSet
				coupon.setTitle(resCoup.getString("title"));
				coupon.setStart_date(resCoup.getDate("start_date"));
				coupon.setEnd_date(resCoup.getDate("end_date"));
				coupon.setExpired(resCoup.getString("expired"));
				coupon.setType(resCoup.getString("type"));
				coupon.setMessage(resCoup.getString("message"));
				coupon.setPrice(resCoup.getInt("price"));
				coupon.setImage(resCoup.getString("image"));
				// // Write retrieval success confirmation to the console
				// System.out.println("Coupon has been found successfully:" +
				// coupon);
			} else {
				// If query result is empty (Coupon hasn't been found) throw
				// exception
				System.out.println("Coupon retrieve has been failed - Coupon ID not found");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon retrieval from the database
			throw new CouponSystemException("SQL error - Coupon retrieve has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}

		// Return Coupon
		return coupon;
	}

	@Override
	/** Returns all Coupons as an array list */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve all Coupons via prepared
			// statement
			String getAllCouponsSQL = "select * from coupon";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getAllCouponsSQL);
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
	/** Returns all Coupons of selected type as an array list */
	public Collection<Coupon> getCouponsByType(String coupType, int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupons via prepared statement
			String getCouponsByTypeSQL = "select * from coupon where type = ? and id in(select couponid from compcoupon where companyid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsByTypeSQL);
			// Set Coupon object type to coupType variable that method received
			pstmt.setString(1, coupType);
			pstmt.setInt(2, compID);
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
	/** Returns all Coupons of selected Company as an array list */
	public Collection<Coupon> getCouponsByCompany(int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupons via prepared statement
			String getCouponsByCompanySQL = "select * from coupon where id in (select couponid from compcoupon where companyid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsByCompanySQL);
			// Set Company ID variable that method received into prepared
			// statement
			pstmt.setInt(1, compID);
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
	 * Returns all Coupons with price less than value method received as an
	 * array list sorted descended
	 */
	public Collection<Coupon> getCouponsByPrice(int price, int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupons via prepared statement

			String getCouponsByPriceSQL = "select * from coupon where price < ? and id in(select couponid from compcoupon where companyid = ?) order by price desc";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsByPriceSQL);
			// Set price variable that method received into prepared statement
			pstmt.setInt(1, price);
			pstmt.setInt(2, compID);
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
	 * Returns all Coupons with end date up to value method received as an array
	 * list sorted descended
	 */
	public Collection<Coupon> getCouponsByDate(Date date, int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		// Create list object to fill it later with Coupons
		Collection<Coupon> colCoup = new ArrayList<>();

		try {
			// Defining SQL string to retrieve Coupons via prepared statement
			String getCouponsByEndDateSQL = "select * from coupon where end_date < ? and id in(select couponid from compcoupon where companyid = ?) order by end_date desc";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(getCouponsByEndDateSQL);
			// Set date variable that method received into prepared statement
			pstmt.setDate(1, getSqlDate(date));
			pstmt.setInt(2, compID);
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
	/** Marks all expired Coupons in the Coupon table in the database */
	public void markExpiredCoupons() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to mark expired Coupons via prepared
			// statement
			String markExpiredCouponsSQL = "update coupon set expired='expired' where end_date < current_date";
			// Not working variants:
			// String markExpiredCouponsSQL = "update coupon set
			// expired='expired' where end_date < 2017-05-21";
			// String markExpiredCouponsSQL = "update coupon set
			// expired='expired' where end_date < getdate()";
			// String markExpiredCouponsSQL = "update coupon set
			// expired='expired' where date < (format ( getdate(),
			// 'YYYY-MM-DD'))";
			// String markExpiredCouponsSQL = "update coupon set
			// expired='expired' where end_date < ?";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(markExpiredCouponsSQL);
			// Executing prepared statement and using its result for
			// post-execute check

			// Not working (prepared statement variant)
			// java.util.Date todayDate = Calendar.getInstance().getTime();
			// SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
			// String todayString = formatter.format(todayDate);
			// pstmt.setString(1, todayString);

			int resExpired = pstmt.executeUpdate();
			if (resExpired != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Expired coupons have been marked successfully");
			} else {
				// Otherwise execution failed due to Coupons not found in the
				// database
				System.out.println("Coupons marking has been failed - subject entries not found in the database");
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon marking in the database
			throw new CouponSystemException("SQL error - Coupons marking has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	@Override
	/** Removes all expired Coupons from the Coupon table in the database */
	public void deleteExpiredCoupons() throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove expired Coupons from the Coupon
			// table via prepared statement
			String deleteExpiredCouponsFinalSQL = "delete from coupon where expired = 'expired'";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteExpiredCouponsFinalSQL);
			// Executing prepared statement and using its result for
			// post-execute check
			int resExpired = pstmt.executeUpdate();
			if (resExpired != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Expired coupons have been deleted from Coupon table successfully");
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

	@Override
	/**
	 * Removes all Coupons from the Coupon table in the database related to
	 * Company ID received
	 */
	public void deleteCouponsByCompanyID(int compID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove Coupons related to specified
			// Company ID from the Coupon table via prepared statement
			String deleteCouponsByCompanyIDSQL = "delete from coupon where id in (select couponid from compcoupon where companyid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCompanyIDSQL);
			// Set Company ID from variable that method received
			pstmt.setInt(1, compID);
			// Executing prepared statement and using its result for
			// post-execute check
			int resRemByComp3 = pstmt.executeUpdate();
			if (resRemByComp3 != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Coupons of specified Company have been deleted from Coupon table successfully");
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

	@Override
	/**
	 * Removes all Coupons from the Coupon table in the database related to
	 * Customer ID received
	 */
	public void deleteCouponsByCustomerID(int custID) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();

		try {
			// Defining SQL string to remove Coupons related to specified
			// Customer ID from the Coupon table via prepared statement
			String deleteCouponsByCustomerIDSQL = "delete from coupon where id in (select couponid from custcoupon where customerid = ?)";
			// Creating prepared statement with predefined SQL string
			PreparedStatement pstmt = con.prepareStatement(deleteCouponsByCustomerIDSQL);
			// Set Customer ID from variable that method received
			pstmt.setInt(1, custID);
			// Executing prepared statement and using its result for
			// post-execute check
			int resRemByCust3 = pstmt.executeUpdate();
			if (resRemByCust3 != 0) {
				// If result row count is not equal to zero - execution has been
				// successful
				System.out.println("Coupons of specified Customer have been deleted from Coupon table successfully");
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

	private java.sql.Date getSqlDate(Date javaDate) {
		sqlDate.setTime(javaDate.getTime());
		return sqlDate;
	}

	@Override
	public boolean couponTitleExists(Coupon coupon) throws CouponSystemException {

		// Creating connection and predefining it as null for a future use
		Connection con = null;
		// Getting a connection from the Connection Pool
		con = ConnectionPool.getInstance().getConnection();
		boolean exists = false;
		try {
			// Checking if Coupon title already exists in DB
			String titleExists = "SELECT * FROM Coupon where Title = ?";
			PreparedStatement pstmt1 = con.prepareStatement(titleExists);
			pstmt1 = con.prepareStatement(titleExists);
			pstmt1.setString(1, coupon.getTitle());
			ResultSet result = pstmt1.executeQuery();
			if (result.next()) {
				System.out.println("Coupon title already exists in Database, please choose another one.");
				exists = true;
			}
		} catch (SQLException e) {
			// Handling SQL exception during Coupon writing into database
			throw new CouponSystemException("SQL error - Company creation has been failed", e);
		} finally {
			// In any case we return connection to the Connection Pool (at least
			// we try to)
			ConnectionPool.getInstance().returnConnection(con);
		}
		return exists;
	}

}
