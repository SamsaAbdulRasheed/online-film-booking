package com.demo.mvc;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.mvc.bean.Customer;
import com.demo.mvc.bean.MovieDetails;
import com.demo.mvc.bean.OrderHistory;
import com.demo.mvc.bean.Seat;
import com.demo.mvc.service.DAO;
import com.demo.mvc.service.HistoryRepo;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class FilmController {
	@Autowired
	private DAO dao;

	@GetMapping("/")
	public String home(Model m, HttpSession session) {
		List<MovieDetails> movies = dao.getAllMovies();
		session.setAttribute("movie", movies);
//		System.out.println("controller"+movies);
		m.addAttribute("moviesList", movies);
		m.addAttribute("menu", "home");

		return "index";

	}

	@GetMapping("/booking")
	public String booking(@RequestParam("moviename") String movieName, Model m, HttpSession session) {
		LocalDate now = LocalDate.now();
		LocalDate monthLimit = LocalDate.now();
		String time = "09:00 am";

		List<String> seatNo = new ArrayList<String>();
		List<Seat> all = dao.getAllByDate(now, time);
		for (Seat s : all) {
			for (String s1 : s.getSeatNo()) {
				seatNo.add(s1);
			}
		}

		session.setAttribute("movieName", movieName);
		m.addAttribute("date", now);
		m.addAttribute("max", now.plusMonths(1));
		m.addAttribute("min", now);
		m.addAttribute("time", time);
		m.addAttribute("seats", seatNo);
		m.addAttribute("menu", "home");

		return "home";
	}

	// DashBord page
	@GetMapping("/booking-seat")
	public String dashbord(@RequestParam("moviename") String moviename, HttpSession session, Model m) {
		session.setAttribute("moviename", moviename);
		LocalDate now = LocalDate.now();
		LocalDate monthLimit = LocalDate.now();
		String time = "09:00 am";
//	Customer customer = (Customer) session.getAttribute("user");
		List<String> seatNo = new ArrayList<String>();
		List<Seat> all = dao.getAllByDate(now, time);
		for (Seat s : all) {
			for (String s1 : s.getSeatNo()) {
				seatNo.add(s1);
			}
		}

		System.out.println("SEATS ARE THESE : " + seatNo);

		m.addAttribute("date", now);
		m.addAttribute("max", now.plusMonths(1));
		m.addAttribute("min", now);
		m.addAttribute("time", time);
		m.addAttribute("seats", seatNo);
		m.addAttribute("menu", "home");

		return "dashbord";
	}

	@GetMapping("/login")
	public String loginForm(Model m) {
		m.addAttribute("menu", "login");
		return "login";
	}

	@GetMapping("/register")
	public String register(Model m) {
		m.addAttribute("menu", "register");
		return "register";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("customer") Customer customer, Model m) {
		dao.saveuser(customer);
		return "redirect:/register";
	}

	@PostMapping("/processing")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model m,
			HttpSession session) {
		Customer customer = dao.login(email, password);
		if (customer == null) {
			m.addAttribute("failed", "Invalid Login");
			return "login";
		} else {
			session.setAttribute("msg", "Login Successfully");
			session.setAttribute("user", customer);
			return "redirect:/home";

		}

	}

	@GetMapping("/home")
	public String maindashbord(HttpSession session, Model m) {
		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");

		String msg = (String) session.getAttribute("msg");
		m.addAttribute("message", msg);
		session.removeAttribute("msg");

		List<MovieDetails> movies = dao.getAllMovies();
		session.setAttribute("movie", movies);
		m.addAttribute("moviesList", movies);
		m.addAttribute("menu", "home");
		return "main-dashbord";

	}

	@PostMapping("/book-seat")
	public String bookSeat(@ModelAttribute("Seat") Seat seat, @RequestParam("movieName") String movieName,
			HttpSession session, Model m) {
		LocalDate currentDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date todayDate = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
		LocalDate date = (LocalDate) session.getAttribute("bookingdate");
		String time = (String) session.getAttribute("bookingtime");
		Customer object = (Customer) session.getAttribute("user");
		if (object == null) {
			return "redirect:/login";
		} else if ((seat.getSeatNo() == null) || (movieName.equals(null))) {

			return "redirect:/home";
		}

		else if (date == null) {
			date = currentDate;
			time = "09:00 am";

			if (((date.isAfter(currentDate)) || (date.equals(currentDate)))
					&& (date.isBefore(currentDate.plusMonths(1)) || date.equals(currentDate.plusMonths(1)))) {

				Date date2 = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
				double sum = 0;
				double price = 525.22d;
				for (String s : seat.getSeatNo()) {
					sum = sum + price;
				}
				seat.setTotal(sum);
				seat.setPrice(price);
				Customer managedCustomer = dao.findCustomerById(object.getCust_id());
				seat.setCutomer(managedCustomer);
				OrderHistory history = new OrderHistory();
				history.setSeatNo(seat.getSeatNo());
				history.setPrice(price);
				history.setTotal(sum);
				history.setMoviename(movieName);
				history.setBookondate(todayDate);
				history.setShowondate(date2);
				history.setShowtime(time);
				history.setCustomer(managedCustomer);
				dao.saveHistory(history, managedCustomer);
				dao.saveseat(seat, date2, time, managedCustomer);

				session.setAttribute("user", object);
				session.setAttribute("msg", "your seat book successsfully");
				return "redirect:/home";

			} else {
				m.addAttribute("moviename", movieName);
				return "redirect:/booking-seat";
			}

		}

		else {
			if (((date.isAfter(currentDate)) || (date.equals(currentDate)))
					&& (date.isBefore(currentDate.plusMonths(1)) || date.equals(currentDate.plusMonths(1)))) {
				Date date2 = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
				double sum = 0;
				double price = 525.22d;
				for (String s : seat.getSeatNo()) {
					sum = sum + price;

				}
				seat.setTotal(sum);
				seat.setPrice(price);

				Customer managedCustomer = dao.findCustomerById(object.getCust_id());
				seat.setCutomer(managedCustomer);

				OrderHistory history = new OrderHistory();
				history.setSeatNo(seat.getSeatNo());
				history.setPrice(price);
				history.setTotal(sum);
				history.setMoviename(movieName);
				history.setBookondate(todayDate);
				history.setShowondate(date2);
				history.setShowtime(time);
				history.setCustomer(managedCustomer);
				dao.saveHistory(history, managedCustomer);

				dao.saveseat(seat, date2, time, managedCustomer);

				session.setAttribute("user", object);
				session.setAttribute("msg", "your seat book successsfully");
				return "redirect:/home";

			} else {
				m.addAttribute("moviename", movieName);
				return "redirect:/booking-seat";
			}
		}
	}

	@PostMapping("/check")
	public String checkDate(@RequestParam("localdate") String date, @RequestParam("localtime") String time,
			HttpSession session, Model m) {
		LocalDate monthlimit = LocalDate.now();
		LocalDate now = LocalDate.parse(date);
		List<Seat> seat = dao.getAllByDate(now, time);
		List<String> seatNo = new ArrayList<String>();

		for (Seat s : seat) {
			for (String s1 : s.getSeatNo()) {
				seatNo.add(s1);
			}
		}
		m.addAttribute("seats", seatNo);
		session.setAttribute("bookingdate", now);
		session.setAttribute("bookingtime", time);
		m.addAttribute("date", now);
		m.addAttribute("time", time);
		m.addAttribute("min", monthlimit);
		m.addAttribute("max", monthlimit.plusMonths(1));

		return "dashbord";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");

		session.removeAttribute("bookingdate");
		session.removeAttribute("bookingtime");
		session.removeAttribute("movieName");

		return "redirect:/";
	}

	@GetMapping("/all-customer-records")
	public String getAllcust(HttpSession session, Model m) {
		Customer object = (Customer) session.getAttribute("user");
		if (object.getCust_id() == 1) {
			List<Customer> list = dao.getAllUser();

			m.addAttribute("list", list);
			m.addAttribute("menu", "alluser");
			return "alluser";
		} else {
			return "redirect:/booking-seat";
		}

	}

	@GetMapping("/order-history")
	public String history(HttpSession session, Model m) {
		Customer object = (Customer) session.getAttribute("user");
		int id = object.getCust_id();
		List<OrderHistory> list = dao.getHistory(id);
		m.addAttribute("hlist", list);
		m.addAttribute("menu", "order");
		return "orders";
	}

	@GetMapping("/view-seats/{id}")
	public String seatById(@PathVariable("id") int id, Model m, HttpSession session) {

		List<OrderHistory> list = dao.getHistory(id);
		m.addAttribute("hlist", list);
		m.addAttribute("menu", "alluser");
		return "orders";

	}

	@GetMapping("/setting")
	public String setting(HttpSession session, Model m) {
		Customer customer = (Customer) session.getAttribute("user");
		m.addAttribute("user", customer);

		m.addAttribute("menu", "settings");

		return "Settings";
	}

	@GetMapping("/setting/update/{id}")
	public String updateuser(@PathVariable("id") int id, Model m) {

		m.addAttribute("menu", "settings");

		return "update-details";
	}

	@PostMapping("/setting/update-userdetails")
	public String updatedetails(@ModelAttribute("Customer") Customer cust, HttpSession session, Model m) {
		Customer obj = (Customer) session.getAttribute("user");
		Customer object = dao.findCustomerById(obj.getCust_id());
		String name = cust.getName();
		String email = cust.getEmail();
		object.setName(name);
		object.setEmail(email);
		dao.saveuser(object);
		session.setAttribute("user", object);
		m.addAttribute("menu", "settings");

		return "redirect:/setting";
	}
}
