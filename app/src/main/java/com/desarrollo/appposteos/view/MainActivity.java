package com.desarrollo.appposteos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.desarrollo.appposteos.databinding.ActivityMainBinding;
import com.desarrollo.appposteos.util.Validaciones;
import com.desarrollo.appposteos.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        manejarEventos();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    private void manejarEventos(){
        binding.tvRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        binding.btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = binding.etEmail.getText().toString().trim();
                String pass = binding.etPassword.getText().toString().trim();

                //Después hago las validaciones
                if (Validaciones.validarCampoVacio(correo)){
                    showToast("El campo correo no puede estar vacío");
                    return;
                }
                if (!Validaciones.validarMail(correo)){
                    showToast("El formato de el correo no es válido");
                    return;
                }
                if (Validaciones.validarCampoVacio(pass)){
                    showToast("El campo contraseña no puede estar vacío");
                }

                viewModel.login(correo, pass).observe(MainActivity.this, userId ->{
                    if (userId != null){
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        showToast("Hubo un error al iniciar sesión");
                    }
                });
            }
        });
    }

    private void showToast(String cadena){
        Toast.makeText(MainActivity.this, cadena, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        limpiarCampos();
    }
    private void limpiarCampos(){
        binding.etEmail.setText("");
        binding.etPassword.setText("");
    }
}