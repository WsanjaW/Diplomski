/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Korisnik;
import ejb.KorisnikSessionBeanLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.Util;

/**
 *
 * @author Sanja
 */
@Named(value = "aktivacijaManagedBean")
@RequestScoped
public class AktivacijaManagedBean {

    @EJB
    private KorisnikSessionBeanLocal korisnikSessionBean;

    String username;
    String email;
    boolean prikazi;

    /**
     * Creates a new instance of AktivacijaManagedBean
     */
    public AktivacijaManagedBean() {
    }

    public void posaljiMejl() {
        Korisnik k = korisnikSessionBean.pronadjiKorisnikaMail(email);
        if (k != null) {
            String kod = Util.generisKod();
            k.setAktivan(false);
            k.setAktivacionikod(kod);
            korisnikSessionBean.promeniKorisnika(k);
            Util.posaljiMail(email, kod);
            prikazi = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Mejl poslat"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Korisnik sa tim mejlom ne postoji"));
        }

    }

    public String getUsername() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String kod = (String) facesContext.getExternalContext().
                getRequestParameterMap().get("key");
        Korisnik k = korisnikSessionBean.pronadjiKorisnikaPoKodu(kod);
        if (k != null) {
            if (k.getAktivan()) {
                prikazi = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Korisnik je vec aktiviran"));
            } else {
                prikazi = false;
                k.setAktivan(true);
                korisnikSessionBean.promeniKorisnika(k);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Korisnik " + k.getUsername() + " je uspesno aktiviran"));
            }
        } else {
            prikazi = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Aktivacija", "Aktivacioni kod ne postoji, posalji mejl za ponovnu aktivaciju"));
        }

        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrikazi() {
        return prikazi;
    }

    public void setPrikazi(boolean prikazi) {
        this.prikazi = prikazi;
    }

}
