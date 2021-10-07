package com.ivoronline.springboot_services_tls_oneway_client2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import javax.xml.soap.SOAPMessage;

@Component
public class MyRunner implements CommandLineRunner {

  //PROPERTIES
  static String soapFile                 = "/Request.xml";
  static String serverURL                = "https://localhost:8085/Echo";

  //CLIENT TRUST STORE
  static String clientTrustStoreName     = "ClientTrustStore.jks";
  static String clientTrustStorePassword = "mypassword";
  static String clientTrustStoreType     = "JKS";

  //===============================================================================
  // RUN
  //===============================================================================
  @Override
  public void run(String... args) throws Exception {

    //CREATE SOAP MESSAGE
    Document    requestXMLDocument = UtilXML.fileToDocument(soapFile);
    SOAPMessage requestSOAPMessage = UtilSOAP.XMLDocumentToSOAPMessage(requestXMLDocument);

    //SEND REQUEST
    SOAPMessage responseSOAPMessage = UtilClientSOAPConnection.sendRequestOneWayTLS(
      serverURL,
      requestSOAPMessage,
      clientTrustStoreName,
      clientTrustStorePassword,
      clientTrustStoreType
    );

    //DISPLAY RESULT
    String responseSOAPString = UtilSOAP.SOAPMessageToSOAPString(responseSOAPMessage);
    System.out.println(responseSOAPString);

  }

}
