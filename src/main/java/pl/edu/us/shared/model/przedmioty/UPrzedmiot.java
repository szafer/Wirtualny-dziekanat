package pl.edu.us.shared.model.przedmioty;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.edu.us.shared.model.Persistent;
import pl.edu.us.shared.model.User;

//@Entity
//@Table(name = "U_PRZEDMIOT")
//@SequenceGenerator(name = "SEQ_GEN_U_PRZEDMIOT", sequenceName = "SEQ_U_PRZEDMIOT", allocationSize = 1)
public class UPrzedmiot{}
//implements Persistent<Integer> {
//
//    private static final long serialVersionUID = 4718627897692145300L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_U_PRZEDMIOT")
//    @Column(name = "ID")
//    private Integer id;
//
//    @Column(name = "DATA_SEMESTRU")
//    @Temporal(TemporalType.DATE)
//    private Date dataSemestru;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "UZYTKOWNIK_ID")
////    private User uzytkownik;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PRZEDMIOT_ID")
//    private Przedmiot przedmiot;
//
//    @Column(name = "OCENA_1")
//    private Float ocena1;
//
//    @Column(name = "OCENA_2")
//    private Float ocena2;
//
//    @Override
//    public Integer getId() {
//        return this.id;
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//}
