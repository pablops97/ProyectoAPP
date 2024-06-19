package Modelo;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("nombreUsuario")
    private String nombreUsuario;

    @SerializedName("imagenUsuario")
    private String imagen;
    @SerializedName("correo")
    private String correo;
    @SerializedName("cuentaIban")
    private String iban;

    public Usuario(String nombreUsuario, String imagen) {
        this.nombreUsuario = nombreUsuario;
        this.imagen = imagen;
    }

    public Usuario(String nombreUsuario, String imagen, String correo, String iban) {
        this.nombreUsuario = nombreUsuario;
        this.imagen = imagen;
        this.correo = correo;
        this.iban = iban;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
