

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
            connection = DriverManager.getConnection("jdbc:mysql://trolley.proxy.rlwy.net:21639/universidad", "root", "hgJIRkqzGGypoobLFoigLcUFYotBMVTP");
            System.out.println("Conectado a la base de datos.");
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean existeID(double id, String tabla) {
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el conteo es mayor a 0, significa que el ID ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void inscribir(Persona persona) {
        listado.add(persona);
        
        String sql;
        try (PreparedStatement stmt = connection.prepareStatement(
                persona instanceof Estudiante ? 
                "INSERT INTO estudiantes (ID, Nombres, Apellidos, Email, Codigo, Programa, Activo, Promedio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)" : 
                "INSERT INTO profesores (ID, Nombres, Apellidos, Email, TipoContrato) VALUES (?, ?, ?, ?, ?)"
            )) {

            stmt.setDouble(1, persona.getID());
            stmt.setString(2, persona.getNombres());
            stmt.setString(3, persona.getApellidos());
            stmt.setString(4, persona.getEmail());

            if (persona instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) persona;
                stmt.setDouble(5, estudiante.getCodigo());
                stmt.setString(6, estudiante.getPrograma().getNombre());
                stmt.setBoolean(7, estudiante.isActivo());
                stmt.setDouble(8, estudiante.getPromedio());
                System.out.println("Estudiante agregado a la BD.");
            } else if (persona instanceof Profesor) {
                Profesor profesor = (Profesor) persona;
                stmt.setString(5, profesor.getTipoContrato());
                System.out.println("Profesor agregado a la BD.");
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Persona persona) {
        listado.remove(persona);

        String sql = persona instanceof Estudiante ? 
                     "DELETE FROM estudiantes WHERE ID = ?" : 
                     "DELETE FROM profesores WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, persona.getID());
            stmt.executeUpdate();

            if (persona instanceof Estudiante) {
                System.out.println("Estudiante eliminado de la BD.");
            } else if (persona instanceof Profesor) {
                System.out.println("Profesor eliminado de la BD.");
            }
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

        String sql;
        if (persona instanceof Estudiante) {
            sql = "UPDATE estudiantes SET Nombres=?, Apellidos=?, Email=?, Codigo=?, Programa=?, Activo=?, Promedio=? WHERE ID=?";
        } else if (persona instanceof Profesor) {
            sql = "UPDATE profesores SET Nombres=?, Apellidos=?, Email=?, TipoContrato=? WHERE ID=?";
        } else {
            System.out.println("Error: Persona no válida para actualizar.");
            return;
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, persona.getNombres());
            stmt.setString(2, persona.getApellidos());
            stmt.setString(3, persona.getEmail());

            if (persona instanceof Estudiante) {
                Estudiante est = (Estudiante) persona;
                stmt.setDouble(4, est.getCodigo());
                stmt.setString(5, est.getPrograma().getNombre());
                stmt.setBoolean(6, est.isActivo());
                stmt.setDouble(7, est.getPromedio());
                stmt.setDouble(8, persona.getID());
            } else if (persona instanceof Profesor) {
                Profesor prof = (Profesor) persona;
                stmt.setString(4, prof.getTipoContrato());
                stmt.setDouble(5, persona.getID());
            }

            stmt.executeUpdate();

            if (persona instanceof Estudiante) {
                System.out.println("Estudiante actualizado en la BD.");
            } else {
                System.out.println("Profesor actualizado en la BD.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Facultad obtenerFacultad(int facultadID) {
        String sql = "SELECT f.ID, f.Nombre, p.ID AS ID, p.Nombre, p.Apellido, p.Email " +
                     "FROM facultades f " +
                     "LEFT JOIN decano p ON f.ID = p.ID " +
                     "WHERE f.ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, facultadID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Persona decano = null;
                if (rs.getObject("ID") != null) {
                    decano = new Persona(
                        rs.getDouble("ID"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getString("Email")
                    );
                }
                return new Facultad(rs.getDouble("ID"), rs.getString("Nombre"), decano);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    
    public Persona buscarEstudiantePorID(long id) {
    String sql = "SELECT * FROM estudiantes WHERE ID = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Estudiante(
                rs.getLong("ID"),  
                rs.getString("Nombres"),
                rs.getString("Apellidos"),
                rs.getString("Email"),
                rs.getLong("Codigo"), 
                new Programa(rs.getString("Programa")),  
                rs.getBoolean("Activo"),
                rs.getDouble("Promedio")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public void cargarDatos() {
        listado.clear();
        File archivo = new File("personas_registradas.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // --- Cargar estudiantes ---
            String sqlEstudiantes = "SELECT * FROM estudiantes";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlEstudiantes)) {

                while (rs.next()) {
                    Programa programa = obtenerProgramaPorNombre(rs.getString("Programa"));
                    if (programa == null) {
                        programa = new Programa(0, "Programa Desconocido", 0, new java.util.Date(), new Facultad(0, "Facultad Desconocida", null));
                    }

                    Persona estudiante = new Estudiante(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getDouble("Codigo"),
                        programa,
                        rs.getBoolean("Activo"),
                        rs.getDouble("Promedio")
                    );
                    listado.add(estudiante);
     
                    writer.write("Estudiante," + estudiante.getID() + "," + estudiante.getNombres() + "," +
                                 estudiante.getApellidos() + "," + estudiante.getEmail() + "," +
                                 ((Estudiante) estudiante).getCodigo() + "," +
                                 ((Estudiante) estudiante).getPrograma().getNombre() + "," +
                                 ((Estudiante) estudiante).isActivo() + "," +
                                 ((Estudiante) estudiante).getPromedio());
                    writer.newLine();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            String sqlProfesores = "SELECT * FROM profesores";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlProfesores)) {

                while (rs.next()) {
                    Persona profesor = new Profesor(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("TipoContrato")
                    );
                    listado.add(profesor);

                    writer.write("Profesor," + profesor.getID() + "," + profesor.getNombres() + "," +
                                 profesor.getApellidos() + "," + profesor.getEmail() + "," +
                                 ((Profesor) profesor).getTipoContrato());
                    writer.newLine();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Datos guardados en personas_registradas.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer cantidadActual() {
            return listado.size();
        }

    public List<String> imprimirListado(String tipo) {
        List<String> resultado = new ArrayList<>();
        listado.clear();  

        String sql = "";

        if ("profesor".equalsIgnoreCase(tipo)) {
            sql = "SELECT * FROM profesores";
        } else if ("estudiante".equalsIgnoreCase(tipo)) {
            sql = "SELECT * FROM estudiantes";
        } else {
            System.out.println("Tipo de persona no válido.");
            return resultado;
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if ("profesor".equalsIgnoreCase(tipo)) {
                    Profesor profe = new Profesor(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getString("TipoContrato")
                    );
                    listado.add(profe);
                    resultado.add(profe.getID() + "," + profe.getNombres() + "," + profe.getApellidos() + "," +
                                  profe.getEmail() + "," + profe.getTipoContrato());
                } else {
                    Programa programa = obtenerProgramaPorNombre(rs.getString("Programa"));;
                    Estudiante est = new Estudiante(
                        rs.getDouble("ID"),
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getString("Email"),
                        rs.getDouble("Codigo"),
                        programa,
                        rs.getBoolean("Activo"),
                        rs.getDouble("Promedio")
                    );
                    listado.add(est);
                    resultado.add(est.getID() + "," + est.getNombres() + "," + est.getApellidos() + "," +
                                  est.getEmail() + "," + est.getCodigo() + "," + est.getPrograma().getNombre() + "," +
                                  est.isActivo() + "," + est.getPromedio());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }
}

