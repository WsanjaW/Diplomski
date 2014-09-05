/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import domen.Mesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sanja
 */
@Local
public interface MestoSessionBeanLocal {

    public List<Mesto> svaMesta();

    Mesto getById(String id);

   

}
