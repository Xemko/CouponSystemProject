package c.Beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Coupon {

	private int id;
	private String title;
	private Date start_date;
	private Date end_date;
	private String expired;
	private String type;
	private String message;
	private int price;
	private String image;

	public Coupon() {

	}

	public Coupon(String title, Date start_date, Date end_date, String expired, String type, String message, int price,
			String image) {
		
		this.title = title;
		this.start_date = start_date;
		this.end_date = end_date;
		this.expired = expired;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getExpired() {
		return expired;
	}

	public void setExpired(String expired) {
		this.expired = expired;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", expired=" + expired + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
