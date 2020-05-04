package hart.JDungeon.client.Entity.Player;

import hart.JDungeon.client.Entity.Entity;
import hart.JDungeon.client.Entity.Interfaces.Combatant;
import hart.JDungeon.client.Entity.Interfaces.Movement;
import hart.JDungeon.client.item.Item;

import java.io.Serializable;
import java.util.ArrayList;

public class Player extends Entity implements Combatant, Movement, Serializable
{
    private String name;
    private int health;
    private int mana;
    private int gold;
    private int level;
    private int xp;
    private ArrayList<Item> inv;

    public Player(String name)
    {
        this.name = name;
        this.health = 20;
        this.mana = 10;
        this.gold = 100;
        this.level = 0;
        this.xp = 0;
        PlayerWatcher plyw = new PlayerWatcher(this);
        plyw.start();
    }

    public String getName() { return name; }

}
