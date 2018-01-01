package pl.edu.us.shared.model.wnioski;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import pl.edu.us.shared.enums.TypWniosku;
import pl.edu.us.shared.model.Persistent;

@Entity
@Table(name = "WNIOSEK")
public class Wniosek implements Persistent<Integer> {

    private static final long serialVersionUID = -7960675005132642988L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TYP")
    @Enumerated(EnumType.ORDINAL)
    private TypWniosku typ;

    @Column(name = "WZOR")
    private Byte[] wzor;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wniosek")
//    private List<UWniosek> wnioskiUzytkownika;

    public Wniosek() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public TypWniosku getTyp() {
        return typ;
    }

    public void setTyp(TypWniosku typ) {
        this.typ = typ;
    }

    public Byte[] getWzor() {
        return wzor;
    }

    public void setWzor(Byte[] wzor) {
        this.wzor = wzor;
    }

//    getW
//    private static void savePersonWithPhoto(String photoFilePath) throws IOException, SQLException {
//        Person person = new Person("Peter");
//        File file = new File(photoFilePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        Blob blob = Hibernate.getLobCreator(session)
//                            .createBlob(inputStream, file.length());
//        person.setPhoto(blob);
//        session.save(person);
//        blob.free();
//    }
//    private static void readPhotoOfPerson(int personId, String photoFilePath) throws IOException, SQLException {
//        Person person = (Person) session.get(Person.class, personId);
//        Blob blob = person.getPhoto();
//        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
//        saveBytesToFile(photoFilePath, blobBytes);
//        blob.free();
//    }
//     
//    private static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
//        FileOutputStream outputStream = new FileOutputStream(filePath);
//        outputStream.write(fileBytes);
//        outputStream.close();
//    }
}
