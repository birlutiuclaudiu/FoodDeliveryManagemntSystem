package presentation;

import businesslogic.DeliveryService;
import model.UserType;
import model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Clasa descriere controller login window
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class ControllerLogin {
    /**
     *log View asociat
     */
    private LogView logView;
    /**
     *deliveryService asociat
     */
    private DeliveryService deliveryService;
    /**
     *client View asociat
     */
    private ClientView clientView;
    /**
     *administrator View asociat
     */
    private AdministratorView administratorView;
    /**
     *employee View asociat
     */
    private EmployeeView employeeView;
    /**
     * client controller
     */
    private ClientController clientController;
    /**
     * administrator controller
     */
    private AdministratorController administratorController;

    /**
     * Constructor
     * @param logView log View asociat
     * @param clientView client View asociat
     * @param administratorView administrator View asociat
     * @param employeeView employee View asociat
     * @param deliveryService deliveryService asociat
     */
    public ControllerLogin(LogView logView,ClientView clientView, AdministratorView administratorView,
                           EmployeeView employeeView, DeliveryService deliveryService){
        this.logView=logView;
        this.clientView=clientView;
        this.administratorView=administratorView;
        this.employeeView=employeeView;
        clientController=new ClientController(deliveryService,clientView,logView);
        administratorController=new AdministratorController(deliveryService,administratorView,logView);
        this.deliveryService=deliveryService;
        this.logView.addLogListener(new LogListener());
        this.logView.addRegListener(new RegListener());
        this.logView.addClosingListener(deliveryService);
    }

    /**
     * Listener pentru butonul de logare
     */
    public class LogListener implements ActionListener {
        /**
         * Metoda suprascrisa
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            User user=new User(0,logView.getUsername(),logView.getPass(),logView.getSelected());
            User userConnected=null;
            if((userConnected=deliveryService.getConnection(user))!=null){
                if(logView.getSelected()==UserType.CLIENT){
                    clientController.setConnectedUser(userConnected);
                    clientController.newConnectionSetup();
                    clientView.setVisible(true); logView.setVisible(false);
                }else if(logView.getSelected()==UserType.ADMIN){
                    administratorView.setVisible(true); logView.setVisible(false);
                }else{
                    employeeView.setVisible(true);
                    employeeView.addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            logView.setVisible(true);
                        }
                    });
                    logView.setVisible(false);
                }
            }else
                logView.showMessage("WRONG username or password! Try again!");
        }
    }
    /**
     * Listener pentru butonul de register client
     */
    public class RegListener implements ActionListener {
        /**
         * Metoda suprascrisa
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(logView.getSelected()!=UserType.CLIENT){
                logView.showMessage("Invalid type"); return;
            }
            if(logView.getUsername().length()<8 || logView.getUsername().contains(" ")){
                logView.showMessage("Invalid user name! length>=8, no spaces"); return;
            }
            if(logView.getPass().length()<6){
                logView.showMessage("Invalid pass! length>=6"); return;
            }
            if(deliveryService.getConnection(new User(0, logView.getUsername(),logView.getPass(),UserType.CLIENT))!=null) {
                logView.showMessage("Account already exists");
                return;
            }
            if(deliveryService.registerClient(new User(deliveryService.useMaxId(),logView.getUsername(),
                                                                                logView.getPass(),UserType.CLIENT))){
                logView.showMessage("Account with username: "+logView.getUsername()+" created");
                return;
            }
            logView.showMessage("Could not create account!");
        }
    }
}
