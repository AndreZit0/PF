package Usuarios;

public class Programas_getset {
    int ID_programas;
    String nombre_programa, estado;

    public Programas_getset(String nombre_programa, String estado) {
        this.nombre_programa = nombre_programa;
        this.estado = estado;
    }
    public Programas_getset(int ID_programas,String nombre_programa, String estado) {
        this.ID_programas = ID_programas;
        this.nombre_programa = nombre_programa;
        this.estado = estado;
    }


    public Integer getID_programas() {
        return ID_programas;
    }

    public void setID_programas(int ID_programas) {
        this.ID_programas = ID_programas;
    }

    public String getNombre_programa() {
        return nombre_programa;
    }

    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
