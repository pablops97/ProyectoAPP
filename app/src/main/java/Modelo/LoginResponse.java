package Modelo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    //Clase para extraer los datos del usuario de la API

    @SerializedName("idResultado")
    private String idUsuario;
    @SerializedName("nombre")
    private String usuario;

    @SerializedName("email")
    private String email;

    @SerializedName("registro")
    private String registro;

    public LoginResponse(String idUsuario, String usuario, String email, String registro) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.email = email;
        this.registro = registro;
    }

    public String getEmail() {
        return email;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}