package Example_Screen.Model;
/**
 * Clase que representa la información completa de un usuario en el sistema.
 * Contiene todos los datos personales.
 */
public class Usuarios {

    int ID_usuarios; String tipo_dc,numero,nombres,apellidos,email,direccion,contacto1,estado; int ID_rol;

    public Usuarios(int ID_usuarios, String tipo_dc, String numero, String nombres, String apellidos, String email, String direccion, String contacto1, String estado, int ID_rol) {
        this.ID_usuarios = ID_usuarios;
        this.tipo_dc = tipo_dc;
        this.numero = numero;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.contacto1 = contacto1;
        this.estado = estado;
        this.ID_rol = ID_rol;
    }

    public int getID_usuarios() {
        return ID_usuarios;
    }

    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getID_rol() {
        return ID_rol;
    }

    public void setID_rol(int ID_rol) {
        this.ID_rol = ID_rol;
    }
}