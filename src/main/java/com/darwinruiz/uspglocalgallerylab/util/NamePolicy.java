package com.darwinruiz.uspglocalgallerylab.util;

public class NamePolicy {
    /** TODO-2:
     *  normalizar nombre:
     *   - tomar solo nombre base (sin ruta)
     *   - pasar a minúsculas
     *   - reemplazar espacios por guiones
     *   - remover caracteres no [a-z0-9._-]
     *   - limitar a 80 caracteres
     */
    public static String normalize(String original) {
        // TODO implementar
        return original; // placeholder
    }

    /** subcarpeta por fecha: "imagenes/yyyy/MM/dd" */
    public static String datedSubdir(java.time.LocalDate d) {
        return String.format("imagenes/%04d/%02d/%02d", d.getYear(), d.getMonthValue(), d.getDayOfMonth());
    }
}