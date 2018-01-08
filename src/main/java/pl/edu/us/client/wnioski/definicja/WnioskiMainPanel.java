package pl.edu.us.client.wnioski.definicja;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

import elemental.client.Browser;
import elemental.events.Event;
import elemental.events.EventListener;
import elemental.events.ProgressEvent;
import elemental.html.File;
import elemental.html.FileReader;
import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;

public class WnioskiMainPanel extends BazowyPanel {

    private BorderLayoutData westData;
    private WnioskiModel model;
    private WnioskiGridPanel gridPanel;
    private ContentPanel centerPanel;
    private static int AUTO_ID = 0;

    @Inject
    public WnioskiMainPanel(final WnioskiModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new WnioskiGridPanel(model.getStoreWnioski(), model.getWnioskiProp());
        westData = new BorderLayoutData(600);
        westData.setCollapsible(true);

        getBorderLayoutContainer().setWestWidget(gridPanel, westData);

        centerPanel = new ContentPanel();
        getBorderLayoutContainer().setCenterWidget(centerPanel);

        gridPanel.getBtnDodaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                WniosekDTO wniosek = new WniosekDTO();
                wniosek.setId(--AUTO_ID);
                model.getStoreWnioski().add(0, wniosek);

            }
        });
        gridPanel.getBtnWczytaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                if (!gridPanel.getFp().isValid())
                    return;
                gridPanel.getFp().submit();
                WniosekDTO dto = model.getWniosek();
                dto.setNazwaObrazu(gridPanel.getFileUploadField().getValue());
            }
        });
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

    public void setSaveEnabled(boolean enabled) {
        zapisz.setEnabled(enabled && model.getStoreWnioski().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreWnioski().getModifiedRecords().size() > 0);
    }

    public WnioskiGridPanel getGridPanel() {
        return gridPanel;
    }
}
