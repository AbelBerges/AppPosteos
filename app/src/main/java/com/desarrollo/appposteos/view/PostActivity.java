package com.desarrollo.appposteos.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.desarrollo.appposteos.R;
import com.desarrollo.appposteos.adapters.ImageAdapter;
import com.desarrollo.appposteos.databinding.ActivityPostBinding;
import com.desarrollo.appposteos.model.Post;
import com.desarrollo.appposteos.util.ImageUtils;
import com.desarrollo.appposteos.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    private static final int MAX_IMAGES = 3;
    private final int REQUEST_IMAGE = 1;
    private ActivityPostBinding binding;
    private PostViewModel viewModel;
    private final List<String> imageUrl = new ArrayList<>();
    private ImageAdapter adapter;
    private String categoria;
    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupCategorySpinner();
        setupRecycleView();
        setupViewModels();
        setupGalleryLauncher();
        binding.btnPublicarPost.setOnClickListener(v-> publicarPost());
    }
    private void setupRecycleView(){
        adapter = new ImageAdapter(imageUrl, this);
        binding.reciclyViewPost.setLayoutManager(new GridLayoutManager(this,3));
        binding.reciclyViewPost.setAdapter(adapter);

    }

    private void setupViewModels(){
        viewModel = new ViewModelProvider(PostActivity.this).get(PostViewModel.class);
        viewModel.getPostSuccess().observe(this, success -> {
            String message = success ? "Post publicado con exito" : "Error al publicar. ViewModel";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (success) finish();
        });
    }

    private void setupCategorySpinner(){
        ArrayAdapter<String> spinner = new ArrayAdapter<>(this,R.layout.spinner_item,
                getResources().getStringArray(R.array.categoria_array));
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoria.setAdapter(spinner);
        binding.spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoria = null;
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupGalleryLauncher(){
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),resultado ->{
            if (resultado.getResultCode() == Activity.RESULT_OK && resultado.getData() != null){
                Uri imageUri = resultado.getData().getData();
                if (imageUri != null && imageUrl.size() < MAX_IMAGES){
                    ImageUtils.subirImagenAParse(PostActivity.this, imageUri, new ImageUtils.ImageUploadCallback() {
                        @Override
                        public void onSuccess(String imgUrl) {
                            imageUrl.add(imgUrl);
                            adapter.notifyDataSetChanged();
                            updateRecyclerViewVisibility();
                        }

                        @Override
                        public void onFailure(Exception err) {
                            Toast.makeText(PostActivity.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(imageUrl.size() >= MAX_IMAGES){
                    Toast.makeText(PostActivity.this, "Máximo de imagenes alcanzado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.uploadImage.setOnClickListener(v -> {
            ImageUtils.pedirPermisos(PostActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE);
        });
    }


    private void publicarPost(){
        String elTitulo = binding.etTitulo.getText().toString().trim();
        String laDescripcion = binding.etDescripcion.getText().toString().trim();
        int laDuracion = Integer.parseInt(binding.etDuracion.getText().toString().trim());
        String laCategoria = binding.spinnerCategoria.toString();
        double elPresupuesto = Double.parseDouble(binding.etPresupuesto.getText().toString().trim());

        //Después pongo las validaciones
        Post temp = new Post(elTitulo,laDescripcion,laDuracion,laCategoria,elPresupuesto,new ArrayList<>(imageUrl));
        viewModel.publicar(temp);
       // Post post=new Post(titulo,descripcion, Integer.parseInt(duracionStr),categoria,presupuesto ,new ArrayList<>(imagenesUrls));
       // postViewModel.publicar(post);
    }

    private void updateRecyclerViewVisibility(){
        boolean hasImage = !imageUrl.isEmpty();
        binding.reciclyViewPost.setVisibility(hasImage ? View.VISIBLE : View.GONE);
        binding.uploadImage.setVisibility(imageUrl.size() < MAX_IMAGES ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == REQUEST_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            ImageUtils.openGallery(PostActivity.this, galleryLauncher);
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
        }
    }
}