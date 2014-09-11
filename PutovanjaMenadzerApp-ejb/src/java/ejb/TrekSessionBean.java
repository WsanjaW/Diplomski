/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import domen.Trek;
import domen.Wp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sanja
 */
@Stateless
public class TrekSessionBean implements TrekSessionBeanLocal {

    @PersistenceContext(unitName = "PutovanjaMenadzerApp-ejbPU")
    private EntityManager em;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<Wp> listaWp(Trek trek) {
        return em.createNamedQuery("Wp.findId").setParameter("idPutovanje", trek.getTrekPK().getIdPutovanje())
                .setParameter("idTrek", trek.getTrekPK().getIdTrek()).getResultList();
    }
    
    @Override
    public void dodajTrek(Trek trek) {
        em.persist(trek);
    }

    
    
    
}
