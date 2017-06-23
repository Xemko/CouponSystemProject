package f.Facades;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import c.Beans.Company;
import c.Beans.Coupon;
import d.DAOs.CompanyCouponDAOdb;
import d.DAOs.CompanyDAO;
import d.DAOs.CompanyDAOdb;
import d.DAOs.CouponDAO;
import d.DAOs.CouponDAOdb;
import d.DAOs.CustomerCouponDAOdb;
import e.Exceptions.CouponSystemException;

public class CompanyFacade implements ClientFacade {

	private CompanyDAO companyDAO = new CompanyDAOdb();
	private CouponDAO couponDAO = new CouponDAOdb();
	private CompanyCouponDAOdb companyCouponDAOdb = new CompanyCouponDAOdb();
	private CustomerCouponDAOdb customerCouponDAOdb = new CustomerCouponDAOdb();

	private int company_ID;

	public CompanyFacade(int company_ID) {
		super();
		this.company_ID = company_ID;
	}

	/** Login for user of Company type */
	public ClientFacade login(String user, String password) throws CouponSystemException {

		// Verifying correct user credentials (with no hint where user failed -
		// avoiding credentials guessing)
		if (companyDAO.login(user, password) < 0) {
			throw new CouponSystemException("Invalid username or password, try again.");
		}
		// Returning Company Facade with logged in Company ID
		return new CompanyFacade(companyDAO.login(user, password));
	}

	/** Writes provided Coupon into DB and links logged in Company to it */
	public int createCoupon(Coupon coupon, int amount) throws CouponSystemException {

		// Creating Coupon in DB according to Coupon object provided and saving
		// its ID to variable
		int couponID;
		boolean exists = couponDAO.couponTitleExists(coupon);
		if (!exists) {
			couponID = couponDAO.createCoupon(coupon);
			companyCouponDAOdb.linkCoupon(this.company_ID, couponID, amount);
		} else {
			throw new CouponSystemException("Coupon Title already exists in DB, try different Title.");
		}
		return couponID;
	}

	/** Removes provided Coupon from DB - both from main and secondary tables */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {

		// Fetching Coupon ID
		int couponID = coupon.getId();

		// Removing Coupon from secondary tables
		companyCouponDAOdb.deleteCouponsByID(couponID);
		customerCouponDAOdb.deleteCouponsByID(couponID);
		// Removing Coupon from main Coupon table
		couponDAO.removeCoupon(coupon);
	}

	/** Updates provided Coupon in DB with new End Date and Price received */
	public void updateCoupon(Coupon coupon, Date new_date, int new_price) throws CouponSystemException {

		// Setting new End Date and Price values
		coupon.setEnd_date(new_date);
		coupon.setPrice(new_price);
		// Updating Coupon in the DB
		couponDAO.updateCoupon(coupon);
	}

	/** Returns current Company object */
	public Company getCompany() throws CouponSystemException {

		int companyID = this.company_ID;

		// Fetching Company with current Company ID from DB
		Company company = companyDAO.getCompany(companyID);
		// Returning Company object
		return company;
	}

	/** Returns all Coupons of current Company */
	public List<Coupon> getAllCompanyCoupons() throws CouponSystemException {

		int companyID = this.company_ID;

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by current Company ID and adding them to the list
		couponsList = (List<Coupon>) couponDAO.getCouponsByCompany(companyID);
		// Returning Coupons list
		return couponsList;
	}

	/** Receives Coupon type and returns all Coupons of this Coupon type */
	public List<Coupon> getAllCouponsByType(String coupType) throws CouponSystemException {
		// ToDo - convert string to enum.
		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by Coupon type and adding them to the list
		couponsList = (List<Coupon>) couponDAO.getCouponsByType(coupType, this.company_ID);
		// Returning Coupons list
		return couponsList;
	}

	/** Receives Price and returns all Coupons below this Price */
	public List<Coupon> getAllCouponsByPrice(int price) throws CouponSystemException {

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by Price and adding them to the list
		couponsList = (List<Coupon>) couponDAO.getCouponsByPrice(price, this.company_ID);
		// Returning Coupons list
		return couponsList;
	}

	/** Receives Date and returns all Coupons with End Date before this Date */
	public List<Coupon> getAllCouponsByDate(Date date) throws CouponSystemException {

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by End Date and adding them to the list
		couponsList = (List<Coupon>) couponDAO.getCouponsByDate(date, this.company_ID);
		// Returning Coupons list
		return couponsList;
	}

	/** Returns requested Coupon object */
	public Coupon getCoupon(int id) throws CouponSystemException {


		// Fetching Coupon with given ID from DB
		Coupon coupon = couponDAO.getCoupon(id);
		// Returning Coupon object
		return coupon;
	}
}
