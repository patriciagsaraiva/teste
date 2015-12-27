import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GUI extends JFrame implements Runnable {
    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    int orderNumber = -1;

    public synchronized void run() {
        JFrame ordersFrame = new JFrame("Ordens");
        JFrame machinesFrame = new JFrame("Maquinas");

        // Change window close option to minimize
        ordersFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        machinesFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ordersFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ordersFrame.setExtendedState(Frame.ICONIFIED);
            }
        });
        machinesFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                machinesFrame.setExtendedState(Frame.ICONIFIED);
            }
        });

        ordersFrame.setSize(600, 350);
        machinesFrame.setSize(600, 350);

        // CREATING THE MENU

        JMenuBar menuBar = new JMenuBar();
        JMenu view = new JMenu("Ver");
        menuBar.add(view);

        JMenuItem orders = new JMenuItem("Ordens");
        view.add(orders);
        JMenuItem machines = new JMenuItem("Maquinas");
        view.add(machines);


        // CREATING THE LAYOUT

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel title1 = new JLabel("Ordens");
        c.insets.top = 10;
        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 0;
        mainPanel.add(title1, c);
        c.gridwidth = 1;


        JLabel l1 = new JLabel("NO");
        c.gridx = 0; c.gridy = 1;
        mainPanel.add(l1, c);
        JLabel v1 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 1;
        mainPanel.add(v1, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l2 = new JLabel("Tipo");
        c.gridx = 0; c.gridy = 2;
        mainPanel.add(l2, c);
        JLabel v2 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 2;
        mainPanel.add(v2, c);
        c.insets.left = 0;

        JLabel l3 = new JLabel("P. Inicial");
        c.gridx = 0; c.gridy = 3;
        mainPanel.add(l3, c);
        JLabel v3 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 3;
        mainPanel.add(v3, c);
        c.insets.left = 0;

        JLabel l4 = new JLabel("P. Final:");
        c.gridx = 0; c.gridy = 4;
        mainPanel.add(l4, c);
        JLabel v4 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 4;
        mainPanel.add(v4, c);
        c.insets.left = 0;

        JLabel l5 = new JLabel("Quantidade");
        c.gridx = 0; c.gridy = 5;
        mainPanel.add(l5, c);
        JLabel v5 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 5;
        mainPanel.add(v5, c);
        c.insets.left = 0;

        JLabel l6 = new JLabel("Estado");
        c.gridx = 0; c.gridy = 6;
        c.insets.top = 10;
        mainPanel.add(l6, c);
        JLabel v6 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 6;
        mainPanel.add(v6, c);
        c.insets.top = 0;
        c.insets.left = 0;

        JLabel l7 = new JLabel("Entrada");
        c.gridx = 0; c.gridy = 7;
        mainPanel.add(l7, c);
        JLabel v7 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 7;
        mainPanel.add(v7, c);
        c.insets.left = 0;

        JLabel l8 = new JLabel("Inicio");
        c.gridx = 0; c.gridy = 8;
        mainPanel.add(l8, c);
        JLabel v8 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 8;
        mainPanel.add(v8, c);
        c.insets.left = 0;

        JLabel l9 = new JLabel("Fim:");
        c.gridx = 0; c.gridy = 9;
        mainPanel.add(l9, c);
        JLabel v9 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 9;
        mainPanel.add(v9, c);
        c.insets.left = 0;

        JLabel l10 = new JLabel("Prontas");
        c.gridx = 0; c.gridy = 10;
        c.insets.top = 10;
        mainPanel.add(l10, c);
        JLabel v10 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 10;
        mainPanel.add(v10, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l11 = new JLabel("A Processar");
        c.gridx = 0; c.gridy = 11;
        mainPanel.add(l11, c);
        JLabel v11 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 11;
        mainPanel.add(v11, c);
        c.insets.left = 0;

        JLabel l12 = new JLabel("Em Espera");
        c.gridx = 0; c.gridy = 12;
        mainPanel.add(l12, c);
        JLabel v12 = new JLabel("/");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 12;
        mainPanel.add(v12, c);
        c.insets.left = 0;

        JButton b1 = new JButton("<");
        c.insets.top = 10;
        c.gridx = 0; c.gridy = 13;
        mainPanel.add(b1, c);
        JButton b2 = new JButton(">");
        c.insets.left = 5;
        c.gridx = 1; c.gridy = 13;
        mainPanel.add(b2, c);
        c.insets.top = 0; c.insets.left = 0;


        ordersFrame.setJMenuBar(menuBar);
        ordersFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        ordersFrame.setVisible(true);

        orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                machinesFrame.dispose();
                ordersFrame.setJMenuBar(menuBar);
                ordersFrame.setVisible(true);

            }
        });
        machines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ordersFrame.dispose();
                machinesFrame.setJMenuBar(menuBar);
                machinesFrame.setVisible(true);
            }
        });
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderNumber--;
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                orderNumber++;
            }
        });

        while(true) {
            try { Thread.sleep(50); } catch (InterruptedException e) { e.printStackTrace(); }
            if(orderNumber == -1){
                b1.setVisible(false);
                b2.setVisible(false);
            }

            if(Manager.orderList.size() > 0) {
                // SETUP THE NAVIGATION

                if(orderNumber == -1) {
                    orderNumber = 0;
                    b2.setVisible(true);
                }
                else {
                    b1.setVisible(true);
                    b2.setVisible(true);
                }
                if(orderNumber == 0)
                    b1.setVisible(false);
                if(orderNumber == (Manager.orderList.size()-1) )
                    b2.setVisible(false);

                // UPDATE THE FIELDS

                switch (Manager.orderList.get(orderNumber).id) {
                    case 'T':
                        l1.setText("NO"); l3.setText("P. Inicial"); l4.setText("P. Final");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).PO.id));
                        v4.setText(Integer.toString(Manager.orderList.get(orderNumber).PF.id));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v8.setText(formatter.format(Manager.orderList.get(orderNumber).init));
                        } else v8.setText("/");
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v9.setText(formatter.format(Manager.orderList.get(orderNumber).done));
                        } else v9.setText("/");
                        v10.setText(Integer.toString(Manager.orderList.get(orderNumber).ready));
                        v11.setText(Integer.toString(Manager.orderList.get(orderNumber).processing));
                        v12.setText(Integer.toString(Manager.orderList.get(orderNumber).pending));
                        break;
                    case 'M':
                        l1.setText("NO"); l3.setText("P. Baixo"); l4.setText("P. Cima");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).PB.id));
                        v4.setText(Integer.toString(Manager.orderList.get(orderNumber).PC.id));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v8.setText(formatter.format(Manager.orderList.get(orderNumber).init));
                        } else v8.setText("/");
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v9.setText(formatter.format(Manager.orderList.get(orderNumber).done));
                        } else v9.setText("/");
                        v10.setText(Integer.toString(Manager.orderList.get(orderNumber).ready));
                        v11.setText(Integer.toString(Manager.orderList.get(orderNumber).processing));
                        v12.setText(Integer.toString(Manager.orderList.get(orderNumber).pending));
                        break;
                    case 'U':
                        l1.setText("NO"); l3.setText("P. Origem"); l4.setText("Destino");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).P.id));
                        v4.setText("PM" + Integer.toString(Manager.orderList.get(orderNumber).D));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v8.setText(formatter.format(Manager.orderList.get(orderNumber).init));
                        } else v8.setText("/");
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v9.setText(formatter.format(Manager.orderList.get(orderNumber).done));
                        } else v9.setText("/");
                        v10.setText(Integer.toString(Manager.orderList.get(orderNumber).ready));
                        v11.setText(Integer.toString(Manager.orderList.get(orderNumber).processing));
                        v12.setText(Integer.toString(Manager.orderList.get(orderNumber).pending));
                        break;
                    case 'C':
                        l1.setText("Tapete"); l3.setText("P. Inicial"); l4.setText("P. Final");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).C));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v8.setText(formatter.format(Manager.orderList.get(orderNumber).init));
                        } else v8.setText("/");
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v9.setText(formatter.format(Manager.orderList.get(orderNumber).done));
                        } else v9.setText("/");
                        v3.setText("/"); v4.setText("/"); v5.setText("/");
                        v10.setText("/"); v11.setText("/"); v12.setText("/");
                        break;
                }

                // VALIDATE CHANGES

                ordersFrame.validate();
                ordersFrame.repaint();
            }
        }
    }
}
