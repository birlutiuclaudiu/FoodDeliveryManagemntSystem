package presentation;

import businesslogic.BaseProduct;
import businesslogic.CompositeProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 * Clasa definire interfata client window
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class ClientView extends JFrame {
    /**
     * ...
     */
    private JList<MenuItem> composite= new JList<MenuItem>();
    /**
     *...
     */
    private JTable tableBaseProd=new JTable();
    /**
     *...
     */
    private JPanel menuPanel =new JPanel();
    /**
     *...
     */
    private JButton showMenuButton=new JButton("Show menu");
    /**
     *
     */
    private final JTextField titleFld=new JTextField(40);
    /**
     *...
     */
    private final JTextField ratingFld=new JTextField(10);
    /**
     *...
     */
    private final JTextField caloriesFld=new JTextField(10);
    /**
     *...
     */
    private final JTextField proteinsFld=new JTextField(10);
    /**
     *...
     */
    private final JTextField fatFld=new JTextField(10);
    /**
     *...
     */
    private final JTextField sodiumFld=new JTextField(10);
    /**
     *...
     */
    private final JTextField priceFld=new JTextField(10);
    /**
     *...
     */
    private final JButton searchButton=new JButton("SEARCH");
    /**
     *panel rezultate cautare
     */
    private JPanel resultsPanel=new JPanel();
    /**
     * panel creare nou comanda
     */
    private JPanel createOrderPanel=new JPanel();
    /**
     *lista produse de baza
     */
    private JList<Object> baseProdList;
    /**
     * lista produse compuse
     */
    private JList<Object> compProductsList;
    /**
     *buton aduagre produs de baza la comanda
     */
    private JButton addBaseProd=new JButton("Add base product");
    /**
     *buton aduagre produs compus la comanda
     */
    private JButton addCompProd=new JButton("Add composite");
    /**
     *buton creare comanda
     */
    private JButton finalizeCreation=new JButton("Finalize order ");
    /**
     *Constructor; aranjare componente
     */
    public ClientView(){
        this.setPreferredSize(new Dimension(500,700));
        this.setMinimumSize(new Dimension(500,700));
        this.setLocation(new Point(600,100));
        JTabbedPane operationPanels=new JTabbedPane();
        menuPanel.add(showMenuButton);
        operationPanels.add("View Menu", menuPanel);

        JPanel searchPanel=new JPanel();
        JPanel inputPanel=new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Title"));
        inputPanel.add(titleFld);
        inputPanel.add(new JLabel("Rating:"));
        inputPanel.add(ratingFld);
        inputPanel.add(new JLabel("Calories:"));
        inputPanel.add(caloriesFld);
        inputPanel.add(new JLabel("Proteins:"));
        inputPanel.add(proteinsFld);
        inputPanel.add(new JLabel("Fat:"));
        inputPanel.add(fatFld);
        inputPanel.add(new JLabel("Sodium:"));
        inputPanel.add(sodiumFld);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceFld);
        searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
        searchPanel.add(new JLabel("SEARCH CRITERIA"));
        searchPanel.add(inputPanel);
        searchPanel.add(searchButton);
        searchPanel.add(resultsPanel);
        resultsPanel.setPreferredSize(new Dimension(this.getWidth(),3*this.getHeight()/4));
        operationPanels.add("Search", searchPanel);

        JPanel orderPanel=new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.add(new JLabel("Place new order"));
        orderPanel.add(createOrderPanel);
        operationPanels.add("New order", orderPanel);
        this.setContentPane(operationPanels);
        this.pack();
        this.setTitle("Client page");
    }

    /**
     * Setare panel cu meniul
     * @param deliveryService deliveryservice-ul asociat
     */
    public void setShowMenuPanel(DeliveryService deliveryService){
        menuPanel.removeAll();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(new JLabel("Welcome ! Client Page!"));
        menuPanel.add(showMenuButton);
        menuPanel.add(new JLabel("Base products"));
        menuPanel.add(new JScrollPane(Tables.createTable( deliveryService)));
        menuPanel.add(new Label("Composite products"));
        menuPanel.add(new JScrollPane(Tables.createList( deliveryService,0)));
        menuPanel.revalidate();
    }
    /**
     * Setare panel cu cu rezultatele cautarii
     * @param menuItemCollection lista de produse filtrate
     */
    public void setResultsPanel(Collection<MenuItem> menuItemCollection){
        resultsPanel.removeAll();
        resultsPanel.setLayout(new GridLayout(2,1));
        JPanel aux1=new JPanel();
        aux1.setLayout(new BoxLayout(aux1,BoxLayout.Y_AXIS));
        aux1.add(new JLabel("Base Products"));
        aux1.add(new JScrollPane(Tables.createTable(menuItemCollection)));
        JPanel aux2=new JPanel();
        aux2.setLayout(new BoxLayout(aux2,BoxLayout.Y_AXIS));
        aux2.add(new JLabel("Composite Products"));
        aux2.add(new JScrollPane(Tables.createList(menuItemCollection)));
        resultsPanel.add(aux1); resultsPanel.add(aux2);
        resultsPanel.revalidate();
    }
    /**
     * Setare panel-ului pentru creare unei noi comenzi
     * @param deliveryService deliveryservice-ul asociat
     */
    public void setCreateOrderPanel(DeliveryService deliveryService){
        this.createOrderPanel.removeAll();
        createOrderPanel.setLayout(new BoxLayout(createOrderPanel,BoxLayout.Y_AXIS));
        baseProdList=Tables.createList(deliveryService,1);
        compProductsList=Tables.createList(deliveryService,0);
        JPanel listsPanel=new JPanel();
        listsPanel.setLayout(new GridLayout(1,2));
        baseProdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        compProductsList.setSelectedIndex(0);
        listsPanel.add(new JScrollPane(baseProdList));
        listsPanel.add(new JScrollPane(compProductsList));
        createOrderPanel.add(listsPanel);
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.add(addBaseProd);
        buttonPanel.add(addCompProd);
        createOrderPanel.add(buttonPanel);
        createOrderPanel.add(finalizeCreation);
        createOrderPanel.revalidate();
    }

    /**
     * Adaugare listener buton de show menu
     * @param e listener asociat cu operatie de facut
     */
    public void addShowListener(ActionListener e){
        this.showMenuButton.addActionListener(e);
    }

    /**
     * Adaugare listener buton de show menu
     * @param e listener asociat cu operatie de facut
     */
    public void addSearchListener(ActionListener e){ this.searchButton.addActionListener(e);}

    /**
     * Adaugare listener buton de show menu
     * @param e listener asociat cu operatie de facut
     */
    public void addAddBaseListener(ActionListener e){this.addBaseProd.addActionListener(e);}
    /**
     * Adaugare listener buton de show menu
     * @param e listener asociat cu operatie de facut
     */
    public void addAddCompListener(ActionListener e){this.addCompProd.addActionListener(e);}

    /**
     * Adaugare listener buton de show menu
     * @param e listener asociat cu operatie de facut
     */
    public void addFinalizeListener(ActionListener e){ this.finalizeCreation.addActionListener(e);}

    /**
     * Adaugare listener inchidere fereastra
     * @param  logView ferareastra de logare
     */
    public void addClosingListener(LogView logView){
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                logView.setVisible(true);
            }
        });
    }

    /**
     * Afisar window dialog
     * @param msg mesaj
     */
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    /**
     * Returneaza lista de campuri completate
     * @return sir de string-uri
     */
    public String[] getProductInfo(){
        String [] infoProd=new String[7];
        infoProd[0]=titleFld.getText();
        infoProd[1]=ratingFld.getText();
        infoProd[2]=caloriesFld.getText();
        infoProd[3]=proteinsFld.getText();
        infoProd[4]=fatFld.getText();
        infoProd[5]=sodiumFld.getText();
        infoProd[6]=priceFld.getText();
        return  infoProd;
    }

    /**
     * getter
     * @return produs de baza selectat
     */
    public BaseProduct getBaseProduct(){
        return (BaseProduct) baseProdList.getSelectedValue();
    }

    /**
     * getter
     * @return produs compus selectat
     */
    public CompositeProduct getCompProduct(){
        return (CompositeProduct) compProductsList.getSelectedValue();
    }
}
