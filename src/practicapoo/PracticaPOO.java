
package practicapoo;
import Clases.Persona;
import Clases.Estudiante;
import Clases.Facultad;
import Clases.Programa;

public class PracticaPOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic 
        Persona decano = new Persona(324, "Elvis", "uasba", "ndjajsdn");
        
        Facultad facultad = new Facultad(234, "cf", decano);
        
        Programa programa = new Programa(123, "ing sistemas", 10, "hola", facultad);
        
        Persona estudiante = new Estudiante(200, true, 30, 2420, "luis", "blanco", "ms@sddas.com", programa);
        
        System.out.println("kasd: " + estudiante.toString());
        
        
    }
    
}
