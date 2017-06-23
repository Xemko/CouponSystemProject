package g.Main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import c.Beans.Company;
import c.Beans.Coupon;
import c.Beans.Customer;
import e.Exceptions.CouponSystemException;
import f.Facades.AdminFacade;
import f.Facades.ClientType;
import f.Facades.CompanyFacade;
import f.Facades.CustomerFacade;

public class MainTest {

	public static void main(String[] args) throws CouponSystemException, ParseException {

		// // Making first CS instance and logging in as an Admin
		CouponSystem cs1 = CouponSystem.getInstance();
		AdminFacade af = (AdminFacade) cs1.login("admin", "1234", ClientType.ADMIN);

		System.out.println("======ADMIN FACADE TEST======");
		System.out.println();

		// 1. FETCHING LIST OF ALL COMPANIES

		System.out.println("1. List of companies : " + af.getAllCompanies());

		// 2. CREATING COMPANY -

		System.out.println("2. Creating company - ");
		Company companyToCreate = new Company();
		// Negative testing for existing name
		// companyToCreate.setName("companyA");
		companyToCreate.setName("new2Company");
		companyToCreate.setEmail("Test@email.com");
		companyToCreate.setPassword("testPass");
		af.createCompany(companyToCreate);

		// 3. FETCHING SPECIFIC COMPANY -

		System.out.println("Company with ID 2 - " + af.getCompany(2));

		// 4. UPDATING COMPANY, EXCEPT NAME -
		System.out.println("Updating company with ID 3 - ");
		Company companyToUpdate = af.getCompany(3);
		companyToUpdate.setEmail("NewCompEmail245");
		companyToUpdate.setPassword("NewPass12");
		af.updateCompany(companyToUpdate);

		// 5. REMOVING COMPANY -
		System.out.println("Removing company with ID 3 - ");
		Company companyToDelete = af.getCompany(3);
		af.removeCompany(companyToDelete);

		// 6. FETCHING LIST OF ALL CUSTOMERS -

		System.out.println("List of customers : " + af.getAllCustomers());

		// 7. CREATING CUSTOMER -
		System.out.println("Creating customer : ");
		Customer customerToCreate = new Customer();
		// Negative testing for existing name
		// customerToCreate.setName("customerA");
		customerToCreate.setName("new1Customer");
		customerToCreate.setPassword("TestPass");
		af.createCustomer(customerToCreate);

		// 8. FETCHING SPECIFIC CUSTOMER -

		System.out.println("Customer with ID 1 - " + af.getCustomer(1));

		// 9. UPDATING CUSTOMER, EXCEPT NAME -
		System.out.println("Updating customer with ID 1 - ");
		Customer customerToUpdate = af.getCustomer(1);
		customerToUpdate.setPassword("UpdPass");
		af.updateCustomer(customerToUpdate);

		// 10. REMOVING CUSTOMER -
		System.out.println("Removing customer with ID 4 - ");
		Customer customerToDelete = af.getCustomer(4);
		af.removeCustomer(customerToDelete);

		// Shut down Admin System process
		// System.out.println("Admin system is shutting down");
		// cs1.shutDown();

		// Making second CS instance and logging in as a Company

		System.out.println("=====COMPANY FACADE TEST=====");
		CouponSystem cs2 = CouponSystem.getInstance();
		CompanyFacade compFacade = (CompanyFacade) cs2.login("companyA", "123", ClientType.COMPANY);

		// FETCHING COMPANY'S COUPONS LIST

		System.out.println("Fetching all company's coupons list - " + compFacade.getAllCompanyCoupons());

		// CREATE COMPANY'S COUPON

		System.out.println("Create company's coupon - ");

		Date startDate = new GregorianCalendar(2017, 5, 21).getTime();
		Date endDate1 = new GregorianCalendar(2018, 5, 21).getTime();
		Date endDate2 = new GregorianCalendar(2018, 7, 21).getTime();

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String stringStartDate= "2017-05-21";
		// Date startDate = sdf.parse(stringStartDate);
		// String stringEndDate1= "2018-05-21";
		// Date endDate1 = sdf.parse(stringEndDate1);
		// String stringEndDate2= "2018-07-21";
		// Date endDate2 = sdf.parse(stringEndDate2);

		Coupon couponToCreate = new Coupon();
		// Negative testing for existing title
		// couponToCreate.setTitle("couponA");
		couponToCreate.setTitle("newCoupon");
		couponToCreate.setStart_date(startDate);
		couponToCreate.setEnd_date(endDate1);
		couponToCreate.setType("Video");
		couponToCreate.setMessage("NewMessage");
		couponToCreate.setPrice(100);
		couponToCreate.setExpired("no");
		couponToCreate.setImage("NewImage");

		compFacade.createCoupon(couponToCreate, 10);

		// UPDATE COMPANY'S COUPON
		System.out.println("Updating company first coupon - ");
		ArrayList<Coupon> couponsUpdate = (ArrayList<Coupon>) compFacade.getAllCompanyCoupons();
		Coupon couponToUpdate = couponsUpdate.get(0);

		int newPrice = 200;
		compFacade.updateCoupon(couponToUpdate, endDate2, newPrice);

		// REMOVE COMPANY'S COUPON
		System.out.println("Removing company second coupon - ");
		ArrayList<Coupon> couponsRemove = (ArrayList<Coupon>) compFacade.getAllCompanyCoupons();
		Coupon couponToRemove = couponsRemove.get(1);
		compFacade.removeCoupon(couponToRemove);

		// GET COMPANY'S COUPONS BY TYPE

		System.out.println("Getting all company's coupons by type Food - " + compFacade.getAllCouponsByType("Food"));

		// GET COMPANY'S COUPONS BY PRICE
		System.out.println("Getting all company's coupons by price 130 in descending order - "
				+ compFacade.getAllCouponsByPrice(130));
		// GET COMPANY'S COUPONS BY DATE
		System.out.println(
				"Getting all company's coupons until date 2018, 7, 21 - " + compFacade.getAllCouponsByDate(endDate2));

		// Shut down Company System process
		// System.out.println("Company system is shutting down");
		// cs2.shutDown();

		// Making third CS instance and logging in as a Customer
		CouponSystem cs3 = CouponSystem.getInstance();
		CustomerFacade custf = (CustomerFacade) cs3.login("customerB", "23456", ClientType.CUSTOMER);

		// PURCHASING COUPON -

		System.out.println("Purchasing coupon with ID 3 - ");
		Coupon coupon = custf.getCouponByID(3);
		custf.purchaseCoupon(coupon);

		// FETCHING ALL CUSTOMER'S COUPONS LIST
		System.out.println("Getting all customer's coupons list - " + custf.getAllCustomerCoupons());

		// FETCHING ALL CUSTOMER'S COUPONS BY TYPE

		System.out.println(
				"Getting all customer's coupons by type Alcohol - " + custf.getAllCustomerCouponsByType("Alcohol"));

		// FETCHING ALL CUSTOMER'S COUPONS BY PRICE

		System.out.println(
				"Getting all customer's coupons up to price - 250 - " + custf.getAllCustomerCouponsByPrice(250));

		// Shut down Customer System process
		// System.out.println("Customer system is shutting down");
		// cs3.shutDown();

	}

}
