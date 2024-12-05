package com.desarrollo.appposteos.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.desarrollo.appposteos.databinding.ActivityRegisterBinding;
import com.desarrollo.appposteos.model.User;
import com.desarrollo.appposteos.util.Validaciones;
import com.desarrollo.appposteos.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        manejarEventos();
    }

    private void manejarEventos(){
        //Creamos la acción para el boton volver
        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Creamos la acción para el botón registrar
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    private void showToast(String cadena){
        Toast.makeText(RegisterActivity.this, cadena, Toast.LENGTH_SHORT).show();
    }

    private void registrar(){
        String usuario = binding.etRegistroUsuario.getText().toString().trim();
        String email = binding.etRegistroEmail.getText().toString().trim();
        String clave = binding.etRegistroClave.getText().toString().trim();
        String clave2 = binding.etRegistroConfirmarClave.getText().toString().trim();

        //hacemos las validaciones de los campos
        //Chequeamos el campo de usuario
        if (Validaciones.validarCampoVacio(usuario)){
            showToast("El campo usuario no puede estar vacío y debe tener más de 4 caracteres");
            return;
        }
        if (!Validaciones.validarMail(email)){
            showToast("El correo no tiene el formato correcto");
        }
        if (!Validaciones.validarPass(clave, clave2)){
            showToast("Revise los campos contraseñas");
        }

        //armamos el usuario y llamamos al método para registrar en la base parse
        User temp = new User();
        temp.setUsername(usuario);
        temp.setEmail(email);
        temp.setPassword(clave);
        viewModel.register(temp);
        finish();
    }
}