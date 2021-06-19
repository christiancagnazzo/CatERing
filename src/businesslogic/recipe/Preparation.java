package businesslogic.recipe;

public class Preparation implements CookingProcedure{
    private int id;
    private String name;

    public Preparation() {
    }

    public String toString() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

}
