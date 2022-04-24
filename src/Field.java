import java.util.Random;

public class Field {
    char[][] field = new char[10][10];
    boolean[][] isVisible = new boolean[10][10];
    /*символы:
    * # - препятствие - 10
    * Д - деревня - 6
    * Г - город - 2
    * 0 - клад(восстановление здоровья) - 5 */

    public Field() {
        initField();
    }

    private void initField() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            field[i / 10][i % 10] = ' ';
        }
        for (int i = 0; i < 100; i++) {
            isVisible[i / 10][i % 10] = false;
        }

        char[] objects = new char[]{
                '#','#','#','#','#','#','#','#','#','#',
                'Д','Д','Д','Д','Д','Д',
                'Г','Г',
                '0','0','0','0','0',
                };
        for (char object : objects) {
            int f = random.nextInt(1, 99);
            if (field[f / 10][f % 10] != ' ')
                while (field[f / 10][f % 10] != ' ')
                    f = random.nextInt(100);
            field[f / 10][f % 10] = object;
        }
    }

    void showField() {
        System.out.println(" ______________________________");
        for (int i = 0; i < 10; i++) {
            System.out.print("|");
            for (int j = 0; j < 10; j++) {
                System.out.print(" " + field[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println(" ______________________________");
    }

    void showVisionField() {
        System.out.println(" ______________________________");
        for (int i = 0; i < 10; i++) {
            System.out.print("|");
            for (int j = 0; j < 10; j++) {
                if (isVisible[i][j])
                    System.out.print(" " + field[i][j] + " ");
                else
                    System.out.print("   ");
            }
            System.out.println("|");
        }
        System.out.println(" ______________________________");
    }

    void setUnit(int x, int y, int newX, int newY, char c) {
        field[newX][newY] = c;
        isVisible[newX][newY] = true;
        field[x][y] = ' ';
    }

    void set(int x, int y, char c) {
        field[x][y] = c;
        isVisible[x][y] = true;
    }

    char get(int x, int y) {
        return field[x][y];
    }

    void setVisible(Unit unit) {
        int x1 = Math.max(unit.x - unit.vision, 0);
        int x2 = Math.min(unit.x+unit.vision, 9);
        int y1 = Math.max(unit.y-unit.vision, 0);
        int y2 = Math.min(unit.y+unit.vision, 9);
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++)
                isVisible[i][j] = true;
        }
    }

    boolean isEmpty() {
        boolean is = true;
        for (int i = 0; i < 100; i++) {
            if (field[i / 10][i % 10] == 'Д' || field[i / 10][i % 10] == 'Г' || field[i / 10][i % 10] == '0')
                is = false;
        }
        return is;
    }
}
