

package Clases;
import Clases.Persona;

/**
 *
 * @author Estudiante_MCA
 */
public class Estudiante extends Persona{
    private double codigo;
    private Programa programa;
    private boolean activo;
    private double promedio;

    public Estudiante() {
    }

    public Estudiante(double codigo, Programa programa, boolean activo, double promedio, double ID, String nombres, String apellidos, String email) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }
    
}
