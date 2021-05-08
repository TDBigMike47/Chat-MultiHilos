package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


        public class Server {
    static int PORT = 1234; //One, two, Three, FOUR!
    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataIn;
    static DataOutputStream dataOut;

    public static void main(String [] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Look at me I'm running at :"+PORT);
        socket = serverSocket.accept();
        System.out.println("Hello there Client: " + socket);

        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());

        Thread inputThread = new Input(dataIn);
        inputThread.start();

        Thread outputThread = new Output(dataOut);
        outputThread.start();
    }

}
        class Input extends Thread implements Runnable{
            DataInputStream dataIn;

            public Input(DataInputStream dataIn){
                this.dataIn = dataIn;
            }


            public void run() {
                while(true){
                    try{
                        String msg = dataIn.readUTF();
                        System.out.println("The client Says: " + msg);
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        class Output extends Thread implements Runnable{
            DataOutputStream dataOut;
            Scanner reader = new Scanner(System.in);

            public Output(DataOutputStream dataOut){
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