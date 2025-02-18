/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class CursoProfesor {
    Profesor profesor;
    int año;
    int semestre;
    Curso curso;

    public CursoProfesor(Profesor profesor, int año, int semestre, Curso curso) {
        this.profesor = profesor;
        this.año = año;
        this.semestre = semestre;
        this.curso = curso;
    }
    
    public String toString(){
        
    }
}
