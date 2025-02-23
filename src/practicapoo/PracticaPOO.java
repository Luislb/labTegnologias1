
package practicapoo;
import Clases.Persona;
import Clases.Estudiante;
import Clases.Profesor;
import Clases.Facultad;
import Clases.Programa;
import Clases.Inscripcion;
import Clases.InscripcionesPersonas;
import java.util.List;
import java.io.*;
import java.util.*;

public class PracticaPOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        escribirEjemplo("estudiantes.txt", "1, Juan Perez, juan@example.com, 1234, Ingenieria, true, 4.5");
        escribirEjemplo("profesores.txt", "1, Maria Gonzalez, maria@example.com, Tiempo Completo");
        escribirEjemplo("cursos.txt", "1, Programacion, Ingenieria, true");
    }
    
    private static void escribirEjemplo(String archivo, String contenido) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(contenido);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
