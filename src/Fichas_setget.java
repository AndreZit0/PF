package src;

import java.util.Date;

/**
 * Clase que representa la estructura de datos de una ficha.
 * Esta clase contiene los atributos que describen una ficha y
 * proporciona métodos para acceder y modificar estos atributos.
 */
public class Fichas_setget {
    // Identificador único de la ficha
    int ID_Fichas;
    // Código de la ficha
    String codigo;
    // Modalidad de la ficha (presencial, virtual, etc.)
    String modalidad;
    // Jornada de la ficha (mañana, tarde, noche)
    String jornada;
    // Nivel de formación de la ficha
    String nivel_formacion;
    // Estado actual de la ficha
    String estado;
    // Nombre del programa asociado a la ficha
    String nombre_programa;
    // Nombre de la sede donde se dicta la ficha
    String nombre_sede;
    // Fecha de inicio de la ficha
    Date fecha_inicio;
    // Fecha de fin de la etapa lectiva de la ficha
    Date fecha_fin_lec;
    // Fecha de finalización de la ficha
    Date fecha_final;

    /**
     * Constructor de la clase Fichas_setget.
     * Inicializa los atributos de la ficha con los valores proporcionados.
     *
     * @param nombre_programa  Nombre del programa asociado a la ficha.
     * @param nombre_sede      Nombre de la sede donde se dicta la ficha.
     * @param codigo           Código de la ficha.
     * @param modalidad        Modalidad de la ficha (presencial, virtual, etc.).
     * @param jornada          Jornada de la ficha (mañana, tarde, noche).
     * @param nivel_formacion  Nivel de formación de la ficha.
     * @param fecha_inicio     Fecha de inicio de la ficha.
     * @param fecha_fin_lec    Fecha de fin de la etapa lectiva de la ficha.
     * @param fecha_final      Fecha de finalización de la ficha.
     * @param estado           Estado actual de la ficha.
     */
    public Fichas_setget(String nombre_programa, String nombre_sede, String codigo, String modalidad, String jornada, String nivel_formacion, Date fecha_inicio, Date fecha_fin_lec, Date fecha_final, String estado) {
        this.ID_Fichas = ID_Fichas;
        this.codigo = codigo;
        this.modalidad = modalidad;
        this.jornada = jornada;
        this.nivel_formacion = nivel_formacion;
        this.estado = estado;
        this.nombre_programa = nombre_programa;
        this.nombre_sede = nombre_sede;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin_lec = fecha_fin_lec;
        this.fecha_final = fecha_final;
    }

    /**
     * Obtiene el identificador único de la ficha.
     *
     * @return El identificador único de la ficha.
     */
    public int getID_Fichas() {
        return ID_Fichas;
    }

    /**
     * Establece el identificador único de la ficha.
     *
     * @param ID_Fichas El nuevo identificador único de la ficha.
     */
    public void setID_Fichas(int ID_Fichas) {
        this.ID_Fichas = ID_Fichas;
    }

    /**
     * Obtiene el código de la ficha.
     *
     * @return El código de la ficha.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Establece el código de la ficha.
     *
     * @param codigo El nuevo código de la ficha.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la modalidad de la ficha.
     *
     * @return La modalidad de la ficha.
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Establece la modalidad de la ficha.
     *
     * @param modalidad La nueva modalidad de la ficha.
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * Obtiene la jornada de la ficha.
     *
     * @return La jornada de la ficha.
     */
    public String getJornada() {
        return jornada;
    }

    /**
     * Establece la jornada de la ficha.
     *
     * @param jornada La nueva jornada de la ficha.
     */
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    /**
     * Obtiene el nivel de formación de la ficha.
     *
     * @return El nivel de formación de la ficha.
     */
    public String getNivel_formacion() {
        return nivel_formacion;
    }

    /**
     * Establece el nivel de formación de la ficha.
     *
     * @param nivel_formacion El nuevo nivel de formación de la ficha.
     */
    public void setNivel_formacion(String nivel_formacion) {
        this.nivel_formacion = nivel_formacion;
    }

    /**
     * Obtiene el estado actual de la ficha.
     *
     * @return El estado actual de la ficha.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual de la ficha.
     *
     * @param estado El nuevo estado actual de la ficha.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el nombre del programa asociado a la ficha.
     *
     * @return El nombre del programa asociado a la ficha.
     */
    public String getNombre_programa() {
        return nombre_programa;
    }

    /**
     * Establece el nombre del programa asociado a la ficha.
     *
     * @param nombre_programa El nuevo nombre del programa asociado a la ficha.
     */
    public void setNombre_programa(String nombre_programa) {
        this.nombre_programa = nombre_programa;
    }

    /**
     * Obtiene el nombre de la sede donde se dicta la ficha.
     *
     * @return El nombre de la sede donde se dicta la ficha.
     */
    public String getNombre_sede() {
        return nombre_sede;
    }

    /**
     * Establece el nombre de la sede donde se dicta la ficha.
     *
     * @param nombre_sede El nuevo nombre de la sede donde se dicta la ficha.
     */
    public void setNombre_sede(String nombre_sede) {
        this.nombre_sede = nombre_sede;
    }

    /**
     * Obtiene la fecha de inicio de la ficha.
     *
     * @return La fecha de inicio de la ficha.
     */
    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    /**
     * Establece la fecha de inicio de la ficha.
     *
     * @param fecha_inicio La nueva fecha de inicio de la ficha.
     */
    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    /**
     * Obtiene la fecha de fin de la etapa lectiva de la ficha.
     *
     * @return La fecha de fin de la etapa lectiva de la ficha.
     */
    public Date getFecha_fin_lec() {
        return fecha_fin_lec;
    }

    /**
     * Establece la fecha de fin de la etapa lectiva de la ficha.
     *
     * @param fecha_fin_lec La nueva fecha de fin de la etapa lectiva de la ficha.
     */
    public void setFecha_fin_lec(Date fecha_fin_lec) {
        this.fecha_fin_lec = fecha_fin_lec;
    }

    /**
     * Obtiene la fecha de finalización de la ficha.
     *
     * @return La fecha de finalización de la ficha.
     */
    public Date getFecha_final() {
        return fecha_final;
    }

    /**
     * Establece la fecha de finalización de la ficha.
     *
     * @param fecha_final La nueva fecha de finalización de la ficha.
     */
    public void setFecha_final(Date fecha_final) {
        this.fecha_final = fecha_final;
    }
}

