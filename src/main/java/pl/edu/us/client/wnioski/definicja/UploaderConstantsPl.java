package pl.edu.us.client.wnioski.definicja;

import gwtupload.client.IUploader;

public interface UploaderConstantsPl extends IUploader.UploaderConstants {
    @Override
    @DefaultStringValue("Plik jest ju¿ wczytywany, spróbuj póŸniej.")
    String uploaderActiveUpload();

    @Override
    @DefaultStringValue("Ten plik zosta³ ju¿ wczytany.")
    String uploaderAlreadyDone();

    @Override
    @DefaultStringValue("It seems the application is configured to use GAE blobstore.\nThe server has raised an error while creating an Upload-Url\nBe sure thar you have enabled billing for this application in order to use blobstore.")
    String uploaderBlobstoreError();

    @Override
    @DefaultStringValue("Wybierz plik do wczytania ...")
    String uploaderBrowse();

    @Override
    @DefaultStringValue("Niepoprawny format pliku.\nDozwolone typy:\n")
    String uploaderInvalidExtension();

    @Override
    @DefaultStringValue("Wczytaj")
    String uploaderSend();

    @Override
    @DefaultStringValue("Invalid server response. Have you configured correctly your application in the server side?")
    String uploaderServerError();

    @Override
    @DefaultStringValue("Unable to auto submit the form, it seems your browser has security issues with this feature.\n Developer Info: If you are using jsupload and you do not need cross-domain, try a version compiled with the standard linker?")
    String submitError();

    @Override
    @DefaultStringValue("Unable to contact with the server: ")
    String uploaderServerUnavailable();

    @Override
    @DefaultStringValue("Timeout sending the file:\n perhaps your browser does not send files correctly,\n your session has expired,\n or there was a server error.\nPlease try again.")
    String uploaderTimeout();

    @Override
    @DefaultStringValue("Error uploading the file, the server response has a format which can not be parsed by the application.\n.")
    String uploaderBadServerResponse();

    @Override
    @DefaultStringValue("Additional information: it seems that you are using blobstore, so in order to upload large files check that your application is billing enabled.")
    String uploaderBlobstoreBilling();

    @Override
    @DefaultStringValue("Error you have typed an invalid file name, please select a valid one.")
    String uploaderInvalidPathError();
}
