package pl.edu.us.shared.model;

import java.io.Serializable;

public interface Persistent <KT> extends Serializable {
    public KT getId();

    public void setId(KT id);
}
