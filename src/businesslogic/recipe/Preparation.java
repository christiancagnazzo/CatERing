package businesslogic.recipe;

public class Preparation implements CookingProcedure{
    private int id;
    private String name;

    public Preparation() {
        id = 0;
    }

    public static CookingProcedure loadPreparationById(int id) {
        // TODO
        return new Preparation();
    }

    public String toString() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }



}
