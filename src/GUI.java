import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.InscripcionesPersonas;
import Clases.Estudiante;
import Clases.Programa;
import Clases.Persona;
import Clases.Profesor;


public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel studentPanel, professorPanel;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3765614";
    private static final String DB_USER = "sql3765614";
    private static final String DB_PASSWORD = "9R5flvQCdE";
    private InscripcionesPersonas inscripciones = new InscripcionesPersonas();
    
    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        studentPanel = new JPanel(new BorderLayout());
        professorPanel = new JPanel(new BorderLayout());
        
        tabbedPane.addTab("Estudiantes", studentPanel);
        tabbedPane.addTab("Profesores", professorPanel);
        
        setJMenuBar(createMenu());
        add(tabbedPane);

        setupDatabase();
        setupStudentPanel();
        setupProfessorPanel();
    }
    private JMenuBar createMenu() {
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
    private void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupStudentPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");

        JLabel nameLabel = new JLabel("Nombres:");
        JTextField nameField = new JTextField(15);

        JLabel lastNameLabel = new JLabel("Apellidos:");
        JTextField lastNameField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);

        JLabel codeLabel = new JLabel("Código:");
        JTextField codeField = new JTextField(15);

        JLabel programLabel = new JLabel("Programa:");
        JTextField programField = new JTextField(15);

        JLabel activeLabel = new JLabel("Activo:");
        JCheckBox activeCheck = new JCheckBox();

        JLabel avgLabel = new JLabel("Promedio:");
        JTextField avgField = new JTextField(15);

        JButton addButton = new JButton("Inscribir");
        JButton updateButton = new JButton("Modificar");
        JButton deleteButton = new JButton("Eliminar");

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(idField, gbc);
        gbc.gridx = 2; formPanel.add(searchButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(nameField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(lastNameField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(emailField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(codeLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(codeField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(programLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(programField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(activeLabel, gbc);
        gbc.gridx = 1; formPanel.add(activeCheck, gbc);

        gbc.gridx = 0; gbc.gridy = 7; formPanel.add(avgLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(avgField, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        studentPanel.add(formPanel, BorderLayout.NORTH);

        // ---- TABLA PARA MOSTRAR LOS ESTUDIANTES ----
        String[] columnNames = {"ID", "Nombres", "Apellidos", "Email", "Código", "Programa", "Activo", "Promedio"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        studentPanel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refrescar Tabla");
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshPanel.add(refreshButton);
        studentPanel.add(refreshPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Limpiar tabla;
            InscripcionesPersonas inscripciones = new InscripcionesPersonas();
            
            List<String> estudiantes = inscripciones.imprimirListado("estudiante");
            for (String estudiante : estudiantes) {
                String[] datos = estudiante.split(","); // Ajusta según el formato de `toString()`
                tableModel.addRow(datos);
            }
        });

        searchButton.addActionListener(e -> searchStudent(
            Double.parseDouble(idField.getText()), nameField, lastNameField, emailField, codeField, programField, activeCheck, avgField
        ));
        addButton.addActionListener(e -> addStudent(
            Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(),
            Double.parseDouble(codeField.getText()), programField.getText(), activeCheck.isSelected(), Double.parseDouble(avgField.getText())
        ));
        updateButton.addActionListener(e -> updateStudent(
            Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(),
            Double.parseDouble(codeField.getText()), programField.getText(), activeCheck.isSelected(), Double.parseDouble(avgField.getText())
        ));
        deleteButton.addActionListener(e -> deleteStudent(Double.parseDouble(idField.getText())));
    }
  
    private void setupProfessorPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(15);
        JButton searchButton = new JButton("Buscar");

        JLabel nameLabel = new JLabel("Nombres:");
        JTextField nameField = new JTextField(15);

        JLabel lastNameLabel = new JLabel("Apellidos:");
        JTextField lastNameField = new JTextField(15);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);

        JLabel contractLabel = new JLabel("Tipo de Contrato:");
        JTextField contractField = new JTextField(15);

        JButton addButton = new JButton("Inscribir");
        JButton updateButton = new JButton("Modificar");
        JButton deleteButton = new JButton("Eliminar");

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(idField, gbc);
        gbc.gridx = 2; formPanel.add(searchButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(nameField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(lastNameField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(emailField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(contractLabel, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; formPanel.add(contractField, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        professorPanel.add(formPanel, BorderLayout.NORTH);

        // ---- TABLA PARA MOSTRAR LOS PROFESORES ----
        String[] columnNames = {"ID", "Nombres", "Apellidos", "Email", "Tipo de Contrato"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable professorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(professorTable);
        professorPanel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refrescar Tabla");
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshPanel.add(refreshButton);
        professorPanel.add(refreshPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Limpiar tabla
            InscripcionesPersonas inscripciones = new InscripcionesPersonas();
            
            List<String> profesores = inscripciones.imprimirListado("profesor");
            for (String profe : profesores) {
                String[] datos = profe.split(","); // Ajusta según el formato de `toString()`
                tableModel.addRow(datos);
            }
        });

        searchButton.addActionListener(e -> searchProfessor(Double.parseDouble(idField.getText()), nameField, lastNameField, emailField, contractField));
        addButton.addActionListener(e -> addProfessor(Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(), contractField.getText()));
        updateButton.addActionListener(e -> updateProfessor(Double.parseDouble(idField.getText()), nameField.getText(), lastNameField.getText(), emailField.getText(), contractField.getText()));
        deleteButton.addActionListener(e -> deleteProfessor(Double.parseDouble(idField.getText())));
    }

    private void addStudent(double ID, String nombres, String apellidos, String email, double codigo, String programa, boolean activo, double promedio) {
        Programa prog = inscripciones.obtenerProgramaPorNombre(programa);
        if (prog == null) {
            JOptionPane.showMessageDialog(this, "Programa no encontrado");
            return;
        }
        if (inscripciones.existeID(ID, "estudiantes")) {
            JOptionPane.showMessageDialog(this, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        Estudiante estudiante = new Estudiante(ID, nombres, apellidos, email, codigo, prog, activo, promedio);
        inscripciones.inscribir(estudiante);
        JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente");
    }
    private void addProfessor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        if (inscripciones.existeID(ID, "profesores")) {
            JOptionPane.showMessageDialog(this, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
        inscripciones.inscribir(profesor);
        
        JOptionPane.showMessageDialog(this, "Profesor registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
    private void searchProfessor(double ID, JTextField nameField, JTextField lastNameField, JTextField emailField, JTextField contractField) {
        for (Persona p : inscripciones.listado) { 
            if (p.getID() == ID && p instanceof Profesor) {
                Profesor profesor = (Profesor) p;
                nameField.setText(profesor.getNombres());
                lastNameField.setText(profesor.getApellidos());
                emailField.setText(profesor.getEmail());
                contractField.setText(profesor.getTipoContrato());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Profesor no encontrado");
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
    private void updateProfessor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
        inscripciones.actualizar(profesor);
        JOptionPane.showMessageDialog(this, "Profesor actualizado correctamente");
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
    private void deleteProfessor(double ID) {
        for (Persona p : inscripciones.listado) { 
            if (p.getID() == ID && p instanceof Profesor) {
                inscripciones.eliminar(p);
                JOptionPane.showMessageDialog(this, "Profesor eliminado correctamente");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Profesor no encontrado");
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
