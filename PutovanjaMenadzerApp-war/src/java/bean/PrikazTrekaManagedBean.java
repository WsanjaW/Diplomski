/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
@SessionScoped
public class PrikazTrekaManagedBean implements Serializable {

    /**
     * Creates a new instance of PrikazTrekaManagedBean
     */
    public PrikazTrekaManagedBean() {
    }
    private MapModel polylineModel;
  
    @PostConstruct
    public void init() {
        polylineModel = new DefaultMapModel();
          
        //Shared coordinates
        LatLng coord1 = new LatLng(36.879466, 30.667648);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
      
        //Polyline
        Polyline polyline = new Polyline();
        polyline.getPaths().add(coord1);
        polyline.getPaths().add(coord2);
        polyline.getPaths().add(coord3);
        polyline.getPaths().add(coord4);
          
        polyline.setStrokeWeight(10);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);
          
        polylineModel.addOverlay(polyline);
    }
  
    public MapModel getPolylineModel() {
        return polylineModel;
    }
  
    public void onPolylineSelect(OverlaySelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Polyline Selected", null));
    }
}
