package util;

import javax.swing.JTextField;

/**
 * @author Ricardo
 */
public class UtilText {

    public static boolean textFieldEmpty(JTextField txt) {
        return txt.getText().isEmpty();
    }

    public static boolean passEquals(JTextField pass1, JTextField pass2) {
        return pass1.getText().equals(pass2.getText());
    }

    public static boolean someEmpty(JTextField[] txt) {
        boolean empty = false;
        for (int i = 0; i < txt.length && !empty; i++) {
            if (textFieldEmpty(txt[i])) {
                empty = true;
            }
        }
        return empty;
    }

}
