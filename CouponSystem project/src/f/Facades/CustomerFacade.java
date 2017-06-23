package f.Facades;

import java.util.ArrayList;
import java.util.List;

import c.Beans.Coupon;
import d.DAOs.CustomerDAOdb;
import d.DAOs.CompanyCouponDAOdb;
import d.DAOs.CouponDAO;
import d.DAOs.CouponDAOdb;
import d.DAOs.CustomerCouponDAOdb;
import d.DAOs.CustomerDAO;
import e.Exceptions.CouponSystemException;

public class CustomerFacade implements ClientFacade {

	private CustomerDAO customerDAO = new CustomerDAOdb();
	private CouponDAO couponDAO = new CouponDAOdb();
	private CompanyCouponDAOdb companyCouponDAOdb = new CompanyCouponDAOdb();
	private CustomerCouponDAOdb customerCouponDAOdb = new CustomerCouponDAOdb();

	private int customer_ID;

	public CustomerFacade(int customer_ID) {
		super();
		this.customer_ID = customer_ID;
	}

	/** Login for user of Customer type */
	public ClientFacade login(String user, String password) throws CouponSystemException {

		// Verifying correct user credentials (with no hint where user failed -
		// avoiding credentials guessing)
		if (customerDAO.login(user, password) < 0) {
			throw new CouponSystemException("Invalid username or password, try again.");
		}
		// Returning Customer Facade with logged in Customer ID
		return new CustomerFacade(customerDAO.login(user, password));

	}

	/**
	 * Verifies provided Coupon ID and links it to Customer ID made purchase
	 * with amount requested
	 */
	public void purchaseCoupon(Coupon couponObj) throws CouponSystemException {

		// Fetching coupon ID.
		int couponID = couponObj.getId();
		// Checking current Coupon amount
		if (customerCouponDAOdb.couponAlreadyPurchased(couponID, this.customer_ID)) {
			System.out.println("Coupon already been purchased.");
		} else {
			int currentAmount = companyCouponDAOdb.getCouponAmount(couponID);
			// Verifying that requested amount not larger than current amount
			if (currentAmount > 0) {
				// Link Coupon with logged in Customer
				customerCouponDAOdb.linkCoupon(this.customer_ID, couponID, 1);
				// Calculate new amount
				int newAmount = currentAmount - 1;
				// Update amount in Company-Coupon table
				companyCouponDAOdb.updateCouponAmount(couponID, newAmount);
			} else {
				// Notify user of aborted operation due to illegal amount
				// requested
				System.out.println("Requested amount is not available, try again.");
			}
		}

	}

	/** Returns all Coupons of current Customer */
	public List<Coupon> getAllCustomerCoupons() throws CouponSystemException {

		int CustomerID = this.customer_ID;

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by Customer ID and adding them to the list
		couponsList = (List<Coupon>) customerCouponDAOdb.getPurchasedCouponsByCustomer(CustomerID);
		// Returning Coupons list
		return couponsList;
	}

	/** Returns all Coupons of specified Coupon type of current Customer */
	public List<Coupon> getAllCustomerCouponsByType(String couponType) throws CouponSystemException {

		int CustomerID = this.customer_ID;

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by Customer ID and Coupon type and adding them to
		// the list
		couponsList = (List<Coupon>) customerCouponDAOdb.getPurchasedCouponsByCustomerByType(CustomerID, couponType);
		// Returning Coupons list
		return couponsList;
	}

	/** Returns all Coupons of specified Price and below of current Customer */
	public List<Coupon> getAllCustomerCouponsByPrice(int price) throws CouponSystemException {

		int CustomerID = this.customer_ID;

		// Creating new list to contain Coupon objects
		List<Coupon> couponsList = new ArrayList<>();
		// Fetching Coupons by Customer ID and Price and adding them to the list
		couponsList = (List<Coupon>) customerCouponDAOdb.getPurchasedCouponsByCustomerByPrice(CustomerID, price);
		// Returning Coupons list
		return couponsList;
	}

	/** Return Coupon by ID */
	public Coupon getCouponByID(int id) throws CouponSystemException {
		Coupon coupon = couponDAO.getCoupon(id);
		return coupon;
	}
}
