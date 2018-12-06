package app.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_couple")
public class Couple {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int coupleId ;
	Date start;
	String image;
	public Couple() {
		super();
	}
	public int getCoupleId() {
		return coupleId;
	}
	public void setCoupleId(int coupleId) {
		this.coupleId = coupleId;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Couple(int coupleId, Date start, String image) {
		super();
		this.coupleId = coupleId;
		this.start = start;
		this.image = image;
	}
	
}
