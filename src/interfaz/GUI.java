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


    
    public GUI() {
        setTitle("Gestión Universitaria");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();

        panelEstudiante = new JPanel(new BorderLayout());
        panelProfesor = new JPanel(new BorderLayout());
        coursePanel = new JPanel(new BorderLayout());
        
        tabbedPane.addTab("Estudiantes", panelEstudiante);
        tabbedPane.addTab("Profesores", panelProfesor);
        tabbedPane.addTab("Cursos", coursePanel);

        
        setJMenuBar(crearMenu());
        add(tabbedPane);


        configuracionBaseDatos();
        ConfiguracionPanelEstudiante();
        ConfiguracionPanelProfesor();
        setupCoursePanel();
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

    private void ConfiguracionPanelEstudiante() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaID = new JLabel("ID:");
        JTextField campoID = new JTextField(15);
        JButton botonBuscar = new JButton("Buscar");

        JLabel etiquetaNombres = new JLabel("Nombres:");
        JTextField campoNombres = new JTextField(15);

        JLabel etiquetaApellidos = new JLabel("Apellidos:");
        JTextField campoApellidos = new JTextField(15);

        JLabel etiquetaCorreo = new JLabel("Correo Electrónico:");
        JTextField campoCorreo = new JTextField(15);

        JLabel etiquetaCodigo = new JLabel("Código:");
        JTextField campoCodigo = new JTextField(15);

        JLabel etiquetaPrograma = new JLabel("Programa:");
        JTextField campoPrograma = new JTextField(15);

        JLabel etiquetaActivo = new JLabel("Activo:");
        JCheckBox checkActivo = new JCheckBox();

        JLabel etiquetaPromedio = new JLabel("Promedio:");
        JTextField campoPromedio = new JTextField(15);

        JButton botonInscribir = new JButton("Inscribir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(etiquetaID, gbc);
        gbc.gridx = 1; panelFormulario.add(campoID, gbc);
        gbc.gridx = 2; panelFormulario.add(botonBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(etiquetaNombres, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNombres, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(etiquetaApellidos, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoApellidos, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(etiquetaCorreo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCorreo, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(etiquetaCodigo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCodigo, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 5; panelFormulario.add(etiquetaPrograma, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoPrograma, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 6; panelFormulario.add(etiquetaActivo, gbc);
        gbc.gridx = 1; panelFormulario.add(checkActivo, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panelFormulario.add(etiquetaPromedio, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoPromedio, gbc);
        gbc.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(botonInscribir);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        panelFormulario.add(panelBotones, gbc);

        panelEstudiante.add(panelFormulario, BorderLayout.NORTH);

        // ---- TABLA PARA MOSTRAR LOS ESTUDIANTES ----
        String[] nombresColumnas = {"ID", "Nombres", "Apellidos", "Correo Electrónico", "Código", "Programa", "Activo", "Promedio"};
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        JTable tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        panelEstudiante.add(scrollPane, BorderLayout.CENTER);

        JButton botonRefrescar = new JButton("Refrescar Tabla");
        JPanel panelRefrescar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRefrescar.add(botonRefrescar);
        panelEstudiante.add(panelRefrescar, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> {
            modeloTabla.setRowCount(0); // Limpiar tabla
            InscripcionesPersonas inscripciones = new InscripcionesPersonas();

            List<String> estudiantes = inscripciones.imprimirListado("estudiante");
            for (String estudiante : estudiantes) {
                String[] datos = estudiante.split(","); // Ajusta según el formato de `toString()`
                modeloTabla.addRow(datos);
            }
        });

        botonBuscar.addActionListener(e -> buscarEstudiante(
            Double.parseDouble(campoID.getText()), campoNombres, campoApellidos, campoCorreo, campoCodigo, campoPrograma, checkActivo, campoPromedio
        ));
        botonInscribir.addActionListener(e -> inscribirEstudiante(
            Double.parseDouble(campoID.getText()), campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(),
            Double.parseDouble(campoCodigo.getText()), campoPrograma.getText(), checkActivo.isSelected(), Double.parseDouble(campoPromedio.getText())
        ));
        botonModificar.addActionListener(e -> modificarEstudiante(
            Double.parseDouble(campoID.getText()), campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(),
            Double.parseDouble(campoCodigo.getText()), campoPrograma.getText(), checkActivo.isSelected(), Double.parseDouble(campoPromedio.getText())
        ));
        botonEliminar.addActionListener(e -> eliminarEstudiante(Double.parseDouble(campoID.getText())));
    }
  
    private void ConfiguracionPanelProfesor() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaID = new JLabel("ID:");
        JTextField campoID = new JTextField(15);
        JButton botonBuscar = new JButton("Buscar");

        JLabel etiquetaNombres = new JLabel("Nombres:");
        JTextField campoNombres = new JTextField(15);

        JLabel etiquetaApellidos = new JLabel("Apellidos:");
        JTextField campoApellidos = new JTextField(15);

        JLabel etiquetaCorreo = new JLabel("Correo Electrónico:");
        JTextField campoCorreo = new JTextField(15);

        JLabel etiquetaContrato = new JLabel("Tipo de Contrato:");
        JTextField campoContrato = new JTextField(15);

        JButton botonInscribir = new JButton("Inscribir");
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        gbc.gridx = 0; gbc.gridy = 0; panelFormulario.add(etiquetaID, gbc);
        gbc.gridx = 1; panelFormulario.add(campoID, gbc);
        gbc.gridx = 2; panelFormulario.add(botonBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(etiquetaNombres, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoNombres, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(etiquetaApellidos, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoApellidos, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; panelFormulario.add(etiquetaCorreo, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoCorreo, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; panelFormulario.add(etiquetaContrato, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; panelFormulario.add(campoContrato, gbc);
        gbc.gridwidth = 1;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.add(botonInscribir);
        panelBotones.add(botonModificar);
        panelBotones.add(botonEliminar);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        panelFormulario.add(panelBotones, gbc);

        panelProfesor.add(panelFormulario, BorderLayout.NORTH);

        // ---- TABLA PARA MOSTRAR LOS PROFESORES ----
        String[] nombresColumnas = {"ID", "Nombres", "Apellidos", "Correo Electrónico", "Tipo de Contrato"};
        DefaultTableModel modeloTabla = new DefaultTableModel(nombresColumnas, 0);
        JTable tablaProfesores = new JTable(modeloTabla);
        JScrollPane panelDesplazamiento = new JScrollPane(tablaProfesores);
        panelProfesor.add(panelDesplazamiento, BorderLayout.CENTER);

        JButton botonRefrescar = new JButton("Refrescar Tabla");
        JPanel panelRefrescar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRefrescar.add(botonRefrescar);
        panelProfesor.add(panelRefrescar, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> {
            modeloTabla.setRowCount(0); // Limpiar tabla
            InscripcionesPersonas inscripciones = new InscripcionesPersonas();
            
            List<String> profesores = inscripciones.imprimirListado("profesor");
            for (String profe : profesores) {
                String[] datos = profe.split(","); // Ajusta según el formato de `toString()`
                modeloTabla.addRow(datos);
            }
        });

        botonBuscar.addActionListener(e -> buscarProfesor(Double.parseDouble(campoID.getText()), campoNombres, campoApellidos, campoCorreo, campoContrato));
        botonInscribir.addActionListener(e -> inscribirProfesor(Double.parseDouble(campoID.getText()), campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(), campoContrato.getText()));
        botonModificar.addActionListener(e -> modificarProfesor(Double.parseDouble(campoID.getText()), campoNombres.getText(), campoApellidos.getText(), campoCorreo.getText(), campoContrato.getText()));
        botonEliminar.addActionListener(e -> eliminarProfesor(Double.parseDouble(campoID.getText())));
    }

    private void inscribirEstudiante(double ID, String nombres, String apellidos, String email, double codigo, String programa, boolean activo, double promedio) {
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
    private void inscribirProfesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        if (inscripciones.existeID(ID, "profesores")) {
            JOptionPane.showMessageDialog(this, "Error: El ID ya existe en la base de datos.", "ID Duplicado", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
        inscripciones.inscribir(profesor);
        
        JOptionPane.showMessageDialog(this, "Profesor registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void buscarEstudiante(double ID, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoCodigo, JTextField campoPrograma, JCheckBox checkActivo, JTextField campoPromedio) {
        for (Persona p : inscripciones.listado) {
            if (p.getID() == ID && p instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) p;
                campoNombre.setText(estudiante.getNombres());
                campoApellido.setText(estudiante.getApellidos());
                campoCorreo.setText(estudiante.getEmail());
                campoCodigo.setText(String.valueOf(estudiante.getCodigo()));
                campoPrograma.setText(estudiante.getPrograma().getNombre());
                checkActivo.setSelected(estudiante.isActivo());
                campoPromedio.setText(String.valueOf(estudiante.getPromedio()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }
    private void buscarProfesor(double ID, JTextField campoNombre, JTextField campoApellido, JTextField campoCorreo, JTextField campoContrato) {
        for (Persona p : inscripciones.listado) { 
            if (p.getID() == ID && p instanceof Profesor) {
                Profesor profesor = (Profesor) p;
                campoNombre.setText(profesor.getNombres());
                campoApellido.setText(profesor.getApellidos());
                campoCorreo.setText(profesor.getEmail());
                campoContrato.setText(profesor.getTipoContrato());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Profesor no encontrado");
    }
    private void modificarEstudiante(double ID, String nombres, String apellidos, String email, double codigo, String programa, boolean activo, double promedio) {
        Programa prog = inscripciones.obtenerProgramaPorNombre(programa);
        if (prog == null) {
            JOptionPane.showMessageDialog(this, "Programa no encontrado");
            return;
        }
        Estudiante estudiante = new Estudiante(ID, nombres, apellidos, email, codigo, prog, activo, promedio);
        inscripciones.actualizar(estudiante);
        JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente");
    }
    private void modificarProfesor(double ID, String nombres, String apellidos, String email, String tipoContrato) {
        Profesor profesor = new Profesor(ID, nombres, apellidos, email, tipoContrato);
        inscripciones.actualizar(profesor);
        JOptionPane.showMessageDialog(this, "Profesor actualizado correctamente");
    }

    private void eliminarEstudiante(double ID) {
        for (Persona p : inscripciones.listado) {
            if (p.getID() == ID) {
                inscripciones.eliminar(p);
                JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Estudiante no encontrado");
    }
    private void eliminarProfesor(double ID) {
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
