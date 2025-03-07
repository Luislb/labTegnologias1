package interfaz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import Clases.InscripcionesPersonas;
import Clases.Estudiante;
import Clases.Programa;
import Clases.Persona;
import Clases.Profesor;
import Clases.CursosInscritos;
import Clases.Curso;
import Clases.CursosProfesores;
import ClasesInterfaz.Cursos.ControladorCursos;
import ClasesInterfaz.Estudiantes.ControladorEstudiante;
import ClasesInterfaz.Profesores.ControladorProfesor;

public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel panelEstudiante, panelProfesor, coursePanel;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://trolley.proxy.rlwy.net:21639/universidad";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hgJIRkqzGGypoobLFoigLcUFYotBMVTP";
    private InscripcionesPersonas inscripciones = new InscripcionesPersonas();
    private CursosInscritos cursosInscritos;
    private CursosProfesores cursosProfesores;
    private ControladorProfesor ControladorProfesor;
    private ControladorEstudiante ControladorEstudiante;
    private ControladorCursos ControladorCursos;
    
    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setVisible(true); 
        this.ControladorProfesor = new ControladorProfesor(connection);
        this.ControladorEstudiante = new ControladorEstudiante(connection);
        configuracionBaseDatos();
        this.ControladorCursos = new ControladorCursos(connection, cursosInscritos, cursosProfesores);
 
        
        tabbedPane = new JTabbedPane();

        ControladorEstudiante.panelEstudiante = new JPanel(new BorderLayout());
        ControladorProfesor.panelProfesor = new JPanel(new BorderLayout());
        ControladorCursos.coursePanel = new JPanel(new BorderLayout());
        
        tabbedPane.addTab("Estudiantes", ControladorEstudiante.panelEstudiante);
        tabbedPane.addTab("Profesores", ControladorProfesor.panelProfesor);
        tabbedPane.addTab("Cursos", ControladorCursos.coursePanel);
       
        setJMenuBar(crearMenu());
        add(tabbedPane);

        ControladorEstudiante.ConfiguracionPanelEstudiante();
        ControladorProfesor.ConfiguracionPanelProfesor();
        ControladorCursos.setupCoursePanel();
    }
    private JMenuBar crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        JMenuItem estudiantesItem = new JMenuItem("Estudiantes");
        JMenuItem profesoresItem = new JMenuItem("Profesores");

        estudiantesItem.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        profesoresItem.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        menu.add(estudiantesItem);
        menu.add(profesoresItem);
        menuBar.add(menu);

        return menuBar;
    }
    private void configuracionBaseDatos() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conectado a la base de datos");
            cursosInscritos = new CursosInscritos(connection, inscripciones);
            cursosProfesores = new CursosProfesores(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
