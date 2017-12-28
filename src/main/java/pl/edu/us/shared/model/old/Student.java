package pl.edu.us.shared.model.old;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import pl.edu.us.shared.model.Person;
@Entity
//@DiscriminatorColumn(name = "ROLA")
//@DiscriminatorValue("0"S)
public class Student  extends Person {
    
    private static final long serialVersionUID = 7440297955003302414L;

    @Id
    private Integer id;
    
    private String imie;

    private String nazwisko;

    private String stanowisko;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Adres> adresy;
    

    
    public List<Adres> getAdresy() {
        return adresy;
    }

    public void setAdresy(List<Adres> adresy) {
        this.adresy = adresy;
    }

    // public long getId() {
    // return id;
    // }
    //
    // public void setId(long id) {
    // this.id = id;
    // }
    //
    // public String getImie() {
    // return imie;
    // }
    //
    // public void setImie(String imie) {
    // this.imie = imie;
    // }
    //
    // public String getNazwisko() {
    // return nazwisko;
    // }
    //
    // public void setNazwisko(String nazwisko) {
    // this.nazwisko = nazwisko;
    // }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    @Override
    public String getImie() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setImie(String imie) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getNazwisko() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNazwisko(String nazwisko) {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setId(Integer id) {
        // TODO Auto-generated method stub

    }

}