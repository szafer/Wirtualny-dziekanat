package pl.edu.us.client.wnioski.kartoteka;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import elemental.client.Browser;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.ProgressEvent;
import elemental.html.File;
import elemental.html.FileReader;
import pl.edu.us.client.main.BazowyPanel;

public class WnioskiKartMainPanel extends BazowyPanel {

    private BorderLayoutData eastData;
    private WnioskiKartModel model;
    private WnioskiKartGridPanel gridPanel;
    private ContentPanel rightContainer;
    private ContentPanel wniosekPanel;
    private TextButton btnDrukuj = new TextButton("Drukuj");

    @Inject
    public WnioskiKartMainPanel(final WnioskiKartModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new WnioskiKartGridPanel(model);

        getBorderLayoutContainer().setCenterWidget(gridPanel);

        eastData = new BorderLayoutData(700);
        eastData.setCollapsible(true);
        rightContainer = createRightPanel();
        getBorderLayoutContainer().setEastWidget(rightContainer, eastData);

    }

    private ContentPanel createRightPanel() {
        ContentPanel cp = new ContentPanel();

        wniosekPanel = new ContentPanel();
        ToolBar tb = new ToolBar();
        tb.add(btnDrukuj);
        VerticalLayoutContainer vlc = new VerticalLayoutContainer();
        vlc.add(tb, new VerticalLayoutData(1, -1));
        vlc.add(wniosekPanel, new VerticalLayoutData(1, 1));
        cp.add(vlc);
        return cp;
    }

    protected void readFiles() {
//        JsInputElement jsInputElement = gridPanel.getFileUploadField().getVFile().cast();
//        JsFileList fileList = jsInputElement.getFiles();
//        if (fileList == null || fileList.getLength() == 0) {
//            GWT.log("file was not selected");
//            return;
//        }
//
//        for (int i = 0; i < fileList.getLength(); i++) {
//            readFile(fileList.item(i));
//        }
    }

    private void readFile(File file) {
        GWT.log("file.name=" + file.getName());

        FileReader fileReader = Browser.getWindow().newFileReader();
        fileReader.setOnload(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                onFileLoad(evt);

                GWT.log("kind of evt=" + evt);
            }
        });
        fileReader.readAsDataURL(file);
    }

    private void onFileLoad(Event evt) {
        ProgressEvent progressEvent = (ProgressEvent) evt;
        FileReader fileReader = (FileReader) evt.getTarget();

        // base64
        String result = (String) fileReader.getResult();

        // GWT.log("result=" + result);
        // GWT.debugger();

        Image image = new Image(result);
        RootPanel.get().add(image);
    }

    public void initialState() {
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public WnioskiKartGridPanel getGridPanel() {
        return gridPanel;
    }

    public TextButton getBtnDrukuj() {
        return btnDrukuj;
    }

    public ContentPanel getWniosekPanel() {
        return wniosekPanel;
    }
}
