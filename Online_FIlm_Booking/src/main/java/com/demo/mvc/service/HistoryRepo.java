package com.demo.mvc.service;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.mvc.bean.OrderHistory;

@Repository
public interface HistoryRepo extends JpaRepository<OrderHistory, Integer> {
//	@Query(value = "selcet * from OrderHistory inner join order_seatNo on OrderHistory.hid=order_seatNo. where showondate=?1,showtime=?2,moviname=?3")
//public void findAllByDateAndTime(LocalDate date , String time , String moviename);

	@Query(value = "select * from order_history where  customer_cust_id=? order by hid desc", nativeQuery = true)
	public List<OrderHistory> findHistory(int custid);
}
