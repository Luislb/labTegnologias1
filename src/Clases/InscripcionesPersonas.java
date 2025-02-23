

package Clases;
import java.util.List;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Estudiante_MCA
 */
public class InscripcionesPersonas {
    private List<Inscripcion> listado = new ArrayList<>();
    
    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
        guardarInformacion();
    }
    
    private void guardarInformacion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inscripciones.txt", true))) {
            for (Inscripcion i : listado) {
                writer.write(i.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cargarDatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("inscripciones.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Cargando inscripción: " + linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mostrarInscritosPorCurso(int cursoID) {
        System.out.println("Estudiantes inscritos en el curso ID " + cursoID + ":");
        for (Inscripcion inscripcion : listado) {
            if (inscripcion.getCurso().getID() == cursoID) {
                System.out.println(inscripcion.getEstudiante().toString());
            }
        }
    }
}
