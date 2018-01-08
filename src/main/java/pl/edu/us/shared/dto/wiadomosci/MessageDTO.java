package pl.edu.us.shared.dto.wiadomosci;

import java.util.List;

public class MessageDTO extends WiadomoscDTO {

    private static final long serialVersionUID = 2184332656027878220L;

    private List<WiadomoscDTO> childrens;

    public List<WiadomoscDTO> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<WiadomoscDTO> childrens) {
        this.childrens = childrens;
    }
}
