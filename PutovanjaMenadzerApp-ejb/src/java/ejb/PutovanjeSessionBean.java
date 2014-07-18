/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import domen.Putovanje;
import domen.Trek;
import domen.TrekPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sanja
 */
@Stateless
public class PutovanjeSessionBean implements PutovanjeSessionBeanLocal {
    @PersistenceContext(unitName = "PutovanjaMenadzerApp-ejbPU")
    private EntityManager em;

    

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void sacuvajPutovanje(Putovanje putovanje) {
        List<Trek> trekovi = putovanje.getTrekList();
        putovanje.setTrekList(null);
        em.persist(putovanje);
        int id = (int) em.createQuery("SELECT MAX(p.idPutovanje) FROM Putovanje p").getSingleResult();
        System.out.println(id);
        putovanje.setTrekList(trekovi);
        int i = 1;
        for (Trek t : putovanje.getTrekList()) {
            t.setPutovanje(putovanje);
            t.setTrekPK(new TrekPK(id, i));
            i++;
        }
        em.merge(putovanje);
    }

    @Override
    public List<Putovanje> vratiPutovanja() {
        List<Putovanje> putovanja = em.createNamedQuery("Putovanje.findAll").getResultList();
//        for (Putovanje putovanje : putovanja) {
//            List<Trek> trekovi =  em.createNamedQuery("Trek.findByIdPutovanje").setParameter("idPutovanje", putovanje.getIdPutovanje()).getResultList();
//            System.out.println(trekovi);
//            putovanje.setTrekList(trekovi);
//           // System.out.println(putovanje.getTrekList().get(0).getNaziv());
//        }
       
        return putovanja;
    }
}
