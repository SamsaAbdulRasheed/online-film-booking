package com.demo.mvc.bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "current_date_operation")
public class CurrentDateOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int d_id;

	@Temporal(TemporalType.DATE)
	private Date showdate;

	private String showtime;
//	@OneToMany
	@OneToMany(mappedBy = "date", fetch = FetchType.EAGER)
	private List<Seat> seat;

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public Date getShowdate() {
		return showdate;
	}

	public void setShowdate(Date showdate) {
		this.showdate = showdate;
	}

	public String getShowtime() {
		return showtime;
	}

	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}

	public List<Seat> getSeat() {
		return seat;
	}

	public void setSeat(List<Seat> seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "CurrentDateOperation [d_id=" + d_id + ", showdate=" + showdate + ", showtime=" + showtime + ", seat="
				+ seat + "]";
	}

}
