
package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante_MCA
 */
public class CursosInscritos {
    private List<Inscripcion> listado = new ArrayList<>();
    
    public void inscribirCurso(Inscripcion inscripcion) {
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
