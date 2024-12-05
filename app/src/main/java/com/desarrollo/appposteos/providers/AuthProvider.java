package com.desarrollo.appposteos.providers;
import static com.parse.Parse.getApplicationContext;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class AuthProvider {
    public AuthProvider(){}
    public LiveData<String> signIn(String email, String pass){
        MutableLiveData<String> result = new MutableLiveData<>();
        ParseUser.logInInBackground(email, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException error) {
                if (error == null){
                    //Si el error es nulo es exitoso el acceso
                    result.setValue(user.getObjectId());
                } else {
                    result.setValue(null);
                }
            }
        });
        return result;
    }
    public LiveData<String> signUp(String email, String pass){
        MutableLiveData<String> result = new MutableLiveData<>();
        ParseUser usuario = new ParseUser();
        usuario.setUsername(email);
        usuario.setPassword(pass);
        usuario.signUpInBackground(error ->{
            if (error == null){
                //Si no hay error el registro es exitoso
                result.setValue(usuario.getObjectId());
            } else {
                //Si hay error no hubo registro
                result.setValue(null);
            }
        });
        return result;
    }

    public LiveData<String> getCurrentUserId(){
        MutableLiveData<String> currentid = new MutableLiveData<>();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            //Si currentUser tiene algo, trae un usuario
            currentid.setValue(currentUser.getObjectId());
        } else {
            currentid.setValue(null);
        }
        return currentid;
    }

    public LiveData<Boolean> logout() {
        MutableLiveData<Boolean> logoutResult = new MutableLiveData<>();
        ParseUser.logOutInBackground(e -> {
            if (e == null) {
                logoutResult.setValue(true);
                if (getApplicationContext() != null) {
                    getApplicationContext().getCacheDir().delete();
                }
                Log.d("AuthProvider", "Caché eliminada y usuario desconectado.");

            } else {

                logoutResult.setValue(false);
                Log.e("AuthProvider", "Error al desconectar al usuario: ", e);
            }
        });
        return logoutResult;
    }
}
