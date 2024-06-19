package Controlador;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.regex.Pattern;

public class ClaseComprobar {

    public ClaseComprobar() {
    }

    //METODOS REGEX

    public final static boolean isValidPassword(String target) {
        return Pattern.compile("^^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$").matcher(target).matches();
    }

    public final static boolean isValidName(String target) {
        return Pattern.compile("^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣]{1,}$").matcher(target).matches();

    }

    public final static boolean isValidNickName(String target) {
        return Pattern.compile("^(?=.*[a-zA-Z\\d])[a-zA-Z0-9가-힣]{2,12}$|^[가-힣]$").matcher(target).matches();
    }

    public final static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile(
                "^[a-zA-Z0-9._%+-]+@(gmail|outlook|hotmail|yahoo|aol|icloud|live|msn|mail|yandex|protonmail|inbox)\\.(com|es|net|org|info|gov|edu)(\\.[a-z]{2})?$",
                Pattern.CASE_INSENSITIVE
        );
        return EMAIL_REGEX.matcher(email).matches();
    }

    //METODO PARA COMPROBAR SHAREDPREFERENCES
    public static boolean comprobarUsuarioConectado(String nombreArchivo, String clave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(nombreArchivo, Context.MODE_PRIVATE);
        return sharedPreferences.getString(clave, null) != null;
    }


    //METODO PARA COMPROBAR SI UN EVENTO ESTÁ LLENO O NO
}
