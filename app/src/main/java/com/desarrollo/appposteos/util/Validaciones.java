package com.desarrollo.appposteos.util;

public class Validaciones {
    public static boolean validarCampoVacio(String cadena){
        return cadena != null && cadena.isEmpty() && cadena.length() > 4;
    }

    public int validarNumero(String num){
        try{
            int numero = Integer.parseInt(num);
            return numero >= 0 ? numero : -1; //Devuelve -1 en caso de número negativo
        } catch (NumberFormatException err){
            return -2;
        }
    }

    public static boolean validarMail(String correo){
        String pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return correo.matches(pattern);
    }

    public static boolean validarPass(String pass1, String pass2){
        if (pass1 == null || pass1.isEmpty() || pass2 == null || pass2.isEmpty()){
            return false;
        }
        if(pass1.length() < 6){
            return false;
        }
        if (!pass1.equals(pass2)){
            return false;
        }
        return true;
    }
}
