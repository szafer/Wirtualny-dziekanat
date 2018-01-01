package pl.edu.us.shared.model.przedmioty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.us.shared.model.Persistent;

@Entity
@Table(name = "PRZEDMIOT")
@SequenceGenerator(name = "SEQ_GEN_PRZEDMIOT", sequenceName = "SEQ_PRZEDMIOT", allocationSize = 1)
public class Przedmiot implements Persistent<Integer> {

    private static final long serialVersionUID = -941343760426901334L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_PRZEDMIOT")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAZWA")
    private String nazwa;

    public Przedmiot() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
