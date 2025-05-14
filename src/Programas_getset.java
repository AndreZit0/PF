package src;
/**
 * Clase que representa la estructura de datos de un programa.
 * Esta clase contiene los atributos que describen un programa y
 * proporciona métodos para acceder y modificar estos atributos.
 */
public class Programas_getset {
    // Identificador único del programa
    int ID_programas;
    // Nombre del programa
    String nombre_programa;
    // Estado del programa
    String estado;

    /**
     * Constructor de la clase Programas_getset.
     * Inicializa los atributos nombre_programa y estado del programa con los valores proporcionados.
     *
     * @param nombre_programa El nombre del programa.
     * @param estado          El estado del programa.
     */
    public Programas_getset(String nombre_programa, String estado) {
        this.nombre_programa = nombre_programa;
        this.estado = estado;
    }

    /**
     * Constructor de la clase Programas_getset.
     * Inicializa los atributos ID_programas, nombre_programa y estado del programa con los valores proporcionados.
     *
     * @param ID_programas    El identificador único del programa.
     * @param nombre_programa El nombre del programa.
     * @param estado          El estado del programa.
     */
    public Programas_getset(int ID_programas, String nombre_programa, String estado) {
        this.ID_programas = ID_programas;
        this.nombre_programa = nombre_programa;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único del programa.
     *
     * @return El identificador único del programa.
     */
    public int getID_programas() {
        return ID_programas;
    }

    /**
     * Establece el identificador único del programa.
     *
     * @param ID_programas El nuevo identificador único del programa.
     */
    public void setID_programas(int ID_programas) {
        this.ID_programas = ID_programas;
    }

    /**
     * Obtiene el nombre del programa.
     *
     * @return El nombre del programa.
     */
    public String getNombre_programa() {
        return nombre_programa;
    }

    /**
     * Establece el nombre del programa.
     *
     * @param nombre_programa El nuevo nombre del programa.
     */
    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    /**
     * Obtiene el estado del programa.
     *
     * @return El estado del programa.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del programa.
     *
     * @param estado El nuevo estado del programa.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

