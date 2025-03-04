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
import Clases.CursosInscritos;
import Clases.Curso;
import Clases.CursosProfesores;


public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel studentPanel, professorPanel, coursePanel;
    private Connection connection;
    private static final String DB_URL = "jdbc:mysql://trolley.proxy.rlwy.net:21639/universidad";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hgJIRkqzGGypoobLFoigLcUFYotBMVTP";
    private InscripcionesPersonas inscripciones = new InscripcionesPersonas();
    private CursosInscritos cursosInscritos;
    private CursosProfesores cursosProfesores;


    
    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        studentPanel = new JPanel(new BorderLayout());
        professorPanel = new JPanel(new BorderLayout());
        coursePanel = new JPanel(new BorderLayout());
        
        tabbedPane.addTab("Estudiantes", studentPanel);
        tabbedPane.addTab("Profesores", professorPanel);
        tabbedPane.addTab("Cursos", coursePanel);
        
        setJMenuBar(createMenu());
        add(tabbedPane);

        setupDatabase();
        setupStudentPanel();
        setupProfessorPanel();
        setupCoursePanel(); 

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
            cursosInscritos = new CursosInscritos(connection, inscripciones);
            cursosProfesores = new CursosProfesores(connection);
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
            tableModel.setRowCount(0); 
            InscripcionesPersonas inscripciones = new InscripcionesPersonas();
            
            List<String> profesores = inscripciones.imprimirListado("profesor");
            for (String profe : profesores) {
                String[] datos = profe.split(","); 
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
    
    private void setupCoursePanel() {
    coursePanel.setLayout(new BorderLayout());

    JPanel formPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    JLabel idLabel = new JLabel("ID Curso:");
    JTextField idField = new JTextField(10);
    JButton searchButton = new JButton("Buscar");

    JLabel nameLabel = new JLabel("Nombre:");
    JTextField nameField = new JTextField(20);
    nameField.setEditable(true);

    JLabel programLabel = new JLabel("Programa:");
    JTextField programField = new JTextField(20);
    programField.setEditable(true);

    JLabel activeLabel = new JLabel("Activo:");
    JCheckBox activeCheckBox = new JCheckBox();

    JButton addButton = new JButton("Agregar");
    JButton updateButton = new JButton("Modificar");
    JButton deleteButton = new JButton("Eliminar");


    gbc.gridx = 0; gbc.gridy = 0;
    formPanel.add(idLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(idField, gbc);
    gbc.gridx = 2;
    formPanel.add(searchButton, gbc);

    gbc.gridx = 0; gbc.gridy = 1;
    formPanel.add(nameLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 2;
    formPanel.add(nameField, gbc);
    gbc.gridwidth = 1;

    gbc.gridx = 0; gbc.gridy = 2;
    formPanel.add(programLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 2;
    formPanel.add(programField, gbc);
    gbc.gridwidth = 1;

    gbc.gridx = 0; gbc.gridy = 3;
    formPanel.add(activeLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(activeCheckBox, gbc);

    gbc.gridx = 0; gbc.gridy = 4;
    formPanel.add(addButton, gbc);
    gbc.gridx = 1;
    formPanel.add(updateButton, gbc);
    gbc.gridx = 2;
    formPanel.add(deleteButton, gbc);


    JLabel studentIdLabel = new JLabel("ID Estudiante:");
    JTextField studentIdField = new JTextField(10);
    JButton searchStudentButton = new JButton("Buscar Estudiante");

    JLabel studentNameLabel = new JLabel("Nombre:");
    JTextField studentNameField = new JTextField(20);
    studentNameField.setEditable(false);

    JButton enrollStudentButton = new JButton("Inscribir Estudiante");
    JButton removeStudentEnrollmentButton = new JButton("Eliminar Inscripción Estudiante");


    JLabel professorIdLabel = new JLabel("ID Profesor:");
    JTextField professorIdField = new JTextField(10);
    JButton searchProfessorButton = new JButton("Buscar Profesor");

    JLabel professorNameLabel = new JLabel("Nombre:");
    JTextField professorNameField = new JTextField(20);
    professorNameField.setEditable(false);

    JButton enrollProfessorButton = new JButton("Inscribir Profesor");
    JButton removeProfessorEnrollmentButton = new JButton("Eliminar Inscripción Profesor");

    gbc.gridx = 0; gbc.gridy = 5;
    formPanel.add(studentIdLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(studentIdField, gbc);
    gbc.gridx = 2;
    formPanel.add(searchStudentButton, gbc);

    gbc.gridx = 0; gbc.gridy = 6;
    formPanel.add(studentNameLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 2;
    formPanel.add(studentNameField, gbc);
    gbc.gridwidth = 1;

    gbc.gridx = 0; gbc.gridy = 7;
    formPanel.add(enrollStudentButton, gbc);
    gbc.gridx = 1;
    formPanel.add(removeStudentEnrollmentButton, gbc);

    gbc.gridx = 0; gbc.gridy = 8;
    formPanel.add(professorIdLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(professorIdField, gbc);
    gbc.gridx = 2;
    formPanel.add(searchProfessorButton, gbc);

    gbc.gridx = 0; gbc.gridy = 9;
    formPanel.add(professorNameLabel, gbc);
    gbc.gridx = 1; gbc.gridwidth = 2;
    formPanel.add(professorNameField, gbc);
    gbc.gridwidth = 1;

    gbc.gridx = 0; gbc.gridy = 10;
    formPanel.add(enrollProfessorButton, gbc);
    gbc.gridx = 1;
    formPanel.add(removeProfessorEnrollmentButton, gbc);
    
    



    coursePanel.add(formPanel, BorderLayout.NORTH);


    coursePanel.revalidate();
    coursePanel.repaint();
    
    searchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            Curso curso = cursosInscritos.buscarCursoPorID(id);

            if (curso != null) {
 
                nameField.setText(curso.getNombre());
                programField.setText(curso.getPrograma().getNombre());
                activeCheckBox.setSelected(curso.isActivo());
            } else {
                JOptionPane.showMessageDialog(coursePanel, "Curso no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String nombre = nameField.getText().trim();
                String nombrePrograma = programField.getText().trim();
                boolean activo = activeCheckBox.isSelected();

                // Validar que los campos no estén vacíos
                if (nombre.isEmpty() || nombrePrograma.isEmpty()) {
                    JOptionPane.showMessageDialog(coursePanel, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

 
                Programa programa = inscripciones.obtenerProgramaPorNombre(nombrePrograma);
                if (programa == null) {
                    JOptionPane.showMessageDialog(coursePanel, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Curso nuevoCurso = new Curso(id, nombre, programa, activo);

                cursosInscritos.inscribirCurso(nuevoCurso);

                JOptionPane.showMessageDialog(coursePanel, "Curso agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                idField.setText("");
                nameField.setText("");
                programField.setText("");
                activeCheckBox.setSelected(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    updateButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nuevoNombre = nameField.getText().trim();
            String nombrePrograma = programField.getText().trim();
            boolean activo = activeCheckBox.isSelected();

            if (nuevoNombre.isEmpty() || nombrePrograma.isEmpty()) {
                JOptionPane.showMessageDialog(coursePanel, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
            if (cursoExistente == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Programa programa = inscripciones.obtenerProgramaPorNombre(nombrePrograma);
            if (programa == null) {
                JOptionPane.showMessageDialog(coursePanel, "El programa no existe. Verifique el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Curso cursoActualizado = new Curso(id, nuevoNombre, programa, activo);

            cursosInscritos.actualizarCurso(cursoActualizado);

            JOptionPane.showMessageDialog(coursePanel, "Curso actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(coursePanel, 
                "¿Está seguro de que desea eliminar este curso?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            Curso cursoExistente = cursosInscritos.buscarCursoPorID(id);
            if (cursoExistente == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso con el ID especificado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cursosInscritos.eliminarCurso(id);

            JOptionPane.showMessageDialog(coursePanel, "Curso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            idField.setText("");
            nameField.setText("");
            programField.setText("");
            activeCheckBox.setSelected(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    searchStudentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int idEstudiante = Integer.parseInt(studentIdField.getText().trim());

            Persona estudiante = inscripciones.buscarEstudiantePorID(idEstudiante);
            if (estudiante != null && estudiante instanceof Estudiante) {
                studentNameField.setText(estudiante.getNombres());
            } else {
                JOptionPane.showMessageDialog(coursePanel, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    
    searchProfessorButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int idProfesor = Integer.parseInt(professorIdField.getText().trim());

            Profesor profesor = cursosProfesores.buscarProfesorPorID(idProfesor);

            if (profesor != null) {
                professorNameField.setText(profesor.getNombres() + " " + profesor.getApellidos());
            } else {
                JOptionPane.showMessageDialog(coursePanel, "Profesor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    
    enrollStudentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int estudianteID = Integer.parseInt(studentIdField.getText().trim());
            int cursoID = Integer.parseInt(idField.getText().trim());

            Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
            if (curso == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Persona estudiante = inscripciones.buscarEstudiantePorID(estudianteID);
            if (estudiante == null || !(estudiante instanceof Estudiante)) {
                JOptionPane.showMessageDialog(coursePanel, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            cursosInscritos.inscribirEstudianteEnCurso(estudianteID, cursoID);
            JOptionPane.showMessageDialog(coursePanel, "Estudiante inscrito en el curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    
    removeStudentEnrollmentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int estudianteID = Integer.parseInt(studentIdField.getText().trim());
            int cursoID = Integer.parseInt(idField.getText().trim());

            
            Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
            if (curso == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            Persona estudiante = inscripciones.buscarEstudiantePorID(estudianteID);
            if (estudiante == null || !(estudiante instanceof Estudiante)) {
                JOptionPane.showMessageDialog(coursePanel, "El estudiante no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            cursosInscritos.eliminarInscripcionEstudiante(estudianteID, cursoID);
            JOptionPane.showMessageDialog(coursePanel, "Estudiante eliminado del curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    
    enrollProfessorButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int profesorID = Integer.parseInt(professorIdField.getText().trim());
            int cursoID = Integer.parseInt(idField.getText().trim());

            
            Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
            if (curso == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            Profesor profesor = cursosProfesores.buscarProfesorPorID(profesorID);
            if (profesor == null) {
                JOptionPane.showMessageDialog(coursePanel, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            if (cursosProfesores.cursoTieneProfesor(cursoID)) {
                JOptionPane.showMessageDialog(coursePanel, "Este curso ya tiene un profesor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            cursosProfesores.inscribirProfesorEnCurso(profesorID, cursoID);
            JOptionPane.showMessageDialog(coursePanel, "Profesor inscrito en el curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
    removeProfessorEnrollmentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int profesorID = Integer.parseInt(professorIdField.getText().trim());
            int cursoID = Integer.parseInt(idField.getText().trim());

            
            Curso curso = cursosInscritos.buscarCursoPorID(cursoID);
            if (curso == null) {
                JOptionPane.showMessageDialog(coursePanel, "El curso no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            Profesor profesor = cursosProfesores.buscarProfesorPorID(profesorID);
            if (profesor == null) {
                JOptionPane.showMessageDialog(coursePanel, "El profesor no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            cursosProfesores.eliminarInscripcionProfesor(profesorID, cursoID);
            JOptionPane.showMessageDialog(coursePanel, "Profesor eliminado del curso correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(coursePanel, "Los IDs deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});





}
    



    private void addCourse(int id, String nombre, String programa, boolean activo) {
    Programa prog = inscripciones.obtenerProgramaPorNombre(programa);
    if (prog == null) {
        JOptionPane.showMessageDialog(this, "Programa no encontrado.");
        return;
    }
    
    Curso nuevoCurso = new Curso(id, nombre, prog, activo);
    cursosInscritos.inscribirCurso(nuevoCurso);
    JOptionPane.showMessageDialog(this, "Curso agregado correctamente.");
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
