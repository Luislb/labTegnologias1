
package Clases; 
/**
 *
 * @author Estudiante_MCA
 */
public class Curso {
    private int ID;
    private String nombre;
    private Programa programa;
    private boolean activo;
    
    public Curso(int ID, String nombre, Programa programa, boolean activo) {
        this.ID = ID;
        this.nombre = nombre;
        this.programa = programa;
        this.activo = activo;
    }
    
    public String toString() {
        return ID + ", " + nombre + ", " + programa.getNombre() + ", " + activo;
    }
}
