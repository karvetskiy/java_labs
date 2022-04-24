import java.util.Random;
import java.util.Scanner;

public class Logic {
    private Scanner in = new Scanner(System.in);
    // boolean multiplayer = false;
    private Unit unit;
    // Unit unit2;
    private Field field;

    private enum End {
        QUIT,
        DIED,
        END
    }

    private End reason = End.QUIT;


    private char[] potions = new char[]{' ',' ','h','h','h','h','h','h','h','v','v','v','v','v',' ',' ','k'};
    private String[] items = new String[]{"sword", "shield", "armor", "helmet",};

    public Logic() {
        field = new Field();
    }

    /* in progress...
    public Logic(Unit unit1, Unit unit2, Field field) {
        this.unit1 = unit1;
        this.unit2 = unit2;
        this.field = field;
        multiplayer = true;
        game();
    }
     */

     void start() {
        int input;
        System.out.println(
                """
                        Hello, dude. Welcome to the Game.
                        How do you like destroy and chaos? I hope, you enjoy it.
                        Now I'll try to explain rules of this world(be attentive...)
                        Our land have a lot of villages and cities: it has some kind of staff(potions, i meant).
                        It helps you to recharge your health, up your vision...\040
                        ...and maybe something else... like kill you
                        
                        ... oh, yeah: if you find somthng like "0"... idk man, be careful!
                        
                        Okey, stop this texting, let's go: choose personage:
                        1 - Ѭ(Warrior) - brave and strong, a lot of health, but vision not so good
                        2 - Ѿ(Scout) - fast and furious(xD), vision is brilliant, but terribly frail\s"""
        );
        input = in.nextInt();
        while (input != 1 && input != 2) {
            System.out.println("1 or 2, man, only 1 or 2...");
            input = in.nextInt();
        }
        switch (input) {
            case 1 -> {
                unit = new Warrior(field);
                System.out.println("Great choose, brave warrior!");
            }
            case 2 -> {
                unit = new Scout(field);
                System.out.println("Great choose, fast scout!");
            }
        }

        move();
    }

    private void move() {
        char input = ' ';
        System.out.println("""
                Okey, you are in map. Now you need to move. Use WASD for it.
                Also, by default you can't see anything. Activate your VISION before move or go without it.
                VISION cost 10 health to you, but if you move to city or village, you need to choose:
                shameful retreat or attack: anyway, it's risk to die or get damaged.
                use key V to activate your VISION;
                use key Q to quit the game;
                LETS GO!\s""");

        while (input != 'Q' && input != 'q'){
            field.showVisionField();
            System.out.println(
                    "INSTANCE: " +
                    "HEALTH " + unit.health + " HP; " +
                    "VISION " + unit.vision + "; " +
                    "ITEMS: " + unit.items + ";"
            );
            System.out.print("input: ");
            input = in.next().charAt(0);
            switch (input) {
                case 'w':
                case 'W':
                    if (unit.x == 0 || field.field[unit.x-1][unit.y] == '#')
                        System.out.println("Wall! Move other side!");
                    else {
                        if (field.get(unit.x-1, unit.y) == 'Д')
                            fight('Д');
                        else if (field.get(unit.x-1, unit.y) == 'Г')
                            fight('Г');
                        else if (field.get(unit.x-1, unit.y) == '0')
                            potion();
                        field.setUnit(unit.x, unit.y, unit.x-1, unit.y, unit.icon);
                        unit.x--;
                    }
                    break;

                case 'a':
                case 'A':
                    if (unit.y == 0 || field.field[unit.x][unit.y-1] == '#')
                        System.out.println("Wall! Move other side!");
                    else {
                        if (field.get(unit.x, unit.y-1) == 'Д')
                            fight('Д');
                        else if (field.get(unit.x, unit.y-1) == 'Г')
                            fight('Г');
                        else if (field.get(unit.x, unit.y-1) == '0')
                            potion();
                        field.setUnit(unit.x, unit.y, unit.x, unit.y-1, unit.icon);
                        unit.y--;
                    }
                    break;

                case 's':
                case 'S':
                    if (unit.y == 9 || field.field[unit.x+1][unit.y] == '#')
                        System.out.println("Wall! Move other side!");
                    else {
                        if (field.get(unit.x, unit.y+1) == 'Д')
                            fight('Д');
                        else if (field.get(unit.x, unit.y+1) == 'Г')
                            fight('Г');
                        else if (field.get(unit.x, unit.y+1) == '0')
                            potion();
                        field.setUnit(unit.x, unit.y, unit.x+1, unit.y, unit.icon);
                        unit.x++;
                    }
                    break;

                case 'd':
                case 'D':
                    if (unit.x == 9 || field.field[unit.x][unit.y+1] == '#')
                        System.out.println("Wall! Move other side!");
                    else {
                        if (field.get(unit.x+1, unit.y) == 'Д')
                            fight('Д');
                        else if (field.get(unit.x+1, unit.y) == 'Г')
                            fight('Г');
                        else if (field.get(unit.x+1, unit.y) == '0')
                            potion();
                        field.setUnit(unit.x, unit.y, unit.x, unit.y+1, unit.icon);
                        unit.y++;
                    }
                    break;

                case 'v':
                case 'V':
                    System.out.println("Vision activated! HEALTH - 10 HP");
                    unit.health -= 10;
                    field.setVisible(unit);
                    break;

                default:
                    System.out.println("Use WASD, V and Q keys only...");
                    break;
            }
            if (field.isEmpty()) {
                input = 'q';
                reason = End.END;
            }
            if (unit.health == 0) {
                input = 'q';
                reason = End.DIED;

            }
        }
        if (reason == End.DIED)
            System.out.println("You died! If you want to play again, I'll be waiting for you");
        else if (reason == End.END)
            System.out.println("Great job! You destroy all villages and cities!");
        else
            System.out.println("Ok! I'll be waiting for you");
    }

