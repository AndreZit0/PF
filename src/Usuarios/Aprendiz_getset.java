package Usuarios;

public class Aprendiz_getset {
    int ID_numeroAprendices,ID_usuarios,ID_Fichas,ID_empresas,ID_instructor,ID_modalidad;
    String estado;

    public Aprendiz_getset(int ID_numeroAprendices, int ID_usuarios, int ID_Fichas, int ID_empresas, int ID_instructor,int ID_modalidad, String estado) {
        this.ID_numeroAprendices = ID_numeroAprendices;
        this.ID_usuarios = ID_usuarios;
        this.ID_Fichas = ID_Fichas;
        this.ID_empresas = ID_empresas;
        this.ID_instructor = ID_instructor;
        this.ID_modalidad = ID_modalidad;
        this.estado = estado;
    }


    public int getID_numeroAprendices() {
        return ID_numeroAprendices;
    }

    public void setID_numeroAprendices(int ID_numeroAprendices) {
        this.ID_numeroAprendices = ID_numeroAprendices;
    }

    public int getID_usuarios() {
        return ID_usuarios;
    }

    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
    }

    public int getID_Fichas() {
        return ID_Fichas;
    }

    public void setID_Fichas(int ID_Fichas) {
        this.ID_Fichas = ID_Fichas;
    }

    public int getID_empresas() {
        return ID_empresas;
    }

    public void setID_empresas(int ID_empresas) {
        this.ID_empresas = ID_empresas;
    }

    public int getID_instructor() {
        return ID_instructor;
    }

    public void setID_instructor(int ID_instructor) {
        this.ID_instructor = ID_instructor;
    }

    public int getID_modalidad() {
        return ID_modalidad;
    }

    public void setID_modalidad(int ID_modalidad) {
        this.ID_modalidad = ID_modalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
