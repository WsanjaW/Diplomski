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

    public void obrisiPutovanje() {

        putovanjeSessionBean.obrisi(putovanje);
        svaPutovanjaManagedBean.getPutovanja().remove(putovanje);

    }

    public void izmeniPutovanje() {
        putovanje = putovanjeSessionBean.izmeniPutovanje(putovanje);
        FacesMessage message = new FacesMessage("Dodati korisnici");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void dodajTrek() {

        sacuvajPutovanje();

    }

    public void sacuvajPutovanje() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Korisnik k = log.korisnik;
        System.out.println(k);
       // Korisnik k = (Korisnik) context.getSessionMap().get("user");
        // putovanje.setTrekList(trekovi);

        // putovanje = (Putovanje) context.getSessionMap().get("putovanje");
        List<Mesto> izabranaMesta = mesta.getTarget();
        putovanje.setMestoList(new ArrayList<Mesto>());
        putovanje.setMestoList(izabranaMesta);

        putovanje.setBiciklistaId(k);
        putovanje.setTrekList(trekovi);
        putovanjeSessionBean.sacuvajPutovanje(putovanje);
        // t.setNaziv(trek.getNaziv());
        //putovanje.getTrekList().add(t);
        //putovanjeSessionBean.dodajTrek(putovanje);
        FacesMessage message = new FacesMessage(putovanje.getNaziv());
        svaPutovanjaManagedBean.getPutovanja().add(putovanje);

//        trek = new Trek();
//        trek.setTrekPK(new TrekPK());
//        putovanje = new Putovanje();
//        trekovi = new LinkedList<>();
        FacesContext.getCurrentInstance().addMessage(null, message);
        try {
            context.redirect("faces/svaputovanja.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PutovanjeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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
