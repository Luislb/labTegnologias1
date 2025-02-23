
package Clases;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiante_MCA
 */
public class CursosProfesores {
    private List<CursoProfesor> listado = new ArrayList<>();
    
    public void inscribir(CursoProfesor cursoProfesor) {
        listado.add(cursoProfesor);
    }
    public void eliminar(CursoProfesor cursoProfesor) {
        listado.remove(cursoProfesor);
    }
    public List<String> toStringList() {
        List<String> result = new ArrayList<>();
        for (CursoProfesor cursoProfesor : listado) {
            result.add(cursoProfesor.toString());
        }
        return result;
    }
}
