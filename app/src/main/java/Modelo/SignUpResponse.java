package Modelo;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    //Clase para comprobar si el registro ha salido correcto o no

    @SerializedName("mensaje")
    private String mensaje;
    @SerializedName("codigo")
    private int codigo;

    public SignUpResponse() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
