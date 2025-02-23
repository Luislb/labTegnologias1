
package Clases;
import java.util.List;
import java.io.*;
import java.util.*;
/**
 *
 * @author Estudiante_MCA
 */
public class InscripcionesPersonas {
    private List<Inscripcion> listado = new ArrayList<>();
    
    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
    }
    public void eliminar(Inscripcion inscripcion) {
        listado.remove(inscripcion);
    }
    public List<String> toStringList() {
        List<String> result = new ArrayList<>();
        for (Inscripcion inscripcion : listado) {
            result.add(inscripcion.toString());
        }
        return result;
    }
}
