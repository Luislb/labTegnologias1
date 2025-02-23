

package Clases;
import Clases.Servicios;
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

    public void inscribir(Persona persona) {
        listado.add(persona);
        guardarInformacion();
    }

    public void eliminar(Persona persona) {
        listado.remove(persona);
        guardarInformacion();
    }

    public void actualizar(Persona persona) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getID() == persona.getID()) {
                listado.set(i, persona);
                break;
            }
        }
        guardarInformacion();
    }

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

    public void cargarDatos() {
        listado.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("inscripciones_personas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
            }
        } catch (IOException e) {
        }
    }

    public String imprimirPosicion(int posición) {
        if (posición >= 0 && posición < listado.size()) {
            return listado.get(posición).toString();  
        }
        return "Posición inválida";
    }

    public Integer cantidadActual() {
        return listado.size();
    }

    public List<String> imprimirListado() {
        List<String> resultado = new ArrayList<>();
        for (Persona p : listado) {
            resultado.add(p.toString() + "\n"); 
        }
        return resultado;
    }
}
