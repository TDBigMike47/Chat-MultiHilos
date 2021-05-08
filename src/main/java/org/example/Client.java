package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static int PORT = 4444;

    public static void main(String [] args) throws IOException{
        Socket socket = new Socket("localhost",PORT);

        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

        Thread inputThread = new ClientInput(dataIn);
        inputThread.start();

        Thread outputThread = new ClientOutput(dataOut);
        outputThread.start();
    }
}

class ClientInput extends Thread implements Runnable{
    DataInputStream dataIn;

    public ClientInput(DataInputStream dataIn){
        this.dataIn = dataIn;
    }

    public void run(){
        while(true){
            try{
                String msg = dataIn.readUTF();
                System.out.println("The server says:  " + msg);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

class ClientOutput extends Thread implements Runnable{
    DataOutputStream dataOut;
    Scanner reader = new Scanner(System.in);

    public ClientOutput(DataOutputStream dataOut){
        this.dataOut = dataOut;
    }

    public void run() {
        while(true){
            try{
                //System.out.print("You: ");
                String msg = reader.nextLine();
                if(msg.trim() != "" && msg.trim() != null && !msg.isEmpty())
                    dataOut.writeUTF(msg.trim());
                dataOut.flush();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
