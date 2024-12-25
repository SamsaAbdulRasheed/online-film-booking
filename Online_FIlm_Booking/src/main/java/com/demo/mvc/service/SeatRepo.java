package com.demo.mvc.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.mvc.bean.Seat;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer> {

	@Query(value = "select * from seat inner join seat_seat_no " + "on seat.seat_id=seat_seat_no.seat_seat_id "
			+ "inner join current_date_operation" + " on seat.date_d_id=current_date_operation.d_id"
			+ " where showdate=?1 and showtime=?2", nativeQuery = true)
	public List<Seat> getAllByDate(LocalDate date, String time);
}
