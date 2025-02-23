
package practicapoo;
import Clases.Persona;
import Clases.Estudiante;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import Clases.Profesor;
import Clases.Facultad;
import Clases.Programa;
import Clases.Inscripcion;
import Clases.Curso;
import Clases.InscripcionesPersonas;
import Clases.CursosProfesores;
import Clases.CursoProfesor;
import Clases.CursosInscritos;
import java.util.List;
import java.util.*;

public class PracticaPOO {
    public static void main(String[] args) {
        InscripcionesPersonas inscripciones = new InscripcionesPersonas();
        CursosProfesores cursosProfesores = new CursosProfesores();
        CursosInscritos cursosInscritos = new CursosInscritos();

        inscripciones.cargarDatos();
        cursosProfesores.cargarDatos();
        cursosInscritos.cargarDatos();

        // Datos de prueba
        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso(1, "Programación", new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true));
        cursos.add(new Curso(2, "Estructuras de Datos", new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true));
        cursos.add(new Curso(3, "Bases de Datos", new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true));

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante(1, "Juan", "Pérez", "juan@example.com", 1234, new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true, 4.5));
        estudiantes.add(new Estudiante(2, "María", "Gómez", "maria@example.com", 5678, new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true, 3.9));
        estudiantes.add(new Estudiante(3, "Carlos", "López", "carlos@example.com", 9101, new Programa(1, "Ingeniería", 5, new Date(), new Facultad(1, "Facultad de Ingeniería", null)), true, 4.2));

        List<Profesor> profesores = new ArrayList<>();
        profesores.add(new Profesor(101, "Luis", "Martínez", "luis@example.com", "Tiempo Completo"));
        profesores.add(new Profesor(102, "Ana", "Fernández", "ana@example.com", "Cátedra"));

        inscripciones.inscribir(estudiantes.get(0));
        inscripciones.inscribir(estudiantes.get(1));
        inscripciones.inscribir(estudiantes.get(2));
        
        System.out.println("Lista de personas inscritas:");
        System.out.println(inscripciones.imprimirListado());

        System.out.println("Cantidad de inscritos: " + inscripciones.cantidadActual());


        System.out.println("Persona en posición 1: " + inscripciones.imprimirPosicion(1));

        cursosInscritos.inscribir(new Inscripcion(cursos.get(0), 2025, 1, estudiantes.get(0)));
        cursosInscritos.inscribir(new Inscripcion(cursos.get(1), 2025, 1, estudiantes.get(1)));
        cursosInscritos.inscribir(new Inscripcion(cursos.get(2), 2025, 1, estudiantes.get(2)));

        System.out.println("Lista de Cursos inscritos:");
        System.out.println(cursosInscritos.imprimirListado());

        System.out.println("Cantidad de inscritos: " + cursosInscritos.cantidadActual());

        System.out.println("Persona en posición 1: " + cursosInscritos.imprimirPosicion(1));
        
        cursosProfesores.inscribir(new CursoProfesor(profesores.get(0), 2025, 3, cursos.get(0)));
        cursosProfesores.inscribir(new CursoProfesor(profesores.get(1), 2025, 1, cursos.get(1)));
        
        System.out.println("Lista de cursos de profesores inscritas:");
        System.out.println(cursosProfesores.imprimirListado());

        System.out.println("Cantidad de inscritos: " + cursosProfesores.cantidadActual());

        System.out.println("Persona en posición 1: " + cursosProfesores.imprimirPosicion(0));
    }
}
