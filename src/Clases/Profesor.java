/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;
import Clases.Persona;
/**
 *
 * @author Estudiante_MCA
 */
public class Profesor extends Persona{
    private String tipoContrato;

    public Profesor() {
    }

    public Profesor(String tipoContrato, double ID, String nombres, String apellidos, String email) {
        super(ID, nombres, apellidos, email);
        this.tipoContrato = tipoContrato;
    }
    
    public String toString(){
        return "hola";
    }
    
}
