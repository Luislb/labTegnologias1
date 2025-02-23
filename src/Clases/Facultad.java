
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Facultad {
    private double ID;
    private String nombre;
    private Persona decano;
    
    public Facultad(double ID, String nombre, Persona decano) {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
    }
    
    public String toString() {
        return ID + ", " + nombre + ", Decano: " + decano.toString();
    }
}
