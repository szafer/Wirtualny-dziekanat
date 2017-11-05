package pl.edu.us.shared.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries(value = {
    @NamedQuery(name = User.DAJ_USERA, query = "Select u from User u where u.login = :login and u.password = :password"),
    @NamedQuery(name = User.POBIERZ_WSZYSTKIE, query = "Select u from User u"),
    @NamedQuery(name = User.NEXT_ID, query = "Select max(u.id) + 1 from User u") })
@Entity
@Table(name = "UZYTKOWNIK")
@SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "SEQ_USER", allocationSize = 1)
public class User extends Person {

    private static final long serialVersionUID = -4864743130982430355L;
    public static final String DAJ_USERA = "User.DAJ_USERA";
    public static final String POBIERZ_WSZYSTKIE = "User.POBIERZ_WSZYSTKIE";
    public static final String NEXT_ID = "User.NEXT_ID";

    public static final String ID = "id";
    public static final String IMIE = "imie";
    public static final String NAZWISKO = "nazwisko";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
    private Integer id;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<Rola> role;

    public User() {
    }

    public User(Integer id, String imie, String nazwisko, String login, String password/*
                                                                                        * ,
                                                                                        * List
                                                                                        * <
                                                                                        * Rola
                                                                                        * >
                                                                                        * role
                                                                                        */) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.login = login;
        this.password = password;
        // this.role = role;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public List<Rola> getRole() {
    // return role;
    // }
    //
    // public void setRole(List<Rola> role) {
    // this.role = role;
    // }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String toString() {
        return imie + " " + nazwisko;
    }

}
