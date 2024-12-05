package com.desarrollo.appposteos.providers;

import androidx.compose.runtime.collection.MutableVector;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.desarrollo.appposteos.model.User;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class UserProvider {

    public LiveData<String> createUser(User user){
        MutableLiveData<String> result = new MutableLiveData<>();
        ParseObject userObject = new ParseObject("User");
        userObject.put("user_id", user.getId());
        userObject.put("email", user.getEmail());
        userObject.put("password", user.getPassword());
        userObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException error) {
                if (error == null){
                    //Sin error la creación es correcta
                    result.setValue("Usuario creado correctamente");
                } else {
                    result.setValue("Error al crear el usuario");
                }
            }
        });
        return result;
    }
    public LiveData<User> getUser(String email){
        MutableLiveData<User> userData = new MutableLiveData<>();
        //Con una consulta buscamos el usuario
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("email", email);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> usuarios, ParseException error) {
                if (error == null && usuarios.size()> 0){
                    ParseObject userObjet =  usuarios.get(0);
                    User user = new User();
                    user.setId(userObjet.getString("user_id"));
                    user.setEmail(userObjet.getString("email"));
                    user.setPassword(userObjet.getString("password"));
                    userData.setValue(user);
                } else {
                    userData.setValue(null);
                }
            }
        });
        return userData ;
    }

    public LiveData<String> updateUser(User user){
        MutableLiveData<String> result = new MutableLiveData<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("user_id",user.getId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> usuario, ParseException error) {
                if (error == null && usuario.size() > 0){
                    ParseObject objeto = usuario.get(0);
                    objeto.put("email", user.getEmail());
                    objeto.put("password", user.getPassword());

                    objeto.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException err) {
                            if (err == null){
                                result.setValue("Usuario actualizdo correctamente");
                            } else {
                                result.setValue("Erro al actualizar el usuario");
                            }
                        }
                    });
                }
            }
        });
        return result;
    }

    //Eliminación de usuario
    public LiveData<String> deleteUser(String userId){
        MutableLiveData<String> result = new MutableLiveData<>();
        //Hacemos el query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("user_id", userId);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> usuario, ParseException error) {
                if (error == null && usuario.size() > 0){
                    ParseObject userObjet = usuario.get(0);
                    //Lo eliminamos
                    userObjet.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException err) {
                            if (err == null){
                                result.setValue("Usuario eliminado correctamente");
                            } else {
                                result.setValue("Error al eliminar el usuario");
                            }
                        }
                    });
                }
            }
        });
        return result;
    }
}
