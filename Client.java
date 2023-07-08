import java.io.*;
import java.net.*;

public class Client
{
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    Client()
    {
        try{
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7999);
            System.out.println("Connection done...");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch(Exception e)
        {

        }
    }

    

    public void startReading()
    {
        Runnable r1=()->
                {
                    try{
                        System.out.println("Reader Started");
                        while(true){
                            String msg=br.readLine();
                            if(msg.equals("exit"))
                            {
                                System.out.println("Server terminated the chat");
                                socket.close();
                                break;
                            }
                            System.out.println("Server : "+msg);
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Connection Closed");
                    }
            };
        new Thread(r1).start();
    }

    public void startWriting()
    {
        Runnable r2=()->
                {
                    try{
                        System.out.println("Writer Started");
                        while(!socket.isClosed())
                        {
                            BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                            String content=br1.readLine();
                            out.println(content);
                            out.flush();
                            if(content.equals("exit"))
                            {
                                socket.close();
                                break;
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("Exception");
                    }
            };
        new Thread(r2).start();
    }

    public static void main(String args[])
    {
        System.out.println("This is Client");
        new Client();
    }
}