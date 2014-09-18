/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import domen.Korisnik;
import domen.Mesto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sanja
 */
@Local
public interface KorisnikSessionBeanLocal {

    void sacuvajKorisnika(Korisnik korisnik);

    List<Korisnik> sviKorisnici();

    Korisnik pronadjiKorisnika(String username);

    Korisnik pronadjiKorisnika(String username, String password);

    Korisnik pronadjiKorisnikaPoKodu(String kod);

    public void izmeniKorisnika(Korisnik k);

    public Korisnik pronadjiKorisnikaMail(String email);

    Korisnik pronadjiKorisnika(int korisnikId);

    

   
    
}
