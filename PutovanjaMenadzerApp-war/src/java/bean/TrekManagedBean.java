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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import obradafajla.ObradaFajla;
import org.primefaces.context.RequestContext;
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
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    @ManagedProperty("#{logInManagedBean}")
    private LogInManagedBean log;

    @ManagedProperty("#{svaPutovanjaManagedBean}")
    private SvaPutovanjaManagedBean svaPutovanjaManagedBean;

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
        Korisnik k = log.korisnik;
        //  Korisnik k = (Korisnik) context.getSessionMap().get("user");
        // putovanje.setTrekList(trekovi);

        Putovanje putovanje = svaPutovanjaManagedBean.getSelektovanoPutovanje();
        putovanje.setBiciklistaId(k);

        putovanje.getTrekList().add(trek);
        putovanjeSessionBean.dodajTrek(putovanje);
        trek = new Trek();
        trek.setTrekPK(new TrekPK());
        context.getSessionMap().put("putovanje", putovanje);
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
        trek = new Trek();
        trek.setKilometraza(of.getKilometraza());
        trek.setUkupanUspon(of.getUkupanUspon());
        trek.setVreme(of.getVreme());

        trek.setProsecnaBrzina(of.getProsecnaBrzina());

        trek.setWpList(of.getAllWp());

        FacesMessage message = new FacesMessage("Kilometrazaaa " + trek.getKilometraza());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private Integer progress;

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + (int) (Math.random() * 35);

            if (progress > 100) {
                progress = 100;
            }
        }

        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Progress Completed"));
    }

    public void cancel() {
        progress = null;
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

    public void setSvaPutovanjaManagedBean(SvaPutovanjaManagedBean svaPutovanjaManagedBean) {
        this.svaPutovanjaManagedBean = svaPutovanjaManagedBean;
    }

}
