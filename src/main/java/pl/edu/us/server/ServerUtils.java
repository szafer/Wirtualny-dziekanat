package pl.edu.us.server;

import org.apache.commons.codec.binary.Base64;

public final class ServerUtils {

    

    public static String getImageData(byte[] bytes) {
//        byte[] b = new byte[bytes.length];
//        for (int i = 0; i < bytes.length; i++) {
//            b[i] = bytes[i];
//        }
//        String base64 = Base64Utils.toBase64(b);
//        base64 = "data:image/png;base64," + base64;
//        return base64;
        String base64 = Base64.encodeBase64String(bytes);
        base64 = "data:image/png;base64," + base64;
        return base64;
    }
}
