
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Facultad {
    private double ID;
    private String nombre;
    private Persona decano;

    public Facultad(double ID, String nombre, Persona decano, double IDPersona, String nombresPersona, String apellidosPersona, String emailPersona) {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
        this.decano = new Persona(IDPersona, nombresPersona, apellidosPersona, emailPersona);
    }
    
    
}
