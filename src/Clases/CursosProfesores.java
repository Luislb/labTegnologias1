
package Clases;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        guardarInformacion();
    }
    
    private void guardarInformacion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cursos_profesores.txt", true))) {
            writer.write(listado.get(listado.size() - 1).toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarDatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cursos_profesores.txt"))) {
            String linea;
            System.out.println("Cargando datos de cursos asignados a profesores:");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
