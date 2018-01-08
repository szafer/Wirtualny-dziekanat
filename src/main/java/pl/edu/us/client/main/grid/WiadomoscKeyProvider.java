package pl.edu.us.client.main.grid;

import com.sencha.gxt.data.shared.ModelKeyProvider;

import pl.edu.us.shared.dto.wiadomosci.BaseDto;
import pl.edu.us.shared.dto.wiadomosci.FolderDto;

public class WiadomoscKeyProvider implements ModelKeyProvider<BaseDto> {

    @Override
    public String getKey(BaseDto item) {
        return (item instanceof FolderDto ? "f-" : "m-") + item.getId().toString();
    }

}
