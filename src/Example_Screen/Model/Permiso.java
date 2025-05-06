package Example_Screen.Model;

public enum Permiso {
    // Permisos de usuario
    CREAR_USUARIO(1, "Crear usuarios"),
    EDITAR_USUARIO(2, "Editar usuarios"),
    VER_USUARIO(3, "Ver usuarios"),
    INHABILITAR_USUARIO(4, "Inhabilitar usuarios"),

    // Permisos de fichas
    CREAR_FICHA(5, "Crear fichas"),
    EDITAR_FICHA(6, "Editar fichas"),
    VER_FICHA(7, "Ver fichas"),
    INHABILITAR_FICHA(8, "Inhabilitar fichas"),

    // Permisos de seguimiento
    CREAR_SEGUIMIENTO(26, "Crear seguimientos"),
    EDITAR_SEGUIMIENTO(27, "Editar seguimientos"),
    ELIMINAR_SEGUIMIENTO(28, "Eliminar seguimientos"),
    FIRMAR_SEGUIMIENTO(32, "Firmar seguimientos"),
    VER_SEGUIMIENTO(34, "Ver seguimientos"),

    // Permisos personales
    EDITAR_NOMBRE(24, "Editar nombre"),
    EDITAR_IDENTIFICACION(25, "Editar identificación");

    private final int id;
    private final String descripcion;

    Permiso(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}