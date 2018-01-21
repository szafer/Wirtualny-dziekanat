package pl.edu.us.shared.dto.wiadomosci;

import java.util.ArrayList;
import java.util.List;

public class FolderDto extends BaseDto {

    private static final long serialVersionUID = -5428786067694623522L;
    private List<FolderDto> children = new ArrayList<FolderDto>();

    public FolderDto() {

    }

    public FolderDto(Integer id, String name) {
        super(id, name);
    }

    @Override
    public List<FolderDto> getChildren() {
        return children;
    }

    public void setChildren(List<FolderDto> children) {
        this.children = children;
    }

    public void addChild(FolderDto child) {
        children.add(child);
    }

    @Override
    public String toString() {
        if (super.getName() == "Wiadomości") {
            return super.getName() + " " + getChildrenSize();
        } else
            return super.toString();

    }

    public Integer getChildrenSize() {
        int s = 0;
        for (FolderDto d : children) {
            s += d.getNowe().size();
            s += d.getOdebrane().size();
            s += d.getWyslane().size();
        }
        return s;
    }
}
