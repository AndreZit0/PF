package src;

/**
 * Clase que representa la estructura de datos de un usuario.
 * Esta clase contiene los atributos que describen a un usuario
 * y proporciona métodos para acceder y modificar estos atributos.
 */
public class Usuarios_getset {
    // Identificador único del usuario
    private int ID_usuarios;
    // Identificador del rol del usuario
    private int ID_rol;
    // Tipo de documento de identificación
    private String tipo_dc;
    // Número de documento de identificación
    private String documento;
    // Nombres del usuario
    private String nombres;
    // Apellidos del usuario
    private String apellidos;
    // Correo electrónico del usuario
    private String email;
    // Dirección del usuario
    private String direccion;
    // Número de contacto principal del usuario
    private String contacto1;
    // Número de contacto secundario del usuario
    private String contacto2;
    // Contraseña del usuario
    private String clave;
    // Estado del usuario
    private String estado;

    /**
     * Constructor de la clase Usuarios_getset.
     * Inicializa todos los atributos del usuario, incluyendo el ID de usuario.
     *
     * @param ID_usuarios El identificador único del usuario.
     * @param ID_rol      El identificador del rol del usuario.
     * @param tipo_dc     El tipo de documento de identificación.
     * @param documento   El número de documento de identificación.
     * @param nombres     Los nombres del usuario.
     * @param apellidos   Los apellidos del usuario.
     * @param email       El correo electrónico del usuario.
     * @param direccion   La dirección del usuario.
     * @param contacto1   El número de contacto principal del usuario.
     * @param contacto2   El número de contacto secundario del usuario.
     * @param clave       La contraseña del usuario.
     * @param estado      El estado del usuario.
     */
    public Usuarios_getset(int ID_usuarios, int ID_rol, String tipo_dc, String documento, String nombres,
                           String apellidos, String email, String direccion, String contacto1,
                           String contacto2, String clave, String estado) {
        this.ID_usuarios = ID_usuarios;
        this.ID_rol = ID_rol;
        this.tipo_dc = tipo_dc;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
        this.clave = clave;
        this.estado = estado;
    }

    /**
     * Constructor de la clase Usuarios_getset.
     * Inicializa los atributos del usuario, excluyendo el ID de usuario.
     * Este constructor se utiliza para crear nuevos usuarios que aún no tienen un ID asignado.
     *
     * @param ID_rol      El identificador del rol del usuario.
     * @param tipo_dc     El tipo de documento de identificación.
     * @param documento   El número de documento de identificación.
     * @param nombres     Los nombres del usuario.
     * @param apellidos   Los apellidos del usuario.
     * @param email       El correo electrónico del usuario.
     * @param direccion   La dirección del usuario.
     * @param contacto1   El número de contacto principal del usuario.
     * @param contacto2   El número de contacto secundario del usuario.
     * @param clave       La contraseña del usuario.
     * @param estado      El estado del usuario.
     */
    public Usuarios_getset(int ID_rol, String tipo_dc, String documento, String nombres,
                           String apellidos, String email, String direccion, String contacto1,
                           String contacto2, String clave, String estado) {
        this.ID_rol = ID_rol;
        this.tipo_dc = tipo_dc;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
        this.clave = clave;
        this.estado = estado;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El identificador único del usuario.
     */
    public int getID_usuarios() {
        return ID_usuarios;
    }

    /**
     * Establece el identificador único del usuario.
     *
     * @param ID_usuarios El nuevo identificador único del usuario.
     */
    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
    }

    /**
     * Obtiene el identificador del rol del usuario.
     *
     * @return El identificador del rol del usuario.
     */
    public int getID_rol() {
        return ID_rol;
    }

    /**
     * Establece el identificador del rol del usuario.
     *
     * @param ID_rol El nuevo identificador del rol del usuario.
     */
    public void setID_rol(int ID_rol) {
        this.ID_rol = ID_rol;
    }

    /**
     * Obtiene el tipo de documento de identificación del usuario.
     *
     * @return El tipo de documento de identificación del usuario.
     */
    public String getTipo_dc() {
        return tipo_dc;
    }

    /**
     * Establece el tipo de documento de identificación del usuario.
     *
     * @param tipo_dc El nuevo tipo de documento de identificación del usuario.
     */
    public void setTipo_dc(String tipo_dc) {
        this.tipo_dc = tipo_dc;
    }

    /**
     * Obtiene el número de documento de identificación del usuario.
     *
     * @return El número de documento de identificación del usuario.
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el número de documento de identificación del usuario.
     *
     * @param documento El nuevo número de documento de identificación del usuario.
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene los nombres del usuario.
     *
     * @return Los nombres del usuario.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Establece los nombres del usuario.
     *
     * @param nombres Los nuevos nombres del usuario.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene los apellidos del usuario.
     *
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     *
     * @param apellidos Los nuevos apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El nuevo correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la dirección del usuario.
     *
     * @return La dirección del usuario.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del usuario.
     *
     * @param direccion La nueva dirección del usuario.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el número de contacto principal del usuario.
     *
     * @return El número de contacto principal del usuario.
     */
    public String getContacto1() {
        return contacto1;
    }

    /**
     * Establece el número de contacto principal del usuario.
     *
     * @param contacto1 El nuevo número de contacto principal del usuario.
     */
    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    /**
     * Obtiene el número de contacto secundario del usuario.
     *
     * @return El número de contacto secundario del usuario.
     */
    public String getContacto2() {
        return contacto2;
    }

    /**
     * Establece el número de contacto secundario del usuario.
     *
     * @param contacto2 El nuevo número de contacto secundario del usuario.
     */
    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param clave La nueva contraseña del usuario.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Obtiene el estado del usuario.
     *
     * @return El estado del usuario.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del usuario.
     *
     * @param estado El nuevo estado del usuario.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}

