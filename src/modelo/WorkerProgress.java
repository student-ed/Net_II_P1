
package modelo;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author edvino
 */
public class WorkerProgress extends SwingWorker{
    String update;
    JTextArea area;
    public WorkerProgress(String update, JTextArea area) {
        this.update = update;
        this.area = area;
        System.out.println("Inited Work");
    }

    @Override
    protected Object doInBackground() throws Exception {
        area.append(update);
        return null;
    }
    
}
