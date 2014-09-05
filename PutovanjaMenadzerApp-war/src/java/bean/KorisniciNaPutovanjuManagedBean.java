/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import domen.Mesto;
import domen.Putovanje;
import ejb.KorisnikSessionBeanLocal;
import ejb.PutovanjeSessionBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "korisniciNaPutovanjuManagedBean")
@ViewScoped
public class KorisniciNaPutovanjuManagedBean {

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    @EJB
    private KorisnikSessionBeanLocal korisnikSessionBean;

    List<Korisnik> korisniciNaPutovanju;

    @ManagedProperty("#{svaPutovanjaManagedBean}")
    private SvaPutovanjaManagedBean svaPutovanjaManagedBean;

    Putovanje putovanje;

    private DualListModel<Korisnik> korisnici;

    /**
     * Creates a new instance of KorisniciNaPutovanjuManagedBean
     */
    public KorisniciNaPutovanjuManagedBean() {

    }

    @PostConstruct
    public void init() {
        korisniciNaPutovanju = new ArrayList<>();
        putovanje = svaPutovanjaManagedBean.getSelektovanoPutovanje();
        List<Korisnik> izvornaKorisnici = new ArrayList<>();
        List<Korisnik> odabraniKorisnici = new ArrayList<>();

        odabraniKorisnici = putovanje.getKorisnikList();

        izvornaKorisnici = korisnikSessionBean.sviKorisnici();
        for (Korisnik korisnik : odabraniKorisnici) {
            izvornaKorisnici.remove(korisnik);
        }
        korisnici = new DualListModel<>(izvornaKorisnici, odabraniKorisnici);
    }

    public void registrujKorisnike() {

        List<Korisnik> dodatiKorisnici = korisnici.getTarget();

        putovanje.setKorisnikList(new ArrayList<Korisnik>());

        for (Korisnik korisnik : dodatiKorisnici) {
            if (korisnik.getPutovanjeList() == null) {
                korisnik.setPutovanjeList(new ArrayList<Putovanje>());

            }
            korisnik.getPutovanjeList().add(putovanje);

        }
        putovanje.setKorisnikList(dodatiKorisnici);

        putovanje = putovanjeSessionBean.dodjListuKorsnika(putovanje);
        FacesMessage message = new FacesMessage("Dodati korisnici");
        FacesContext.getCurrentInstance().addMessage(null, message);
             
    }

    public void setSvaPutovanjaManagedBean(SvaPutovanjaManagedBean svaPutovanjaManagedBean) {
        this.svaPutovanjaManagedBean = svaPutovanjaManagedBean;
    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public DualListModel<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(DualListModel<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

}
