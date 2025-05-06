package Example_Screen.Model;

public enum Rol {
    APRENDIZ(1, "Aprendiz"),
    EVALUADOR(2, "Evaluador"),
    COEVALUADOR(3, "Coevaluador"),
    AUXILIAR(4, "Auxiliar"),
    ADMINISTRADOR_SENA(4, "Administrador Sena"),
    ADMIN_SISTEMA(6, "Admin Sistema");

    private final int id;
    private final String nombre;

    Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public static Rol fromId(int id) {
        for (Rol rol : values()) {
            if (rol.id == id) return rol;
        }
        return APRENDIZ;
    }
}