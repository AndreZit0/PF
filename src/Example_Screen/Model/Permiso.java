package Example_Screen.Model;
/**
 * Este enum define todos los permisos que existen en el sistema.
 * Cada permiso tiene un ID único y una descripción clara de lo que permite hacer.
 * Es como una lista de "superpoderes" que pueden tener los usuarios.
 */
public enum Permiso {

    /**
     * Permiso para agregar nuevos usuarios al sistema.
     * ID: 1
     */
    CREAR_USUARIO(1, "Crear usuarios"),
    /**
     * Permiso para modificar información de usuarios existentes.
     * ID: 2
     */
    EDITAR_USUARIO(2, "Editar usuarios"),
    /**
     * Permiso para ver la información de los usuarios.
     * ID: 3
     */
    VER_USUARIO(3, "Ver usuarios"),
    /**
     * Permiso para desactivar usuarios (pero no eliminarlos permanentemente).
     * ID: 4
     */
    INHABILITAR_USUARIO(4, "Inhabilitar usuarios"),

    /**
     * Permiso para crear nuevas fichas en el sistema.
     * ID: 5
     */
    CREAR_FICHA(5, "Crear fichas"),
    /**
     * Permiso para modificar fichas existentes.
     * ID: 6
     */
    EDITAR_FICHA(6, "Editar fichas"),
    /**
     * Permiso para consultar información de fichas.
     * ID: 7
     */
    VER_FICHA(7, "Ver fichas"),
    /**
     * Permiso para desactivar fichas (pero no eliminarlas permanentemente).
     * ID: 8
     */
    INHABILITAR_FICHA(8, "Inhabilitar fichas"),


    /**
     * Permiso para crear nuevos registros de seguimiento.
     * ID: 26
     */
    CREAR_SEGUIMIENTO(26, "Crear seguimientos"),
    /**
     * Permiso para modificar seguimientos existentes.
     * ID: 27
     */
    EDITAR_SEGUIMIENTO(27, "Editar seguimientos"),
    /**
     * Permiso para eliminar seguimientos del sistema.
     * ID: 28
     */
    ELIMINAR_SEGUIMIENTO(28, "Eliminar seguimientos"),
    /**
     * Permiso para firmar digitalmente seguimientos.
     * ID: 32
     */
    FIRMAR_SEGUIMIENTO(32, "Firmar seguimientos"),
    /**
     * Permiso para consultar seguimientos.
     * ID: 34
     */
    VER_SEGUIMIENTO(34, "Ver seguimientos"),

    /**
     * Permiso para cambiar el nombre propio en el sistema.
     * ID: 24
     */
    EDITAR_NOMBRE(24, "Editar nombre"),
    /**
     * Permiso para modificar el número de identificación.
     * ID: 25
     */
    EDITAR_IDENTIFICACION(25, "Editar identificación");

    private final int id;
    private final String descripcion;

    /**
     * Constructor interno del enum.
     * @param id El número único que identifica este permiso en la BD
     * @param descripcion Texto que explica para qué sirve el permiso
     */
    Permiso(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID numérico del permiso.
     * @return El ID único del permiso
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la descripción amigable del permiso.
     * @return Texto que explica el permiso
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Representación textual del permiso (devuelve la descripción).
     * @return La descripción del permiso
     */
    @Override
    public String toString() {
        return descripcion;
    }
}