/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obradafajla;

import domen.Wp;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Obrada .gpx fajla TODO implement
 *
 * @author Sanja
 */
public class ObradaFajla {
    
    private InputStream fajl;
    private Double kilometraza;
    private Double prosecnaBrzina;
    private Double ukupanUspon;
    private long vreme;
    private List<Object> trekovi;
    private List<Wp> allWp;
    
    public ObradaFajla(InputStream fajl) {
        this.fajl = fajl;
        trekovi = new ArrayList<>();
        allWp = new ArrayList<>();
        napuni();
        obradi();
    }
    
    public Double getKilometraza() {
        return kilometraza;
    }
    
    public Double getProsecnaBrzina() {
        if (kilometraza == 0) {
            return 0.0;
        }
        if (vreme == 0) {
            return 0.0;
        }
        return kilometraza / ((vreme/1000) * 0.000277778);
    }
    
    public long getVreme() {
        return vreme;
    }
    
    public List<Wp> getAllWp() {
        return allWp;
    }
    
    public void napuni() {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(fajl);
            Element root = document.getRootElement();
            
            kilometraza = 0.0;
            vreme = 0;
            prosecnaBrzina = 0.0;

            // iterate through child elements of root with element name "trk"
            for (Iterator i = root.elementIterator("trk"); i.hasNext();) {
                Element trk = (Element) i.next();
                List<Wp> wpList = new LinkedList<>();
                for (Iterator<Element> i1 = trk.elementIterator("trkseg"); i1.hasNext();) {
                    Element trkseg = i1.next();
                    for (Iterator i2 = trkseg.elementIterator("trkpt"); i2.hasNext();) {
                        Element wp = (Element) i2.next();
//                        System.out.println(wp.attributeValue("lat"));
//                        System.out.println(wp.elementText("time"));
                        double lat = Double.parseDouble(wp.attributeValue("lat"));
                        double lon = Double.parseDouble(wp.attributeValue("lon"));
                        double elev = Double.parseDouble(wp.elementText("ele"));
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date time = (ft.parse(wp.elementText("time")));
                        //Date time = (ft.parse("2013-08-12T07:16:30.999Z"));
                      
                        wpList.add(new Wp(lat, lon, elev, time));
                        
                    }
                }
                System.out.println(wpList.size());
                trekovi.add(wpList);
            }
            
        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    
    public void obradi() {
        kilometraza = 0.0;
        ukupanUspon = 0.0;
        vreme = 0;
        //vreme = new Date(0);
        System.out.println(trekovi.size());
        for (Object object : trekovi) {
            
            LinkedList<Wp> list = (LinkedList<Wp>) object;
            Wp priv = list.getFirst();
            System.out.println(list.size());
            for (Wp wp : list.subList(1, list.size())) {
                allWp.add(wp);
                kilometraza += izracunajDistancu(wp, priv);
                double du = wp.getElev() - priv.getElev();
                if (du > 0.7) {
                    ukupanUspon += du;
                }
                long dif = wp.getTime().getTime() - priv.getTime().getTime();
                if (dif < 48000) {
                    vreme += dif;
                }
                
                priv = wp;
            }
            
        }
        
    }
    
    public double izracunajDistancu(Wp wp1, Wp wp2) {
        double total = 0.0;
        double lon1 = wp1.getLon();
        double lat1 = wp1.getLat();
        double lon2 = wp2.getLon();
        double lat2 = wp2.getLat();
        double dlon = Radians(lon2 - lon1);
        double dlat = Radians(lat2 - lat1);
        
        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) + Math.cos(Radians(lat1)) * Math.cos(Radians(lat2)) * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return angle * RADIUS;
        
    }
    final double PIx = 3.141592653589793;
    final double RADIUS = 6371;
    
    private double Radians(double x) {
        return x * PIx / 180;
    }

    public Double getUkupanUspon() {
        return ukupanUspon;
    }

    public void setUkupanUspon(Double ukupanUspon) {
        this.ukupanUspon = ukupanUspon;
    }

    
}
