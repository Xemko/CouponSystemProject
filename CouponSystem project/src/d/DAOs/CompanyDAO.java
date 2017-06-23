package d.DAOs;

import java.util.Collection;

import c.Beans.Company;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public interface CompanyDAO {

	int createCompany(Company company) throws CouponSystemException;
	void removeCompany(Company company) throws CouponSystemException;
	void updateCompany(Company company) throws CouponSystemException;
	Company getCompany(int id) throws CouponSystemException;
	Collection<Company> getAllCompanies() throws CouponSystemException;
	Collection<Coupon> getCoupons(int id) throws CouponSystemException;
	int login(String companyName, String password) throws CouponSystemException;
	boolean companyNameExists(Company company) throws CouponSystemException;
	
}
