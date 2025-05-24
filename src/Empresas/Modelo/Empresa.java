package Empresas.Modelo;

/**
 * Clase que representa una empresa con sus respectivos datos de identificación,
 * contacto y localización.
 */
public class Empresa {

    /** ID único de la empresa */
    int ID_empresas;

    /** ID del usuario asociado a la empresa */
    int ID_usuarios;

    /** Número de identificación tributaria de la empresa */
    String nit;

    /** Nombre de la empresa */
    String nombre_empresa;

    /** Dirección de la empresa */
    String direccion;

    /** Área de actividad de la empresa */
    String area;

    /** Información de contacto de la empresa */
    String contacto;

    /** Correo electrónico de la empresa */
    String email;

    /** Departamento donde se encuentra la empresa */
    String departamento;

    /** Ciudad donde se encuentra la empresa */
    String ciudad;

    /**
     * Constructor que inicializa los datos de la empresa.
     *
     * @param ID_empresas El ID único de la empresa.
     * @param ID_usuarios El ID del usuario asociado a la empresa.
     * @param nit El número de identificación tributaria de la empresa.
     * @param nombre_empresa El nombre de la empresa.
     * @param direccion La dirección de la empresa.
     * @param area El área de actividad de la empresa.
     * @param contacto La información de contacto de la empresa.
     * @param email El correo electrónico de la empresa.
     * @param departamento El departamento donde se encuentra la empresa.
     * @param ciudad La ciudad donde se encuentra la empresa.
     */
    public Empresa(int ID_empresas, int ID_usuarios, String nit, String nombre_empresa, String direccion, String area, String contacto, String email, String departamento, String ciudad) {
        this.ID_empresas = ID_empresas;
        this.ID_usuarios = ID_usuarios;
        this.nit = nit;
        this.nombre_empresa = nombre_empresa;
        this.direccion = direccion;
        this.area = area;
        this.contacto = contacto;
        this.email = email;
        this.departamento = departamento;
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el ID de la empresa.
     *
     * @return El ID de la empresa.
     */
    public int getID_empresas() {
        return ID_empresas;
    }

    /**
     * Establece el ID de la empresa.
     *
     * @param ID_empresas El ID de la empresa.
     */
    public void setID_empresas(int ID_empresas) {
        this.ID_empresas = ID_empresas;
    }

    /**
     * Obtiene el ID del usuario asociado a la empresa.
     *
     * @return El ID del usuario asociado a la empresa.
     */
    public int getID_usuarios() {
        return ID_usuarios;
    }

    /**
     * Establece el ID del usuario asociado a la empresa.
     *
     * @param ID_usuarios El ID del usuario asociado a la empresa.
     */
    public void setID_usuarios(int ID_usuarios) {
        this.ID_usuarios = ID_usuarios;
    }

    /**
     * Obtiene el número de identificación tributaria de la empresa.
     *
     * @return El número de identificación tributaria de la empresa.
     */
    public String getNit() {
        return nit;
    }

    /**
     * Establece el número de identificación tributaria de la empresa.
     *
     * @param nit El número de identificación tributaria de la empresa.
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtiene el nombre de la empresa.
     *
     * @return El nombre de la empresa.
     */
    public String getNombre_empresa() {
        return nombre_empresa;
    }

    /**
     * Establece el nombre de la empresa.
     *
     * @param nombre_empresa El nombre de la empresa.
     */
    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    /**
     * Obtiene la dirección de la empresa.
     *
     * @return La dirección de la empresa.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección de la empresa.
     *
     * @param direccion La dirección de la empresa.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el área de actividad de la empresa.
     *
     * @return El área de actividad de la empresa.
     */
    public String getArea() {
        return area;
    }

    /**
     * Establece el área de actividad de la empresa.
     *
     * @param area El área de actividad de la empresa.
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Obtiene la información de contacto de la empresa.
     *
     * @return La información de contacto de la empresa.
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Establece la información de contacto de la empresa.
     *
     * @param contacto La información de contacto de la empresa.
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Obtiene el correo electrónico de la empresa.
     *
     * @return El correo electrónico de la empresa.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de la empresa.
     *
     * @param email El correo electrónico de la empresa.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el departamento donde se encuentra la empresa.
     *
     * @return El departamento donde se encuentra la empresa.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento donde se encuentra la empresa.
     *
     * @param departamento El departamento donde se encuentra la empresa.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene la ciudad donde se encuentra la empresa.
     *
     * @return La ciudad donde se encuentra la empresa.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se encuentra la empresa.
     *
     * @param ciudad La ciudad donde se encuentra la empresa.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}