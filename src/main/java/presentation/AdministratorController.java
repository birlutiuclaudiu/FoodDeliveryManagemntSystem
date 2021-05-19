package presentation;
import businesslogic.BaseProduct;
import businesslogic.CompositeProduct;
import businesslogic.DeliveryService;
import businesslogic.MenuItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * Clasa definire controller pentru interfata administrator
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class AdministratorController {
    /**
     * Delivery service asociat
     */
    private DeliveryService deliveryService;
    /**
     * administratorView
     */
    private AdministratorView administratorView;
    /**
     * Composite product
     */
    private CompositeProduct newCompositeProduct;
    /**
     * Log view- pentru reintoarcere la delogare
     */
    private LogView logView;

    /**
     * Constructor
     * @param deliveryService deliveryService
     * @param administratorView administratorView
     * @param logView logView
     */
    public AdministratorController(DeliveryService deliveryService, AdministratorView administratorView, LogView logView) {
        this.deliveryService = deliveryService;
        this.logView=logView;
        this.administratorView = administratorView;
        if(deliveryService!=null && deliveryService.getProducts()!=null){
            this.administratorView.setModifyPanel(deliveryService);
            this.administratorView.setCreateProdPanel(deliveryService);
        }
        this.administratorView.addImportListener(e->{
            if(deliveryService!=null && deliveryService .getProducts()!=null)
                administratorView.showMessage("Already imported");
            else if (deliveryService.importProducts()){
                administratorView.showMessage("Successfully imported!");
                this.administratorView.setModifyPanel(deliveryService);
                this.administratorView.setCreateProdPanel(deliveryService);
            } else administratorView.showMessage("Unsuccessfully imported!"); });
        this.administratorView.addModifyListener(new ModifyListener());
        this.administratorView.addDeleteListener(new DeleteListener());
        this.administratorView.addModifyUpdateListener(new UpdateCompositeListener());
        this.administratorView.addDeleteCompositeListener(new DeleteCompListener());
        this.administratorView.addModifyCompListener(new ModifyCompListener());
        this.administratorView.addAddProListener(new AddNewListener());
        this.administratorView.addCompToComposite(new CompToCompositeListener());
        this.administratorView.addBaseToComposite(new BaseToCompositeListener());
        this.administratorView.addFinalizeListener(new CreateCompListener());
        this.administratorView.addClosingListener(logView);
        this.administratorView.addTimeButtonListener(new ReportTimeListener());
        this.administratorView.addNbProdListener(new ReportNbProdListener());
        this.administratorView.addClientButtonListener(new ReportClientsListener());
        this.administratorView.addDayButtonListener(new ReportDayListener());
    }

    /**
     * Listener pentru raportul- interval de ore
     */
    public class ReportTimeListener implements ActionListener{
        /**
         * Metoda
         * @param e action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int startHour=Integer.parseInt(administratorView.getStartHour());
                int endHour=Integer.parseInt(administratorView.getEndHour());
                if(deliveryService.generateReportTime(startHour,endHour))
                    administratorView.showMessage("Report generated successfully");
                else
                    administratorView.showMessage("Report could not be generated");
            }catch (NumberFormatException exc){
                administratorView.showMessage(exc.getMessage());
            }
        }
    }
    /**
     * Listener pentru raportul- produse comandate de un anumit numar de ori
     */
    public class ReportNbProdListener implements ActionListener{
        /**
         * Metoda
         * @param e action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int number=Integer.parseInt(administratorView.getNumberProduct());
                if(deliveryService.generateReportProducts(number))
                    administratorView.showMessage("Report created");
                else
                    administratorView.showMessage("Report could not be created");
            }catch (NumberFormatException exc){
                administratorView.showMessage("Invalid data");
            }
        }
    }

    /**
     * Subclasa pentru raportu - clienti care au comandat de mai multe ori
     */
    public class ReportClientsListener implements ActionListener{
        /**
         * Metoda
         * @param e action listener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int number=Integer.parseInt(administratorView.getNumberClient());
                int value=Integer.parseInt(administratorView.getOrderValue());
                if(deliveryService.generateReportClient(number,value))
                    administratorView.showMessage("Report created");
                else
                    administratorView.showMessage("Report could not be created");
            }catch (NumberFormatException exc){
                     administratorView.showMessage("Invalid data");
            }
        }
    }

    /**
     * Listener pentru raportul -produse in anumita zi
     */
    public class ReportDayListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int day=Integer.parseInt(administratorView.getDayFld());
                int month=Integer.parseInt(administratorView.getMonthFld());
                int year=Integer.parseInt(administratorView.getYearFld());
                if(year<1900 || year>2021 || day<=0 || day>31 || month<=0 || month>11) {
                    administratorView.showMessage("Invalid data"); return;
                }
                if(deliveryService.generateReportDay(day,month,year))
                    administratorView.showMessage("Report created!");
                else administratorView.showMessage("Report could not be created");
            }catch (NumberFormatException exc){
                administratorView.showMessage("Invalid data");
            }
        }
    }

    /**
     * Listener pentru adaugare produs compus la un nou produs compus
     */
    public class CompToCompositeListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(administratorView.getSelectedComposite()==null)
                administratorView.showMessage("Nu composite product selected");
            if(newCompositeProduct==null)
                newCompositeProduct=new CompositeProduct();
            if(!newCompositeProduct.addNewProduct(administratorView.getCompToComposite())){
                administratorView.showMessage("Could not be added");
            }
        }
    }
    /**
     * Listener pentru adaugare produs de baza la un nou produs compus
     */
    public class BaseToCompositeListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(administratorView.getBaseToComposite()==null)
                administratorView.showMessage("Nu composite product selected");
            if(newCompositeProduct==null)
                newCompositeProduct=new CompositeProduct();
            if(!newCompositeProduct.addNewProduct(administratorView.getBaseToComposite())){
                administratorView.showMessage("Could not be added");
            }
        }
    }

    /**
     * Listener pentru creare nou produs compus
     */
    public class CreateCompListener implements ActionListener {
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(newCompositeProduct==null){
                administratorView.showMessage("No products introduced");
                return;
            }
            if(administratorView.getNewCompositeTitle()==null || administratorView.getNewCompositeTitle().equals("") ){
                administratorView.showMessage("Title no introduced");
                return;
            }
            newCompositeProduct.setTitle(administratorView.getNewCompositeTitle());
            if(deliveryService.addProduct(newCompositeProduct)){
                administratorView.showMessage(newCompositeProduct+" added successfully");
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
            }else {
                administratorView.showMessage("Not permitted! Try again!");
            }
            newCompositeProduct=null;
        }
    }

    /**
     * Listener pentru operatia de aduagra produs de baza nou
     */
    public class AddNewListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String[] a=administratorView.getProductInfo();
                MenuItem baseProduct=new BaseProduct(a[0],a[1],a[2],a[3],a[4],a[5],a[6]);
                if(!deliveryService.addProduct(baseProduct)){
                    administratorView.showMessage("Could not add product");
                    return;
                }
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
                administratorView.showMessage(((BaseProduct) baseProduct).getTitle() +" inserted");
            }catch (NumberFormatException exc){
                administratorView.showMessage("In valid data" +exc.getMessage());
            }
        }
    }
    /**
     * Listener pentru operatia de modificare produs din meniu
     */
    public class ModifyListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = administratorView.getTableBaseProduct();
            table.setRowSelectionAllowed(true);
            if(table.getSelectedRow()==-1){
                administratorView.showMessage("No product selected"); return;
            }
            BaseProduct baseProduct;
            try {
                 baseProduct = new BaseProduct((String) table.getValueAt(table.getSelectedRow(), 0),
                        (String) table.getValueAt(table.getSelectedRow(), 1),
                        (String) table.getValueAt(table.getSelectedRow(), 2),
                        (String) table.getValueAt(table.getSelectedRow(), 3),
                        (String) table.getValueAt(table.getSelectedRow(), 4),
                        (String) table.getValueAt(table.getSelectedRow(), 5),
                        (String) table.getValueAt(table.getSelectedRow(), 6));
            }catch (NumberFormatException exc){
                administratorView.showMessage("Incorrect data"); return;
            }
            if (deliveryService.editProduct(baseProduct)) {
                administratorView.showMessage(baseProduct.getTitle() + " edited");
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
            } else administratorView.showMessage("No product edited");
        }
    }
    /**
     * Listener pentru operatia de modificare produs compus din meniu
     */
    public class ModifyCompListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = administratorView.getTableCompositeProduct();
            table.setRowSelectionAllowed(true);
            if(table.getSelectedRow()==-1){
                administratorView.showMessage("No product selected"); return;
            }
            BaseProduct baseProduct;
            try {
                baseProduct = new BaseProduct((String) table.getValueAt(table.getSelectedRow(), 0),
                        (String) table.getValueAt(table.getSelectedRow(), 1),
                        (String) table.getValueAt(table.getSelectedRow(), 2),
                        (String) table.getValueAt(table.getSelectedRow(), 3),
                        (String) table.getValueAt(table.getSelectedRow(), 4),
                        (String) table.getValueAt(table.getSelectedRow(), 5),
                        (String) table.getValueAt(table.getSelectedRow(), 6));
            }catch (NumberFormatException exc){
                administratorView.showMessage("Incorrect data"); return;
            }
            Set<MenuItem> menu=deliveryService.getProducts();
            MenuItem composite=menu.stream().filter(p->p.equals(administratorView.getSelectedComposite()))
                    .findFirst().orElse(null);
            CompositeProduct composite1=(CompositeProduct) composite;
            composite1=editCompositeProduct(composite1,baseProduct);
            if (deliveryService.editProduct(composite1)) {
                administratorView.showMessage(composite1.getTitle() + " edited");
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
            } else administratorView.showMessage("No product edited");
        }

        /**
         * Metoda private de modificare produs compus
         * @param composite1 produs compus
         * @param baseProduct produs de baza
         * @return produs compus
         */
        private CompositeProduct editCompositeProduct(CompositeProduct composite1, BaseProduct baseProduct){
            for(MenuItem menuItem:composite1.getContainedProducts()){
                if(menuItem instanceof CompositeProduct){
                    for(MenuItem menuItem1:((CompositeProduct) menuItem).getContainedProducts()){
                        if(menuItem1 instanceof BaseProduct && menuItem1.equals(baseProduct)){
                            ((CompositeProduct) menuItem).getContainedProducts().remove(menuItem1);
                            ((CompositeProduct) menuItem).getContainedProducts().add(baseProduct);
                            return composite1;
                        }
                    }
                }else if(((BaseProduct)menuItem).equals(baseProduct)){
                        composite1.getContainedProducts().remove(menuItem);
                        composite1.getContainedProducts().add(baseProduct);
                        return composite1;
                    }
            }
           return composite1;
        }
    }
    /**
     * Listener pentru operatia de modificare produs compus din meniu; actualizare inteerfata admin
     */
    public class UpdateCompositeListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            CompositeProduct value=(CompositeProduct) administratorView.getSelectedComposite();
            if(value==null){
                administratorView.showMessage("Not composite products");
            }else {
                MenuItem menuItem = deliveryService.getProducts().stream()
                        .filter(p -> p.equals(value))
                        .findFirst()
                        .orElse(null);
                administratorView.setModifyPanelComposite((CompositeProduct) menuItem);
            }
        }
    }

    /**
     * Listener pentru operatia de sterger produs de baza din meniu
     */
    public class DeleteListener implements ActionListener {
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = administratorView.getTableBaseProduct();
            table.setRowSelectionAllowed(true);
           if(table.getSelectedRow()==-1){
               administratorView.showMessage("No product selected"); return;
           }
            BaseProduct baseProduct = new BaseProduct((String) table.getValueAt(table.getSelectedRow(), 0));
            if (deliveryService.deleteProduct(baseProduct)) {
                administratorView.showMessage(baseProduct.getTitle() + " deleted");
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
            } else administratorView.showMessage("No product deleted");
        }
    }
    /**
     * Listener pentru operatia de sterger produs compus din meniu
     */
    public class DeleteCompListener implements ActionListener{
        /**
         * Metoda suprascrisa
         * @param e actionListener
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            CompositeProduct value=(CompositeProduct)administratorView.getSelectedComposite();
            if(deliveryService.deleteProduct(value)){
                administratorView.showMessage("Successfully deleted");
                administratorView.setModifyPanel(deliveryService);
                administratorView.setCreateProdPanel(deliveryService);
                System.out.println(deliveryService.getProducts().size());
            }
        }
    }
}
