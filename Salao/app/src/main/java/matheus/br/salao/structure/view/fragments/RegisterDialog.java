package matheus.br.salao.structure.view.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import matheus.br.salao.R;
import matheus.br.salao.structure.Constants;
import matheus.br.salao.structure.model.User;
import matheus.br.salao.structure.soap.LoginServices;

public class RegisterDialog extends DialogFragment implements View.OnClickListener {

    private EditText mRegisterName;
    private EditText mRegisterEmail;
    private EditText mRegisterUser;
    private EditText mRegisterPassword;

    private CheckBox mShowPassword;

    private Button mCancel;
    private Button mOk;

    private Dialog createDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog();
    }

    private Dialog dialog(){
        createDialog = new Dialog(getActivity());

        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.register_dialog, null);

        mRegisterName = (EditText) layout.findViewById(R.id.registerName);
        mRegisterEmail = (EditText) layout.findViewById(R.id.registerEmail);
        mRegisterUser = (EditText) layout.findViewById(R.id.registerUser);
        mRegisterPassword = (EditText) layout.findViewById(R.id.registerPassword);

        mShowPassword = (CheckBox) layout.findViewById(R.id.showPassword);

        mCancel = (Button) layout.findViewById(R.id.cancelButton);
        mOk = (Button) layout.findViewById(R.id.okButton);

        mShowPassword.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mOk.setOnClickListener(this);

        createDialog.setTitle(Constants.REGISTER);

        createDialog.setContentView(layout);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 20;
        int height = size.y - 200;

        if(createDialog.getWindow() != null){
            createDialog.getWindow().setLayout(width,height);
        }
        return createDialog;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.showPassword:
                if(mShowPassword.isChecked()){
                    mRegisterPassword.setTransformationMethod(null);
                } else {
                    mRegisterPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
                break;
            case R.id.cancelButton:
                createDialog.dismiss();
                break;
            case R.id.okButton:
                User user = new User();

                user.setName(mRegisterName.getText().toString());
                user.setEmail(mRegisterEmail.getText().toString());
                user.setUsername(mRegisterUser.getText().toString());
                user.setPassword(mRegisterPassword.getText().toString());

                LoginServices.registerUser(user, this);
                break;
        }
    }
}
