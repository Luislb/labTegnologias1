package Clases;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;  
import java.sql.ResultSet;
import Clases.Curso;


public class CursosInscritos implements Servicios {
    private List<Inscripcion> listado = new ArrayList<>();
    private InscripcionesPersonas inscripciones;
    
    public CursosInscritos(Connection connection, InscripcionesPersonas inscripciones) {
    this.connection = connection;
    this.inscripciones = inscripciones;
}
    
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
            while ((linea = reader.readLine()) != null) {
            }
        } catch (IOException e) {
        }
    }
    
  public void inscribirCurso(Curso curso) {
    String checkSql = "SELECT COUNT(*) FROM cursos WHERE ID = ?";
    String insertSql = "INSERT INTO cursos (ID, Nombre, Programa, Activo) VALUES (?, ?, ?, ?)";

    try {
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, curso.getID());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("ID duplicado detectado. Saliendo del método.");
                    JOptionPane.showMessageDialog(null, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        System.out.println("ID no duplicado. Procediendo con la inserción.");

        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setInt(1, curso.getID());
            stmt.setString(2, curso.getNombre());
            stmt.setString(3, curso.getPrograma().getNombre());
            stmt.setBoolean(4, curso.isActivo());

            stmt.executeUpdate();
            System.out.println("Curso insertado en la base de datos.");
            JOptionPane.showMessageDialog(null, "Curso agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
  public Curso buscarCursoPorID(int id) {
    String sql = "SELECT ID, Nombre, Programa, Activo FROM cursos WHERE ID = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
              
                Programa programa = inscripciones.obtenerProgramaPorNombre(rs.getString("Programa"));
                boolean activo = rs.getBoolean("Activo");

                return new Curso(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    programa,
                    activo
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; 
}
  
  public void actualizarCurso(Curso curso) {
    String sql = "UPDATE cursos SET Nombre = ?, Programa = ?, Activo = ? WHERE ID = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, curso.getNombre());
        stmt.setString(2, curso.getPrograma().getNombre());
        stmt.setBoolean(3, curso.isActivo());
        stmt.setInt(4, curso.getID());

        int filasActualizadas = stmt.executeUpdate();
        
        if (filasActualizadas > 0) {
            System.out.println("Curso actualizado correctamente en la base de datos.");
        } else {
            System.out.println("No se encontró el curso con el ID proporcionado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
  public void eliminarCurso(int cursoID) {
   
    String checkStudentsSql = "SELECT COUNT(*) FROM estudiantes_cursos WHERE curso_id = ?";
   
    String checkProfessorSql = "SELECT COUNT(*) FROM profesores_cursos WHERE curso_id = ?";

    try {
      
        try (PreparedStatement checkStudentsStmt = connection.prepareStatement(checkStudentsSql)) {
            checkStudentsStmt.setInt(1, cursoID);
            ResultSet rsStudents = checkStudentsStmt.executeQuery();
            if (rsStudents.next() && rsStudents.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar el curso porque tiene estudiantes inscritos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

      
        try (PreparedStatement checkProfessorStmt = connection.prepareStatement(checkProfessorSql)) {
            checkProfessorStmt.setInt(1, cursoID);
            ResultSet rsProfessor = checkProfessorStmt.executeQuery();
            if (rsProfessor.next() && rsProfessor.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar el curso porque tiene un profesor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String deleteCourseSql = "DELETE FROM cursos WHERE ID = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteCourseSql)) {
            deleteStmt.setInt(1, cursoID);
            deleteStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al eliminar el curso.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
  
  public void inscribirEstudianteEnCurso(int estudianteID, int cursoID) {
    String sql = "INSERT INTO estudiantes_cursos (estudiante_id, curso_id) VALUES (?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, estudianteID);
        stmt.setInt(2, cursoID);
        stmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Estudiante inscrito en el curso correctamente.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al inscribir al estudiante en el curso.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
  
 public void eliminarInscripcionEstudiante(int estudianteID, int cursoID) {
    String sql = "DELETE FROM estudiantes_cursos WHERE estudiante_id = ? AND curso_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, estudianteID);
        stmt.setInt(2, cursoID);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}





    private Connection connection;
    
    public CursosInscritos(Connection connection){
        this.connection = connection;
    }

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
