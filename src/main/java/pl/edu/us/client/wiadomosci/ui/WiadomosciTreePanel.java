package pl.edu.us.client.wiadomosci.ui;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.ToStringValueProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.tree.Tree;

import pl.edu.us.client.main.grid.TreeBundle;
import pl.edu.us.client.wiadomosci.WiadomosciModel;
import pl.edu.us.shared.dto.wiadomosci.BaseDto;

public class WiadomosciTreePanel extends ContentPanel {

    private WiadomosciModel model;

    TreeBundle bundle = GWT.create(TreeBundle.class);
    private Tree<BaseDto, String> tree;

    @Inject
    public WiadomosciTreePanel(WiadomosciModel model) {
        this.model = model;

        tree = new Tree<BaseDto, String>(model.getStoreTypySkrzynek(), new ToStringValueProvider<BaseDto>("name"));
//        tree.setWidth(300);
//        tree.getStyle().setLeafIcon(bundle.leaf());
//        tree.getStyle().setJointCloseIcon(bundle.plus());
//        tree.getStyle().setJointOpenIcon(bundle.minus());
//        tree.getStyle().setNodeCloseIcon(bundle.folder());
//        tree.getStyle().setNodeOpenIcon(bundle.folderOpen());

        add(tree);
//        ToolBar tb = new ToolBar();
//        tb.add(btnDodaj);
//        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//        vlc.add(tb, new VerticalLayoutData(1, -1));
//        vlc.add(grid, new VerticalLayoutData(1, 1));
//
//        add(vlc);
    }

    public Tree<BaseDto, String> getTree() {
        return tree;
    }
}
