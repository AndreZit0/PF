package Example_Screen.Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 * Representa un aprendiz con su nombre y fechas relacionadas al período de la etapa productiva.
 * Esta clase permite calcular el progreso del aprendiz basado en las fechas de inicio y fin
 * de un periodo.
 */
public class Aprendiz {
    private String nombre;
    private LocalDate fecha_fin_lec;
    private LocalDate fechaFin;
    /**
     * Crea un nuevo objeto Aprendiz.
     *
     * @param nombre       El nombre del aprendiz.
     * @param fecha_fin_lec Fecha de inicio del período productivo.
     * @param fechaFin     Fecha final del período productivo.
     */
    public Aprendiz(String nombre, LocalDate fecha_fin_lec, LocalDate fechaFin) {
        this.nombre = nombre;
        this.fecha_fin_lec = fecha_fin_lec;
        this.fechaFin = fechaFin;
    }

    public String getNombre() { return nombre; }
    public LocalDate getFecha_fin_lec() { return fecha_fin_lec; }
    public LocalDate getFechaFin() { return fechaFin; }

    /**
     * Calcula el progreso del aprendiz en el período productivo en porcentaje (%).
     * Si la fecha actual es anterior a la fecha de inicio, el progreso es 0%.
     * Si la fecha actual es posterior a la fecha final, el progreso es 100%.
     * Si está en medio, calcula el porcentaje transcurrido.
     * @return El progreso como un número entero entre 0 y 100.
     */
    public int calcularProgreso() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(fecha_fin_lec)) return 0;
        if (hoy.isAfter(fechaFin)) return 100;

        long totalDias = ChronoUnit.DAYS.between(fecha_fin_lec, fechaFin);
        long diasTranscurridos = ChronoUnit.DAYS.between(fecha_fin_lec, hoy);

        return (int) ((diasTranscurridos * 100) / totalDias);
    }
}