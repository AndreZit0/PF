package Usuarios;

import java.util.Date;

public class Fichas_setget {
    int ID_Fichas;

    String codigo, modalidad, jornada, nivel_formacion, estado,nombre_programa,nombre_sede;

    Date fecha_inicio, fecha_fin_lec, fecha_final;

    public Fichas_setget(String nombre_programa, String nombre_sede, String codigo,
                         String modalidad, String jornada, String nivel_formacion,
                         Date fecha_inicio, Date fecha_fin_lec, Date fecha_final, String estado) {
        // Elimina esta línea incorrecta:
        // this.ID_Fichas = ID_Fichas;

        this.codigo = codigo;
        this.modalidad = modalidad;
        this.jornada = jornada;
        this.nivel_formacion = nivel_formacion;
        this.estado = estado;
        this.nombre_programa = nombre_programa;
        this.nombre_sede = nombre_sede;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin_lec = fecha_fin_lec;
        this.fecha_final = fecha_final;
    }

    public int getID_Fichas() {
        return ID_Fichas;
    }

    public void setID_Fichas(int ID_Fichas) {
        this.ID_Fichas = ID_Fichas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getNivel_formacion() {
        return nivel_formacion;
    }

    public void setNivel_formacion(String nivel_formacion) {
        this.nivel_formacion = nivel_formacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre_programa() {
        return nombre_programa;
    }

    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    public String getNombre_sede() {
        return nombre_sede;
    }

    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin_lec() {
        return fecha_fin_lec;
    }

    public void setFecha_fin_lec(Date fecha_fin_lec) {
        this.fecha_fin_lec = fecha_fin_lec;
    }

    public Date getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }
}
