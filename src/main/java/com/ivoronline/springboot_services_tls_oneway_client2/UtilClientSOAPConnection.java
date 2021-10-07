package com.ivoronline.springboot_services_tls_oneway_client2;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import java.security.KeyStore;

public class UtilClientSOAPConnection {

  //=======================================================================================
  // SEND REQUEST ONE WAY TLS
  //=======================================================================================
  public static SOAPMessage sendRequestOneWayTLS(
    String      serverURL,
    SOAPMessage requestSOAPMessage,
    String      trustStoreName,     //"/MyKeyStore.jks"
    String      trustStorePassword, //"mypassword";
    String      trustStoreType      //"JKS"
  ) throws Exception {

    //CONFIGURE ONE WAY TLS
    configureOneWayTLS(trustStoreName, trustStorePassword, trustStoreType);

    //SEND REQUEST
    SOAPMessage responseSOAPMessage = sendRequest(serverURL, requestSOAPMessage);

    //RETURN RESPONSE
    return responseSOAPMessage;

  }

  //=======================================================================================
  // SEND REQUEST
  //=======================================================================================
  public static SOAPMessage sendRequest(
    String      serverURL,
    SOAPMessage requestSOAPMessage
  ) throws Exception {

    //SEND REQUEST
    SOAPConnectionFactory factory             = SOAPConnectionFactory.newInstance();
    SOAPConnection        connection          = factory.createConnection();
    SOAPMessage           responseSOAPMessage = connection.call(requestSOAPMessage, serverURL);

    //RETURN RESPONSE
    return responseSOAPMessage;

  }

  //=======================================================================================
  // CONFIGURE ONE WAY TLS
  //=======================================================================================
  // When using SOAPConnection
  public static void configureOneWayTLS(
    String trustStoreName,     //"/MyKeyStore.jks"
    String trustStorePassword, //"mypassword";
    String trustStoreType      //"JKS"
  ) throws Exception {

    //LOAD TRUST STORE (For One-Way TLS)
    KeyStore            trustStore = UtilKeys.getStore(trustStoreName, trustStorePassword, trustStoreType);

    //CONFIGURE ONE WAY TLS
    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
                        trustManagerFactory.init(trustStore);

    SSLContext          sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

  }

}

