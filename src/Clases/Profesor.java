
package Clases;
import Clases.Persona;
/**
 *
 * @author Estudiante_MCA
 */
public class Profesor extends Persona{
    private String tipoContrato;
    
    public Profesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        super(ID, nombres, apellidos, email);
        this.tipoContrato = tipoContrato;
    }
    
    public String toString() {
        return super.toString() + ", " + tipoContrato;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
}
