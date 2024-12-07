package com.desarrollo.appposteos.view.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desarrollo.appposteos.R;
import com.desarrollo.appposteos.databinding.FragmentPerfilBinding;
import com.desarrollo.appposteos.util.ImageUtils;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;


import java.io.IOException;

import okhttp3.internal.concurrent.Task;


public class PerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private ActivityResultLauncher<Intent> galleryLauncher;
    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        setupMenu();
        setupToolbar();
        displayUserInfo();
        setupGalleryLauncher();
        setupProfileImageClick();
        return binding.getRoot();
    }

    private void setupMenu(){
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.close_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.itemClose){
                    Toast.makeText(requireContext(), "Cerrar sesión", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
    private void setupToolbar(){
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.getRoot().findViewById(R.id.tools_filtro));
    }

    private void displayUserInfo(){
        ParseUser usuarioActual = ParseUser.getCurrentUser();
        if (usuarioActual != null){
            binding.nameUser.setText(usuarioActual.getUsername());
            binding.emailUser.setText(usuarioActual.getEmail());
            binding.insta.setText(usuarioActual.getString("instagram"));

            String fotoPerfil = usuarioActual.getString("foto_perfil");
            if (fotoPerfil != null){
                Picasso.get().load(fotoPerfil).placeholder(R.drawable.ic_perona_registro)
                        .error(R.drawable.ic_perona_registro).into(binding.circuloImageView);
            } else {
                binding.circuloImageView.setImageResource(R.drawable.ic_perona_registro);
            }
        } else {
            Toast.makeText(getContext(), "Usuario no logueado", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupGalleryLauncher(){
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultado -> {
            if (resultado.getResultCode() == Activity.RESULT_OK && resultado.getData() != null){
                Uri imgUri = resultado.getData().getData();
                if (imgUri != null){
                    handImageSelection(imgUri);
                }
            }
        });
    }

    private void setupProfileImageClick(){
        binding.circuloImageView.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                ImageUtils.pedirPermisos(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            ImageUtils.openGallery(requireContext(), galleryLauncher);
        });
    }

    private void handImageSelection(Uri imageUri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
            binding.circuloImageView.setImageBitmap(bitmap);
            ImageUtils.subirImagenAParse(requireContext(), imageUri, new ImageUtils.ImageUploadCallback() {
                @Override
                public void onSuccess(String imgUrl) {
                    ParseUser usuarioActual = ParseUser.getCurrentUser();
                    if (usuarioActual != null){
                        usuarioActual.put("foto_perfil", imgUrl);
                        usuarioActual.saveInBackground(err -> {
                            if (err == null){
                                Toast.makeText(requireContext(), "Foto subida correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "Error al guardar la url", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Exception err) {
                    Toast.makeText(requireContext(), "Error al subir la foto" + err.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }
}