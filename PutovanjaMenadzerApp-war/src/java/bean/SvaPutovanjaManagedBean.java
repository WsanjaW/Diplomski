/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Putovanje;
import ejb.PutovanjeSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@ManagedBean(name = "svaPutovanjaManagedBean")
@SessionScoped
public class SvaPutovanjaManagedBean implements Serializable {

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    //lista svih putovanje
    private List<Putovanje> putovanja;
    
    //lista nakon pretrage
    private List<Putovanje> filtriranaPutovanja;

    private Putovanje selektovanoPutovanje;

    /**
     * Creates a new instance of SvaPutovanjaManagedBean
     */
    public SvaPutovanjaManagedBean() {
    }

    @PostConstruct
    public void init() {

        putovanja = putovanjeSessionBean.vratiPutovanja();

    }

    /**
     * Redirektuje na stranicu 
     * za prikaz jednog putovanja.
     */
    public void prikaziPutovanje() {
        ExternalContext contex = null;
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath() + "/stranice/jednoputovanje.xhtml");
            
        } catch (IOException ex) {
             FacesMessage message= new FacesMessage("Greska u prikazu putovanja");
             FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    //get i set metode
    public List<Putovanje> getPutovanja() {
        //putovanja = putovanjeSessionBean.vratiPutovanja();
        return putovanja;
    }

    public void setPutovanja(List<Putovanje> putovanja) {
        this.putovanja = putovanja;
    }

    public List<Putovanje> getFiltriranaPutovanja() {
        return filtriranaPutovanja;
    }

    public void setFiltriranaPutovanja(List<Putovanje> filtriranaPutovanja) {
        this.filtriranaPutovanja = filtriranaPutovanja;
    }

    public Putovanje getSelektovanoPutovanje() {
        return selektovanoPutovanje;
    }

    public void setSelektovanoPutovanje(Putovanje selektovanoPutovanje) {
        this.selektovanoPutovanje = selektovanoPutovanje;
    }

}
