package c.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyCoupon {

	private int compID;
	private int coupID;
	
		
	public CompanyCoupon() {
		super();
	}
	
	
	public int getCompID() {
		return compID;
	}
	public void setCompID(int compID) {
		this.compID = compID;
	}
	public int getCoupID() {
		return coupID;
	}
	public void setCoupID(int coupID) {
		this.coupID = coupID;
	}
	
	
	@Override
	public String toString() {
		return "CompanyCoupon [compID=" + compID + ", coupID=" + coupID + "]";
	}
	
	
	
	
}
