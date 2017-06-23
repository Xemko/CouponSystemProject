package d.DAOs;


import java.util.ArrayList;
import java.util.List;

import c.Beans.Company;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CompanyDAOtest {

	public static void main(String[] args) throws CouponSystemException {
		
		
		// Please comment all unwanted methods during test phase to ensure pinpoint testing!!!
		
		// Creating working object for DAO methods
		CompanyDAOdb companyDAO = new CompanyDAOdb();

		// Creating Company object to insert into database
		Company c1 = new Company();
		c1.setName("YES24");
		c1.setPassword("555524");
		c1.setEmail("yes24@mail.com");
		// Insert created Company object into database
		companyDAO.createCompany(c1);

		
		// Creating Company object to update existing Company in the database
		Company c2 = new Company();
		c2.setName("NO");
		c2.setPassword("666");
		c2.setEmail("no@mail.com");
		// Update 4th Company (just-inserted-YES-company ID if test run for the first time)
		companyDAO.updateCompany(c2);

		
		// Define Company for removal 
		Company c3 = new Company();
		c3.setName("NO");
		// Remove defined Company
		companyDAO.removeCompany(c3);
	
		// Get Company with ID 2 and point c4 object onto it 
		Company c4 = companyDAO.getCompany(2);
		System.out.println(c4);
			
		// Get all Companies and print them
		List<Company> compList = new ArrayList<>();
		compList = (List<Company>) companyDAO.getAllCompanies();
		for (int i = 0; i < compList.size(); i++) {
			System.out.println(compList.get(i));
		}
		//System.out.println(compList);
		
		
		// Get all Coupons related to Company with ID = 1
		List<Coupon> coupList = new ArrayList<>();
		coupList = (List<Coupon>) companyDAO.getCoupons(1);
		for (int j = 0; j < coupList.size(); j++) {
			System.out.println(coupList.get(j));
		}
		//System.out.println(coupList);


		// Login test with existing Company
		companyDAO.login("YES", "5555");
		
	}

}
