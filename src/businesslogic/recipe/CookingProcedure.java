package businesslogic.recipe;

public interface CookingProcedure {
    int getId();

    static CookingProcedure loadCookingProcedureById(int id) {
        CookingProcedure ck;
        ck = Recipe.loadRecipeById(id);
        if (ck.getId() != 0)
            return ck;
        ck = Preparation.loadPreparationById(id);
        if (ck.getId() != 0)
            return ck;
        return null;
    }
}
