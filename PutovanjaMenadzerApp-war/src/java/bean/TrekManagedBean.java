/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Putovanje;
import domen.Trek;
import domen.TrekPK;
import domen.Wp;
import domen.WpPK;
import ejb.PutovanjeSessionBeanLocal;
import ejb.TrekSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import obradafajla.ObradaFajla;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "trekManagedBean")
@ViewScoped
public class TrekManagedBean implements Serializable {

    @EJB
    private TrekSessionBeanLocal trekSessionBean;

    @ManagedProperty("#{svaPutovanjaManagedBean}")
    private SvaPutovanjaManagedBean svaPutovanjaManagedBean;

    private Trek trek;
    private String fajl;
    private boolean rendered;

    /**
     * Creates a new instance of TrekManagedBean
     */
    public TrekManagedBean() {
    }

    @PostConstruct
    public void init() {
        rendered = true;
        trek = new Trek();
        trek.setTrekPK(new TrekPK());
    }

    /**
     * Dodaje trek u selektovano putovanje i upisuje ga u bazu
     *
     * @return stranica
     */
    public String dodajTrek() {

        Putovanje putovanje = svaPutovanjaManagedBean.getSelektovanoPutovanje();

        try {
            //id poslednjeg treka
            int id = sledeciId(putovanje);

            //postavi parametre za trek
            trek.setPutovanje(putovanje);
            trek.setTrekPK(new TrekPK());
            trek.getTrekPK().setIdPutovanje(putovanje.getIdPutovanje());
            trek.getTrekPK().setIdTrek(id);

            //postavljanje id-eva za wp-te
            int j = 1;
            for (Wp wp : trek.getWpList()) {
                wp.setWpPK(new WpPK(j, trek.getTrekPK().getIdTrek(), trek.getTrekPK().getIdPutovanje()));
                j++;
            }
            //dodavanje treka putovanju
            putovanje.getTrekList().add(trek);
            //cuvanje treka u bazi
            trekSessionBean.dodajTrek(trek);

            return "jednoputovanje.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Greska pri dodavanju treka"));
            return "";
        }
    }

    /**
     * Handluje uploadovanje fajla, obradjuje fajl i popunjava potrebne
     * parametre za trek i za listu wp. Poziva se kada se fajl upladuje
     *
     * @param event
     */
    public void handleFileUpload(FileUploadEvent event) {

        //upladovan fajl
        UploadedFile uFajl = event.getFile();
        fajl = uFajl.getFileName();
        FacesMessage message;
        ObradaFajla of = null;
        try {

            of = new ObradaFajla(uFajl.getInputstream());
        } catch (IOException ex) {
            message = new FacesMessage("Greska pri citanju fajla");
            ex.printStackTrace();
        }

        try {
            trek = new Trek();
            trek.setKilometraza(of.getKilometraza());
            trek.setUkupanUspon(of.getUkupanUspon());
            trek.setVreme(of.getVreme());
            trek.setProsecnaBrzina(of.getProsecnaBrzina());
            trek.setWpList(of.getAllWp());
            rendered = false;
            message = new FacesMessage("Trek uspesno ucitan");
        } catch (Exception e) {
            message = new FacesMessage("Greska pri obradi fajla!");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //get i set metode
    public Trek getTrek() {
        return trek;
    }

    public void setTrek(Trek trek) {
        this.trek = trek;
    }

    public String getFajl() {
        return fajl;
    }

    public void setFajl(String fajl) {
        this.fajl = fajl;
    }

    public void setSvaPutovanjaManagedBean(SvaPutovanjaManagedBean svaPutovanjaManagedBean) {
        this.svaPutovanjaManagedBean = svaPutovanjaManagedBean;
    }

    public boolean isRenderd() {
        return rendered;
    }

    public void setRenderd(boolean renderd) {
        this.rendered = renderd;
    }

    private int sledeciId(Putovanje putovanje) {
        int max = 0;
        int id = 0;
        for (Trek trek1 : putovanje.getTrekList()) {
            if (trek1.getTrekPK().getIdTrek() > max) {
                max = trek1.getTrekPK().getIdTrek();
            }
        }
        id = max + 1;
        return id;
    }

}
