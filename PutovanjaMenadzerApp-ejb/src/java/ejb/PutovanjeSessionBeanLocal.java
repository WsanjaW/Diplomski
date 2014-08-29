/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import domen.Putovanje;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sanja
 */
@Local
public interface PutovanjeSessionBeanLocal {

    void sacuvajPutovanje(Putovanje putovanje);

    public List<Putovanje> vratiPutovanja();

    void izmeniPutovanje(Putovanje putovanje);

    
    
}
