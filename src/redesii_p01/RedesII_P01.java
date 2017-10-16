
package redesii_p01;

import controlador.Controller;
import vista.Chooser;

/**
 *
 * @author edvino
 */
public class RedesII_P01 {


    public static void main(String[] args) {
        Chooser window = new Chooser();
        window.start();
        Controller controlador = new Controller(window);
        
        window.setController(controlador);
        
    }
    
}
