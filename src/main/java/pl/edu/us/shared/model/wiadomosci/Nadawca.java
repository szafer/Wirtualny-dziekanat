package pl.edu.us.shared.model.wiadomosci;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

@NamedQueries(value = {
    @NamedQuery(name = Nadawca.MOJE_WIADOMOSCI, query = "Select u from Nadawca u where u.user.id = :userId")
})
@Entity
@Table(name = "NADAWCA")
@SequenceGenerator(name = "SEQ_GEN_NADAWCA", sequenceName = "SEQ_NADAWCA", allocationSize = 1)
public class Nadawca implements Persistent<Integer> {

    private static final long serialVersionUID = -6919464230603785426L;

    public static final String MOJE_WIADOMOSCI = "Nadawca.MOJE_WIADOMOSCI";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_NADAWCA")
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private User user;

    @Column(name = "TEMAT", length = 150)
    private String temat;

    @Column(name = "WIADOMOSC", length = 2000)
    private String wiadomosc;

    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    private Date data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nadawca", cascade = CascadeType.PERSIST)
    private List<Odbiorca> odbiorcy;

    public Nadawca() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTemat() {
        return temat;
    }

    public void setTemat(String temat) {
        this.temat = temat;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        this.wiadomosc = wiadomosc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Odbiorca> getOdbiorcy() {
        return odbiorcy;
    }

    public void setOdbiorcy(List<Odbiorca> odbiorcy) {
        this.odbiorcy = odbiorcy;
    }
}
