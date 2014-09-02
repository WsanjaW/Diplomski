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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
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
@Named(value = "trekManagedBean")
@SessionScoped
public class TrekManagedBean implements Serializable{

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    private Trek trek;
    private UploadedFile fajl;

    /**
     * Creates a new instance of TrekManagedBean
     */
    public TrekManagedBean() {
    }

    @PostConstruct
    public void init() {
        trek = new Trek();
        trek.setTrekPK(new TrekPK());

    }

    public String dodajTrek() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Korisnik k = (Korisnik) context.getSessionMap().get("user");
        // putovanje.setTrekList(trekovi);

        Putovanje putovanje = (Putovanje) context.getSessionMap().get("putovanje");
        putovanje.setBiciklistaId(k);
        List<Trek> privTrekovi = new ArrayList<>();
      
        putovanje.getTrekList().add(trek);
        putovanjeSessionBean.izmeniPutovanje(putovanje);
        trek = new Trek();
        trek.setTrekPK(new TrekPK());
        context.getSessionMap().put("putovanje",putovanje);
        FacesMessage message = new FacesMessage(putovanje.getNaziv());
        return "jednoputovanje.xhtml";
    }

    public void handleFileUpload(FileUploadEvent event) {

        UploadedFile uFajl = event.getFile();

        ObradaFajla of = null;
        try {
            of = new ObradaFajla(uFajl.getInputstream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        trek.setKilometraza(of.getKilometraza());
        trek.setWpList(of.getAllWp());

        FacesMessage message = new FacesMessage("Kilometrazaaa " + trek.getKilometraza());
        FacesContext.getCurrentInstance().addMessage(null, message);
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
