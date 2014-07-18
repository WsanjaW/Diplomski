/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obradafajla;

import java.util.Date;

/**
 *
 * @author Sanja
 */
public class WP {

    private double lat;
    private double lon;
    private double elev;
    private Date time;

    public WP() {
    }

    public WP(double lat, double lon, double elev, Date time) {
        this.lat = lat;
        this.lon = lon;
        this.elev = elev;
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getElev() {
        return elev;
    }

    public void setElev(double elev) {
        this.elev = elev;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    
}
