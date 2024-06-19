package Modelo;

import com.google.gson.annotations.SerializedName;

public class Matriculacion {
    @SerializedName("idmatriculacion")
    private int idMatriculacion;
    @SerializedName("evento")
    private Evento evento;

    public Matriculacion(int idMatriculacion, Evento evento) {
        this.idMatriculacion = idMatriculacion;
        this.evento = evento;
    }

    public int getIdMatriculacion() {
        return idMatriculacion;
    }

    public void setIdMatriculacion(int idMatriculacion) {
        this.idMatriculacion = idMatriculacion;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
