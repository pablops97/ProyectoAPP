package Modelo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    //Clase para extraer los datos del usuario de la API

    @SerializedName("idResultado")
    private int idUsuario;
    @SerializedName("nombre")
    private String usuario;

    @SerializedName("email")
    private String email;
    @SerializedName("imagen")
    private String imagen;
    @SerializedName("cuentaIban")
    private String cuentaIban;

    @SerializedName("registro")
    private String registro;

    public LoginResponse(int idUsuario, String usuario, String email, String imagen, String cuentaIban) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.email = email;
        this.imagen = imagen;
        this.cuentaIban = cuentaIban;
    }

    public String getEmail() {
        return email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCuentaIban() {
        return cuentaIban;
    }

    public void setCuentaIban(String cuentaIban) {
        this.cuentaIban = cuentaIban;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
}