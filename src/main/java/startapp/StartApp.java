package startapp;

import businesslogic.DeliveryService;
import presentation.*;
/**
 * Clasa pentru pornirea aplicatiei
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class StartApp {
    /**
     * Metoda pornire aplizatie
     * @param args lista argumente
     */
    public static void main(String[] args){
        LogView logView=new LogView();
        EmployeeView employeeView=new EmployeeView();
        ClientView clientView=new ClientView();
        AdministratorView administratorView=new AdministratorView();

        DeliveryService deliveryService=new DeliveryService();
        deliveryService.addObserver(employeeView);
        ControllerLogin controllerLogin=new ControllerLogin(logView,clientView,
                                        administratorView,employeeView, deliveryService);
        logView.setVisible(true);
    }
}
