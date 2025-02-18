

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

    public Estudiante(double codigo, boolean activo, double promedio, double ID, String nombres, String apellidos, String email, double IDPrograma, String nombrePrograma, double duracionPrograma, String registroPrograma, Facultad facultad) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = new Programa(IDPrograma, nombrePrograma, duracionPrograma, registroPrograma, facultad);
        this.activo = activo;
        this.promedio = promedio;
    }
    
    public String toString(){
        System.out.println("datos: " + this.toString());
        System.out.println("codigo: " + codigo);
        System.out.println("programa: " + programa);
        System.out.println("activo: " + activo);
        System.out.println("promedio: " + promedio);
        return "hola";
    }
}
