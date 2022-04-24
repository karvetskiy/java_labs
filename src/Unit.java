import java.util.ArrayList;

abstract public class Unit {
    protected int x;

    protected int y;

    protected char icon;

    protected int vision;

    protected String items = "";

    protected float health;

    public Unit() {}

    void addItem(String item) {
        items += item;
    }

    String removeItem(String item) {
        return items.substring(0, items.lastIndexOf(item));
    }
}
