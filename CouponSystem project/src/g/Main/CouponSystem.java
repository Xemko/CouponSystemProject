package g.Main;

import b.ConnectionPool.ConnectionPool;
import d.DAOs.CompanyDAOdb;
import d.DAOs.CustomerDAOdb;
import e.Exceptions.CouponSystemException;
import f.Facades.AdminFacade;
import f.Facades.ClientFacade;
import f.Facades.ClientType;
import f.Facades.CompanyFacade;
import f.Facades.CustomerFacade;
import g.Main.CouponSystem;
import h.Thread.DailyCleanupThread;

public class CouponSystem {

	private static CouponSystem instance;
	private DailyCleanupThread dailyThread = new DailyCleanupThread();

	private CompanyDAOdb companyDAOdb = new CompanyDAOdb();
	private CustomerDAOdb customerDAOdb = new CustomerDAOdb();

	public CouponSystem() {
		Thread dailyTask = new Thread(dailyThread);
		dailyTask.start();
	}

	public static CouponSystem getInstance() {

		if (instance == null) {
			instance = new CouponSystem();
		}

		return instance;
	}

	public ClientFacade login(String user, String pass, ClientType client) throws CouponSystemException {

		switch (client) {
		case ADMIN:

			AdminFacade adminFacade = AdminFacade.login(user, pass);

			return adminFacade;

		case COMPANY:

			int companyID = companyDAOdb.login(user, pass);

			CompanyFacade company = new CompanyFacade(companyID);

			return company;
		case CUSTOMER:

			int customerID = customerDAOdb.login(user, pass);

			CustomerFacade customer = new CustomerFacade(customerID);

			return customer;

		default:

			return null;
		}
	}

	public void shutDown() {
		try {
			System.out.println("Coupon system will go down in 10 seconds.");
			Thread.sleep(10000);
			System.out.println("System is shutting down.");
		} catch (InterruptedException e1) {
			System.out.println("Sleep of current shutdown process has been failed.");
		}
		dailyThread.interrupt();
		try {
			ConnectionPool.getInstance().CloseAllConnections();
		} catch (CouponSystemException e) {
			System.out.printf("Closing all connection action received an exception", e);
		}
	}

}
