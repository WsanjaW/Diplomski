/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sanja
 */
@Embeddable
public class WpPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idWP")
    private int idWP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idTrek")
    private int idTrek;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPutovanje")
    private int idPutovanje;

    public WpPK() {
    }

    public WpPK(int idWP, int idTrek, int idPutovanje) {
        this.idWP = idWP;
        this.idTrek = idTrek;
        this.idPutovanje = idPutovanje;
    }

    public int getIdWP() {
        return idWP;
    }

    public void setIdWP(int idWP) {
        this.idWP = idWP;
    }

    public int getIdTrek() {
        return idTrek;
    }

    public void setIdTrek(int idTrek) {
        this.idTrek = idTrek;
    }

    public int getIdPutovanje() {
        return idPutovanje;
    }

    public void setIdPutovanje(int idPutovanje) {
        this.idPutovanje = idPutovanje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idWP;
        hash += (int) idTrek;
        hash += (int) idPutovanje;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WpPK)) {
            return false;
        }
        WpPK other = (WpPK) object;
        if (this.idWP != other.idWP) {
            return false;
        }
        if (this.idTrek != other.idTrek) {
            return false;
        }
        if (this.idPutovanje != other.idPutovanje) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.WpPK[ idWP=" + idWP + ", idTrek=" + idTrek + ", idPutovanje=" + idPutovanje + " ]";
    }
    
}
