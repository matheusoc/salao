package matheus.br.salao.structure.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import matheus.br.salao.R;
import matheus.br.salao.structure.view.fragments.RegisterDialog;

/**
 * Created by MatheusdeOliveiraCam on 26/12/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private CallbackManager mCallbackManager;
    private LoginButton mLoginButtonFacebook;

    private Button mRegisterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        mRegisterButton = (Button) findViewById(R.id.button_to_register);
        mRegisterButton.setOnClickListener(this);

        if(AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        mLoginButtonFacebook = (LoginButton) findViewById(R.id.facebook_login_button);
        mCallbackManager = CallbackManager.Factory.create();

        mLoginButtonFacebook.setReadPermissions(Arrays.asList( "public_profile", "email",
                "user_birthday", "user_friends"));

        mLoginButtonFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_to_register:
                RegisterDialog dialog = new RegisterDialog();

                dialog.show(getFragmentManager(), null);
                break;
        }
    }
}
