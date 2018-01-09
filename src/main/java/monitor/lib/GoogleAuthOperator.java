package monitor.lib;


import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

public class GoogleAuthOperator {
    public static void generateQRCode() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        final GoogleAuthenticatorKey gCredentials = gAuth.createCredentials();
        String qr_code = GoogleAuthenticatorQRGenerator.getOtpAuthURL("HostMonitor", "LeoPhong", gCredentials);
        System.out.println(qr_code);
        System.out.println(gCredentials.getKey());
    }
    
    public static boolean authorize(int client_code, final String secret_key) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(secret_key, client_code);
    }
}