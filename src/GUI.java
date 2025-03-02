import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.InscripcionesPersonas;
import Clases.Estudiante;
import Clases.Programa;
import Clases.Persona;
import Clases.Facultad;


public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel studentPanel;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/universidad";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "klmx2001";
    private InscripcionesPersonas inscripciones = new InscripcionesPersonas();

    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        studentPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Estudiantes", studentPanel);
        add(tabbedPane);

        setupDatabase();
        setupStudentPanel();
    }

    private void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupStudentPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField();
        JButton searchButton = new JButton("Buscar");
        JLabel nameLabel = new JLabel("Nombres:");
        JTextField nameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Apellidos:");
        JTextField lastNameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel codeLabel = new JLabel("Código:");
        JTextField codeField = new JTextField();
        JLabel programLabel = new JLabel("Programa:");
        JTextField programField = new JTextField();
        JLabel activeLabel = new JLabel("Activo:");
        JCheckBox activeCheck = new JCheckBox();
        JLabel avgLabel = new JLabel("Promedio:");
        JTextField avgField = new JTextField();
        
        JButton addButton = new JButton("Inscribir");
        JButton updateButton = new JButton("Modificar");
        JButton deleteButton = new JButton("Eliminar");

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(searchButton);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(codeLabel);
        formPanel.add(codeField);
        formPanel.add(programLabel);
        formPanel.add(programField);
        formPanel.add(activeLabel);
        formPanel.add(activeCheck);
        formPanel.add(avgLabel);
        formPanel.add(avgField);
        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);

        studentPanel.add(formPanel, BorderLayout.NORTH);

        searchButton.addActionListener(e -> searchStudent(Double.parseDouble(idField.getText()), nameField, lastNameField, emailField, codeField, programField, activeCheck, avgField));
        addButton.addActionListener(e -> addStudent(Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(), Double.parseDouble(codeField.getText()), programField.getText(), activeCheck.isSelected(), Double.parseDouble(avgField.getText())));
        updateButton.addActionListener(e -> updateStudent(Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(), Double.parseDouble(codeField.getText()), programField.getText(), activeCheck.isSelected(), Double.parseDouble(avgField.getText())));
        deleteButton.addActionListener(e -> deleteStudent(Double.parseDouble(idField.getText())));
    }

    private void addStudent(double ID, String nombres, String apellidos, String email, double codigo, String programa, boolean activo, double promedio) {
        Programa prog = inscripciones.obtenerProgramaPorNombre(programa);
        if (prog == null) {
            JOptionPane.showMessageDialog(this, "Programa no encontrado");
            return;
        }
        Estudiante estudiante = new Estudiante(ID, nombres, apellidos, email, codigo, prog, activo, promedio);
        inscripciones.inscribir(estudiante);
        JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente");
    }
    private void searchStudent(double ID, JTextField nameField, JTextField lastNameField, JTextField emailField, JTextField codeField, JTextField programField, JCheckBox activeCheck, JTextField avgField) {
        for (Persona p : inscripciones.listado) {
            if (p.getID() == ID && p instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) p;
                nameField.setText(estudiante.getNombres());
                lastNameField.setText(estudiante.getApellidos());
                emailField.setText(estudiante.getEmail());
                codeField.setText(String.valueOf(estudiante.getCodigo()));
                programField.setText(estudiante.getPrograma().getNombre());
                activeCheck.setSelected(estudiante.isActivo());
                avgField.setText(String.valueOf(estudiante.getPromedio()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }
    private void updateStudent(double ID, String nombres, String apellidos, String email, double codigo, String programa, boolean activo, double promedio) {
        Programa prog = inscripciones.obtenerProgramaPorNombre(programa);
        if (prog == null) {
            JOptionPane.showMessageDialog(this, "Programa no encontrado");
            return;
        }
        Estudiante estudiante = new Estudiante(ID, nombres, apellidos, email, codigo, prog, activo, promedio);
        inscripciones.actualizar(estudiante);
        JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente");
    }

    private void deleteStudent(double ID) {
        for (Persona p : inscripciones.listado) {
            if (p.getID() == ID) {
                inscripciones.eliminar(p);
                JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
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
