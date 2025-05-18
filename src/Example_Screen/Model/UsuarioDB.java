package Example_Screen.Model;

import java.util.HashMap;
import java.util.Map;
/**
 * Clase que representa un usuario en la base de datos con todos sus datos y permisos.
 */
public class UsuarioDB {
    private int id;
    private String usuario;
    private String contraseña;
    private String nombre;
    private String identificacion;
    private final Rol rol;
    private Map<Permiso, Boolean> permisos = new HashMap<>();
    /**
     * Constructor principal para crear un usuario con todos sus datos.
     * @param id Identificador único en la BD
     * @param usuario Nombre de usuario
     * @param contraseña Contraseña
     * @param nombre Nombre completo
     * @param identificacion Número de documento
     * @param rol Rol del usuario (determina sus permisos)
     *
     * @example
     * UsuarioDB admin = new UsuarioDB(1, "admin", "1234", "María", "123456", Rol.ADMIN_SISTEMA);
     */
    public UsuarioDB(int id, String usuario, String contraseña, String nombre, String identificacion, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.rol = rol;
        cargarPermisosDesdeDB();
    }
    /**
     * Carga los permisos desde la base de datos según el rol del usuario.
     * Se ejecuta automáticamente al crear el objeto.
     */
    private void cargarPermisosDesdeDB() {
        this.permisos = DatabaseManager.cargarPermisosRol(this.rol);
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public Rol getRol() { return rol; }

    public boolean tienePermiso(Permiso permiso) {
        return permisos.getOrDefault(permiso, false);
    }

    public void setPermiso(Permiso permiso, boolean valor) {
        permisos.put(permiso, valor);
    }
}