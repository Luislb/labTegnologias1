
package Clases;
/**
 *
 * @author Estudiante_MCA
 */
public class Inscripcion {
    private Curso curso;
    private int anio, semestre;
    private Estudiante estudiante;
    
    public Inscripcion(Curso curso, int anio, int semestre, Estudiante estudiante) {
        this.curso = curso;
        this.anio = anio;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }
    
    public String toString() {
        return curso.toString() + ", " + anio + ", " + semestre + ", " + estudiante.toString();
    }
}
