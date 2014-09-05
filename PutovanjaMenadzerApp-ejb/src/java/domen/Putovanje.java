/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sanja
 */
@Entity
@Table(name = "putovanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Putovanje.findAll", query = "SELECT p FROM Putovanje p"),
    @NamedQuery(name = "Putovanje.findByIdPutovanje", query = "SELECT p FROM Putovanje p WHERE p.idPutovanje = :idPutovanje"),
    @NamedQuery(name = "Putovanje.findByNaziv", query = "SELECT p FROM Putovanje p WHERE p.naziv = :naziv"),
    @NamedQuery(name = "Putovanje.findByDatumOd", query = "SELECT p FROM Putovanje p WHERE p.datumOd = :datumOd"),
    @NamedQuery(name = "Putovanje.findByDatumDo", query = "SELECT p FROM Putovanje p WHERE p.datumDo = :datumDo")})
public class Putovanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPutovanje")
    private Integer idPutovanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 245)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "datumOd")
    @Temporal(TemporalType.DATE)
    private Date datumOd;
    @Column(name = "datumDo")
    @Temporal(TemporalType.DATE)
    private Date datumDo;

    @JoinTable(name = "korisnikputovanje", joinColumns = {
        @JoinColumn(name = "idPutovanje", referencedColumnName = "idPutovanje")}, inverseJoinColumns = {
        @JoinColumn(name = "idKorisnik", referencedColumnName = "idKorisnik")})
    @ManyToMany
    private List<Korisnik> korisnikList;
    @JoinTable(name = "mestoputovanje", joinColumns = {
        @JoinColumn(name = "idPutovanje", referencedColumnName = "idPutovanje")}, inverseJoinColumns = {
        @JoinColumn(name = "idMesto", referencedColumnName = "idMesto")})
    @ManyToMany
    private List<Mesto> mestoList;
    @JoinColumn(name = "biciklistaId", referencedColumnName = "idKorisnik")
    @ManyToOne(optional = false)
    private Korisnik biciklistaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "putovanje")
    private List<Trek> trekList;

    public Putovanje() {
    }

    public Putovanje(Integer idPutovanje) {
        this.idPutovanje = idPutovanje;
    }

    public Putovanje(Integer idPutovanje, String naziv) {
        this.idPutovanje = idPutovanje;
        this.naziv = naziv;
    }

    public Integer getIdPutovanje() {
        return idPutovanje;
    }

    public void setIdPutovanje(Integer idPutovanje) {
        this.idPutovanje = idPutovanje;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @XmlTransient
    public List<Mesto> getMestoList() {
        return mestoList;
    }

    public void setMestoList(List<Mesto> mestoList) {
        this.mestoList = mestoList;
    }

    public Korisnik getBiciklistaId() {
        return biciklistaId;
    }

    public void setBiciklistaId(Korisnik biciklistaId) {
        this.biciklistaId = biciklistaId;
    }

    @XmlTransient
    public List<Trek> getTrekList() {
        return trekList;
    }

    public void setTrekList(List<Trek> trekList) {
        this.trekList = trekList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPutovanje != null ? idPutovanje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Putovanje)) {
            return false;
        }
        Putovanje other = (Putovanje) object;
        if ((this.idPutovanje == null && other.idPutovanje != null) || (this.idPutovanje != null && !this.idPutovanje.equals(other.idPutovanje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Putovanje[ idPutovanje=" + idPutovanje + " ]";
    }

    public double ukupnaKilometraza() {
        double d = 0.0;
        if (trekList != null) {
            for (Trek trek : trekList) {
                d += trek.getKilometraza();
            }
        }
        return d;
    }

    public double ukupanUspon() {
        double d = 0.0;
        if (trekList != null) {
            for (Trek trek : trekList) {
                d += trek.getUkupanUspon();
            }
        }
        return d;
    }

    public String ukupnoVreme() {
        long v = 0;
        if (trekList != null) {
            for (Trek trek : trekList) {
                v += trek.getVreme();
            }
        }
        v = v / 1000;
        long h = v / 3600;
        v = v % 3600;
        long m = v / 60;
        v = v % 60;
        long s = v;
        String hs = String.valueOf(h);
        String ms = String.valueOf(m);
        String ss = String.valueOf(s);
        System.out.println(hs + " " + ms + " " + ss);
       
        return String.format("%02d%n:%02d%n:%02d%n",h,m,v);
    }
    
    public String biciklistiNaPutovanju(){
        String s = "";
        for (Korisnik korisnik : korisnikList) {
            s += korisnik.getIme()+ ",";
        }
        if ("".equals(s)) {
            return s;
        }
        return s.substring(0, s.length()-1);
                
    }

}
