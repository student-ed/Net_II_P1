/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import modelo.Cliente;
import modelo.UpdateArea;
import modelo.WorkerProgress;
import vista.Chooser;
import vista.TransferWindow;
import vista.Window;

/**
 *
 * @author edvino
 */
public class Controller implements ActionListener{
    private Chooser vista;
    private File[] files = null;
    private String ipServer = null;
    private TransferWindow window;
    public Controller(Chooser vista) {
        this.vista = vista;
        
    }

    
    @Override
    public void actionPerformed(ActionEvent e) { 
        int lengthBuffer = 0;        
        JButton boton = (JButton)e.getSource();
        String nameBtn = boton.getName();
        if(nameBtn.equals("btnChooser")){
            JFileChooser chooser =  new JFileChooser();
            chooser.setMultiSelectionEnabled(true);
            chooser.showOpenDialog(vista.getMainPanel());
            this.files = chooser.getSelectedFiles();
            if(ipServer== null)
                ipServer = vista.getIpServer();
            chooser = null;
            vista.getMainPanel().removeAll();
            window = new TransferWindow();
            window.setController(this);
            window.start();            
        }else if(nameBtn.equals("btnAcep")){
            window.setVisible(false);
            window.dispose();
        }else{            
            lengthBuffer  = window.getBytes();
            window.removeIniBtn();                                                    
            try {
                window.addTexAre("Buffer:"+ lengthBuffer+" bytes");
                window.addTexAre("Archivos:"+files.length+"\n---------Inicio de transmisión-------");
                Socket client = new Socket(ipServer, 5000);
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
                int read;
                long progress=0;
                int flag = -1;
                //Reading length buffer
                out.writeUTF(""+lengthBuffer);
                out.flush();
                
                
                //Sending number of files             
                out.writeUTF(""+files.length);
                out.flush();
                
                
                for(int i= 0; i< this.files.length; i++){
                    progress =0;
                    byte buffer[] = new byte[lengthBuffer];
                    String fileName = files[i].getName();
                    FileInputStream fileInStr = new FileInputStream(files[i]);
                    
                    //File Info
                    String name =fileName.substring(0,fileName.indexOf('.')) ;                    
                    System.out.println("sending name...");
                    out.writeUTF(name);//name
                    out.flush();
                    System.out.println(in.readUTF());
                    
                    String extension = fileName.substring(fileName.indexOf('.')+1);
                    System.out.println("sending extension...");
                    out.writeUTF(extension);//extension
                    out.flush();
                    System.out.println(in.readUTF());
                    
                    out.writeUTF(""+files[i].length());// length
                    System.out.println("sending length...\n-------");
                    String aux = name+"."+extension+"\ttamaño:"+ files[i].length()+"\n";                    
                    out.flush();                  
                    System.out.println(in.readUTF());
                    
                    while((read = fileInStr.read(buffer))>0){
                        progress += read;
                        int per;
                        per = (int) ((double)progress/(double)files[i].length()*10.0);
                        if(flag!= per){                            
                            aux+=""+per*10+"% \n";
                            flag = per;
                        }
                        out.write(buffer, 0, read);
                        out.flush();
                        in.readUTF();
                        
                    }
                    String resultado = in.readUTF()+"\n";
                    window.addTexAre(aux+ resultado);                    
                    fileInStr.close();                
                
                }               
                out.close();
                in.close();
                client.close();
                System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>");
                client = null;                
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vista, "Error al conectarse con el servidor...");
            
                }
                window.enableJButton();
            
        }
        
    }
    
}
