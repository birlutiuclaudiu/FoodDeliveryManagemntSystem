package presentation;

import businesslogic.DeliveryService;
import model.UserType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clasa descriere  window de log-are;
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class LogView extends JFrame {
    /**
     *  selectie client
     */
    private JRadioButton clientRadio=new JRadioButton("Client");
    /**
     * selectia admin
     */
    private JRadioButton adminRadio=new JRadioButton("Administrator");
    /**
     * selectie employee
     */
    private JRadioButton empRadio=new JRadioButton("Employee");
    /**
     * grupare butoane radio
     */
    private ButtonGroup selectGroup;
    /**
     * user field
     */
    private JTextField userField=new JTextField("admin");
    /**
     *  field parola
     */
    private JPasswordField passField=new JPasswordField("admin");
    /**
     * buton log in
     */
    private JButton logButton=new JButton("Log in");
    /**
     *buton register
     */
    private JButton registerClient=new JButton("Register client");

    /**
     * Constructor log view
     */
    public LogView(){
        this.setPreferredSize(new Dimension(400,400));
        this.setMinimumSize(new Dimension(400,400));
        this.setLocation(new Point(150,150));
        JPanel mainPanel=new JPanel();

        JPanel selectPanel=new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel,BoxLayout.X_AXIS));
        selectPanel.add(clientRadio);
        selectPanel.add(adminRadio);
        selectPanel.add(empRadio);
        selectGroup=new ButtonGroup();
        selectGroup.add(clientRadio);
        selectGroup.add(adminRadio);
        selectGroup.add(empRadio);

        JPanel inputPanel=new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(userField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passField);

        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.add(logButton);
        buttonPanel.add(registerClient);
        inputPanel.add(buttonPanel);

        mainPanel.add(selectPanel);
        mainPanel.add(inputPanel);

        this.setContentPane(mainPanel);
        this.pack();
        this.setTitle("Log Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adaugare listener log in
     * @param e listener cu operatia corespunzatoare
     */
    public void addLogListener(ActionListener e){
        this.logButton.addActionListener(e);
    }
    /**
     * Adaugare listener register
     * @param e listener cu operatia corespunzatoare
     */
    public void addRegListener(ActionListener e){ this.registerClient.addActionListener(e); }

    /**
     * Adaugare listener la inchiderea ferestrei; se vor salva datele din delivery-ul asociat
     * @param deliveryService delivery Service-ul asociat
     */
    public void addClosingListener(DeliveryService deliveryService){
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                deliveryService.saveData();
                System.exit(0);
            }
        });
    }

    /**
     * Getter
     * @return username
     */
    public String getUsername(){
        return this.userField.getText();
    }

    /**
     * getter
     * @return password field
     */
    public String getPass(){
        return String.valueOf(this.passField.getPassword());
    }

    /**
     * Getter
     * @return usertype selectat
     */
    public UserType getSelected(){
        if(adminRadio.isSelected())
            return UserType.ADMIN;
        if(clientRadio.isSelected())
            return UserType.CLIENT;
        if(empRadio.isSelected())
            return UserType.EMP;
        return UserType.ADMIN;
    }

    /**
     * Message dialog
     * @param msg mesaj de afisat
     */
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
}
