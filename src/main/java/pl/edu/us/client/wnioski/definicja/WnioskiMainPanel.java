package pl.edu.us.client.wnioski.definicja;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent;
import com.sencha.gxt.widget.core.client.event.CancelEditEvent.CancelEditHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.StartEditEvent;
import com.sencha.gxt.widget.core.client.event.StartEditEvent.StartEditHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.info.Info;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploaderConstants;
import gwtupload.client.PreloadedImage;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;
import gwtupload.client.SingleUploader;
import gwtupload.client.SingleUploaderModal;
import jsupload.client.ChismesUploadProgress;
import pl.edu.us.client.main.AppKontekst;
import pl.edu.us.client.main.BazowyPanel;
import pl.edu.us.shared.dto.wnioski.WniosekDTO;

public class WnioskiMainPanel extends BazowyPanel {

    private BorderLayoutData westData, eastData;
    private WnioskiModel model;
    private WnioskiGridPanel gridPanel;

    private static int AUTO_ID = 0;
    private TextButton btnWczytaj = new TextButton("Wczytaj");
    private FileUploadField fileUploadField = new FileUploadField();
    private FormPanel fp = new FormPanel();
    private ContentPanel podgladPanel = new ContentPanel();
//    @Inject
    protected UploaderConstants i18nStrs;
    
    OnLoadPreloadedImageHandler showImage; /*= new OnLoadPreloadedImageHandler() {
//        public void onLoad(PreloadedImage img) {
////              img.setWidth("75px");
//            podgladPanel.add(img);
//        }
//    };*/
    @Inject
    public WnioskiMainPanel(final WnioskiModel model) {
        this.model = model;
        nowy.setVisible(false);
        usun.setVisible(false);
        zatwierdz.setVisible(false);
        initialState();
        gridPanel = new WnioskiGridPanel(model);
        westData = new BorderLayoutData(400);
        westData.setCollapsible(true);

        eastData = new BorderLayoutData(600);
        eastData.setCollapsible(false);

        btnWczytaj.setWidth(100);
        VerticalLayoutContainer vl = new VerticalLayoutContainer();
        vl.add(btnWczytaj);
        vl.add(fileUploadField);
        btnWczytaj.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
//                if (!fp.isValid())
//                    return;
//                fp.submit();
//                WniosekDTO dto = model.getWniosek();
//                dto.setNazwaObrazu(fileUploadField.getValue());
//                GWT.log(fileUploadField.getValue());
                
            }
        });
        fp.setWidget(vl);
        fp.setAction(GWT.getHostPageBaseURL() + "usosweb/usosweb/raport_img");
        fp.setEncoding(Encoding.MULTIPART);
        fp.setMethod(Method.POST);
        fp.setHeight(100);
        fp.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {
                String resultHtml = event.getResults();

                Info.display("Upload Response", resultHtml);

                System.out.println(resultHtml);
//                Image image = new Image("./img/"+"monica.txt");
//                image.setSize("300px", "300px");
                
//                if (kontekst.getObraz() != null && model.getWniosek() != null) {
//                    WniosekDTO wniosek = model.getWniosek();
//                    wniosek.setWzor(kontekst.getObraz().getBs());
                Image image = new Image("img/"+resultHtml);

//                Image img = new Image(resultHtml);
                podgladPanel.clear();
                podgladPanel.add(image);
//                }
            }
        });
        
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
                setSaveEnabled(false);
            }

        });
        gridPanel.getEditing().addCancelEditHandler(new CancelEditHandler<WniosekDTO>() {

            @Override
            public void onCancelEdit(CancelEditEvent<WniosekDTO> event) {
                setSaveEnabled(true);
            }
        });
        gridPanel.getEditing().addCompleteEditHandler(new CompleteEditHandler<WniosekDTO>() {

            @Override
            public void onCompleteEdit(CompleteEditEvent<WniosekDTO> event) {
                setSaveEnabled(true);
            }
        });
        // Configurable servlet path to experiment with different options
        String servletPath = Window.Location.getParameter("servlet");
        if (servletPath == null) {
          servletPath = //GWT.getHostPageBaseURL()+
              "/usosweb/servlet.gupld";
        }
        showImage = new OnLoadPreloadedImageHandler() {
            public void onLoad(PreloadedImage img) {
//                  img.setWidth("75px");
                podgladPanel.add(img);
            }
        };
        IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
            public void onFinish(IUploader uploader) {
              if (uploader.getStatus() == Status.SUCCESS) {
                String msg = uploader.getServerMessage().getMessage();
                if (msg != null && msg.startsWith("data:")) {
                  new PreloadedImage(msg, showImage);
                } else {
                  for (String url : uploader.getServerMessage().getUploadedFileUrls()) {
                    new PreloadedImage(url, showImage);
                  }
                }
              }
            }
          };
        SingleUploader single1 = new SingleUploaderModal();
        single1.setServletPath(servletPath);

        // This enables php apc progress mechanism
        single1.add(new Hidden("APC_UPLOAD_PROGRESS", single1.getInputName()), 0);
        single1.avoidEmptyFiles(false);
        single1.addOnFinishUploadHandler(onFinishUploaderHandler);
        single1.setMultipleSelection(false);
        fp.add(single1);

