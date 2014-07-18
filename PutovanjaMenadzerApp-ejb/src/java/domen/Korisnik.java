/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sanja
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdKorisnik", query = "SELECT k FROM Korisnik k WHERE k.idKorisnik = :idKorisnik"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByPrezime", query = "SELECT k FROM Korisnik k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Korisnik.findByJmbg", query = "SELECT k FROM Korisnik k WHERE k.jmbg = :jmbg"),
    @NamedQuery(name = "Korisnik.findByNazivBicikla", query = "SELECT k FROM Korisnik k WHERE k.nazivBicikla = :nazivBicikla"),
    @NamedQuery(name = "Korisnik.findByTipBicikla", query = "SELECT k FROM Korisnik k WHERE k.tipBicikla = :tipBicikla"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByPassword", query = "SELECT k FROM Korisnik k WHERE k.password = :password"),
    @NamedQuery(name = "Korisnik.findByUsername", query = "SELECT k FROM Korisnik k WHERE k.username = :username"),
    @NamedQuery(name = "Korisnik.findByAktivan", query = "SELECT k FROM Korisnik k WHERE k.aktivan = :aktivan"),
    @NamedQuery(name = "Korisnik.findByAktivacionikod", query = "SELECT k FROM Korisnik k WHERE k.aktivacionikod = :aktivacionikod")})
public class Korisnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorisnik")
    private Integer idKorisnik;
    @Size(max = 45)
    @Column(name = "ime")
    private String ime;
    @Size(max = 45)
    @Column(name = "prezime")
    private String prezime;
    @Size(max = 13)
    @Column(name = "jmbg")
    private String jmbg;
    @Size(max = 45)
    @Column(name = "nazivBicikla")
    private String nazivBicikla;
    @Size(max = 15)
    @Column(name = "tipBicikla")
    private String tipBicikla;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Column(name = "aktivan")
    private Boolean aktivan;
    @Size(max = 9)
    @Column(name = "aktivacionikod")
    private String aktivacionikod;
    @JoinTable(name = "korisnikputovanje", joinColumns = {
        @JoinColumn(name = "idKorisnik", referencedColumnName = "idKorisnik")}, inverseJoinColumns = {
        @JoinColumn(name = "idPutovanje", referencedColumnName = "idPutovanje")})
    @ManyToMany
    private List<Putovanje> putovanjeList;
    @JoinColumn(name = "mestoID", referencedColumnName = "idMesto")
    @ManyToOne
    private Mesto mestoID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "biciklistaId")
    private List<Putovanje> putovanjeList1;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Korisnik(Integer idKorisnik, String email, String password, String username) {
        this.idKorisnik = idKorisnik;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Integer getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getNazivBicikla() {
        return nazivBicikla;
    }

    public void setNazivBicikla(String nazivBicikla) {
        this.nazivBicikla = nazivBicikla;
    }

    public String getTipBicikla() {
        return tipBicikla;
    }

    public void setTipBicikla(String tipBicikla) {
        this.tipBicikla = tipBicikla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getAktivacionikod() {
        return aktivacionikod;
    }

    public void setAktivacionikod(String aktivacionikod) {
        this.aktivacionikod = aktivacionikod;
    }

    @XmlTransient
    public List<Putovanje> getPutovanjeList() {
        return putovanjeList;
    }

    public void setPutovanjeList(List<Putovanje> putovanjeList) {
        this.putovanjeList = putovanjeList;
    }

    public Mesto getMestoID() {
        return mestoID;
    }

    public void setMestoID(Mesto mestoID) {
        this.mestoID = mestoID;
    }

    @XmlTransient
    public List<Putovanje> getPutovanjeList1() {
        return putovanjeList1;
    }

    public void setPutovanjeList1(List<Putovanje> putovanjeList1) {
        this.putovanjeList1 = putovanjeList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorisnik != null ? idKorisnik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idKorisnik == null && other.idKorisnik != null) || (this.idKorisnik != null && !this.idKorisnik.equals(other.idKorisnik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.Korisnik[ idKorisnik=" + idKorisnik + " ]";
    }
    
}
