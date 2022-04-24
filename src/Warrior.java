
public class Warrior extends Unit {

    public Warrior(Field field) {
        x = 9;
        y = 9;
        icon = 'Ѭ';
        health = 160;
        vision = 1;
        field.set(x, y, icon);
    }


}