//        SingleUploader single2 = new SingleUploaderModal(FileInputType.ANCHOR, new ChismesUploadProgress(true));
//        single2.setServletPath(servletPath);
//        single2.addOnFinishUploadHandler(onFinishUploaderHandler);
//        add(single2);

        SingleUploader single3 = new SingleUploader(FileInputType.BUTTON);
        single3.addOnFinishUploadHandler(onFinishUploaderHandler);
        single3.setServletPath(servletPath);
        fp.add(single3);

//        Label customButton = new Label();
//        customButton.setStyleName("customButton");
//        Label externalZone = new Label();
//        externalZone.setStyleName("customZone");
//
//        SingleUploader single4 = new SingleUploader(FileInputType.CUSTOM.with(customButton).withZone(externalZone));
//        single4.add(new Hidden("APC_UPLOAD_PROGRESS", single4.getInputName()), 0);
//        single4.setServletPath(servletPath);
//        single4.setAutoSubmit(true);
//        single4.setValidExtensions("jpg", "gif", "png");
//        single4.addOnFinishUploadHandler(onFinishUploaderHandler);
//        single4.avoidRepeatFiles(true);
//        RootPanel.get("single4").add(single4);
//        single4.getElement().getStyle().setProperty("display", "inline-block");
//        RootPanel.get("single4").add(externalZone);
//        externalZone.getElement().getStyle().setProperty("display", "inline-block");
//
//        SingleUploader single5 = new SingleUploader(FileInputType.DROPZONE);
//        single5.setServletPath(servletPath);
//        single5.setAutoSubmit(true);
//        single5.setValidExtensions("jpg", "gif", "png");
//        single5.addOnFinishUploadHandler(onFinishUploaderHandler);
//        single5.avoidRepeatFiles(true);
//        RootPanel.get("single5").add(single5);
//
//        Label uploadLabel = new Label("Select images ...");
//        Label externalDropZone = new Label();
//        externalDropZone.setText("Drop files here");
//        externalDropZone.setSize("160px", "30px");
//        externalDropZone.getElement().getStyle().setBorderStyle(Style.BorderStyle.DASHED);
//        externalDropZone.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);
//        SingleUploader single6 = new SingleUploader(FileInputType.DROPZONE.with(uploadLabel).withZone(externalDropZone));
//        single6.setServletPath(servletPath);
//        single6.setAutoSubmit(true);
//        single6.setValidExtensions("jpg", "gif", "png");
//        single6.addOnFinishUploadHandler(onFinishUploaderHandler);
//        single6.avoidRepeatFiles(true);
//        RootPanel.get("single6").add(single6);
//        RootPanel.get("single6").add(externalDropZone);
//
//        RootPanel.get("thumbnails").add(panelImages);
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
