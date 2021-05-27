package businesslogic.recipe;

import businesslogic.menu.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RecipeManager {

    public RecipeManager() {
        Recipe.loadAllRecipes();
    }

    public ObservableList<Recipe> getRecipes() {
        return FXCollections.unmodifiableObservableList(Recipe.getAllRecipes());
    }

    // TODO
    public ArrayList<CookingProcedure> getAllNecessaryProcedure(CookingProcedure cp){
        ArrayList<CookingProcedure> p = new ArrayList<>();
        return p;
    }
}
