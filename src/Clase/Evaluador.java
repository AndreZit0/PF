package Clase;

public class Evaluador {

    int id_NumeroAprendices;
    int id_Usuarios;
    int id_Fichas;
    int id_Empresas;
    int id_Instructor;
    int id_Modalidad;

    String estado;


    public Evaluador(int id_NumeroAprendices, int id_Usuarios, int id_Fichas, int id_Empresas, int id_Instructor, int id_Modalidad, String estado) {
        this.id_NumeroAprendices = id_NumeroAprendices;
        this.id_Usuarios = id_Usuarios;
        this.id_Fichas = id_Fichas;
        this.id_Empresas = id_Empresas;
        this.id_Instructor = id_Instructor;
        this.id_Modalidad = id_Modalidad;
        this.estado = estado;
    }

    public int getId_NumeroAprendices() {
        return id_NumeroAprendices;
    }

    public void setId_NumeroAprendices(int id_NumeroAprendices) {
        this.id_NumeroAprendices = id_NumeroAprendices;
    }

    public int getId_Usuarios() {
        return id_Usuarios;
    }

    public void setId_Usuarios(int id_Usuarios) {
        this.id_Usuarios = id_Usuarios;
    }

    public int getId_Fichas() {
        return id_Fichas;
    }

    public void setId_Fichas(int id_Fichas) {
        this.id_Fichas = id_Fichas;
    }

    public int getId_Empresas() {
        return id_Empresas;
    }

    public void setId_Empresas(int id_Empresas) {
        this.id_Empresas = id_Empresas;
    }
}

