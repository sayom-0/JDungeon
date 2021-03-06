package hart.JDungeon.client;

import hart.JDungeon.client.Entity.Player.Player;
import hart.Valkyrie.exceptions.IllegalDimensionsException;
import hart.Valkyrie.objects.ScreenControllerFX;
import hart.Valkyrie.util.GenericData;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class JDT extends Application
{
    static String SFL;
    static Scanner scan;
    static String IP;
    static String Port;
    static GenericData<Player> dmgr;
    static Player ply;
    static int ver = 8;
    static ScreenControllerFX SCFX;
    static Coms coms;

    public static void main(String[] args)
    {
        System.out.println("Starting JDungeon Client Version " + ver);
        scan = new Scanner(System.in);

        while (true)
        {
            System.out.print("Enter Save File Location (Leave Blank for new Player) : ");
            SFL = scan.nextLine();

            if (!SFL.equals(""))
            {
                try
                {
                    dmgr = new GenericData<>(new File(SFL));
                    break;
                } catch (IOException e)
                {
                    System.out.println("Save file not found or you lack read permissions");
                }
            }
            else
            {
                System.out.print("\nEnter player name : ");
                ply = new Player(scan.nextLine(), coms);
                break;
            }
        }

        System.out.print("\nEnter Server IP :");
        IP = scan.nextLine();
        System.out.print("\nEnter Server Port : ");
        Port = scan.nextLine();

        try
        {
            System.out.println("Connecting to " + IP + " on port " + Port);
            Socket client = new Socket(IP, Integer.parseInt(Port));

            System.out.println("Connected to " + client.getRemoteSocketAddress());

            System.out.println("Socket creation complete, switching stream control to Coms thread...");
            coms = new Coms(client, ply, ver);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        coms.start();

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IllegalDimensionsException
    {
        BorderPane HUD = new BorderPane();
        SCFX = new ScreenControllerFX(1600, 900);
        Scene gmap = new Scene(HUD, SCFX.getRes("width"), SCFX.getRes("height"));
        HBox topH = new HBox();

        HUD.setTop(topH);
        VBox topHLeft = new VBox();
        VBox topHRight = new VBox();
        topH.getChildren().add(topHLeft);
        topH.getChildren().add(topHRight);
        SCFX.setFont("Title", Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        topHRight.getChildren().add(SCFX.setText("Title", new Text("Sayom"), "Title"));


        primaryStage.setScene(gmap);
        primaryStage.setTitle("JDungeon Alpha Version " + ver);
        primaryStage.show();
    }
}
