

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

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
    
}
