package matheus.br.salao.structure.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class CalculadoraClient {

    public static String converterCelsiusParaFarenheit() {
        String resultado = "-1";

        String NAMESPACE = "http://calc/";
        String URL = "http://192.168.1.8/calc?wsdl";
        String METHOD_NAME = "soma";
        String SOAP_ACTION = NAMESPACE + METHOD_NAME;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("arg0", 5);
        request.addProperty("arg1", 5);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION, envelope);
        } catch (HttpResponseException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        } //send request
        SoapPrimitive result = null;
        try {
            result = (SoapPrimitive) envelope.getResponse();
            resultado = (String) result.getValue();
        } catch (SoapFault e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
