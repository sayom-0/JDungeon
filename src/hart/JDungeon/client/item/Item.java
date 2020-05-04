package hart.JDungeon.client.item;

public class Item
{
    private String name;
    private int amt;
    private int value;

    public Item(String name, int amt, int value)
    {
        this.name = name;
        this.amt = amt;
        this.value = value;
    }

    public void add(int i)
    {
        this.amt += i;
    }

    public void remove(int i)
    {
        this.amt -= i;
    }

    public String getName()
    {
        return name;
    }

    public int getAmt()
    {
        return amt;
    }

    public int getValue()
    {
        return value;
    }
}
