public class Scout extends Unit {

    public Scout(Field field) {
        x = 0;
        y = 0;
        icon = 'Ѿ';
        health = 100;
        vision = 3;
        field.set(x, y, icon);
    }


}
