package AsignacionInstructor;

public class Asignacion {
    int id_asignacion;
    String nombre, documento, ficha, programa, evaluador, fecha_inicial, fecha_final;

    public Asignacion(int id_asignacion, String nombre, String documento, String ficha, String programa, String evaluador, String fecha_inicial, String fecha_final) {
        this.id_asignacion = id_asignacion;
        this.nombre = nombre;
        this.documento = documento;
        this.ficha = ficha;
        this.programa = programa;
        this.evaluador = evaluador;
        this.fecha_inicial = fecha_inicial;
        this.fecha_final = fecha_final;
    }

    public Asignacion() {

    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(String evaluador) {
        this.evaluador = evaluador;
    }

    public String getFecha_inicial() {
        return fecha_inicial;
    }

    public void setFecha_inicial(String fecha_inicial) {
        this.fecha_inicial = fecha_inicial;
    }

    public String getFecha_final() {
        return fecha_final;
    }

    public void setFecha_final(String fecha_final) {
        this.fecha_final = fecha_final;
    }
}
