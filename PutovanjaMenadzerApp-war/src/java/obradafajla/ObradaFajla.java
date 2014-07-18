/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obradafajla;

import java.io.File;
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

    private File fajl;
    private Double kilometraza;
    private Double prosecnaBrzina;
    private long vreme;
    private List<Object> trekovi;

    public ObradaFajla(File fajl) {
        this.fajl = fajl;
        trekovi = new ArrayList<>();
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
        return kilometraza / (vreme * 0.000277778);
    }

    public long getVreme() {
        return vreme;
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
                List<WP> wpList = new LinkedList<>();
                for (Iterator<Element> i1 = trk.elementIterator("trkseg"); i1.hasNext();) {
                    Element trkseg = i1.next();
                    for (Iterator i2 = trkseg.elementIterator("trkpt"); i2.hasNext();) {
                        Element wp = (Element) i2.next();
                        System.out.println(wp.attributeValue("lat"));
                        System.out.println(wp.elementText("time"));
                        double lat = Double.parseDouble(wp.attributeValue("lat"));
                        double lon = Double.parseDouble(wp.attributeValue("lon"));
                        // double elev = Double.parseDouble(wp.elementText("ele"));
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date time = (ft.parse(wp.elementText("time")));
                        //  Date time = (ft.parse("2013-08-12T07:16:30.999Z"));

                        wpList.add(new WP(lat, lon, 0.0, time));

                    }
                }
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

        vreme = 0;
        //vreme = new Date(0);
        
        for (Object object : trekovi) {
            LinkedList<WP> list = (LinkedList<WP>) object;
            WP priv = list.getFirst();
            for (WP wp : list.subList(1, list.size())) {
                kilometraza += izracunajDistancu(wp, priv);
                long dif = wp.getTime().getTime() - priv.getTime().getTime();
                if (dif < 48000) {
                    vreme += dif;
                }
                priv = wp;
            }

        }


    }

    public double izracunajDistancu(WP wp1, WP wp2) {
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

    public static void main(String[] args) {
        ObradaFajla o = new ObradaFajla(new File("SokoBanja-Nis.gpx"));
        o.napuni();
        o.obradi();
        System.out.println("gdgfgf");
        System.out.println(o.kilometraza);
        long diff = o.vreme;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        System.out.println(diffHours + ":" + diffMinutes + ":" + diffSeconds);
    }
}
