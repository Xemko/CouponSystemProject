package d.DAOs;


import java.util.Collection;
import java.util.Date;

import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public interface CouponDAO {

		int createCoupon(Coupon coupon) throws CouponSystemException;
		void removeCoupon(Coupon coupon) throws CouponSystemException;
		void updateCoupon(Coupon coupon) throws CouponSystemException;
		Coupon getCoupon(int id) throws CouponSystemException;
		Collection<Coupon> getAllCoupons() throws CouponSystemException;
		Collection<Coupon> getCouponsByType(String coupType, int compID) throws CouponSystemException;
		Collection<Coupon> getCouponsByCompany(int compID) throws CouponSystemException;
		Collection<Coupon> getCouponsByPrice(int price, int compID) throws CouponSystemException;
		Collection<Coupon> getCouponsByDate(Date date, int compID) throws CouponSystemException;
		void markExpiredCoupons() throws CouponSystemException;
		void deleteExpiredCoupons() throws CouponSystemException;
		void deleteCouponsByCompanyID(int compID) throws CouponSystemException;
		void deleteCouponsByCustomerID(int custID) throws CouponSystemException;
		boolean couponTitleExists(Coupon coupon) throws CouponSystemException;

}
