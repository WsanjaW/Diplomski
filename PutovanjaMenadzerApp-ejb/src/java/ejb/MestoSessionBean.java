/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import domen.Mesto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sanja
 */
@Stateless
public class MestoSessionBean implements MestoSessionBeanLocal {

    @PersistenceContext(unitName = "PutovanjaMenadzerApp-ejbPU")
    private EntityManager em;

    @Override
    public List<Mesto> svaMesta() {
        return em.createNamedQuery("Mesto.findAll").getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Mesto pronadjiMesto(String sid) {
        int id = Integer.parseInt(sid);
        return (Mesto) em.createNamedQuery("Mesto.findByIdMesto").setParameter("idMesto", id).getSingleResult();
    }

  

}
