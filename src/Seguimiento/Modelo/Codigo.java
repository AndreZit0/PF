package Seguimiento.Modelo;

import java.util.Arrays;
import java.util.Date;

public class Codigo {
    private int idSeguimiento;
    private String tipoFormato;
    private Date fecha;
    private byte[] archivo;
    private String rutaArchivo;
    private String observaciones;
    private String nombreArchivo;
    private int idUsuario;
    private int idAprendiz;
    private String nombreAprendiz;
    private String cedulaAprendiz;
    private String val1;
    private String val2;
    private String val3;// Nuevo campo para almacenar la cédula

    public Codigo(int idSeguimiento, String tipoFormato, Date fecha, byte[] archivo, String rutaArchivo, String observaciones, String nombreArchivo, int idUsuario, int idAprendiz, String nombreAprendiz) {
        this.idSeguimiento = idSeguimiento;
        this.tipoFormato = tipoFormato;
        this.fecha = fecha;
        this.archivo = archivo;
        this.rutaArchivo = rutaArchivo;
        this.observaciones = observaciones;
        this.nombreArchivo = nombreArchivo;
        this.idUsuario = idUsuario;
        this.idAprendiz = idAprendiz;
        this.nombreAprendiz = nombreAprendiz;

    }

    // Constructor
    public Codigo() {
    }

    public Codigo(String name, String absolutePath, Date date, long length) {
    }

    // Getters y Setters

    public String getCedulaAprendiz() {
        return cedulaAprendiz;
    }

    public void setCedulaAprendiz(String cedulaAprendiz) {
        this.cedulaAprendiz = cedulaAprendiz;
    }

    public String getNombreAprendiz() {
        return nombreAprendiz;
    }

    public void setNombreAprendiz(String nombreAprendiz) {
        this.nombreAprendiz = nombreAprendiz;
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(String tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public byte[] getArchivo() { // Modificado para devolver byte[]
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(int idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal2() {
        return val2;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal3() {
        return val3;
    }

    public void setVal3(String val3) {
        this.val3 = val3;
    }
}