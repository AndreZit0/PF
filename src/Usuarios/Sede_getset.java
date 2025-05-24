package Usuarios;

public class Sede_getset {
    int ID_sede;
    String nombre_sede, direccion, estado;

    // Constructor sin ID (ideal para la creación desde la GUI)
    public Sede_getset(String nombre_sede, String direccion, String estado) {
        this.nombre_sede = nombre_sede;
        this.direccion = direccion;
        this.estado = estado;
    }
    // Constructor con ID_sede
    public Sede_getset(int ID_sede, String nombre_sede, String direccion, String estado) {
        this.ID_sede = ID_sede;
        this.nombre_sede = nombre_sede;
        this.direccion = direccion;
        this.estado = estado;
    }

    // Getters y Setters
    public int getID_sede() {
        return ID_sede;
    }

    public void setID_sede(int ID_sede) {
        this.ID_sede = ID_sede;
    }

    public String getNombre_sede() {
        return nombre_sede;
    }

    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
