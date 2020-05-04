package hart.JDungeon.client;

import hart.JDungeon.client.Entity.Player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Coms extends Thread
{
    private DataOutputStream out;
    private DataInputStream in;
    private Player ply;
    private int ver;

    public Coms(DataOutputStream out, DataInputStream in, Player ply, int ver) throws IOException
    {
        this.in = in;
        this.out = out;
        this.ply = ply;
        this.ver = ver;
        System.out.println("Coms Thread created");
    }

    public void run()
    {
        String smsg = "";
        System.out.println("Coms Thread Started");
        while (true)
        {
            try
            {
                smsg = in.readUTF();
            } catch (IOException e)
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
                        out.writeUTF(ply.getName());
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
                        out.writeInt(ver);
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
                        out.writeUTF("");
                    } catch (IOException e)
                    {
                        System.out.println("Connection failure, unable to send/receive message, exiting");
                        System.exit(-2);
                        e.printStackTrace();
                    }
                    try
                    {
                        System.out.println(in.readUTF());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    try
                    {
                        out.writeUTF("");
                    } catch (IOException e)
                    {
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
