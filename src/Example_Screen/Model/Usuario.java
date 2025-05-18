package Example_Screen.Model;
/**
 * Esta es la clase que representa a un usuario del sistema.
 * Guarda toda la información básica de una persona: nombre, rol, documento, etc.
 *
 */
public class Usuario {
    private String  nombre;
    private String rol;
    private String tipo_dc;
    private String numero;
    private String nombres;
    private String apellidos;
    private String email;
    /**
     * Constructor para crear un usuario.
     * @param nombre Nombre de usuario.
     * @param rol Rol asignado.
     */
    public Usuario(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.tipo_dc = tipo_dc;
        this.numero = numero;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTipo_dc() {
        return tipo_dc;
    }

    public void setTipo_dc(String tipo_dc) {
        this.tipo_dc = tipo_dc;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
