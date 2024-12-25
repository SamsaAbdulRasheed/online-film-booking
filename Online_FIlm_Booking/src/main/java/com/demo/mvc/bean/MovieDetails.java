package com.demo.mvc.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies_details")
public class MovieDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long movie_id;

	private String movie_name;

	private String image;

	private String movie_details;

	public long getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(long movie_id) {
		this.movie_id = movie_id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMovie_details() {
		return movie_details;
	}

	public void setMovie_details(String movie_details) {
		this.movie_details = movie_details;
	}

	@Override
	public String toString() {
		return "MovieDeatails [movie_id=" + movie_id + ", movie_name=" + movie_name + ", image=" + image
				+ ", movie_details=" + movie_details + "]";
	}

}
