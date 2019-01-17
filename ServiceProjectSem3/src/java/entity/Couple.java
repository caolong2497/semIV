/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author chinam
 */
@Entity
@Table(name = "tbl_couple")
public class Couple implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coupleId")
    private int coupleID;
    @Column(name = "start")

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start;
    @Column(name = "image")
    private String image;

    public Couple(int coupleID, Date start, String image) {
        this.coupleID = coupleID;
        this.start = start;
        this.image = image;
    }

    public Couple() {
    }

    public int getCoupleID() {
        return coupleID;
    }

    public void setCoupleID(int coupleID) {
        this.coupleID = coupleID;
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
    
    
}
