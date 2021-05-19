package presentation;

import businesslogic.BaseProduct;
import businesslogic.CompositeProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Clasa definire window administrator
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class AdministratorView extends JFrame {
    /**
     *Numele campurilor sun sugestive
     */
    private final JButton importButton=new JButton("Import products");
    /**
     * text field pentru nume produs
     */
    private final JTextField titleFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField ratingFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField caloriesFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField proteinsFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField fatFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField sodiumFld=new JTextField(5);
    /**
     *...
     */
    private final JTextField priceFld=new JTextField(5);
    /**
     * buton de adaugare produs
     */
    private final JButton addButton=new JButton("Add new product");

    /**
     * buton de modificare produs
     */
    private JButton modifyButton=new JButton("Modify product");
    /**
     *...
     */
    private JButton deleteButton=new JButton("Delete product");
    /**
     *....
     */
    private JTable baseProductTable;
    /**
     *....
     */
    private JList<Object> compositeProductJList;
    /**
     *...
     */
    private JButton modifyComposite=new JButton("Update composite");
    /**
     *...
     */
    private JButton modifyCompositeButton=new JButton("Modify");
    /**
     *...
     */
    private JButton deleteComposite=new JButton("Delete product");
    /**
     *...
     */
    private JTable compositeTable;
    /**
     *...
     */
    private JPanel compositePanel=new JPanel();
    /**
     *...
     */
    private JPanel modifyPanel=new JPanel();
    /**
     *...
     */
    private JPanel createProdPanel=new JPanel();
    /**
     *...
     */
    private JList<Object> baseProdList;
    /**
     *...
     */
    private JList<Object> compProductsList;
    /**
     *...
     */
    private JButton addBaseProd=new JButton("Add Base Prod");
    /**
     *...
     */
    private JButton addCompProd=new JButton("Add Composite Prod");
    /**
     *...
     */
    private JTextField nameField=new JTextField(30);
    /**
     *...
     */
    private JButton finalizeCreation=new JButton("Finalize");
    /**
     *...
     */
    private JTextField startHour=new JTextField(5);
    /**
     *...
     */
    private JTextField endHour=new JTextField(5);
    /**
     *...
     */
    private JButton timeIntButton=new JButton("Generate report");
    /**
     *...
     */
    private JTextField numberProduct=new JTextField(5);
    /**
     *..
     */
    private JButton numberProdButton=new JButton("Generate report");
    /**
     *...
     */
    private JTextField numberClient=new JTextField(5);
    /**
     *...
     */
    private JTextField valueOrder=new JTextField(5);
    /**
     *...
     */
    private JButton clientButton=new JButton("Generate report");
    /**
     *...
     */
    private JTextField dayField=new JTextField(5);
    /**
     *...
     */
    private JTextField monthField=new JTextField(5);
    /**
     *...
     */
    private JTextField yearField=new JTextField(5);
    /**
     *...
     */
    private JButton dayButton=new JButton("Generate report");

    /**
     *Constructor; definire aspect interfata
     */
    public AdministratorView(){
        this.setPreferredSize(new Dimension(600,700));
        this.setMinimumSize(new Dimension(600,700));
        this.setLocation(new Point(600,100));
        JTabbedPane operationPanels=new JTabbedPane();
        JPanel welcomePanel=new JPanel();
        JLabel welcomeTxt=new JLabel("WELCOME!! ADMINISTRATOR");
        welcomeTxt.setFont(new Font("TimesRoman",Font.PLAIN,20));
        welcomePanel.add(welcomeTxt);
        welcomePanel.add(importButton);
        operationPanels.add("Import products",welcomePanel);

        JPanel createTabbed=new JPanel();
        JPanel addPanel=new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel,BoxLayout.Y_AXIS));
        addPanel.add(new JLabel("Add new product "));
        addPanel.add(new JLabel("Title:"));
        addPanel.add(titleFld);
        addPanel.add(new JLabel("Rating:"));
        addPanel.add(ratingFld);
        addPanel.add(new JLabel("Calories:"));
        addPanel.add(caloriesFld);
        addPanel.add(new JLabel("Proteins:"));
        addPanel.add(proteinsFld);
        addPanel.add(new JLabel("Fat:"));
        addPanel.add(fatFld);
        addPanel.add(new JLabel("Sodium:"));
        addPanel.add(sodiumFld);
        addPanel.add(new JLabel("Price:"));
        addPanel.add(priceFld);
        addPanel.add(addButton);
        addPanel.setBorder(BorderFactory.createTitledBorder("Product Information"));
        createTabbed.add(addPanel);
        operationPanels.add("Add product",createTabbed);

        modifyPanel=new JPanel();
        operationPanels.add("Modify product", modifyPanel);

        createProdPanel=new JPanel();
        JPanel tabCreate=new JPanel();
        tabCreate.setLayout(new BoxLayout(tabCreate,BoxLayout.Y_AXIS));
        JPanel inputPanel=new JPanel();
        tabCreate.add(new JLabel("Title new prod:"));
        inputPanel.add(nameField); nameField.setPreferredSize(new Dimension(30,40));
        tabCreate.add(inputPanel);
        tabCreate.add(createProdPanel);
        operationPanels.add("Create product", tabCreate);

        JPanel reportsTabbed=new JPanel();
        JPanel reportsPanel=new JPanel();
        reportsPanel.setLayout(new BoxLayout(reportsPanel,BoxLayout.Y_AXIS));
        reportsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        reportsPanel.add(new JLabel("Generate reports"));
        reportsPanel.add(new JLabel("Time interval of the orders"));
        reportsPanel.add(new JLabel("Start hour")); reportsPanel.add(startHour);
        reportsPanel.add(new JLabel("End hour")); reportsPanel.add(endHour);
        reportsPanel.add(timeIntButton);
        reportsPanel.add(new JLabel("The products ordered more than a specified number of times so far"));
        reportsPanel.add(new JLabel("Number")); reportsPanel.add(numberProduct);
        reportsPanel.add(numberProdButton);
        reportsPanel.add(new JLabel("The clients that have ordered more than a specified"));
        reportsPanel.add(new JLabel("Number orders")); reportsPanel.add(numberClient);
        reportsPanel.add(new JLabel("Value higher then ")); reportsPanel.add(valueOrder);
        reportsPanel.add(clientButton);
        reportsPanel.add(new JLabel("The products ordered within a specified day"));
        JPanel datePanel=new JPanel(); datePanel.setLayout(new BoxLayout(datePanel,BoxLayout.X_AXIS));
        reportsPanel.add(new JLabel("Day(dd)                                   Month(mm)                            Year(yyyy)"));
        datePanel.add(dayField); datePanel.add(monthField); datePanel.add(yearField);
        reportsPanel.add(datePanel);
        reportsPanel.add(dayButton);
        reportsPanel.setBorder(BorderFactory.createTitledBorder("REPORTS"));
        reportsTabbed.add(reportsPanel);
        operationPanels.add("Generate reports", reportsTabbed);

        this.setContentPane(operationPanels);
        this.pack();
        this.setTitle("Administrator");
    }

    /**
     * Setare panel pentru modifacrea produsleor
     * @param deliveryService deliveryService cu noile campuri actualizate
     */
    public void setModifyPanel(DeliveryService deliveryService){
        modifyPanel.removeAll();
        modifyPanel.setLayout(new BoxLayout(modifyPanel,BoxLayout.Y_AXIS));
        modifyPanel.add(new JLabel("Table with base products"));

        baseProductTable =Tables.createTable(deliveryService);
        modifyPanel.add(new JScrollPane(baseProductTable));
        modifyPanel.add(modifyButton);
        modifyPanel.add(deleteButton);

        compositePanel.removeAll();
        compositePanel.setLayout(new BoxLayout(compositePanel,BoxLayout.Y_AXIS));
        compositePanel.add(new JLabel("Composite Products"));

        compositeProductJList = Tables.createList(deliveryService,0);
        compositeProductJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        compositeProductJList.setSelectedIndex(0);
        compositePanel.add(new JScrollPane(compositeProductJList));

        compositePanel.add(modifyComposite);
        compositePanel.add(deleteComposite);
        compositePanel.revalidate();
        modifyPanel.add(compositePanel);
        modifyPanel.revalidate();
    }

    /**
     * Actulizare panel pentru modifacrea produsleor compuse
     * @param compositeProduct noile produse compuse
     */
    public void setModifyPanelComposite(CompositeProduct compositeProduct){
       compositePanel.add(new JLabel("Table with base products of "+compositeProduct.getTitle()));
        Set<MenuItem> menuItems=compositeProduct.getContainedProducts();
        Set<MenuItem> baseProducts=new HashSet<>();
        for(MenuItem menuItem:menuItems){
            if(menuItem instanceof CompositeProduct){
                for(MenuItem menuItem1:((CompositeProduct) menuItem).getContainedProducts()){
                    if(menuItem1 instanceof BaseProduct)
                    baseProducts.add((BaseProduct) menuItem1);
                }
            }else
                baseProducts.add((BaseProduct) menuItem);
        }
        compositeTable=Tables.createTable(baseProducts);
        compositeTable.setRowSelectionAllowed(true);
        compositePanel.add(compositeTable);
        compositePanel.add(modifyCompositeButton);
        compositePanel.revalidate();
    }

    /**
     * Actualizare panel pentru creare produs
     * @param deliveryService deliveryservice cu campurile actualizate
     */
    public void setCreateProdPanel(DeliveryService deliveryService) {
        this.createProdPanel.removeAll();
        createProdPanel.setLayout(new BoxLayout(createProdPanel,BoxLayout.Y_AXIS));
        baseProdList=Tables.createList(deliveryService,1);
        compProductsList=Tables.createList(deliveryService,0);
        JPanel listsPanel=new JPanel();
        listsPanel.setLayout(new GridLayout(1,2));
        baseProdList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        compProductsList.setSelectedIndex(0);
        listsPanel.add(new JScrollPane(baseProdList));
        listsPanel.add(new JScrollPane(compProductsList));
        createProdPanel.add(listsPanel);
        JPanel buttonPanel=new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.add(addBaseProd);
        buttonPanel.add(addCompProd);
        createProdPanel.add(buttonPanel);
        createProdPanel.add(finalizeCreation);
        createProdPanel.revalidate();
    }
    //listeneri...............................................

    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addImportListener(ActionListener e){
        this.importButton.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addAddProListener(ActionListener e){
        this.addButton.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addModifyListener(ActionListener e){
        this.modifyButton.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addDeleteListener(ActionListener e){
        this.deleteButton.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addDeleteCompositeListener(ActionListener e){
        this.deleteComposite.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addModifyUpdateListener(ActionListener e){
        this.modifyComposite.addActionListener(e);
    }
    /**
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addModifyCompListener(ActionListener e) { this.modifyCompositeButton.addActionListener(e); }

    /**
     * //pentru creare produs compus
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addCompToComposite(ActionListener e){
        this.addCompProd.addActionListener(e);
    }
    /**
     * //pentru creare produs compus
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addBaseToComposite(ActionListener e){
        this.addBaseProd.addActionListener(e);
    }
    /**
     * //pentru creare produs compus
     * Listener (numele sugestiv)
     * @param e actionlistener
     */
    public void addFinalizeListener(ActionListener e){ this.finalizeCreation.addActionListener(e);}
    /**
     * Listener pentru ca in momentul in care se inchide fereastra sa se fac vizibila cea de logare
     * @param logView window-ul de logare
     */
    public void addClosingListener(LogView logView){
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                logView.setVisible(true);
            }
        });
    }

    /**
     * Getter
     * @return produs compus selectat
     */
    public Object getSelectedComposite(){
        return this.compositeProductJList.getSelectedValue();
    }

    /**
     * Afisare mesa dialog window
     * @param msg mesaj
     */
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * Returnare tabel cu produse de baza
     * @return tabel
     */
    public JTable getTableBaseProduct(){
        return this.baseProductTable;
    }
    /**
     * Returnare tabel cu produse compuse
     * @return tabel
     */
    public JTable getTableCompositeProduct(){
        return this.compositeTable;
    }

    /**
     * Getter
     * @return fieldurile pentru creare de produs de baza
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
     * Getter
     * @return prodsu compu din lista pentru adaugarea lui la nou produs compus
     */
    public CompositeProduct getCompToComposite(){
        return (CompositeProduct) compProductsList.getSelectedValue();
    }
    /**
     * Getter
     * @return prodsu de baza din lista pentru adaugarea lui la nou produs compus
     */
    public BaseProduct getBaseToComposite(){
        return (BaseProduct) baseProdList.getSelectedValue();
    }

    /**
     * Getter
     * @return numele produsului compus nou
     */
    public String getNewCompositeTitle(){
        return this.nameField.getText();
    }

    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getStartHour(){return this.startHour.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getEndHour(){return this.endHour.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getNumberProduct(){return this.numberProduct.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getNumberClient(){return this.numberClient.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getOrderValue(){return this.valueOrder.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getDayFld(){return this.dayField.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getMonthFld(){return this.monthField.getText();}
    /**
     * Getter rapoarte
     * @return valoare fieldului
     */
    public String getYearFld(){return this.yearField.getText();}

    /**
     * Adaugare listener pentru rapoarte
     * @param e listener adaptat
     */
    public void addTimeButtonListener(ActionListener e){this.timeIntButton.addActionListener(e);}
    /**
     * Adaugare listener pentru rapoarte
     * @param e listener adaptat
     */
    public void addNbProdListener(ActionListener e){this.numberProdButton.addActionListener(e);}
    /**
     * Adaugare listener pentru rapoarte
     * @param e listener adaptat
     */
    public void addClientButtonListener(ActionListener e){this.clientButton.addActionListener(e);}
    /**
     * Adaugare listener pentru rapoarte
     * @param e listener adaptat
     */
    public void addDayButtonListener(ActionListener e){this.dayButton.addActionListener(e);}
}
