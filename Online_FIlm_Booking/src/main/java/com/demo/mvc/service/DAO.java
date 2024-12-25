package com.demo.mvc.service;

import java.time.LocalDate;


import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.mvc.bean.CurrentDateOperation;
import com.demo.mvc.bean.Customer;
import com.demo.mvc.bean.MovieDetails;
import com.demo.mvc.bean.OrderHistory;
import com.demo.mvc.bean.Seat;

@Component
public class DAO {

	@Autowired
	private MovieRepo movierepo;
	@Autowired
	private CustomerRepo custrepo;
	@Autowired
	private SeatRepo seatrepo;
	@Autowired
	private DateOperationRepo daterepo;

	@Autowired
	private HistoryRepo histrepo;

	public List<MovieDetails> getAllMovies() {
		List<MovieDetails> movie = movierepo.findAll();
		return movie;
	}

	public void saveuser(Customer customer) {
		custrepo.save(customer);
	}

	public Customer login(String email, String pwd) {
		Customer cust = custrepo.findByEmailAndPassword(email, pwd);
		return cust;
	}

	public void saveseat(Seat seat, Date date, String time, Customer customer) {
		CurrentDateOperation operation = new CurrentDateOperation();
		operation.setShowdate(date);
		operation.setShowtime(time);
		List<Seat> list = new ArrayList<Seat>();
		list.add(seat);
		operation.setSeat(list);
		customer.setSeat(list);

		seat.setDate(operation);
		seatrepo.save(seat);
		daterepo.save(operation);

	}

	public Customer findCustomerById(int id) {
		Customer cust = custrepo.findById(id).orElse(null);
		return cust;
	}

	public List<Seat> getAllByDate(LocalDate date, String time) {
		List<Seat> seat = seatrepo.getAllByDate(date, time);
		List<String> seatNo = new ArrayList<String>();
		for (Seat s : seat) {
			for (String s1 : s.getSeatNo()) {
				seatNo.add(s1);

			}
		}

		System.out.println("SEATS ARE THESE  in DAO : " + seatNo);

		return seat;
	}

	public void saveHistory(OrderHistory history, Customer cust) {
		List<OrderHistory> l1 = new ArrayList<OrderHistory>();
		l1.add(history);
		histrepo.save(history);
		cust.setHistory(l1);

	}

	public List<OrderHistory> getHistory(int cust_id) {
		List<OrderHistory> list = histrepo.findHistory(cust_id);
		return list;
	}

	public List<Customer> getAllUser() {
		List<Customer> list = custrepo.findAll();

		return list;
	}

	public void updatecustomer(Customer cust) {
		custrepo.save(cust);

	}
}