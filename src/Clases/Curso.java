/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Estudiante_MCA
 */
public class Curso {
    int ID;
    Programa programa;
    boolean activo;

    public Curso(int ID, Programa programa, boolean activo) {
        this.ID = ID;
        this.programa = programa;
        this.activo = activo;
    }
    
    public String toString(){
        
    }
}
