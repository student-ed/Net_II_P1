
package modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author edvino
 */
public class Cliente extends SwingWorker{
    private  DataInputStream in;
    private DataOutputStream out;
    private File files [];
    private int lengthBuffer;
    public Cliente(DataInputStream in, DataOutputStream out, File[] files, int bufferLength) {
        this.out = out;
        this.in = in;
        this.files = files;
        this.lengthBuffer =bufferLength;
    }

    @Override
    protected Boolean doInBackground() throws Exception {        
        byte[] buffer = new byte[lengthBuffer];
        FileInputStream fileInStream;
        int read;
        //send info
        out.writeUTF("Buffer"+lengthBuffer);
        out.writeUTF(""+files.length);
        
        /*for (int i = 0; i < files.length; i++) {
            byte percentage=0;
            long aux=0;
            byte flag = percentage;
            //File Info
            String fileName = files[i].getName();
            String fileExten = fileName.substring(fileName.indexOf('.')+1);
            long fileLength = files[i].length();
            fileName = fileName.substring(0, fileName.indexOf('.'));
            
            //send info            
            out.writeUTF(fileName);
            System.out.println(in.readUTF());
            out.writeUTF(fileExten);
            System.out.println(in.readUTF());
            out.writeUTF(""+fileLength);
            System.out.println(in.readUTF());
            
            //
            fileInStream = new FileInputStream(files[i]);
            while((read = fileInStream.read(buffer, 0, buffer.length))!= -1){
                out.write(buffer,0,read);
                aux += read;
                percentage = (byte) ((double)aux/(double)files[i].length()*10.0);
                if(percentage!= flag){
                    System.out.println(percentage*10+"%");
                    percentage = flag;
                }
                out.flush();
                
            }
            System.out.println(percentage*10+"%");
            
        }*/
        out.close();
        in.close();
        return true;
    }
        
    

}
