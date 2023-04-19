
package Utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTextField;

public class Campos {
    
    public static void habilitarCampos(Container container, boolean b) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField txtField = ((JTextField) component);
                txtField.setEditable(b);
            }
        }

    }

    public static void limpiarCampos(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField txtField = ((JTextField) component);
                txtField.setText(null);
            }
        }

    }
    
}
