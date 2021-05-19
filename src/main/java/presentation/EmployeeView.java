package presentation;

import businesslogic.MenuItem;
import businesslogic.Order;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Clasa descriere  window employee; Acesta este clasa pentru observer care ca urmari deliveryservice-ul
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public class EmployeeView extends JFrame implements Observer {
    /**
     * Panel pentru orders
     */
    private JPanel ordersPanel=new JPanel();
    /**
     * Txt box in care apar comenzile de procesat
     */
    private JTextArea textField=new JTextArea(900,54);

    /**
     * Constructor
     */
    public EmployeeView(){
        this.setPreferredSize(new Dimension(600,700));
        this.setMinimumSize(new Dimension(600,700));
        this.setLocation(new Point(150,150));

        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.add(new JLabel("Orders to be processed"));
        JScrollPane scrollPane=new JScrollPane(textField);
        textField.setEditable(false);
        ordersPanel.add(scrollPane);
        ordersPanel.setPreferredSize(new Dimension(this.getWidth(),400));

        mainPanel.add(ordersPanel);
        this.setContentPane(mainPanel);
        this.pack();
        this.setTitle("Employee Window");
    }

    /**
     * Impmenetarea metodei update; va actualiza lista comenzilor din interfata
     * @param o observer
     * @param arg argumentul transimi
     */
    @Override
    public void update(Observable o, Object arg) {
        Map<Order, Collection<MenuItem>> orders=(Map<Order, Collection<MenuItem>>)arg;
        Map<Order, Collection<MenuItem>> result = orders.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Set<Map.Entry<Order,Collection<MenuItem>>> entrySet=result.entrySet();

        StringBuilder stringBuilder=new StringBuilder();
        for(Map.Entry<Order,Collection<MenuItem>> entry: entrySet){
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append("\n  ");
            stringBuilder.append(entry.getValue().toString());
            stringBuilder.append("\n--------------------------------------------------------------\n");
        }
        textField.setText(stringBuilder.toString());
    }
}
