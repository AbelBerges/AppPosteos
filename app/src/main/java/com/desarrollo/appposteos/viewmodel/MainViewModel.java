package com.desarrollo.appposteos.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.desarrollo.appposteos.providers.AuthProvider;

public class MainViewModel extends ViewModel {
    public AuthProvider authProvider;

    public MainViewModel(){
        authProvider = new AuthProvider();
    }

    public LiveData<String> login(String email, String pass){
        MutableLiveData<String> loginResult = new MutableLiveData<>();
        authProvider.signIn(email,pass).observeForever(userId -> {
            loginResult.setValue(userId);
        });
        return loginResult;
    }
}
