package Usuarios;

public class Modalidad_getset {
    int ID_modalidad;
    String modalidad;

    public Modalidad_getset(int ID_modalidad, String modalidad) {
        this.ID_modalidad = ID_modalidad;
        this.modalidad = modalidad;
    }
    public Modalidad_getset(String modalidad) {

        this.modalidad = modalidad;
    }

    public int getID_modalidad() {
        return ID_modalidad;
    }

    public void setID_modalidad(int ID_modalidad) {
        this.ID_modalidad = ID_modalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }
}
