package d.DAOs;


import java.util.ArrayList;

import java.util.List;

import c.Beans.Customer;
import c.Beans.Coupon;
import e.Exceptions.CouponSystemException;

public class CustomerDAOtest {

	public static void main(String[] args) throws CouponSystemException {
		
				// Please comment all unwanted methods during test phase to ensure pinpoint testing!!!
				
				// Creating working object for DAO methods
				CustomerDAOdb customerDAO = new CustomerDAOdb();

				// Creating Customer object to insert into database
				Customer p1 = new Customer();
				p1.setName("Petya");
				p1.setPassword("9911");
				// Insert created Customer object into database
				customerDAO.createCustomer(p1);
				
				// Creating Customer object to update existing Customer in the database
				Customer p2 = new Customer();
				p2.setName("Vasya");
				p2.setPassword("5476");
				// Update 4th Customer (just-inserted-Petya-customer ID if test run for the first time)
				customerDAO.updateCustomer(p2);

				
				// Define Customer for removal 
				Customer p3 = new Customer();
				p3.setName("Vasya");
				// Remove defined Customer
				customerDAO.removeCustomer(p3);
			
				// Get Customer with ID 2 and point p4 object onto it 
				Customer p4 = customerDAO.getCustomer(2);
				System.out.println(p4);
					
				// Get all Customers and print them
				List<Customer> custList = new ArrayList<>();
				custList = (List<Customer>) customerDAO.getAllCustomers();
				for (int i = 0; i < custList.size(); i++) {
					System.out.println(custList.get(i));
				}
				//System.out.println(custList);
				
				
				// Get all coupons related to Customer with ID = 1
				List<Coupon> coupList = new ArrayList<>();
				coupList = (List<Coupon>) customerDAO.getCoupons(1);
				for (int j = 0; j < coupList.size(); j++) {
					System.out.println(coupList.get(j));
				}
				//System.out.println(coupList);


				// Login test with existing Customer
				customerDAO.login("Petya", "9911");


		}

		

	}


