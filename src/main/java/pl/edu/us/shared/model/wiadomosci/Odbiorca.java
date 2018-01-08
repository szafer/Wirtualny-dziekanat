package pl.edu.us.shared.model.wiadomosci;

import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

@NamedQueries(value = {
    @NamedQuery(name = Odbiorca.MOJE_WIADOMOSCI, query = "Select u from Odbiorca u where u.user.id = :userId and u.odebrano = :typ "),
    @NamedQuery(name = Odbiorca.NOWE_WIADOMOSCI, query = "Select u from Odbiorca u where u.user.id = :userId and u.odebrano = false ")

})
@Entity
@Table(name = "ODBIORCA")
@SequenceGenerator(name = "SEQ_GEN_ODBIORCA", sequenceName = "SEQ_ODBIORCA", allocationSize = 1)
public class Odbiorca implements Persistent<Integer> {

    private static final long serialVersionUID = -8326354655999127073L;
    public static final String MOJE_WIADOMOSCI = "Odbiorca.MOJE_WIADOMOSCI";
    public static final String NOWE_WIADOMOSCI = "Odbiorca.NOWE_WIADOMOSCI";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_ODBIORCA")
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UZYTKOWNIK_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NADAWCA_ID")
    private Nadawca nadawca;

    @Column(name = "ODEBRANO")
    private Boolean odebrano;

    @Column(name = "DATA_ODBIORU")
    @Temporal(TemporalType.DATE)
    private Date dataOdbioru;
    @Column(name = "EMAIL")
    private Boolean email;

    public Odbiorca() {

    }

    @Override
    public Integer getId() {
        return this.id;
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

    public Nadawca getNadawca() {
        return nadawca;
    }

    public void setNadawca(Nadawca nadawca) {
        this.nadawca = nadawca;
    }

    public Boolean getOdebrano() {
        return odebrano;
    }

    public void setOdebrano(Boolean odebrano) {
        this.odebrano = odebrano;
    }

    public Date getDataOdbioru() {
        return dataOdbioru;
    }

    public void setDataOdbioru(Date dataOdbioru) {
        this.dataOdbioru = dataOdbioru;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

}
