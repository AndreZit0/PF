package Usuarios;

public class Usuarios_getset {
    private int ID_usuarios;
    private int ID_rol;
    private String tipo_dc;
    private String documento;
    private String nombres;
    private String apellidos;
    private String email;
    private String email_insti;
    private String direccion;
    private String contacto1;
    private String contacto2;
    private String clave;
    private String estado;

    // Constructor completo (con ID)
    public Usuarios_getset(int ID_usuarios, int ID_rol, String tipo_dc, String documento, String nombres,
                           String apellidos, String email, String email_insti, String direccion, String contacto1,
                           String contacto2, String clave, String estado) {
        this.ID_usuarios = ID_usuarios;
        this.ID_rol = ID_rol;
        this.tipo_dc = tipo_dc;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.email_insti = email_insti;
        this.direccion = direccion;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
        this.clave = clave;
        this.estado = estado;
    }

    // Constructor sin ID (para nuevos registros)
    public Usuarios_getset(int ID_rol, String tipo_dc, String documento, String nombres,
                           String apellidos, String email,  String email_insti, String direccion, String contacto1,
                           String contacto2, String clave, String estado) {
        this.ID_rol = ID_rol;
        this.tipo_dc = tipo_dc;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.email_insti = email_insti;
        this.direccion = direccion;
        this.contacto1 = contacto1;
        this.contacto2 = contacto2;
        this.clave = clave;
        this.estado = estado;
    }

    // Getters y Setters
    public int getID_usuarios() {
        return ID_usuarios;
    }

    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
    }

    public int getID_rol() {
        return ID_rol;
    }

    public void setID_rol(int ID_rol) {
        this.ID_rol = ID_rol;
    }

    public String getTipo_dc() {
        return tipo_dc;
    }

    public void setTipo_dc(String tipo_dc) {
        this.tipo_dc = tipo_dc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getEmail_insti() {
        return email_insti;
    }

    public void setEmail_insti(String email_insti) {
        this.email_insti = email_insti;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getContacto2() {
        return contacto2;
    }

    public void setContacto2(String contacto2) {
        this.contacto2 = contacto2;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
