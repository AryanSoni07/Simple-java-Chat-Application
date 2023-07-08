import java.io.*;
import java.net.*;
public class Server
{
    BufferedReader br;
    PrintWriter out;
    ServerSocket server;
    Socket socket;
    Server()
    {
        try{
            server=new ServerSocket(7999);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting..");
            socket=server.accept();

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
                                System.out.println("Client terminated the chat");
                                socket.close();
                                break;
                            }
                            System.out.println("Client : "+msg);
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
                        System.out.println("Connection Closed");
                    }
            };
        new Thread(r2).start();
    }

    public static void main(String args[])
    {
        System.out.println("This is server");
        new Server();
    }
}