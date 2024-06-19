package Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Evento {
    @SerializedName("idevento")
    private int idEvento;
    @SerializedName("nombreEvento")
    private String nombreEvento;
    @SerializedName("estadoEvento")
    private String estadoEvento;
    @SerializedName("descripcionEvento")
    private String descripcionEvento;
    @SerializedName("numeroparticipantes")
    private int numeroParticipantes;

    @SerializedName("precio")
    private double precio;

    @SerializedName("fechainicioinscripcion")
    private String fechaInicioInscripcion;

    @SerializedName("fechaInicio")
    private String fechaInicio;

    @SerializedName("fechaFin")
    private String fechaFin;

    @SerializedName("imagenevento")
    private String imagenEvento;

    @SerializedName("usuarios")
    private ArrayList<Usuario> usuarios;
    //utilizaré esta variable para comprobar si el usuario se ha unido al evento ya o no
    private boolean unido = false;
    private boolean lleno = false;

    public Evento(int idEvento, String nombreEvento, String estadoEvento, String descripcionEvento, int numeroParticipantes, double precio, String fechaInicioInscripcion, String fechaInicio, String fechaFin, String imagenEvento, ArrayList<Usuario> usuarios) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.estadoEvento = estadoEvento;
        this.descripcionEvento = descripcionEvento;
        this.numeroParticipantes = numeroParticipantes;
        this.precio = precio;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.imagenEvento = imagenEvento;
        this.usuarios = usuarios;
    }

    public Evento(int idEvento, String nombreEvento, String estadoEvento, String descripcionEvento, int numeroParticipantes, double precio, String fechaInicioInscripcion, String fechaInicio, String fechaFin, String imagenEvento) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.estadoEvento = estadoEvento;
        this.descripcionEvento = descripcionEvento;
        this.numeroParticipantes = numeroParticipantes;
        this.precio = precio;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.imagenEvento = imagenEvento;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(String estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaInicioInscripcion() {
        return fechaInicioInscripcion;
    }

    public void setFechaInicioInscripcion(String fechaInicioInscripcion) {
        this.fechaInicioInscripcion = fechaInicioInscripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getImagenEvento() {
        return imagenEvento;
    }

    public void setImagenEvento(String imagenEvento) {
        this.imagenEvento = imagenEvento;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isUnido() {
        return unido;
    }

    public void setUnido(boolean unido) {
        this.unido = unido;
    }

    public boolean isLleno() {
        return lleno;
    }

    public void setLleno(boolean lleno) {
        this.lleno = lleno;
    }
}
