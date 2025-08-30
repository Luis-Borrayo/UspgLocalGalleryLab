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
        if (original == null || original.isEmpty()) {
            return "unnamed";
        }
        
        // Extract filename from path
        String name = new java.io.File(original).getName();
        
        // Convert to lowercase
        name = name.toLowerCase();
        
        // Replace spaces with hyphens
        name = name.replaceAll("\\s+", "-");
        
        // Remove invalid characters (keep only a-z, 0-9, ., -, _)
        name = name.replaceAll("[^a-z0-9._-]", "");
        
        // Limit to 80 characters, preserving extension
        if (name.length() > 80) {
            int lastDot = name.lastIndexOf('.');
            if (lastDot > 0 && lastDot < name.length() - 1) {
                // Has an extension
                String ext = name.substring(lastDot);
                String base = name.substring(0, lastDot);
                name = base.substring(0, Math.min(80 - ext.length(), base.length())) + ext;
            } else {
                // No extension or invalid position
                name = name.substring(0, 80);
            }
        }
        
        // If after all processing the name is empty, return a default name
        return name.isEmpty() ? "unnamed" : name;
    }

    /** subcarpeta por fecha: "imagenes/yyyy/MM/dd" */
    public static String datedSubdir(java.time.LocalDate d) {
        return String.format("imagenes/%04d/%02d/%02d", d.getYear(), d.getMonthValue(), d.getDayOfMonth());
    }
}