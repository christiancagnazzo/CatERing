package businesslogic.recipe;

public class Preparation implements CookingProcedure{
    private int id;
    private String name;

    private Preparation() {

    }

    public String toString() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
