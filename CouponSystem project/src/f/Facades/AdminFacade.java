package f.Facades;

import java.util.ArrayList;
import java.util.List;

import c.Beans.Company;
import c.Beans.Customer;
import d.DAOs.CompanyCouponDAOdb;
import d.DAOs.CompanyDAO;
import d.DAOs.CompanyDAOdb;
import d.DAOs.CouponDAO;
import d.DAOs.CustomerDAOdb;
import d.DAOs.CouponDAOdb;
import d.DAOs.CustomerCouponDAOdb;
import d.DAOs.CustomerDAO;
import e.Exceptions.CouponSystemException;

public class AdminFacade implements ClientFacade {

	private CompanyDAO companyDAO = new CompanyDAOdb();
	private CustomerDAO customerDAO = new CustomerDAOdb();
	private CouponDAO couponDAO = new CouponDAOdb();
	private CompanyCouponDAOdb companyCouponDAOdb = new CompanyCouponDAOdb();
	private CustomerCouponDAOdb customerCouponDAOdb = new CustomerCouponDAOdb();
	private static final String admin_user = "admin";
	private static final String admin_pass = "1234";

	public AdminFacade() {

	}

	/** Login for user of Admin type */
	public static AdminFacade login(String user, String password) throws CouponSystemException {
		// Verifying correct user credentials (with no hint where user failed -
		// avoiding credentials guessing)
		if ((!(admin_user.equals(user))) || (!(admin_pass.equals(password)))) {
			throw new CouponSystemException("Invalid username or password, try again.");
		}
		// Returning Admin Facade
		return new AdminFacade();
	}

	/** Creates new Company according to Company object received */
	public int createCompany(Company company) throws CouponSystemException {
		int compID;
		// Writing Company object into DB
		boolean exists = companyDAO.companyNameExists(company);
		if (!exists) {
			compID = companyDAO.createCompany(company);
			System.out.println("Company has been added successfully : " + company);
			return compID;
		} else {
			throw new CouponSystemException("Company name already exists, try different name.");
		}
	}

	/** Removes Company object from both main and secondary tables */
	public void removeCompany(Company company) throws CouponSystemException {

		// Fetching Company ID from received object
		int companyID = company.getId();

		// Removing Company-related Coupons from main Coupon table
		couponDAO.deleteCouponsByCompanyID(companyID);

		// Removing Company-related Coupons from secondary tables
		customerCouponDAOdb.deleteCouponsByCompanyID(companyID);
		companyCouponDAOdb.deleteCouponsByCompanyID(companyID);

		// Removing Company from Company table
		companyDAO.removeCompany(company);
	}

	/** Updates Company with new Company object */
	public void updateCompany(Company company) throws CouponSystemException {

		// Updating Company in DB with new Company object
		companyDAO.updateCompany(company);
	}

	/** Returns Company object of Company ID received */
	public Company getCompany(int compID) throws CouponSystemException {

		// Creating new Company object and fetching data from DB via Company ID
		// received
		Company company = companyDAO.getCompany(compID);
		// Returning Company object
		return company;
	}

	/** Returns list of all Companies existing in DB */
	public List<Company> getAllCompanies() throws CouponSystemException {

		// Creating new list to contain Company objects
		List<Company> compList = new ArrayList<>();
		// Filling created list with Companies fetched from DB
		compList = (List<Company>) companyDAO.getAllCompanies();

		// for (int i = 0; i < compList.size(); i++) {
		// System.out.println(compList.get(i));
		// }

		// Returning List of Companies
		return compList;
	}

	/** Creates new Customer according to Customer object received */
	public int createCustomer(Customer customer) throws CouponSystemException {
		int custID;
		// Writing Customer object into DB
		boolean exists = customerDAO.customerNameExists(customer);
		if (!exists) {
			custID = customerDAO.createCustomer(customer);
			System.out.println("Customer has been added successfully:" + customer);
			return custID;
		} else {
			throw new CouponSystemException("Customer name already exists, try different name.");
		}

	}

	/** Removes Customer object from both main and secondary tables */
	public void removeCustomer(Customer customer) throws CouponSystemException {

		// Fetching Customer ID from received object
		int customerID = customer.getId();

		// Removing Customer-related Coupons from secondary tables
		customerCouponDAOdb.deleteCouponsByCustomerID(customerID);
		// companyCouponDAOdb.deleteCouponsByCustomerID(customerID);
		// // Removing Customer-related Coupons from main Coupon table
		// couponDAOdb.deleteCouponsByCustomerID(customerID);
		// Removing Customer from Customer table
		customerDAO.removeCustomer(customer);
	}

	/** Updates Customer with new Customer object */
	public void updateCustomer(Customer customer) throws CouponSystemException {

		// Updating Customer in DB with new Customer object
		customerDAO.updateCustomer(customer);
	}

	/** Returns Customer object of Customer ID received */
	public Customer getCustomer(int custID) throws CouponSystemException {

		// Creating new Customer object and fetching data from DB via Customer
		// ID received
		Customer customer = customerDAO.getCustomer(custID);
		// Returning Customer object
		return customer;
	}

	/** Returns list of all Customers existing in DB */
	public List<Customer> getAllCustomers() throws CouponSystemException {

		// Creating new list to contain Customer objects
		List<Customer> custList = new ArrayList<>();
		// Filling created list with Customers fetched from DB
		custList = (List<Customer>) customerDAO.getAllCustomers();
		// for (int i = 0; i < custList.size(); i++) {
		// System.out.println(custList.get(i));
		// }

		// Returning List of Customers
		return custList;
	}

}
