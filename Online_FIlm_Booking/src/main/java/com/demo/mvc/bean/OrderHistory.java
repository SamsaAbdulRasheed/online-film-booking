package com.demo.mvc.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hid;

	@ElementCollection
	private List<String> seatNo;

	private double price;

	private double total;

	private String moviename;
	@Temporal(value = TemporalType.DATE)
	private Date bookondate;

	@Temporal(value = TemporalType.DATE)
	private Date showondate;

	private String showtime;

	@OneToOne
	private Customer customer;

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public List<String> getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(List<String> seatNo) {
		this.seatNo = seatNo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getMoviename() {
		return moviename;
	}

	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}

	public Date getBookondate() {
		return bookondate;
	}

	public void setBookondate(Date bookondate) {
		this.bookondate = bookondate;
	}

	public Date getShowondate() {
		return showondate;
	}

	public void setShowondate(Date showondate) {
		this.showondate = showondate;
	}

	public String getShowtime() {
		return showtime;
	}

	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "OrderHistory [hid=" + hid + ", seatNo=" + seatNo + ", price=" + price + ", total=" + total
				+ ", moviename=" + moviename + ", bookondate=" + bookondate + ", showondate=" + showondate
				+ ", showtime=" + showtime + ", customer=" + customer + "]";
	}

}
