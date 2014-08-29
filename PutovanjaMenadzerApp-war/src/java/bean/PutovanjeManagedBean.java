/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import domen.Putovanje;
import domen.Trek;
import domen.TrekPK;
import ejb.PutovanjeSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import obradafajla.ObradaFajla;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Sanja
 */
@Named(value = "putovanjeManagedBean")
@SessionScoped
public class PutovanjeManagedBean implements Serializable {

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    private Putovanje putovanje;
    private Trek trek;
    private List<Trek> trekovi;
    private UploadedFile fajl;
    private Trek t;

    /**
     * Creates a new instance of PutovanjeManagedBean
     */
    public PutovanjeManagedBean() {
    }

    @PostConstruct
    public void init() {
        trek = new Trek();
        trek.setTrekPK(new TrekPK());
        t = new Trek();
        //putovanje = new Putovanje();
        trekovi = new LinkedList<>();
    }

    public void dodajTrek() {

        sacuvajPutovanje();

    }

    public void sacuvajPutovanje() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Korisnik k = (Korisnik) context.getSessionMap().get("user");
       // putovanje.setTrekList(trekovi);

        putovanje = (Putovanje) context.getSessionMap().get("putovanje");
        putovanje.setBiciklistaId(k);
        t.setNaziv(trek.getNaziv());
        putovanje.getTrekList().add(t);
        putovanjeSessionBean.izmeniPutovanje(putovanje);
        FacesMessage message = new FacesMessage(putovanje.getNaziv());
//        trek = new Trek();
//        trek.setTrekPK(new TrekPK());
//        putovanje = new Putovanje();
//        trekovi = new LinkedList<>();
//        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void handleFileUpload(FileUploadEvent event) {

        UploadedFile uFajl = event.getFile();

        ObradaFajla of = null;
        try {
            of = new ObradaFajla(uFajl.getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(PutovanjeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        t.setTrekPK(trek.getTrekPK());

        t.setKilometraza(of.getKilometraza());
        t.setWpList(of.getAllWp());

        FacesMessage message = new FacesMessage("Kilometraza " + t.getKilometraza());
        FacesContext.getCurrentInstance().addMessage(null, message);
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

}
