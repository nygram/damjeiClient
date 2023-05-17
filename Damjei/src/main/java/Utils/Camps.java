package Utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Camps {

    public static void habilitarCampos(Container container, boolean b) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField txtField = ((JTextField) component);
                txtField.setEditable(b);
            }
        }

    }

    public static void netejaCamps(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField txtField = ((JTextField) component);
                txtField.setText(null);
            }
            if (component instanceof JComboBox) {
                JComboBox combo = ((JComboBox) component);
                combo.setSelectedIndex(0);
            }
        }
    }

}
