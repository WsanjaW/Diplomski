/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import domen.Putovanje;
import domen.Trek;
import domen.Wp;
import ejb.PutovanjeSessionBeanLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Sanja
 */
@ManagedBean(name = "visinaBean")
@ViewScoped
public class VisinaBean {

    @EJB
    private PutovanjeSessionBeanLocal putovanjeSessionBean;

    private LineChartModel model;

    @PostConstruct
    public void init() {
        model = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        List<Putovanje> putovanja = putovanjeSessionBean.vratiPutovanja();
        series1.setLabel("Series 1");
        Putovanje p = putovanja.get(0);
        Trek t = p.getTrekList().get(1);
        List<Wp> wplist = t.getWpList();
        int i = 1;
        double km = 0;
        for (Wp wp : wplist) {
            series1.set(i, wp.getElev());
            i++;
        }

        model.addSeries(series1);

        model.setTitle("Linear Chart");
        model.setLegendPosition("e");
    }

    public LineChartModel getModel() {
        return model;
    }
}
