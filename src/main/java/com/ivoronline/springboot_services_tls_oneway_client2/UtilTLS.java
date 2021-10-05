package com.ivoronline.springboot_services_tls_oneway_client2;

import org.springframework.core.io.ClassPathResource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

public class UtilTLS {

  //CLIENT TRUST STORE
  static String clientTrustStoreName     = "ClientTrustStore.jks";
  static String clientTrustStoreType     = "JKS";
  static String clientTrustStorePassword = "mypassword";

  //=======================================================================================
  // CONFIGURE ONE WAY TLS
  //=======================================================================================
  public static void configureOneWayTLS() throws Exception {

    //LOAD TRUST STORE
    ClassPathResource   classPathResource   = new ClassPathResource(clientTrustStoreName);
    InputStream         inputStream         = classPathResource.getInputStream();
    KeyStore            trustStore          = KeyStore.getInstance(clientTrustStoreType);
                        trustStore.load(inputStream, clientTrustStorePassword.toCharArray());

    //CONFIGURE ONE WAY TLS
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
                        trustManagerFactory.init(trustStore);

    SSLContext          sslContext          = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

  }

}
