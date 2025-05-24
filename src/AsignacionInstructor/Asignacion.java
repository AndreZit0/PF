package AsignacionInstructor;

/**
 * Clase que representa la asignación de un instructor a un aprendiz.
 * Contiene información sobre la asignación, el aprendiz y el instructor.
 */
public class Asignacion {
    int id_asignacion;
    int ID_numeroAprendices;
    int ID_instructor;
    String nombre;
    String nombre_instructor;
    String documento;
    String ficha;
    String programa;
    String evaluador;
    String fecha_inicial;
    String fecha_final;

    /**
     * Constructor para crear un objeto Asignacion con todos sus atributos.
     *
     * @param id_asignacion       Identificador único de la asignación.
     * @param ID_numeroAprendices Identificador del aprendiz asignado.
     * @param ID_instructor       Identificador del instructor asignado.
     * @param nombre              Nombre del aprendiz.
     * @param nombre_instructor   Nombre del instructor.
     * @param documento           Documento del aprendiz.
     * @param ficha               Ficha del programa al que pertenece el aprendiz.
     * @param programa            Nombre del programa de formación del aprendiz.
     * @param evaluador           Nombre del evaluador (puede ser el mismo que el instructor).
     * @param fecha_inicial       Fecha de inicio de la asignación.
     * @param fecha_final         Fecha de finalización de la asignación.
     */
    public Asignacion(int id_asignacion, int ID_numeroAprendices, int ID_instructor, String nombre, String nombre_instructor, String documento, String ficha, String programa, String evaluador, String fecha_inicial, String fecha_final) {
        this.id_asignacion = id_asignacion;
        this.ID_numeroAprendices = ID_numeroAprendices;
        this.ID_instructor = ID_instructor;
        this.nombre = nombre;
        this.nombre_instructor = nombre_instructor;
        this.documento = documento;
        this.ficha = ficha;
        this.programa = programa;
        this.evaluador = evaluador;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
    }

    /**
     * Obtiene el identificador único de la asignación.
     *
     * @return El identificador de la asignación.
     */
    public int getId_asignacion() {
        return id_asignacion;
    }

    /**
     * Establece el identificador único de la asignación.
     *
     * @param id_asignacion El nuevo identificador de la asignación.
     */
    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    /**
     * Obtiene el identificador del aprendiz asignado.
     *
     * @return El identificador del aprendiz.
     */
    public int getID_numeroAprendices() {
        return ID_numeroAprendices;
    }

    /**
     * Establece el identificador del aprendiz asignado.
     *
     * @param ID_numeroAprendices El nuevo identificador del aprendiz.
     */
    public void setID_numeroAprendices(int ID_numeroAprendices) {
        this.ID_numeroAprendices = ID_numeroAprendices;
    }

    /**
     * Obtiene el identificador del instructor asignado.
     *
     * @return El identificador del instructor.
     */
    public int getID_instructor() {
        return ID_instructor;
    }

    /**
     * Establece el identificador del instructor asignado.
     *
     * @param ID_instructor El nuevo identificador del instructor.
     */
    public void setID_instructor(int ID_instructor) {
        this.ID_instructor = ID_instructor;
    }

    /**
     * Obtiene el nombre del aprendiz.
     *
     * @return El nombre del aprendiz.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del aprendiz.
     *
     * @param nombre El nuevo nombre del aprendiz.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre del instructor.
     *
     * @return El nombre del instructor.
     */
    public String getNombre_instructor() {
        return nombre_instructor;
    }

    /**
     * Establece el nombre del instructor.
     *
     * @param nombre_instructor El nuevo nombre del instructor.
     */
    public void setNombre_instructor(String nombre_instructor) {
        this.nombre_instructor = nombre_instructor;
    }

    /**
     * Obtiene el documento del aprendiz.
     *
     * @return El documento del aprendiz.
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Establece el documento del aprendiz.
     *
     * @param documento El nuevo documento del aprendiz.
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    /**
     * Obtiene la ficha del programa del aprendiz.
     *
     * @return La ficha del programa.
     */
    public String getFicha() {
        return ficha;
    }

    /**
     * Establece la ficha del programa del aprendiz.
     *
     * @param ficha La nueva ficha del programa.
     */
    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    /**
     * Obtiene el nombre del programa de formación del aprendiz.
     *
     * @return El nombre del programa.
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * Establece el nombre del programa de formación del aprendiz.
     *
     * @param programa El nuevo nombre del programa.
     */
    public void setPrograma(String programa) {
        this.programa = programa;
    }

    /**
     * Obtiene el nombre del evaluador.
     *
     * @return El nombre del evaluador.
     */
    public String getEvaluador() {
        return evaluador;
    }

    /**
     * Establece el nombre del evaluador.
     *
     * @param evaluador El nuevo nombre del evaluador.
     */
    public void setEvaluador(String evaluador) {
        this.evaluador = evaluador;
    }

    /**
     * Obtiene la fecha de inicio de la asignación.
     *
     * @return La fecha de inicio.
     */
    public String getFecha_inicial() {
        return fecha_inicial;
    }

    /**
     * Establece la fecha de inicio de la asignación.
     *
     * @param fecha_inicial La nueva fecha de inicio.
     */
    public void setFecha_inicial(String fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    /**
     * Obtiene la fecha de finalización de la asignación.
     *
     * @return La fecha de finalización.
     */
    public String getFecha_final() {
        return fecha_final;
    }

    /**
     * Establece la fecha de finalización de la asignación.
     *
     * @param fecha_final La nueva fecha de finalización.
     */
    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }

    /**
     * Constructor por defecto de la clase Asignacion.
     */
    public Asignacion() {

    }


}