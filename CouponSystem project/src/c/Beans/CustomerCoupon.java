package c.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerCoupon {
	
	private int custID;
	private int coupID;
	
	
	
	
	public CustomerCoupon() {
		super();
	}
	
	
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public int getCoupID() {
		return coupID;
	}
	public void setCoupID(int coupID) {
		this.coupID = coupID;
	}
	
	
	@Override
	public String toString() {
		return "CustomerCoupon [custID=" + custID + ", coupID=" + coupID + "]";
	}

	
	
}
