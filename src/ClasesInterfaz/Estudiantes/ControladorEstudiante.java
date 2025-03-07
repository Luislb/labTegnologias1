
package ClasesInterfaz.Estudiantes;
import Clases.Estudiante;
import Clases.InscripcionesPersonas;
import Clases.Persona;
import Clases.Programa;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author klmxl
 */
public class ControladorEstudiante extends JFrame{
    private InscripcionesPersonas inscripciones = new InscripcionesPersonas();
    public JPanel panelEstudiante;
    private Connection connection;
    
    public ControladorEstudiante(Connection connection) {
        this.connection = connection;
        this.setLayout(new BorderLayout());  // Define el layout
        this.add(new JLabel("Panel de Profesores"), BorderLayout.CENTER); 
    }
        
    public void ConfiguracionPanelEstudiante() {
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
    }
}
