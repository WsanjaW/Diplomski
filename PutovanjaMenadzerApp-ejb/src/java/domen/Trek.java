/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sanja
 */
@Entity
@Table(name = "trek")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trek.findAll", query = "SELECT t FROM Trek t"),
    @NamedQuery(name = "Trek.findByIdPutovanje", query = "SELECT t FROM Trek t WHERE t.trekPK.idPutovanje = :idPutovanje"),
    @NamedQuery(name = "Trek.findByIdTrek", query = "SELECT t FROM Trek t WHERE t.trekPK.idTrek = :idTrek"),
    @NamedQuery(name = "Trek.findByNaziv", query = "SELECT t FROM Trek t WHERE t.naziv = :naziv"),
    @NamedQuery(name = "Trek.findByKilometraza", query = "SELECT t FROM Trek t WHERE t.kilometraza = :kilometraza"),
    @NamedQuery(name = "Trek.findByVreme", query = "SELECT t FROM Trek t WHERE t.vreme = :vreme"),
    @NamedQuery(name = "Trek.findByProsecnaBrzina", query = "SELECT t FROM Trek t WHERE t.prosecnaBrzina = :prosecnaBrzina"),
    @NamedQuery(name = "Trek.findByUkupanUspon", query = "SELECT t FROM Trek t WHERE t.ukupanUspon = :ukupanUspon")})
public class Trek implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TrekPK trekPK;
    @Size(max = 245)
    @Column(name = "naziv")
    private String naziv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kilometraza")
    private Double kilometraza;
    @Column(name = "vreme")
    private Long vreme;
    @Column(name = "prosecnaBrzina")
    private Double prosecnaBrzina;
    @Column(name = "ukupanUspon")
    private Double ukupanUspon;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trek")
    private List<Wp> wpList;
    @JoinColumn(name = "idPutovanje", referencedColumnName = "idPutovanje", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Putovanje putovanje;

    public Trek() {
    }

    public Trek(TrekPK trekPK) {
        this.trekPK = trekPK;
    }

    public Trek(int idPutovanje, int idTrek) {
        this.trekPK = new TrekPK(idPutovanje, idTrek);
    }

    public TrekPK getTrekPK() {
        return trekPK;
    }

    public void setTrekPK(TrekPK trekPK) {
        this.trekPK = trekPK;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getKilometraza() {
        return kilometraza;
    }

    public void setKilometraza(Double kilometraza) {
        this.kilometraza = kilometraza;
    }

    public Long getVreme() {
        return vreme;
    }

    public void setVreme(Long vreme) {
        this.vreme = vreme;
    }

    public Double getProsecnaBrzina() {
        return prosecnaBrzina;
    }

    public void setProsecnaBrzina(Double prosecnaBrzina) {
        this.prosecnaBrzina = prosecnaBrzina;
    }

    public Double getUkupanUspon() {
        return ukupanUspon;
    }

    public void setUkupanUspon(Double ukupanUspon) {
        this.ukupanUspon = ukupanUspon;
    }

    @XmlTransient
    public List<Wp> getWpList() {
        return wpList;
    }

    public void setWpList(List<Wp> wpList) {
        this.wpList = wpList;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trekPK != null ? trekPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trek)) {
            return false;
        }
        Trek other = (Trek) object;
        if ((this.trekPK == null && other.trekPK != null) || (this.trekPK != null && !this.trekPK.equals(other.trekPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Trek[ trekPK=" + trekPK + " ]";
    }

    public String vremeString() {
        long v = vreme;

        v = v / 1000;
        long h = v / 3600;
        v = v % 3600;
        long m = v / 60;
        v = v % 60;
        long s = v;
        String hs = String.valueOf(h);
        String ms = String.valueOf(m);
        String ss = String.valueOf(s);
        
        return String.format("%02d%n:%02d%n:%02d%n",h,m,v);

    }

}
