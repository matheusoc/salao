package matheus.br.salao.structure.soap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import matheus.br.salao.structure.model.User;
import matheus.br.salao.structure.view.fragments.RegisterDialog;

/**
 * Created by MatheusdeOliveiraCam on 14/01/2017.
 */

public class LoginServices {

    public class RegisterUser extends AsyncTask<Object, Void, String> {

        private ProgressDialog mProgressDialog;
        private RegisterDialog mRegisterDialog;

        public RegisterUser(ProgressDialog mProgressDialog, RegisterDialog mRegisterDialog){
            this.mRegisterDialog = mRegisterDialog;
            this.mProgressDialog = mProgressDialog;
        }

        protected String doInBackground(Object... params) {
            String resultado;
            User user = (User) params[0];
            String NAMESPACE = "http://loginsystem/";
            String URL = "http://192.168.1.8/loginsystem?wsdl";
            String METHOD_NAME = "cadastrar";
            String SOAP_ACTION = NAMESPACE + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("arg0", user.getName());
            request.addProperty("arg1", user.getEmail());
            request.addProperty("arg2", user.getUsername());
            request.addProperty("arg3", user.getPassword());


            SoapSerializationEnvelope envelope = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE httpTransport = new HttpTransportSE(URL);

            httpTransport.debug = true;
            try {
                httpTransport.call(SOAP_ACTION, envelope);
            } catch (HttpResponseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapPrimitive result = null;
            try {
                result = (SoapPrimitive) envelope.getResponse();
                resultado = (String) result.getValue();
            } catch (Exception e) {
                resultado = "false";
                e.printStackTrace();
            }

            return resultado;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("false")) {
                Toast.makeText(mRegisterDialog.getActivity().getApplicationContext(),
                        "Erro de conexão, tente mais tarde", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mRegisterDialog.getActivity().getApplicationContext(),
                        "Registrado com sucesso", Toast.LENGTH_SHORT).show();
                mRegisterDialog.dismiss();
            }
            mProgressDialog.dismiss();
        }
    }

}

