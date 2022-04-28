import java.net.*; 
import java.io.*;

class ServerClientThread extends Thread {
  Socket serverClient;
  int clientNo;
  String squre;
  ServerClientThread(Socket inSocket,int counter){
    serverClient = inSocket;
    clientNo=counter;
  }
 
  public void run(){
    try{
      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
      String clientMessage="", serverMessage="";
      while(!clientMessage.equals("exit")){
        clientMessage=inStream.readUTF();
        System.out.println("From Client-" +clientNo+ ": message is :"+clientMessage);
        serverMessage="From Client to Server-" + clientNo + " mesage " + "sent succsfully";
        outStream.writeUTF(serverMessage);
        outStream.flush();
      }
      inStream.close();
      outStream.close();
      serverClient.close();
    }catch(Exception ex){
      System.out.println(ex);
    }finally{
      System.out.println("Client -" + clientNo + " exited!! ");
    }
  }
}
//Thread class for MultithreadedSocketServer
public class MultithreadedSocketServer {
  public static void main(String[] args) throws Exception {
    try{
      ServerSocket server=new ServerSocket(8888);
      int counter=0;
      System.out.println("Server Started ....");
      while(true){
        counter++;
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " connected!");
        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
        sct.start();
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
