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
public class TrekPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPutovanje")
    private int idPutovanje;
    @Basic(optional = false)
    @Column(name = "idTrek")
    private int idTrek;

    public TrekPK() {
    }

    public TrekPK(int idPutovanje, int idTrek) {
        this.idPutovanje = idPutovanje;
        this.idTrek = idTrek;
    }

    public int getIdPutovanje() {
        return idPutovanje;
    }

    public void setIdPutovanje(int idPutovanje) {
        this.idPutovanje = idPutovanje;
    }

    public int getIdTrek() {
        return idTrek;
    }

    public void setIdTrek(int idTrek) {
        this.idTrek = idTrek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPutovanje;
        hash += (int) idTrek;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrekPK)) {
            return false;
        }
        TrekPK other = (TrekPK) object;
        if (this.idPutovanje != other.idPutovanje) {
            return false;
        }
        if (this.idTrek != other.idTrek) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domen.TrekPK[ idPutovanje=" + idPutovanje + ", idTrek=" + idTrek + " ]";
    }
    
}
