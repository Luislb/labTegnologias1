

package Clases; 
import Clases.Programa;
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
    public int getID() {
        return ID;
    }
    public String getNombre() {
    return this.nombre;
}
public Programa getPrograma() {
    return this.programa;
}
    public String toString() {
        return ID + ", " + nombre + ", " + programa.getNombre() + ", " + activo;
    }
    public boolean isActivo() {
    return activo;
}

}