

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
    private List<Persona> listado = new ArrayList<>();

    // Método para inscribir una persona
    public void inscribir(Persona persona) {
        listado.add(persona);
        guardarInformacion();
    }

    // Método para eliminar una persona
    public void eliminar(Persona persona) {
        listado.remove(persona);
        guardarInformacion();
    }

    // Método para actualizar información de una persona
    public void actualizar(Persona persona) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getID() == persona.getID()) {
                listado.set(i, persona);
                break;
            }
        }
        guardarInformacion();
    }

    // Método para guardar los datos en un archivo
    private void guardarInformacion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("inscripciones_personas.txt"))) {
            for (Persona persona : listado) {
                writer.write(persona.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar los datos desde el archivo
    public void cargarDatos() {
        listado.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("inscripciones_personas.txt"))) {
            String linea;
            System.out.println("Cargando datos de inscripciones:");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
                // Aquí podrías convertir la línea a un objeto Persona si es necesario.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
