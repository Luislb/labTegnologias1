

package Clases;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.Programa;

public class InscripcionesPersonas {
    public List<Persona> listado = new ArrayList<>();
    private Connection connection;

    public InscripcionesPersonas() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universidad", "root", "klmx2001");
            System.out.println("Conectado a la base de datos.");
            cargarDatos(); // Carga inicial desde la BD
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inscribir(Persona persona) {
        listado.add(persona);
        String sql = "INSERT INTO estudiantes (ID, Nombres, Apellidos, Email, Codigo, Programa, Activo, Promedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, persona.getID());
            stmt.setString(2, persona.getNombres());
            stmt.setString(3, persona.getApellidos());
            stmt.setString(4, persona.getEmail());
            stmt.setDouble(5, ((Estudiante) persona).getCodigo());
            stmt.setString(6, ((Estudiante) persona).getPrograma().getNombre());
            stmt.setBoolean(7, ((Estudiante) persona).isActivo());
            stmt.setDouble(8, ((Estudiante) persona).getPromedio());
            stmt.executeUpdate();
            System.out.println("Estudiante agregado a la BD.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Persona persona) {
        listado.remove(persona);
        String sql = "DELETE FROM estudiantes WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, persona.getID());
            stmt.executeUpdate();
            System.out.println("Estudiante eliminado de la BD.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Persona persona) {
        for (int i = 0; i < listado.size(); i++) {
            if (listado.get(i).getID() == persona.getID()) {
                listado.set(i, persona);
                break;
            }
        }
        String sql = "UPDATE estudiantes SET Nombres=?, Apellidos=?, Email=?, Codigo=?, Programa=?, Activo=?, Promedio=? WHERE ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombres());
            stmt.setString(2, persona.getApellidos());
            stmt.setString(3, persona.getEmail());
            stmt.setDouble(4, ((Estudiante) persona).getCodigo());
            stmt.setString(5, ((Estudiante) persona).getPrograma().getNombre());
            stmt.setBoolean(6, ((Estudiante) persona).isActivo());
            stmt.setDouble(7, ((Estudiante) persona).getPromedio());
            stmt.setDouble(8, persona.getID());
            stmt.executeUpdate();
            System.out.println("Estudiante actualizado en la BD.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Facultad obtenerFacultad(int facultadID) {
        String sql = "SELECT f.ID, f.Nombre, p.ID AS DecanoID, p.Nombres, p.Apellidos, p.Email " +
                     "FROM facultades f " +
                     "LEFT JOIN personas p ON f.DecanoID = p.ID " +
                     "WHERE f.ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, facultadID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Persona decano = null;
                if (rs.getObject("DecanoID") != null) {
                    decano = new Persona(
                        rs.getDouble("DecanoID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email")
                    );
                }
                return new Facultad(rs.getDouble("ID"), rs.getString("Nombre"), decano);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra la facultad.
    }

    public Programa obtenerProgramaPorNombre(String nombrePrograma) {
        String sql = "SELECT ID, Nombre, IFNULL(Duracion, 0) AS Duracion, Fecha_inicio, Facultad_id FROM programas WHERE Nombre = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombrePrograma);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Programa(
                    rs.getDouble("ID"),
                    rs.getString("Nombre"),
                    rs.getDouble("Duracion"), // Se usa IFNULL en SQL para evitar null
                    rs.getDate("Fecha_inicio"),
                    obtenerFacultad(rs.getInt("Facultad_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarDatos() {
        listado.clear();
        String sql = "SELECT * FROM estudiantes";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Programa programa = obtenerProgramaPorNombre(rs.getString("Programa"));
                if (programa == null) {
                    programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                }

                Persona persona = new Estudiante(
                    rs.getDouble("ID"),
                    rs.getString("Nombres"),
                    rs.getString("Apellidos"),
                    rs.getString("Email"),
                    rs.getDouble("Codigo"),
                    programa,
                    rs.getBoolean("Activo"),
                    rs.getDouble("Promedio")
                );
                listado.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer cantidadActual() {
        return listado.size();
    }

    public List<String> imprimirListado() {
        List<String> resultado = new ArrayList<>();
        for (Persona p : listado) {
            resultado.add(p.toString() + "\n");
        }
        return resultado;
    }
}

