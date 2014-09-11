/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Mesto;
import domen.Putovanje;
import domen.Trek;
import domen.Wp;
import ejb.TrekSessionBeanLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;
import util.Util;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "prikazTrekaManagedBean")
@ViewScoped
public class PrikazTrekaManagedBean implements Serializable {

    @EJB
    private TrekSessionBeanLocal trekSessionBean;

    @ManagedProperty("#{svaPutovanjaManagedBean}")
    private SvaPutovanjaManagedBean svaPutovanjaMB;

    /**
     * Creates a new instance of PrikazTrekaManagedBean
     */
    public PrikazTrekaManagedBean() {
    }
    private MapModel polylineModel;
    private MapModel simpleModel;

    private CartesianChartModel profilVisineModel;

    private String kordinate;

    public void prikaziTrek() {

        
        polylineModel = new DefaultMapModel();
        simpleModel = new DefaultMapModel();
        Polyline polyline = new Polyline();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Putovanje selektovanoPutovanje = svaPutovanjaMB.getSelektovanoPutovanje();

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

                    polyline.setId(Integer.toString(trek.getTrekPK().getIdTrek()));
                    polyline.setStrokeWeight(3);
                    polyline.setStrokeColor(Util.generisiBoju());
                    polyline.setStrokeOpacity(1);
                    polyline.setData(trek.getNaziv());

                    polylineModel.addOverlay(polyline);

                }

            }
            for (Mesto mesto : selektovanoPutovanje.getMestoList()) {
                simpleModel.addOverlay(new Marker(new LatLng(mesto.getLat(), mesto.getLon()), mesto.getNaziv()));
            }

        }

    }

    public void prikazProfilaVisine() {
        profilVisineModel = new CartesianChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        profilVisineModel.addSeries(series1);
        
        profilVisineModel.setTitle("Linear Chart");
        profilVisineModel.setLegendPosition("e");
    }

    public MapModel getPolylineModel() {
        return polylineModel;
    }

    public void onPolylineSelect(OverlaySelectEvent event) {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Polyline Selected " + event.getOverlay().getData(), null));
    }

    public String getKordinate() {
        return kordinate;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSvaPutovanjaMB(SvaPutovanjaManagedBean svaPutovanjaMB) {
        this.svaPutovanjaMB = svaPutovanjaMB;
    }

    public CartesianChartModel getProfilVisineModel() {
        return profilVisineModel;
    }

    public void setProfilVisineModel(CartesianChartModel profilVisineModel) {
        this.profilVisineModel = profilVisineModel;
    }

}
