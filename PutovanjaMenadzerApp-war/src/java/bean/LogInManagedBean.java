/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import ejb.KorisnikSessionBeanLocal;
import ejb.MestoSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "logInManagedBean")
@SessionScoped
public class LogInManagedBean implements Serializable {

    @EJB
    private KorisnikSessionBeanLocal korisnikSessionBean;
    
    @EJB
    private MestoSessionBeanLocal mestoSessionBean;

    private String username = "";
    private String password = "";
    Korisnik korisnik;
    private String mestoId;

    /**
     * Creates a new instance of LogInManagedBean
     */
    public LogInManagedBean() {
    }

    public void login() {

        korisnik = korisnikSessionBean.pronadjiKorisnika(username);
        if (korisnik == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ne postoji korisnik"));
        } else {
            korisnik = korisnikSessionBean.pronadjiKorisnika(username, password);
            if (korisnik == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Los password"));
            } else if (korisnik.getAktivan()) {
                try {

                    ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                    context.redirect(context.getRequestContextPath() + "/faces/svaputovanja.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(LogInManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Korinik nije aktivan. Aktivirajte nalog"));
            }
        }

    }

    public void logout() {
        username = "";
        password = "";
        korisnik = null;
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            //     context.getSessionMap().put("user", korisnik);
            context.redirect(context.getRequestContextPath() + "/");
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    
    public void izmeniPodatke(){
        korisnikSessionBean.promeniKorisnika(korisnik);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Korisnik promenjen", ""));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String getMestoId() {
        return korisnik.getMestoID().getIdMesto().toString();
    }

    public void setMestoId(String mestoId) {
        this.korisnik.setMestoID(mestoSessionBean.getById(mestoId));
    }

  
    

}
