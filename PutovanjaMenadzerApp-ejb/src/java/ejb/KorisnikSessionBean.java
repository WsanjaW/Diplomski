/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import domen.Korisnik;
import domen.Mesto;
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
public class KorisnikSessionBean implements KorisnikSessionBeanLocal {

    @PersistenceContext(unitName = "PutovanjaMenadzerApp-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void sacuvajKorisnika(Korisnik korisnik) {
        em.persist(korisnik);
    }

    @Override
    public List<Korisnik> sviKorisnici() {
        Query q = em.createNamedQuery("Korisnik.findAll");
        return q.getResultList();
    }

    @Override
    public List<Mesto> svaMesta() {
        return em.createNamedQuery("Mesto.findAll").getResultList();
    }

    @Override
    public Mesto vratiMesto(String mestoID) {
        return (Mesto) em.createNamedQuery("Mesto.findByIdMesto").setParameter("idMesto", Integer.parseInt(mestoID)).getSingleResult();
    }

    @Override
    public Korisnik pronadjiKorisnika(String username) {
        Korisnik k = null;
        try {
            k = (Korisnik) em.createNamedQuery("Korisnik.findByUsername").setParameter("username", username).getSingleResult();
        } catch (Exception ex) {
            k = null;
        }
        return k;
    }

    @Override
    public Korisnik pronadjiKorisnika(String username, String password) {
        Korisnik k = null;
        try {
            Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :username AND k.password = :password");
            k = (Korisnik) q.setParameter("username", username).setParameter("password", password).getSingleResult();
        } catch (Exception ex) {
            k = null;
        }
        return k;
    }

    @Override
    public Korisnik pronadjiKorisnikaPoKodu(String kod) {
        Korisnik k = null;
        try {
            k = (Korisnik) em.createNamedQuery("Korisnik.findByAktivacionikod").setParameter("aktivacionikod", kod).getSingleResult();
        } catch (Exception ex) {
            k = null;
        }
        return k;
    }

    @Override
    public void promeniKorisnika(Korisnik k) {
       em.merge(k);
    }

    @Override
    public Korisnik pronadjiKorisnikaMail(String email) {
         Korisnik k = null;
        try {
            k = (Korisnik) em.createNamedQuery("Korisnik.findByEmail").setParameter("email", email).getSingleResult();
        } catch (Exception ex) {
            k = null;
        }
        return k;
    }

}
