
package Clases;

import java.util.Date;

/**
 *
 * @author Estudiante_MCA
 */
public class Programa {
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
