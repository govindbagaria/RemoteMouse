package pkgfinal;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_F5;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class finalproject extends javax.swing.JFrame {

    String ip;
    Dimension screenSize;
    double width,height,androidwidth,androidheight,heightratio,widthratio;
    Boolean flag=false;
    Robot robot;
        
    public finalproject() {
        initComponents();
        try {
            robot=new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(finalproject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     public void getIpAddress() {
          
          try {          
            InetAddress ipAddr = InetAddress.getLocalHost();
            ip=ipAddr.getHostAddress();
           } catch (Exception e) {
             ip += "Something Wrong! " + e.toString() + "\n";
          }
       System.out.println(ip); 
      
       Ipadd.setText(ip);
       System.out.println(ip);
       }
    
       public void Threadcreate(){
         System.out.println("Thread is going to be created");
         Thread socketServerThread = new Thread(new SocketServerThread());
         socketServerThread.start();
       }
  
       public void WindowScreenSize(){
         screenSize=Toolkit.getDefaultToolkit().getScreenSize();
         width= screenSize.getWidth();
         height=screenSize.getHeight();
         System.out.println(width);
         System.out.println(height);
       }
   
           
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Ipadd = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Ipadd.setEditable(false);
        Ipadd.setText("Ip Address....");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Caviar Dreams", 1, 14)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("REMOTE MOUSE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1)
                    .addComponent(Ipadd, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(Ipadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
       final finalproject fp= new finalproject();
          java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               }
        });
            
     fp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     fp.setVisible(true);
          
        fp.WindowScreenSize();
        fp.Threadcreate();
        fp.getIpAddress();
    }

    private class SocketServerThread extends Thread {
           ServerSocket serverSocket;
           Socket socket;
           BufferedReader bufferreader;
           DataInputStream inputStream;
           DataOutputStream dataStream;
          // String messageFromClient = "";
           //String message = "";
           static final int SocketServerPORT = 39999;
           @Override
        
           public void run() {
              try {
                      serverSocket = new ServerSocket(SocketServerPORT);
                      while (true) {
                      socket = serverSocket.accept();
                      
                      Runtime r=Runtime.getRuntime();
                      inputStream=new DataInputStream(socket.getInputStream());
                      dataStream=new DataOutputStream(socket.getOutputStream());                 
                      androidheight=inputStream.readDouble();
                      androidwidth=inputStream.readDouble();
                      System.out.println(""+androidheight+","+androidwidth);
                      androidwidth=0.85*androidwidth;
                      androidheight=0.7*androidheight;
                      heightratio=height/androidheight;
                      widthratio=width/androidwidth;
                      bufferreader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                      String cor;
                      while(flag!=true){
                      cor=bufferreader.readLine();
                    
                      if(cor.contains(",")){
                       Double movex=Double.parseDouble(cor.split(",")[0]);   
                       Double movey=Double.parseDouble(cor.split(",")[1]);
                       movex=movex*widthratio;
                       movey=movey*heightratio;
                       int tempx,tempy;
                       tempx=(int)Math.round(movex);
                       tempy=(int)Math.round(movey);
                       System.out.println(""+tempx+"......"+tempy);
                       robot.mouseMove(tempx,tempy);
                      }
                      if(cor.equalsIgnoreCase("leftClick")){
                        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        robot.delay(100);
                        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                      }
                      if(cor.equalsIgnoreCase("rightClick")){
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.delay(100);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                      }
                      if(cor.equalsIgnoreCase("up")){
                          robot.mouseWheel(-3);
                       }
                      if(cor.equalsIgnoreCase("down")){
                          robot.mouseWheel(3);
                      }
                      if(cor.equalsIgnoreCase("shutdown")){
                          r.exec("shutdown -s -t 0");
                          System.exit(0);
                      }
                      if(cor.equalsIgnoreCase("restart")){
                          r.exec("shutdown -r");
                      }
                      if(cor.equalsIgnoreCase("sleep")){
                          r.exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
                      }
                      if(cor.equalsIgnoreCase("logoff")){
                          r.exec("shutdown -l");
                      }
                      
                      if(cor.equalsIgnoreCase("windows")){
                      robot.keyPress(KeyEvent.VK_WINDOWS);
                      robot.keyRelease(KeyEvent.VK_WINDOWS);
                      }
                     
                     
                      if(cor.equalsIgnoreCase("key")){
                          System.out.println("reading from buffer");
                          String msg= bufferreader.readLine();
                          System.out.println("msg"+msg);
                          int a;
                          a=Integer.parseInt(msg);
                          System.out.println("value of a="+a);
                          switch(a)
                          {
                              case 1000:
                                  robot.keyPress(KeyEvent.VK_BACK_SPACE);
                                  robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                                  break;                              
                              
                              case 1001: 
                                  robot.keyPress(KeyEvent.VK_SPACE);
                                  robot.keyRelease(KeyEvent.VK_SPACE);
                                  break;
                                  
                              case 1002:
                                  robot.keyPress(KeyEvent.VK_ENTER);
                                  robot.keyRelease(KeyEvent.VK_ENTER);
                                  break;
                                  
                              case 1003:
                                  robot.keyPress(KeyEvent.VK_COMMA);
                                  robot.keyRelease(KeyEvent.VK_COMMA);
                                  break;
                                  
                              case 1004:
                                  robot.keyPress(KeyEvent.VK_PERIOD);
                                  robot.keyRelease(KeyEvent.VK_PERIOD);
                                  break;
                              
                                  
                              case 1200:
                                  robot.keyPress(KeyEvent.VK_ADD);
                                  robot.keyRelease(KeyEvent.VK_ADD);
                                  break;
                              
                              case 1201:
                                  robot.keyPress(KeyEvent.VK_MINUS);
                                  robot.keyRelease(KeyEvent.VK_MINUS);
                                  break;
                              
                              case 1202:
                                  robot.keyPress(KeyEvent.VK_BACK_SLASH);
                                  robot.keyRelease(KeyEvent.VK_BACK_SLASH);
                                  break;
                              
                              case 1204:
                                  robot.keyPress(KeyEvent.VK_EQUALS);
                                  robot.keyRelease(KeyEvent.VK_EQUALS);
                                  break;                
                                                         
                              case 1214:
                                  robot.keyPress(KeyEvent.VK_SEMICOLON);
                                  robot.keyRelease(KeyEvent.VK_SEMICOLON);
                                  break;
                                                             
                              case 59:
                                  String msg2= bufferreader.readLine();
                                  String msg1= bufferreader.readLine();
                                  System.out.println("msg1="+msg1);
                                  int a1=Integer.parseInt(msg1);
                                  System.out.println("valuse of a1="+a1);
                                  robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                                  robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                                  robot.keyPress(a1+36);
                                  robot.keyRelease(a1+36);      
                                  robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                                  robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                                  break;
                                  
                              case 1500:
                                  System.out.println("abc");
                                  break;
                                                          
                              default:
                                  if(a>=7 && a<=16){
                                      robot.keyPress(a+41);
                                      robot.keyRelease(a+41);
                                  }
                                  else{
                                  robot.keyPress(a+36);
                                  robot.keyRelease(a+36);
                                  }
                              break;
                                  
                          }
                      }
                      
                       if(cor.equalsIgnoreCase("slideshow")){
                           robot.keyPress(KeyEvent.VK_F5);
                           robot.keyRelease(KeyEvent.VK_5);
                       }
                       if(cor.equalsIgnoreCase("closeslideshow")){
                           robot.keyPress(KeyEvent.VK_ESCAPE);
                           robot.keyRelease(KeyEvent.VK_ESCAPE); 
                       }
                       if(cor.equalsIgnoreCase("right")){
                           robot.keyPress(KeyEvent.VK_RIGHT);
                           robot.keyRelease(KeyEvent.VK_RIGHT);
                       }
                       if(cor.equalsIgnoreCase("left")){
                           robot.keyPress(KeyEvent.VK_LEFT);
                           robot.keyRelease(KeyEvent.VK_LEFT);
                       }
                       
                      
                      
                      if(cor.equalsIgnoreCase("myComputer")){
                         Runtime.getRuntime().exec("cmd /c start explorer");
                      }
                      
                       if(cor.equalsIgnoreCase("controlPanel")){
                         Runtime.getRuntime().exec("cmd /c start control");
                      }
                       if(cor.equalsIgnoreCase("browser")){
                          try {
                              Desktop.getDesktop().browse(new URI("http://www.google.co.in"));
                          } catch (URISyntaxException ex) {
                              Logger.getLogger(finalproject.class.getName()).log(Level.SEVERE, null, ex);
                          }
                      }
                       if(cor.equalsIgnoreCase("refresh")){
                           robot.keyPress(VK_F5);
                           robot.keyRelease(VK_F5);
                       }
                       
                       
                      }
                  }
              } catch (IOException e) {
                  final String errMsg = e.toString();
                  Ipadd.setText(errMsg);  
                  System.out.println(Ipadd.getText());
              } finally {
                  if (socket != null) {
                      try {
                          socket.close();
                      } catch (IOException e) {
                      }
                  }

              }
          }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ipadd;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
