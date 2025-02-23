
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Persona {
    protected double ID;
    protected String nombres, apellidos, email;

    public Persona(double ID, String nombres, String apellidos, String email) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }
    
    public String toString() {
        return ID + ", " + nombres + " " + apellidos + ", " + email;
    }
}
