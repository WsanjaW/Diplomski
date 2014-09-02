/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

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

    @Override
    public void izmeniPutovanje(Putovanje putovanje) {

        int id = putovanje.getIdPutovanje();
        Query query = em.createQuery("SELECT MAX(tr.trekPK.idTrek) FROM Trek tr WHERE tr.trekPK.idPutovanje = :idPut").setParameter("idPut", id);
        List<Object> rez = query.getResultList();
        int i = 1;
        if (rez.get(0) != null) {
            i = (int) rez.get(0) + 1;
        }
        Trek t = putovanje.getTrekList().get(putovanje.getTrekList().size() - 1);
        t.setPutovanje(putovanje);
        t.setTrekPK(new TrekPK(id, i));
        int j = 1;
        for (Wp wp : t.getWpList()) {
            wp.setWpPK(new WpPK(j, t.getTrekPK().getIdTrek(), t.getTrekPK().getIdPutovanje()));
            j++;
        }
        //putovanje.getTrekList().add(t);
        em.merge(putovanje);
        

    }
}
