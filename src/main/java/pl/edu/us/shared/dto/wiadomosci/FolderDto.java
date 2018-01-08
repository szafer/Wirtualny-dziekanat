package pl.edu.us.shared.dto.wiadomosci;

import java.util.ArrayList;
import java.util.List;

public class FolderDto extends BaseDto {

    private static final long serialVersionUID = -5428786067694623522L;
    private List<BaseDto> children = new ArrayList<BaseDto>();

    public FolderDto() {

    }

    public FolderDto(Integer id, String name) {
        super(id, name);
    }

    @Override
    public List<BaseDto> getChildren() {
        return children;
    }

    public void setChildren(List<BaseDto> children) {
        this.children = children;
    }

    public void addChild(BaseDto child) {
        children.add(child);
    }
}
