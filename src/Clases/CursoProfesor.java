
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class CursoProfesor {
    private Profesor profesor;
    private int anio, semestre;
    private Curso curso;
    
    public CursoProfesor(Profesor profesor, int anio, int semestre, Curso curso) {
        this.profesor = profesor;
        this.anio = anio;
        this.semestre = semestre;
        this.curso = curso;
    }
    
    public String toString() {
        return profesor.toString() + ", " + anio + ", " + semestre + ", " + curso.toString();
    }
}
