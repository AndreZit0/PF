package Example_Screen.Model;
/**
 * Este enum representa los diferentes roles que pueden tener los usuarios en el sistema.
 * Cada rol tiene un ID único y un nombre descriptivo.
 *
 */
public enum Rol {
    /**
     * Rol para aprendices.
     * ID: 1
     */
    APRENDIZ(1, "Aprendiz"),
    /**
     * Rol para usuarios que evalúan a otros (instructor).
     * ID: 2
     */
    EVALUADOR(2, "Evaluador"),

    /**
     * Rol para usuarios que co-evalúan.
     * ID: 3
     */
    COEVALUADOR(3, "Coevaluador"),
    /**
     * Rol para auxiliares.
     * ID: 4
     */
    AUXILIAR(4, "Auxiliar"),
    /**
     * Rol para administradores del SENA.
     * ID: 4
     */
    ADMINISTRADOR_SENA(4, "Administrador Sena"),
    /**
     * Rol para administradores del sistema (máximos privilegios es decir, todos los permisos).
     * ID: 6
     */
    ADMIN_SISTEMA(6, "Admin Sistema");

    private final int id;
    private final String nombre;
    /**
     * Constructor para crear un rol.
     * @param id Número identificador.
     * @param nombre Nombre descriptivo del rol
     */
    Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Obtiene el ID del rol.
     * @return El número identificador del rol
     */
    public int getId() {
        return id;
    }
    /**
     * Obtiene el nombre del rol.
     * @return El nombre legible (ej: "Aprendiz")
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Representación textual del rol (devuelve su nombre).
     * @return El nombre del rol
     */
    @Override
    public String toString() {
        return nombre;
    }
    /**
     * Busca un rol por su ID.
     * @param id El ID a buscar
     * @return El rol correspondiente, o APRENDIZ si no se encuentra
     */
    public static Rol fromId(int id) {
        for (Rol rol : values()) {
            if (rol.id == id) return rol;
        }
        return APRENDIZ;
    }
}