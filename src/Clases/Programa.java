
package Clases;
import java.util.Date;
import java.util.Date;

/**
 *
 * @author Estudiante_MCA
 */
public class Programa {

    static Programa valueOf(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private double ID, duracion;
    private String nombre;
    private Date registro;
    private Facultad facultad;
    
    public Programa(double ID, String nombre, double duracion, Date registro, Facultad facultad) {
        this.ID = ID;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String toString() {
        return ID + ", " + nombre + ", " + duracion + " años, " + registro + ", " + facultad.toString();
    }
}
