package util;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Ricardo
 */
public class Render extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            return btn;
        } else if (value instanceof JLabel) {
            JLabel lbl = (JLabel) value;
            return lbl;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
