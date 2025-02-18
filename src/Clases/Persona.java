
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Persona {
    private double ID;
    private String nombres;
    private String apellidos;
    private String email;
    
    public Persona() {
    }

    public Persona(double ID, String nombres, String apellidos, String email) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }
    
    public String toString() {
        System.out.println("ID: " + ID);
        System.out.println("nombre: " + nombres);
        System.out.println("apellido: " + apellidos);
        System.out.println("email: " + email);
        return "hola";
    }
}
