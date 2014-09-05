/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import domen.Korisnik;
import domen.Mesto;
import ejb.KorisnikSessionBean;
import ejb.KorisnikSessionBeanLocal;
import ejb.MestoSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Sanja
 */
@FacesConverter(value = "korisnikConverter")
public class KorisnikConverter implements Converter{
    
    KorisnikSessionBeanLocal korisnikSessionBean = lookupKorisnikSessionBeanLocal();

    

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            return korisnikSessionBean.pronadjiKorisnika(Integer.parseInt(value));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "" + ((Korisnik) value).getIdKorisnik();
    }

    private KorisnikSessionBeanLocal lookupKorisnikSessionBeanLocal() {
        try {
            Context c = new InitialContext();
            return (KorisnikSessionBeanLocal) c.lookup("java:global/PutovanjaMenadzerApp/PutovanjaMenadzerApp-ejb/KorisnikSessionBean!ejb.KorisnikSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
