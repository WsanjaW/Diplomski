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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Sanja
 */
@Named(value = "svaPutovanjaManagedBean")
@RequestScoped
public class SvaPutovanjaManagedBean implements Serializable {

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    private List<Putovanje> putovanja;
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
   
    public void prikaziPutovanje() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.getSessionMap().put("putovanje", selektovanoPutovanje);
            context.redirect(context.getRequestContextPath() + "/faces/jednoputovanje.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SvaPutovanjaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
