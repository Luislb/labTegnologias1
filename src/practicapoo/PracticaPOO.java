
package practicapoo;
import interfaz.GUI;
import javax.swing.SwingUtilities;


public class PracticaPOO {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
