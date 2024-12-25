package com.demo.mvc.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seat")
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seat_id;

	@ElementCollection
	private List<String> seatNo;

//	@ElementCollection
	private double price;

	private double total;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Customer customer;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CurrentDateOperation date;

	public int getSeat_id() {
		return seat_id;
	}

	public void setSeat_id(int seat_id) {
		this.seat_id = seat_id;
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

	public Customer getCutomer() {
		return customer;
	}

	public void setCutomer(Customer cutomer) {
		this.customer = cutomer;
	}

	public CurrentDateOperation getDate() {
		return date;
	}

	public void setDate(CurrentDateOperation date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "seat [seat_id=" + seat_id + ", seatNo=" + seatNo + ", price=" + price + ", total=" + total
				+ ", cutomer=" + customer + ", date=" + date + "]";
	}

}
