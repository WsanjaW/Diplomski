/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import domen.Mesto;
import domen.Putovanje;
import domen.Trek;
import domen.TrekPK;
import domen.Wp;
import domen.WpPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sanja
 */
@Stateless
public class PutovanjeSessionBean implements PutovanjeSessionBeanLocal {

    @PersistenceContext(unitName = "PutovanjaMenadzerApp-ejbPU")
    private EntityManager em;
    
    @Override
    public void sacuvajPutovanje(Putovanje putovanje) {

        // za id novog putovanja
        //TODO posebna metoda
        int id = 0;
        try {
            id = (int) em.createQuery("SELECT MAX(p.idPutovanje) FROM Putovanje p").getSingleResult();
        } catch (Exception e) {
            id = 0;
        }
        id = id + 1;
        putovanje.setIdPutovanje(id);

        int i = 1;
        for (Trek t : putovanje.getTrekList()) {
            t.setPutovanje(putovanje);
            t.setTrekPK(new TrekPK(id, i));
            i++;
        }
        for (Mesto mesto : putovanje.getMestoList()) {
            mesto.getMestoputovanjeList().add(putovanje);
        }

        em.persist(putovanje);
    }

    @Override
    public List<Putovanje> vratiPutovanja() {
        List<Putovanje> putovanja = em.createNamedQuery("Putovanje.findAll").getResultList();

        return putovanja;
    }  
    

    @Override
    public void obrisiPutovanje(Putovanje selektovanoPutovanje) {
        Putovanje p = em.merge(selektovanoPutovanje);
        em.remove(p);
    }

    @Override
    public Putovanje izmeniPutovanje(Putovanje putovanje) {
        Putovanje p = em.merge(putovanje);
        return p;
    }

    
   

}
