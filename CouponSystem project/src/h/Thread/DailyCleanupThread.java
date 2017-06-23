package h.Thread;

import d.DAOs.CompanyCouponDAOdb;
import d.DAOs.CouponDAO;
import d.DAOs.CouponDAOdb;
import d.DAOs.CustomerCouponDAOdb;
import e.Exceptions.CouponSystemException;

public class DailyCleanupThread implements Runnable {

	private CouponDAO couponDAO = new CouponDAOdb();
	private CompanyCouponDAOdb companyCouponDAOdb = new CompanyCouponDAOdb();
	private CustomerCouponDAOdb customerCouponDAOdb = new CustomerCouponDAOdb();
	Boolean taskRunning = false;

	@Override
	public void run() {

		this.taskRunning = true;

		try {
			couponDAO.markExpiredCoupons();
			Thread.sleep(2000); // 5 seconds sleep
			companyCouponDAOdb.deleteExpiredCoupons();
			Thread.sleep(2000); // 5 seconds sleep
			customerCouponDAOdb.deleteExpiredCoupons();
			Thread.sleep(2000); // 5 seconds sleep
			couponDAO.deleteExpiredCoupons();
			Thread.sleep(86400000); // 24 hours sleep
		} catch (InterruptedException e) {
			this.taskRunning = false;
			System.out.printf("Daily thread received an Interrupted Exception\n", e);
		} catch (CouponSystemException e1) {
			System.out.println(e1.getMessage());
		}

	}

	public void interrupt() {
		this.taskRunning = false;
		Thread.currentThread().interrupt();
	}

}
