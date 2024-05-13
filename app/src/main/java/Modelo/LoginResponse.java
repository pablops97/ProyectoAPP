package Modelo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    //Clase para extraer los datos del usuario de la API

    @SerializedName("nombreusuario")
    private String nombreUsuario;
    @SerializedName("email")
    private String email;
    @SerializedName("telefono")
    private int telefono;

    public LoginResponse(String nombreUsuario, String email, int telefono) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.telefono = telefono;
    }

    public LoginResponse() {
    }


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
