package test;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.task.Sheet;
import javafx.collections.ObservableList;

public class TestCatERing1 {
    public static void main(String[] args) throws UseCaseLogicException {
        System.out.println("TEST FAKE LOGIN");
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

        ObservableList<EventInfo> events = CatERing.getInstance().getEventManager().getEventInfo();
        ServiceInfo s = events.get(0).getServices().get(1);
        Menu m = CatERing.getInstance().getMenuManager().getAllMenus().get(2);
        s.setMenu(m);
        System.out.println("Menu nr. "+m.getId());

        Sheet sheet = CatERing.getInstance().getTaskManager().createSheet(events.get(0),s);
        System.out.println(sheet);
    }
}