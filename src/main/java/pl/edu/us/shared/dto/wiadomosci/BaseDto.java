package pl.edu.us.shared.dto.wiadomosci;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;

public class BaseDto implements Serializable, TreeStore.TreeNode<BaseDto> {

    private static final long serialVersionUID = -8935581442275246930L;
    private Integer id;
    private String name;
    private List<NadawcaDTO> wyslane = new ArrayList<NadawcaDTO>();
    private List<OdbiorcaDTO> odebrane = new ArrayList<OdbiorcaDTO>();
    private List<OdbiorcaDTO> nowe = new ArrayList<OdbiorcaDTO>();

    public BaseDto() {

    }

    public BaseDto(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public List<? extends TreeNode<BaseDto>> getChildren() {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BaseDto getData() {
        return this;
    }

    @Override
    public String toString() {
        if (name == "Odebrane") {
            return name + " " + odebrane.size() + " " 
                + (!nowe.isEmpty() ? nowe.size() == 1 ? "1 nowa wiadomość" : nowe.size() + " nowych wiadomości" : "");
        } else {
            return name + " " + wyslane.size();
        }
    }

    public List<OdbiorcaDTO> getNowe() {
        return nowe;
    }

    public List<OdbiorcaDTO> getOdebrane() {
        return odebrane;
    }

    public List<NadawcaDTO> getWyslane() {
        return wyslane;
    }

    public void setNowe(List<OdbiorcaDTO> nowe) {
        this.nowe = nowe;
    }

    public void setOdebrane(List<OdbiorcaDTO> odebrane) {
        this.odebrane = odebrane;
    }

    public void setWyslane(List<NadawcaDTO> wyslane) {
        this.wyslane = wyslane;
    }
}
