package Example_Screen.Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Aprendiz {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Aprendiz(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() { return nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }

    public int calcularProgreso() {
        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(fechaInicio)) return 0;
        if (hoy.isAfter(fechaFin)) return 100;

        long totalDias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        long diasTranscurridos = ChronoUnit.DAYS.between(fechaInicio, hoy);

        return (int) ((diasTranscurridos * 100) / totalDias);
    }
}