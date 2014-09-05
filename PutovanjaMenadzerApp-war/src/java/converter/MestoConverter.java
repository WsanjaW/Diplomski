/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import domen.Mesto;
import ejb.MestoSessionBeanLocal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sanja
 */
@FacesConverter(value = "mestoConverter")
public class MestoConverter implements Converter {

    @EJB
    private MestoSessionBeanLocal mestoSessionBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !"".equals(value)) {
            return mestoSessionBean.getById(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return "" + ((Mesto) value).getIdMesto();
    }

}
