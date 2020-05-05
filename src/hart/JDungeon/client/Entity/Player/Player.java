package hart.JDungeon.client.Entity.Player;

import hart.JDungeon.client.Coms;
import hart.JDungeon.client.Entity.Entity;
import hart.JDungeon.client.Entity.Interfaces.Combatant;
import hart.JDungeon.client.Entity.Interfaces.Movement;
import hart.JDungeon.client.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Player extends Entity implements Combatant, Movement, Serializable
{
    private String name;
    private Coms coms;
    private int health;
    private int mana;
    private int gold;
    private int level;
    private int xp;
    private ArrayList<Item> inv;
    private int x;
    private int y;

    public Player(String name, Coms coms)
    {
        this.name = name;
        this.coms = coms;
        this.health = 20;
        this.mana = 10;
        this.gold = 100;
        this.level = 0;
        this.xp = 0;
        PlayerWatcher plyw = new PlayerWatcher(this);
        plyw.start();
    }

    public String getName() { return name; }

    public void dmg(int dmg)
    {
        this.health -= dmg;
        if (0 >= health)
        {
            System.out.println("Death!");
            coms.queueMsg("!" + name + " has died!");
        }
    }

    public void xp(int xp)
    {
        this.xp += xp;
    }

}
