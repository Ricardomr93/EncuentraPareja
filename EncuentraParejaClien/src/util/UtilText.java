package util;

import javax.swing.JTextField;



/**
 * @author Ricardo
 */
public class UtilText {
    public static boolean textFieldEmpty(JTextField txt){
        return txt.getText().isEmpty();
    }
    public static boolean passEquals(JTextField pass1, JTextField pass2 ){
        return pass1.getText().equals(pass2.getText());
    }

}
