package hart.JDungeon.client.Entity.Player;

import hart.JDungeon.client.Entity.Entity;
import hart.JDungeon.client.Entity.Interfaces.Combatant;
import hart.JDungeon.client.Entity.Interfaces.Movement;

import java.io.Serializable;

public class Player extends Entity implements Combatant, Movement, Serializable
{
    private String name;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName() { return name; }

}
