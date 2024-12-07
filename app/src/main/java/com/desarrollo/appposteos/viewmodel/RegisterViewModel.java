package com.desarrollo.appposteos.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desarrollo.appposteos.model.User;
import com.parse.ParseUser;

public class RegisterViewModel extends ViewModel {
    private final MutableLiveData registerResult;

    public RegisterViewModel(){
        registerResult = new MutableLiveData<>();
    }

    public LiveData<String> getRegisterResult(){
        return registerResult;
    }

    public void register(@NonNull User user){
        //Crear el ParseUser
        ParseUser parseUser = new ParseUser();
        parseUser.setUsername(user.getUsername());
        parseUser.setEmail(user.getEmail());
        parseUser.setPassword(user.getPassword());

        //Hacemos el registro en parse
        parseUser.signUpInBackground(error -> {
            if (error == null){
                registerResult.setValue("Registro exitoso");
            } else {
                registerResult.setValue("Error " + error.getMessage());
            }
        });
    }
}
