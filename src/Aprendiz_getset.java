package src;
/**
 * Clase que representa la estructura de datos de un aprendiz.
 * Esta clase contiene los atributos que describen a un aprendiz y
 * proporciona métodos para acceder y modificar estos atributos.
 */
public class Aprendiz_getset {
    // Identificador único del aprendiz
    int ID_numeroAprendices;
    // Identificador del usuario asociado al aprendiz
    int ID_usuarios;
    // Identificador de la ficha a la que pertenece el aprendiz
    int ID_Fichas;
    // Identificador de la empresa donde realiza sus prácticas el aprendiz
    int ID_empresas;
    // Identificador del instructor que supervisa al aprendiz
    int ID_instructor;
    // Identificador de la modalidad del aprendiz
    int ID_modalidad;
    // Estado actual del aprendiz
    String estado;

    /**
     * Constructor de la clase Aprendiz_getset.
     * Inicializa los atributos del aprendiz con los valores proporcionados.
     *
     * @param ID_numeroAprendices Identificador único del aprendiz.
     * @param ID_usuarios         Identificador del usuario asociado al aprendiz.
     * @param ID_Fichas           Identificador de la ficha a la que pertenece el aprendiz.
     * @param ID_empresas         Identificador de la empresa donde realiza sus prácticas el aprendiz.
     * @param ID_instructor       Identificador del instructor que supervisa al aprendiz.
     * @param ID_modalidad        Identificador de la modalidad del aprendiz.
     * @param estado              Estado actual del aprendiz.
     */
    public Aprendiz_getset(int ID_numeroAprendices, int ID_usuarios, int ID_Fichas, int ID_empresas, int ID_instructor, int ID_modalidad, String estado) {
        this.ID_numeroAprendices = ID_numeroAprendices;
        this.ID_usuarios = ID_usuarios;
        this.ID_Fichas = ID_Fichas;
        this.ID_empresas = ID_empresas;
        this.ID_instructor = ID_instructor;
        this.ID_modalidad = ID_modalidad;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único del aprendiz.
     *
     * @return El identificador único del aprendiz.
     */
    public int getID_numeroAprendices() {
        return ID_numeroAprendices;
    }

    /**
     * Establece el identificador único del aprendiz.
     *
     * @param ID_numeroAprendices El nuevo identificador único del aprendiz.
     */
    public void setID_numeroAprendices(int ID_numeroAprendices) {
        this.ID_numeroAprendices = ID_numeroAprendices;
    }

    /**
     * Obtiene el identificador del usuario asociado al aprendiz.
     *
     * @return El identificador del usuario asociado al aprendiz.
     */
    public int getID_usuarios() {
        return ID_usuarios;
    }

    /**
     * Establece el identificador del usuario asociado al aprendiz.
     *
     * @param ID_usuarios El nuevo identificador del usuario asociado al aprendiz.
     */
    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
    }

    /**
     * Obtiene el identificador de la ficha a la que pertenece el aprendiz.
     *
     * @return El identificador de la ficha a la que pertenece el aprendiz.
     */
    public int getID_Fichas() {
        return ID_Fichas;
    }

    /**
     * Establece el identificador de la ficha a la que pertenece el aprendiz.
     *
     * @param ID_Fichas El nuevo identificador de la ficha a la que pertenece el aprendiz.
     */
    public void setID_Fichas(int ID_Fichas) {
        this.ID_Fichas = ID_Fichas;
    }

    /**
     * Obtiene el identificador de la empresa donde realiza sus prácticas el aprendiz.
     *
     * @return El identificador de la empresa donde realiza sus prácticas el aprendiz.
     */
    public int getID_empresas() {
        return ID_empresas;
    }

    /**
     * Establece el identificador de la empresa donde realiza sus prácticas el aprendiz.
     *
     * @param ID_empresas El nuevo identificador de la empresa donde realiza sus prácticas el aprendiz.
     */
    public void setID_empresas(int ID_empresas) {
        this.ID_empresas = ID_empresas;
    }

    /**
     * Obtiene el identificador del instructor que supervisa al aprendiz.
     *
     * @return El identificador del instructor que supervisa al aprendiz.
     */
    public int getID_instructor() {
        return ID_instructor;
    }

    /**
     * Establece el identificador del instructor que supervisa al aprendiz.
     *
     * @param ID_instructor El nuevo identificador del instructor que supervisa al aprendiz.
     */
    public void setID_instructor(int ID_instructor) {
        this.ID_instructor = ID_instructor;
    }

    /**
     * Obtiene el identificador de la modalidad del aprendiz.
     *
     * @return El identificador de la modalidad del aprendiz.
     */
    public int getID_modalidad() {
        return ID_modalidad;
    }

    /**
     * Establece el identificador de la modalidad del aprendiz.
     *
     * @param ID_modalidad El nuevo identificador de la modalidad del aprendiz.
     */
    public void setID_modalidad(int ID_modalidad) {
        this.ID_modalidad = ID_modalidad;
    }

    /**
     * Obtiene el estado actual del aprendiz.
     *
     * @return El estado actual del aprendiz.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del aprendiz.
     *
     * @param estado El nuevo estado actual del aprendiz.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

