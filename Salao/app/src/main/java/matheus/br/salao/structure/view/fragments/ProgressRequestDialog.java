package matheus.br.salao.structure.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import matheus.br.salao.structure.model.User;
import matheus.br.salao.structure.soap.LoginServices;

/**
 * Created by matheusoliveira on 17/01/2017.
 */

public class ProgressRequestDialog extends LoginServices{

    private ProgressDialog mProgressDialog;
    private User mUser;
    private RegisterDialog mRegisterDialog;

    public ProgressRequestDialog(Activity context, String message, User user, RegisterDialog dialog){
        this.mUser = user;
        this.mRegisterDialog = dialog;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void start(){
        RegisterUser registerUser = new RegisterUser(mProgressDialog, mRegisterDialog);
        mProgressDialog.show();
        registerUser.execute(mUser);
    }

}
