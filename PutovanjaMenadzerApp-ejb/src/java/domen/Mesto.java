/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sanja
 */
@Entity
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdMesto", query = "SELECT m FROM Mesto m WHERE m.idMesto = :idMesto"),
    @NamedQuery(name = "Mesto.findByPttBroj", query = "SELECT m FROM Mesto m WHERE m.pttBroj = :pttBroj"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "Mesto.findByLon", query = "SELECT m FROM Mesto m WHERE m.lon = :lon"),
    @NamedQuery(name = "Mesto.findByLat", query = "SELECT m FROM Mesto m WHERE m.lat = :lat")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Basic(optional = false)
    @Column(name = "idMesto")
    private Integer idMesto;
    @Size(max = 45)
    @Column(name = "pttBroj")
    private String pttBroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "lon")
    private Short lon;
    @Column(name = "lat")
    private Short lat;
    
    @ManyToMany(mappedBy = "mestoList")
    private List<Putovanje> mestoputovanjeList;
    @OneToMany(mappedBy = "mestoID")
    private List<Korisnik> korisnikList;

    public Mesto() {
    }

    public Mesto(Integer idMesto) {
        this.idMesto = idMesto;
    }

    public Mesto(Integer idMesto, String naziv) {
        this.idMesto = idMesto;
        this.naziv = naziv;
    }

    public Integer getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Integer idMesto) {
        this.idMesto = idMesto;
    }

    public String getPttBroj() {
        return pttBroj;
    }

    public void setPttBroj(String pttBroj) {
        this.pttBroj = pttBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Short getLon() {
        return lon;
    }

    public void setLon(Short lon) {
        this.lon = lon;
    }

    public Short getLat() {
        return lat;
    }

    public void setLat(Short lat) {
        this.lat = lat;
    }

    @XmlTransient
    public List<Putovanje> getMestoputovanjeList() {
        return mestoputovanjeList;
    }

    public void setMestoputovanjeList(List<Putovanje> mestoputovanjeList) {
        this.mestoputovanjeList = mestoputovanjeList;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMesto != null ? idMesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idMesto == null && other.idMesto != null) || (this.idMesto != null && !this.idMesto.equals(other.idMesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv;
    }

}
