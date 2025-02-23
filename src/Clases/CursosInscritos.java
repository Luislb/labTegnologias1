
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
public class CursosInscritos {
    private List<Inscripcion> listado = new ArrayList<>();
    
    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
        guardarInformacion();
    }
    
    private void guardarInformacion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cursos_inscritos.txt", true))) {
            writer.write(listado.get(listado.size() - 1).toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarDatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cursos_inscritos.txt"))) {
            String linea;
            System.out.println("Cargando datos de estudiantes inscritos en cursos:");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