    private void potion() {
        System.out.println("""
                WOW! You just find cave with potion!
                But... You don't know what's inside...
                Would you try it?
                y or n
                """);
        char input = in.next().charAt(0);
        while (input != 'y' && input != 'n') {
            System.out.println("y or n, man, only y or n...");
            input = in.next().charAt(0);
        }
        switch (input) {
            case 'y' -> {
                Random rand = new Random();
                int item;
                char potion = potions[rand.nextInt(0, potions.length)];
                if (potion == ' ') {
                    System.out.println("Just water! But... oh... It's been standing here for a very long time");
                    unit.health -= 5;
                    System.out.println("DAMAGE 5 HP");
                    item = rand.nextInt(1, 40);
                    if (item >= 35) {
                        unit.addItem(items[rand.nextInt(4)]);
                        System.out.println("LOOK! You find item!");
                    }
                }
                else if (potion == 'h') {
                    int h = rand.nextInt(5, 30);
                    unit.health += h;
                    System.out.println("Health potion! HEALTH +" + h + " HP");
                    item = rand.nextInt(1, 40);
                    if (item >= 37) {
                        unit.addItem(items[rand.nextInt(4)]);
                        System.out.println("LOOK! You find item!");
                    }
                }
                else if (potion == 'v') {
                    int v = rand.nextInt(1, 2);
                    unit.vision += v;
                    System.out.println("Vision potion! VISION +" + v);
                    item = rand.nextInt(1, 40);
                    if (item >= 25) {
                        unit.addItem(items[rand.nextInt(4)]);
                        System.out.println("LOOK! You find item!");
                    }
                }
                else if (potion == 'k') {
                    unit.health = 0;
                    System.out.println("OH, FUUUUUCK.....");
                }
            }
            case 'n' -> System.out.println("Okey! Let's go for the...");
        }

    }

    private void fight(char type) {
        if (type == 'Д') {
            System.out.println("""
                    This is village. Do you want to attack???
                    """);
            char input = in.next().charAt(0);
            while (input != 'y' && input != 'n') {
                System.out.println("y or n, man, only y or n...");
                input = in.next().charAt(0);
            }
            switch (input) {
                case 'y' -> {
                    Random rand = new Random();
                    int strong = rand.nextInt(20, 60);
                    System.out.println("YOUR DAMAGE IS " + strong + " HP!");
                    unit.health -= strong;
                    if (unit.health <= 0)
                        System.out.println("Oh no...");
                    else {
                        int damage = rand.nextInt(10, 50);
                        System.out.println("You win!!! Health + " + damage + " HP!");
                        unit.health += damage;
                    }
                }
                case 'n' -> System.out.println("Okey! Let's go for the...");
            }
        } else {
            System.out.println("""
                    This is city. Do you want to attack???
                    """);
            char input = in.next().charAt(0);
            while (input != 'y' && input != 'n') {
                System.out.println("y or n, man, only y or n...");
                input = in.next().charAt(0);
            }
            switch (input) {
                case 'y' -> {
                    Random rand = new Random();
                    int strong = rand.nextInt(40, 100);
                    System.out.println("YOUR DAMAGE IS " + strong + " HP!");
                    unit.health -= strong;
                    if (unit.health <= 0)
                        System.out.println("Oh no...");
                    else {
                        int damage = rand.nextInt(20, 80);
                        System.out.println("You win!!! Health + " + damage + " HP!");
                        unit.health += damage;
                    }
                }
                case 'n' -> System.out.println("Okey! Let's go for the...");
            }
        }
    }

}
