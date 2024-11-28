import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

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