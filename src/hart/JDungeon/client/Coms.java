package hart.JDungeon.client;

import hart.JDungeon.client.Entity.Player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Coms extends Thread
{
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player ply;
    private int ver;
    private ArrayList<String> inqueue, outqueue;
    private Socket sock;

    public Coms(Socket sock, Player ply, int ver) throws IOException
    {
        inqueue = new ArrayList<>();
        outqueue = new ArrayList<>();
        this.sock = sock;
        this.out = new ObjectOutputStream(sock.getOutputStream());
        this.in = new ObjectInputStream(sock.getInputStream());
        this.ply = ply;
        this.ver = ver;
        System.out.println("Coms Thread created");
        out.writeObject(sock.getLocalSocketAddress() + " | " + ply.getName() + " Has Joined the Junjeon Crawl");
    }

    public void queueMsg(String msg)
    {
        inqueue.add(msg);
    }

    public void run()
    {
        String smsg = "";
        System.out.println("Coms Thread Started");
        while (true)
        {
            try
            {
                smsg = (String) in.readObject();
            } catch (IOException | ClassNotFoundException e)
            {
                System.out.println("Connection failure, unable to send/receive message, exiting");
                System.exit(-2);
                e.printStackTrace();
            }

            switch (smsg)
            {
                case "RQ:NAME":
                    try
                    {
                        System.out.println("RQ:NAME, sending");
                        out.writeObject(ply.getName());
                    } catch (IOException e)
                    {
                        System.out.println("Connection failure, unable to send/receive message, exiting");
                        System.exit(-2);
                        e.printStackTrace();
                    }
                    break;

                case "RQ:VER":
                    try
                    {
                        System.out.println("RQ:VER, sending");
                        out.writeObject(ver);
                    } catch (IOException e)
                    {
                        System.out.println("Connection failure, unable to send/receive message, exiting");
                        System.exit(-2);
                        e.printStackTrace();
                    }
                    break;

                case "MSG":
                    try
                    {
                        out.writeObject("");
                        System.out.println((String) in.readObject());
                        out.writeObject("");
                    } catch (IOException | ClassNotFoundException e)
                    {
                        System.out.println("Connection failure, unable to send/receive message, exiting");
                        System.exit(-2);
                        e.printStackTrace();
                    }
                    break;

                case "RQ:INFO":
                    try
                    {
                        out.writeObject(inqueue);
                        outqueue = (ArrayList<String>) in.readObject();
                        inqueue.clear();
                    } catch (IOException | ClassNotFoundException e)
                    {
                        System.out.println("Connection failure, unable to send/receive message, exiting");
                        System.exit(-2);
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.out.println("Unknown command : " + smsg);
                    System.out.println("Invalid Server Message, exiting");
                    System.exit(-1);
                    break;
            }
        }
    }
}
