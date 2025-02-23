package Clases;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {
    private List<Inscripcion> listado = new ArrayList<>();
    
    public void inscribir(Inscripcion inscripcion) {
        listado.add(inscripcion);
        guardarInformacion();
    }
    
    public void eliminar(Inscripcion inscripcion) {
        listado.remove(inscripcion);
        guardarInformacion();
    }
    
    public void actualizar(Inscripcion inscripcion) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getCurso().getID() == inscripcion.getCurso().getID() &&
                listado.get(i).getEstudiante().getID() == inscripcion.getEstudiante().getID()) {
                listado.set(i, inscripcion);
                break;
            }
        }
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
            //System.out.println("Cargando datos de estudiantes inscritos en cursos:");
            while ((linea = reader.readLine()) != null) {
                //System.out.println(linea);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    // Implementación de los métodos de Servicios

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < listado.size()) {
            return listado.get(posicion).toString();
        }
        return "Posición inválida";
    }

    @Override
    public int cantidadActual() {
        return listado.size();
    }

    @Override
    public List<String> imprimirListado() {
        List<String> lista = new ArrayList<>();
        for (Inscripcion ins : listado) {
            lista.add(ins.toString() + "\n");
        }
        return lista;
    }
}
