package pl.edu.us.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

import gwtupload.server.UploadServlet;
import pl.edu.us.shared.action.WczytanePliki;
import pl.edu.us.shared.action.WczytanePlikiResult;
import pl.edu.us.shared.dto.ObrazDTO;

public class WczytanePlikiActionHandler implements ActionHandler<WczytanePliki, WczytanePlikiResult> {

    private Provider<HttpServletRequest> provider;

    @Inject
    public WczytanePlikiActionHandler(Provider<HttpServletRequest> provider) {
        this.provider = provider;
    }

    @Override
    public WczytanePlikiResult execute(WczytanePliki action, ExecutionContext context) throws ActionException {
        return new WczytanePlikiResult(
            new PagingLoadResultBean<ObrazDTO>(
                Lists.newArrayList(
                    Iterables.transform(dajPliki(), new Function<FileItem, ObrazDTO>() {
                        @Override
                        public ObrazDTO apply(FileItem input) {
                            return new ObrazDTO(input.getName(), getImageData(input.get(), input.getContentType()), input.get());
                        }
                    })),
                1, 1));
    }

    public String getImageData(byte[] bytes, String ext) {
        String base64 = Base64.encodeBase64String(bytes);
        base64 = "data:image/" + ext + ";base64," + base64;
        return base64;
    }

    private List<FileItem> dajPliki() {
        ArrayList<FileItem> lista = (ArrayList<FileItem>) (UploadServlet.getSessionFileItems(provider.get()) == null ? new ArrayList<FileItem>()
            : UploadServlet.getSessionFileItems(provider.get()));
        if (lista.isEmpty())
            return lista;
        else
            return Arrays.asList(lista.get(lista.size() - 1));
    }

    @Override
    public void undo(WczytanePliki action, WczytanePlikiResult result, ExecutionContext context) throws ActionException {
    }

    @Override
    public Class<WczytanePliki> getActionType() {
        return WczytanePliki.class;
    }
}
