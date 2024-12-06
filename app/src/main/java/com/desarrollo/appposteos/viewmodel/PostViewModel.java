package com.desarrollo.appposteos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.desarrollo.appposteos.model.Post;
import com.desarrollo.appposteos.providers.PostProvider;

import java.util.List;

public class PostViewModel extends ViewModel {
    private MutableLiveData<Boolean> postSuccess = new MutableLiveData<>();
    private PostProvider postProvider;
    private LiveData<List<Post>> posteos;

    public PostViewModel(){
        posteos = new MutableLiveData<>();
        postProvider = new PostProvider();
    }

    public LiveData<Boolean> getPostSuccess(){
        return postSuccess;
    }

    public void publicar(Post post){
        postProvider.addPost(post).observeForever(resultado ->{
            if ("Post publicado con exito".equals(resultado)){
                postSuccess.setValue(true);
            } else {
                postSuccess.setValue(false);
            }
        });
    }

    public LiveData<List<Post>> getPost(){
        posteos = postProvider.getPostCurrentUser();
        return posteos;
    }
}
