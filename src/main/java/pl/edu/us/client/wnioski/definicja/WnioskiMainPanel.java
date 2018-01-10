package pl.edu.us.client.wnioski.definicja;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.sencha.gxt.data.shared.Store.Change;
import com.sencha.gxt.data.shared.Store.Record;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.form.FormPanel;

import gwtupload.client.IUploader;
import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.IUploader.UploaderConstants;
import gwtupload.client.SingleUploader;
import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.ObrazDTO;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;
import pl.edu.us.shared.enums.TypWniosku;

public class WnioskiMainPanel extends BazowyPanel {

    private BorderLayoutData westData, eastData;
    private WnioskiModel model;
    private WnioskiGridPanel gridPanel;

    private static int AUTO_ID = 0;

    private FormPanel fp = new FormPanel();
    private ContentPanel podgladPanel = new ContentPanel();
    private SingleUploader uploader = new SingleUploader();

    private PlikiProxy proxy;
    private final DispatchAsync dispatcher;

    public static final UploaderConstants I18N_CONSTANTS = GWT.create(UploaderConstantsPl.class);

    @Inject
    public WnioskiMainPanel(final WnioskiModel model, DispatchAsync dispatcher) {
        this.model = model;
        this.dispatcher = dispatcher;
        proxy = new PlikiProxy(this, dispatcher);
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new WnioskiGridPanel(model);
        westData = new BorderLayoutData(500);
        westData.setCollapsible(true);

        eastData = new BorderLayoutData(800);
        eastData.setCollapsible(false);
        podgladPanel.setHeadingText("Wzór");

        utworzUploadera();
        fp.add(uploader);

        getBorderLayoutContainer().setWestWidget(gridPanel, westData);

        getBorderLayoutContainer().setCenterWidget(fp);
        getBorderLayoutContainer().setEastWidget(podgladPanel, eastData);
        gridPanel.getBtnDodaj().addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                WniosekDTO wniosek = new WniosekDTO();
                wniosek.setId(--AUTO_ID);
                model.getStoreWnioski().add(0, wniosek);
//                model.setWniosek(wniosek);
            }
        });
        gridPanel.getEditing().addStartEditHandler(new StartEditHandler<WniosekDTO>() {

            @Override
            public void onStartEdit(StartEditEvent<WniosekDTO> event) {
                setSaveEnabled(false, true);
            }

        });
        gridPanel.getEditing().addCancelEditHandler(new CancelEditHandler<WniosekDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<WniosekDTO> event) {
                setSaveEnabled(true, true);
            }
        });
        gridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<WniosekDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<WniosekDTO> event) {
                setSaveEnabled(true, true);
            }
        });
    }

    private void utworzUploadera() {
        uploader = new SingleUploader();
        uploader.setI18Constants(I18N_CONSTANTS);
        uploader.setAvoidRepeatFiles(false);
        uploader.setServletPath("uploader.fileUpload");
        uploader.addOnStartUploadHandler(new OnStartUploaderHandler() {

            @Override
            public void onStart(IUploader uploader) {
                setSaveEnabled(false, false);
                gridPanel.mask();
            }
        });
        uploader.addOnFinishUploadHandler(new OnFinishUploaderHandler() {
            @Override
            public void onFinish(IUploader uploader) {
                proxy.load(null, new AsyncCallback<PagingLoadResult<ObrazDTO>>() {

                    @Override
                    public void onSuccess(PagingLoadResult<ObrazDTO> result) {
                        ObrazDTO obraz = result.getData().get(0);
                        ustawObraz(obraz.getObraz());
                        if (model.getWniosek() != null) {
                            Record r = model.getStoreWnioski().getRecord(model.getWniosek());
                            Change id = r.getChange(model.getWnioskiProp().id());
                            Change typ = r.getChange(model.getWnioskiProp().typ());

//                            r.getaddChange(model.getWnioskiProp().wzor(), obraz.getObraz());

                            WniosekDTO wniosek = (WniosekDTO) r.getModel();
//                            wniosek.setNazwaObrazu(obraz.getNazwa());
                            wniosek.setObraz(obraz.getObraz());
                            wniosek.setWzor(obraz.getBs());
                            model.getStoreWnioski().update(wniosek);
                            r = model.getStoreWnioski().getRecord(wniosek);
                            if (id != null) {
                                r.addChange(model.getWnioskiProp().id(), (Integer) id.getValue());
                            }
                            if (typ != null) {
                                r.addChange(model.getWnioskiProp().typ(), (TypWniosku) typ.getValue());

                            }
                            r.addChange(model.getWnioskiProp().nazwaObrazu(), obraz.getNazwa());
                            setSaveEnabled(true, false);
                        }
                        gridPanel.unmask();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO Auto-generated method stub

                    }
                });
            }
        });
        uploader.addOnCancelUploadHandler(new OnCancelUploaderHandler() {

            @Override
            public void onCancel(IUploader uploader) {
                setSaveEnabled(true, false);
                gridPanel.unmask();
            }
        });
        uploader.setWidth("300px");
    }

    public void ustawObraz(String obraz) {
        podgladPanel.clear();
        if (obraz != null) {
            Image img = new Image(obraz);
            podgladPanel.add(img);
        }
    }

    public void initialState() {
        podgladPanel.clear();
        zapisz.setEnabled(false);
        anuluj.setEnabled(false);
    }

    public void setSaveEnabled(boolean enabled, boolean b) {
        zapisz.setEnabled(enabled && model.getStoreWnioski().getModifiedRecords().size() > 0);
        anuluj.setEnabled(model.getStoreWnioski().getModifiedRecords().size() > 0);
//        if (b)
//            uploader.setEnabled(enabled && gridPanel.getGrid().getSelectionModel().getSelectedItem() != null);
    }

    public WnioskiGridPanel getGridPanel() {
        return gridPanel;
    }

    public ContentPanel getPodgladPanel() {
        return podgladPanel;
    }

    public SingleUploader getUploader() {
        return uploader;
    }
}
