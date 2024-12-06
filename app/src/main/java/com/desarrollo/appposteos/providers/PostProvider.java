package com.desarrollo.appposteos.providers;

import android.graphics.PorterDuff;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.desarrollo.appposteos.model.Post;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostProvider {
    /*
        titulo; descripcion; duracion; categoria; presupuesto; imagenes;
     */
    public LiveData<String> addPost(Post post){
        MutableLiveData<String> resultado = new MutableLiveData<>();
        ParseObject elPosteo = new ParseObject("Post");
        elPosteo.put("titulo", post.getTitulo());
        elPosteo.put("descripcion", post.getDescripcion());
        elPosteo.put("duracion", post.getDuracion());
        elPosteo.put("categoria", post.getCategoria());
        elPosteo.put("presupuesto", post.getPresupuesto());
        elPosteo.put("publicaciones", ParseUser.getCurrentUser());
        //String titulo, String descripcion, int duracion, String categoria, double presupuesto, List<String> imagenes
        Log.d("Usuario ", "Muestro el USUARIO ACTUAL: " + elPosteo.get("publicaciones"));
        elPosteo.saveInBackground(error -> {
            if (error == null) {
                ParseRelation<ParseObject> relacion = elPosteo.getRelation("imagenes");
                for (String direccion : post.getImagenes()) {
                    ParseObject imgObjeto = new ParseObject("Image");
                    imgObjeto.put("url", direccion);
                    imgObjeto.saveInBackground(imgError -> {
                        if (imgError == null){
                            relacion.add(imgObjeto);
                            elPosteo.saveInBackground(publicado -> {
                                if (publicado == null){
                                    resultado.setValue("Post publicado con exito");
                                } else {
                                    resultado.setValue("Error al guardar la relación con las imágenes: " + publicado.getMessage());
                                }
                            });
                        } else {
                            resultado.setValue("Error al guardar la imagen: " +  imgError.getMessage());
                        }
                    });
                }
            } else {
                resultado.setValue("Error al guardar el post: " + error.getMessage());
            }
        });
        return resultado;
    }

    public LiveData<List<Post>> getPostCurrentUser(){
        MutableLiveData<List<Post>> resultado = new MutableLiveData<>();
        ParseUser elUsuario = ParseUser.getCurrentUser();
        if (elUsuario == null){
            resultado.setValue(null);
            return resultado;
        }
        //Armamos el query para traer los posteos por el identidicador del usuario
        ParseQuery<ParseObject> elQuery = ParseQuery.getQuery("Post");
        elQuery.whereEqualTo("user", elUsuario);
        //probar si funciona con otra variable user
        elQuery.include("user");
        elQuery.findInBackground((posteos, error)-> {
            if (error == null){
                List<Post> listado = new ArrayList<>();
                for (ParseObject elObjeto: posteos) {
                    /*
                    Armamos el objeto Post segun el constructor sin las imágenes
                    titulo; descripcion; duracion; categoria; presupuesto; imagenes;
                    */
                    Post elPost = new Post(elObjeto.getString("titulo"), elObjeto.getString("descripcion"), elObjeto.getInt("duracion"),
                            elObjeto.getString("categoria"), elObjeto.getDouble("presupuesto"));
                    //Agregamos las imagenes
                    ParseRelation<ParseObject> relacion = elObjeto.getRelation("imagenes");
                    try {
                        List<ParseObject> lasImagenes = relacion.getQuery().find();
                        List<String> lasurls = new ArrayList<>();
                        for (ParseObject objImagenes : lasImagenes) {
                            lasurls.add(objImagenes.getString("url"));
                        }
                        //Agregamos el array en el objeto Post
                        elPost.setImagenes(lasurls);
                    } catch (ParseException err){
                        err.printStackTrace();
                    }
                    listado.add(elPost);
                }
                resultado.setValue(listado);
            } else {
                resultado.setValue(new ArrayList<>());
            }
        });
        return resultado;
    }
}
