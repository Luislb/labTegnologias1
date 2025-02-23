

package Clases;
import Clases.Persona;

/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante extends Persona {
    private double codigo, promedio;
    private boolean activo;
    private Programa programa;

    public Estudiante(double ID, String nombres, String apellidos, String email, double codigo, Programa programa, boolean activo, double promedio) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }
    
    public String toString() {
        return super.toString() + ", " + codigo + ", " + programa.getNombre() + ", " + activo + ", " + promedio;
    }
}
