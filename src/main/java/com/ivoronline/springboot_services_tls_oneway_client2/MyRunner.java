package com.ivoronline.springboot_services_tls_oneway_client2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;

@Component
public class MyRunner implements CommandLineRunner {

  //PROPERTIES
  String serverURL = "https://localhost:8085/GetPerson";

  //===============================================================================
  // RUN
  //===============================================================================
  @Override
  public void run(String... args) throws Exception {

    //CONFIGURE ONE-WAY TLS
    UtilTLS.configureOneWayTLS();

    //CREATE SOAP MESSAGE
    Document              document            = UtilXML.fileToDocument("/Request.xml");
    SOAPMessage           soapMessage         = UtilSOAP.createSOAP(document);

    //SEND REQUEST
    SOAPConnectionFactory factory             = SOAPConnectionFactory.newInstance();
    SOAPConnection        connection          = factory.createConnection();
    SOAPMessage           responseSOAPMessage = connection.call(soapMessage, serverURL);

    //DISPLAY RESULT
    System.out.println(UtilSOAP.SOAPToString(responseSOAPMessage));

  }

}
