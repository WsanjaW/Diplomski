/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sanja
 */
@Entity
@Table(name = "wp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wp.findAll", query = "SELECT w FROM Wp w"),
    @NamedQuery(name = "Wp.findByIdWP", query = "SELECT w FROM Wp w WHERE w.wpPK.idWP = :idWP"),
    @NamedQuery(name = "Wp.findByIdTrek", query = "SELECT w FROM Wp w WHERE w.wpPK.idTrek = :idTrek"),
    @NamedQuery(name = "Wp.findByIdPutovanje", query = "SELECT w FROM Wp w WHERE w.wpPK.idPutovanje = :idPutovanje"),
    @NamedQuery(name = "Wp.findByLon", query = "SELECT w FROM Wp w WHERE w.lon = :lon"),
    @NamedQuery(name = "Wp.findByLat", query = "SELECT w FROM Wp w WHERE w.lat = :lat"),
    @NamedQuery(name = "Wp.findByElev", query = "SELECT w FROM Wp w WHERE w.elev = :elev"),
    @NamedQuery(name = "Wp.findByTime", query = "SELECT w FROM Wp w WHERE w.time = :time")})
public class Wp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WpPK wpPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lon")
    private Double lon;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "elev")
    private Double elev;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumns({
        @JoinColumn(name = "idPutovanje", referencedColumnName = "idPutovanje", insertable = false, updatable = false),
        @JoinColumn(name = "idTrek", referencedColumnName = "idTrek", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Trek trek;

    public Wp() {
    }

    public Wp(WpPK wpPK) {
        this.wpPK = wpPK;
    }

    public Wp(int idWP, int idTrek, int idPutovanje) {
        this.wpPK = new WpPK(idWP, idTrek, idPutovanje);
    }

    public WpPK getWpPK() {
        return wpPK;
    }

    public void setWpPK(WpPK wpPK) {
        this.wpPK = wpPK;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getElev() {
        return elev;
    }

    public void setElev(Double elev) {
        this.elev = elev;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Trek getTrek() {
        return trek;
    }

    public void setTrek(Trek trek) {
        this.trek = trek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wpPK != null ? wpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wp)) {
            return false;
        }
        Wp other = (Wp) object;
        if ((this.wpPK == null && other.wpPK != null) || (this.wpPK != null && !this.wpPK.equals(other.wpPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Wp[ wpPK=" + wpPK + " ]";
    }
    
}
