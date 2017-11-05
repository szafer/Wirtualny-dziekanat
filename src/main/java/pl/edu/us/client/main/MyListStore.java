package pl.edu.us.client.main;

import java.util.Collection;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.Store;

public class MyListStore<T> extends ListStore<T> {

	public MyListStore(ModelKeyProvider<? super T> keyProvider) {
		super(keyProvider);
	}

	@Override
	public Collection<Store<T>.Record> getModifiedRecords() {
		// TODO Auto-generated method stub
		return super.getModifiedRecords();
	}

}
