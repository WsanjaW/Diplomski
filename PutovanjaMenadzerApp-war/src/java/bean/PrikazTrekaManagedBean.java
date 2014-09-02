/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Putovanje;
import domen.Trek;
import domen.Wp;
import ejb.TrekSessionBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author Sanja
 */
@Named(value = "prikazTrekaManagedBean")
@RequestScoped
public class PrikazTrekaManagedBean implements Serializable {

    @EJB
    private TrekSessionBeanLocal trekSessionBean;

    /**
     * Creates a new instance of PrikazTrekaManagedBean
     */
    public PrikazTrekaManagedBean() {
    }
    private MapModel polylineModel;
    private String kordinate;

    public void prikaziTrek() {
        polylineModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Putovanje selektovanoPutovanje = (Putovanje) context.getSessionMap().get("putovanje");

        if (selektovanoPutovanje != null) {
            for (Trek trek : selektovanoPutovanje.getTrekList()) {
                List<Wp> wps = trekSessionBean.listaWp(trek);
                polyline = new Polyline();
                if (wps != null && wps.size() > 0) {
                    //Polyline
                    Wp middle = wps.get((wps.size() / 2) - 1);
                    kordinate = middle.getLat() + "," + middle.getLon();
                    for (Wp wp : wps) {
                        LatLng coord = new LatLng(wp.getLat(), wp.getLon());
                        polyline.getPaths().add(coord);
                    }

                    polyline.setStrokeWeight(3);
                    polyline.setStrokeColor("#ED28DD");
                    polyline.setStrokeOpacity(1);
                    polyline.setData(trek.getNaziv());

                    polylineModel.addOverlay(polyline);

                }

            }

        }

    }

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public void onPolylineSelect(OverlaySelectEvent event) {
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Polyline Selected" + event.getOverlay().getData(), null));
    }

    public String getKordinate() {
        return kordinate;
    }

}
