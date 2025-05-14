package src;
/**
 * Clase que representa la estructura de datos de una sede.
 * Esta clase contiene los atributos que describen una sede y
 * proporciona métodos para acceder y modificar estos atributos.
 */
public class Sede_getset {
    // Identificador único de la sede
    int ID_sede;
    // Nombre de la sede
    String nombre_sede;
    // Dirección de la sede
    String direccion;
    // Estado de la sede
    String estado;

    /**
     * Constructor de la clase Sede_getset.
     * Inicializa los atributos nombre_sede, direccion y estado de la sede con los valores proporcionados.
     * Este constructor se utiliza cuando no se conoce el ID de la sede, por ejemplo, al crear una nueva sede.
     *
     * @param nombre_sede El nombre de la sede.
     * @param direccion   La dirección de la sede.
     * @param estado      El estado de la sede.
     */
    public Sede_getset(String nombre_sede, String direccion, String estado) {
        this.nombre_sede = nombre_sede;
        this.direccion = direccion;
        this.estado = estado;
    }

    /**
     * Constructor de la clase Sede_getset.
     * Inicializa todos los atributos de la clase, incluyendo el ID de la sede.
     * Este constructor se utiliza cuando se conoce el ID de la sede, por ejemplo, al recuperar datos de la base de datos.
     *
     * @param ID_sede     El identificador único de la sede.
     * @param nombre_sede El nombre de la sede.
     * @param direccion   La dirección de la sede.
     * @param estado      El estado de la sede.
     */
    public Sede_getset(int ID_sede, String nombre_sede, String direccion, String estado) {
        this.ID_sede = ID_sede;
        this.nombre_sede = nombre_sede;
        this.direccion = direccion;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único de la sede.
     *
     * @return El identificador único de la sede.
     */
    public int getID_sede() {
        return ID_sede;
    }

    /**
     * Establece el identificador único de la sede.
     *
     * @param ID_sede El nuevo identificador único de la sede.
     */
    public void setID_sede(int ID_sede) {
        this.ID_sede = ID_sede;
    }

    /**
     * Obtiene el nombre de la sede.
     *
     * @return El nombre de la sede.
     */
    public String getNombre_sede() {
        return nombre_sede;
    }

    /**
     * Establece el nombre de la sede.
     *
     * @param nombre_sede El nuevo nombre de la sede.
     */
    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    /**
     * Obtiene la dirección de la sede.
     *
     * @return La dirección de la sede.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la sede.
     *
     * @param direccion La nueva dirección de la sede.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estado de la sede.
     *
     * @return El estado de la sede.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la sede.
     *
     * @param estado El nuevo estado de la sede.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

