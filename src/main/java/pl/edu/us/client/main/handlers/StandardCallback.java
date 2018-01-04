package pl.edu.us.client.main.handlers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public abstract class StandardCallback<T> implements AsyncCallback<T>{

    //private static AddEmployeeConstants CONSTANS = AddEmployeeConstants.CONSTANTS;
    private final AsyncCallback<?> nextCallback;

    public StandardCallback(){
        nextCallback = null;
    }

    public StandardCallback(AsyncCallback<?> nextCallback) {
        this.nextCallback = nextCallback;
    }

    @SuppressWarnings("unchecked")
    protected <C> AsyncCallback<C> getNextCallback() {
        return (AsyncCallback<C>) nextCallback;
    }

    @Override
    public void onFailure(Throwable caught) {
        try{
            if( nextCallback != null ){
                nextCallback.onFailure(caught);
            }
        }finally{
            if( caught instanceof RuntimeException ){
                Integer statusCode = null;
                if( caught instanceof StatusCodeException){
                    StatusCodeException sce = (StatusCodeException) caught;
                    statusCode = sce.getStatusCode();
                    switch( statusCode ){
                    case 500:
                        onStatus500( sce );
                        break;
                    case 401:
                        onStatus401();
                        break;
                    }
                }               
                throw new RuntimeException(caught);
            }else{
                onCheckedException(caught);
            }
        }
    }

    private void onStatus401() {
//        MessageBox.alert( "", "Przepraszamy, sesja wygas³a (401). Prosimy o wylogowanie siê i uruchomienie ponowne aplikacji", null);
    }

    private void onStatus500(StatusCodeException sce) {
        
//        
//        String message = sce.getEncodedResponse();
//        message += "\nProsimy o wylogowanie siê i uruchomienie ponowne aplikacji lub kontakt z zespo³em serwisowym";
//        
//        MessageBox box = new MessageBox();
//        box.setTitleHtml( "("+sce.getStatusCode()+")" );
//        box.setMessage( HtmlHelper.quote( message ) );
//        box.setButtons( MessageBox.OK );
//        box.setIcon(MessageBox.WARNING);
//        box.setMinWidth(500);
//        box.show();     
        
    }

    private void onCheckedException(Throwable caught) {
        if( caught == null ){
            throw new RuntimeException();
        }
        String localizedMessage = caught.getLocalizedMessage();
        if( localizedMessage == null ){
            throw new RuntimeException( caught );
        }
//        MessageBox.alert( "", HtmlHelper.quote( localizedMessage ),null);
    }

}
