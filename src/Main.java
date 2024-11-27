import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class Main {
    private static final String JKS_KEYSTORE_PASSWORD = "changeit";

    public static void main(String[] args) {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("truststore.jks"), JKS_KEYSTORE_PASSWORD.toCharArray());
            tmf.init(ks);

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory sslFactory = sc.getSocketFactory();
            SSLSocket cli = (SSLSocket) sslFactory.createSocket("www.secure-server.edu",4433);

            cli.startHandshake();
            SSLSession session = cli.getSession();
            System.out.println(session.getCipherSuite());
            System.out.println(session.getPeerCertificates()[0]);
            cli.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}