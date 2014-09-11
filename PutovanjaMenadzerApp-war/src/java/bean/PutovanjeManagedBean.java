/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import domen.Mesto;
import domen.Putovanje;
import domen.Trek;
import domen.TrekPK;
import ejb.MestoSessionBeanLocal;
import ejb.PutovanjeSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "putovanjeManagedBean")
@ViewScoped
public class PutovanjeManagedBean implements Serializable {

    @EJB
    private MestoSessionBeanLocal mestoSessionBean;

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    @ManagedProperty("#{logInManagedBean}")
    private LogInManagedBean log;

    @ManagedProperty("#{svaPutovanjaManagedBean}")
    private SvaPutovanjaManagedBean svaPutovanjaManagedBean;

    private Putovanje putovanje;
    private Trek trek;
    private List<Trek> trekovi;
    private UploadedFile fajl;

    private DualListModel<Mesto> mesta;

    /**
     * Creates a new instance of PutovanjeManagedBean
     */
    public PutovanjeManagedBean() {
    }

    @PostConstruct
    public void init() {
        trek = new Trek();
        trek.setTrekPK(new TrekPK());

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        //ako se se manage bean koristi sa stranice dodajputovanje.xhtml
        //ond je potrebno napraviti novo putovanje
        //u supretom za putovanje postavljamo putovanje iz sesije
        if ("/dodajputovanje.xhtml".equals(context.getRequestPathInfo())) {
            putovanje = new Putovanje();
            trekovi = new LinkedList<>();
            List<Mesto> izvornaMesta = new ArrayList<>();
            List<Mesto> odabranaMesta = new ArrayList<>();
            izvornaMesta = mestoSessionBean.svaMesta();
            mesta = new DualListModel<>(izvornaMesta, odabranaMesta);
        } else {
            putovanje = svaPutovanjaManagedBean.getSelektovanoPutovanje();
        }

    }

    /**
     * Brise putovanje iz baze
     */
    public void obrisiPutovanje() {

        try {
            putovanjeSessionBean.obrisiPutovanje(putovanje);
            svaPutovanjaManagedBean.getPutovanja().remove(putovanje);
            FacesMessage message = new FacesMessage("Putovanje " + putovanje.getNaziv() + " izbrisano");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Putovanje " + putovanje.getNaziv() + " ne moze biti izbrisano");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    /**
     * Izmena podataka o putovanju
     */
    public void izmeniPutovanje() {
        try {
            putovanje = putovanjeSessionBean.izmeniPutovanje(putovanje);
            FacesMessage message = new FacesMessage("Putovanje uspesno izmenjeno");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Greska pri izmeni putovanja");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /**
     * Cuva putovanje u bazi
     */
    public void sacuvajPutovanje() {
        
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {

            Korisnik k = log.korisnik;

            List<Mesto> izabranaMesta = mesta.getTarget();
            putovanje.setMestoList(new ArrayList<Mesto>());
            putovanje.setMestoList(izabranaMesta);

            putovanje.setBiciklistaId(k);
            putovanje.setTrekList(trekovi);

            putovanjeSessionBean.sacuvajPutovanje(putovanje);
            //dodaj putovanje u sesiju
            svaPutovanjaManagedBean.getPutovanja().add(putovanje);

            FacesMessage message = new FacesMessage("Putovanje dodato " + putovanje.getNaziv());
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            context.redirect("faces/svaputovanja.xhtml");
        } catch (Exception ex) {
            
            FacesMessage message = new FacesMessage("Greska pri unosu putovanja");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public Putovanje getPutovanje() {
        return putovanje;
    }

    public void setPutovanje(Putovanje putovanje) {
        this.putovanje = putovanje;
    }

    public List<Trek> getTrekovi() {
        return trekovi;
    }

    public void setTrekovi(List<Trek> trekovi) {
        this.trekovi = trekovi;
    }

    public Trek getTrek() {
        return trek;
    }

    public void setTrek(Trek trek) {
        this.trek = trek;
    }

    public UploadedFile getFajl() {
        return fajl;
    }

    public void setFajl(UploadedFile fajl) {
        this.fajl = fajl;
    }

    public void setLog(LogInManagedBean log) {
        this.log = log;
    }

    public void setMesta(DualListModel<Mesto> mesta) {
        this.mesta = mesta;
    }

    public DualListModel<Mesto> getMesta() {
        return mesta;
    }

    public void setSvaPutovanjaManagedBean(SvaPutovanjaManagedBean svaPutovanjaManagedBean) {
        this.svaPutovanjaManagedBean = svaPutovanjaManagedBean;
    }

}
