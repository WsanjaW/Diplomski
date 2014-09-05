/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import domen.Mesto;
import ejb.KorisnikSessionBeanLocal;
import ejb.MestoSessionBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.model.TreeNode;
import util.Util;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "korisnikbean")
@RequestScoped
public class KorisnikManagedBean implements Serializable {

    @EJB
    private KorisnikSessionBeanLocal korisnikSessionBean;
    
    @EJB
    private MestoSessionBeanLocal mestoSessionBean;
                
    private Korisnik korisnik;
    private String mestoId;

    /**
     * Creates a new instance of KorisnikManagedBean
     */
    public KorisnikManagedBean() {
    }

    @PostConstruct
    public void init() {
        korisnik = new Korisnik();
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<Korisnik> vratiSveKorisnike() {
        return korisnikSessionBean.sviKorisnici();
    }

    public void sacuvajKorsinika(Korisnik k) {
        
        System.out.println(mestoId);
        korisnik.setMestoID(mestoSessionBean.getById(mestoId));
        String kod = Util.generisKod();
        korisnik.setAktivacionikod(kod);
        korisnik.setAktivan(false);
        korisnikSessionBean.sacuvajKorisnika(korisnik);
        Util.posaljiMail(korisnik.getEmail(),kod);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Korisnik unet", "Mejl za aktivaciju"));

    }

    public List<Mesto> svaMesta() {
        return mestoSessionBean.svaMesta();
    }

    public String getMestoId() {
        return mestoId;
    }

    public void setMestoId(String mestoId) {
        this.mestoId = mestoId;
    }

    public List<String> tipoviBicikla() {
        List<String> s = new ArrayList<>();
        s.add("MTB");
        s.add("Road");
        s.add("Hibrid");
        return s;
    }

    
    


}
