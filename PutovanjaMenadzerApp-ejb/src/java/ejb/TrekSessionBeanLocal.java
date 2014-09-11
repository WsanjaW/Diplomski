/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import domen.Trek;
import domen.Wp;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sanja
 */
@Local
public interface TrekSessionBeanLocal {

    List<Wp> listaWp(Trek trek);

    void dodajTrek(Trek trek);
}
