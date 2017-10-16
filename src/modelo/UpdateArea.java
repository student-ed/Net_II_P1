
package modelo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author edvino
 */
public class UpdateArea extends Thread {
    JTextArea text;

    public UpdateArea(JTextArea area) {
        text = area;
    }
    @Override
    public void run() {
        while(true){
            try {
                System.err.println("Listening");
                this.sleep(9000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UpdateArea.class.getName()).log(Level.SEVERE, null, ex);
            }
            text.repaint();
        }
    }
    public void append(String string){
        text.append(string);
    }
}
