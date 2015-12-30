import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GUI extends JFrame implements Runnable {
    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatterS = new SimpleDateFormat("mm:ss");
    int orderNumber = -1;
    int machineNumber = 0;

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


        // CREATING THE LAYOUT FOR THE ORDERS

        JPanel mainPanelOrders = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel title1 = new JLabel("ORDEM");
        c.insets.top = 10;
        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 0;
        mainPanelOrders.add(title1, c);
        c.gridwidth = 1;

        JLabel l1 = new JLabel("NO");
        c.gridx = 0; c.gridy = 1;
        mainPanelOrders.add(l1, c);
        JLabel v1 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 1;
        mainPanelOrders.add(v1, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l2 = new JLabel("Tipo");
        c.gridx = 0; c.gridy = 2;
        mainPanelOrders.add(l2, c);
        JLabel v2 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 2;
        mainPanelOrders.add(v2, c);
        c.insets.left = 0;

        JLabel l3 = new JLabel("P. Inicial");
        c.gridx = 0; c.gridy = 3;
        mainPanelOrders.add(l3, c);
        JLabel v3 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 3;
        mainPanelOrders.add(v3, c);
        c.insets.left = 0;

        JLabel l4 = new JLabel("P. Final");
        c.gridx = 0; c.gridy = 4;
        mainPanelOrders.add(l4, c);
        JLabel v4 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 4;
        mainPanelOrders.add(v4, c);
        c.insets.left = 0;

        JLabel l5 = new JLabel("Quantidade");
        c.gridx = 0; c.gridy = 5;
        mainPanelOrders.add(l5, c);
        JLabel v5 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 5;
        mainPanelOrders.add(v5, c);
        c.insets.left = 0;

        JLabel l6 = new JLabel("Estado");
        c.gridx = 0; c.gridy = 6;
        c.insets.top = 10;
        mainPanelOrders.add(l6, c);
        JLabel v6 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 6;
        mainPanelOrders.add(v6, c);
        c.insets.top = 0;
        c.insets.left = 0;

        JLabel l7 = new JLabel("Entrada");
        c.gridx = 0; c.gridy = 7;
        mainPanelOrders.add(l7, c);
        JLabel v7 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 7;
        mainPanelOrders.add(v7, c);
        c.insets.left = 0;

        JLabel l8 = new JLabel("Inicio");
        c.gridx = 0; c.gridy = 8;
        mainPanelOrders.add(l8, c);
        JLabel v8 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 8;
        mainPanelOrders.add(v8, c);
        c.insets.left = 0;

        JLabel l9 = new JLabel("Fim");
        c.gridx = 0; c.gridy = 9;
        mainPanelOrders.add(l9, c);
        JLabel v9 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 9;
        mainPanelOrders.add(v9, c);
        c.insets.left = 0;

        JLabel l10 = new JLabel("Prontas");
        c.gridx = 0; c.gridy = 10;
        c.insets.top = 10;
        mainPanelOrders.add(l10, c);
        JLabel v10 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 10;
        mainPanelOrders.add(v10, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l11 = new JLabel("A Processar");
        c.gridx = 0; c.gridy = 11;
        mainPanelOrders.add(l11, c);
        JLabel v11 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 11;
        mainPanelOrders.add(v11, c);
        c.insets.left = 0;

        JLabel l12 = new JLabel("Em Espera");
        c.gridx = 0; c.gridy = 12;
        mainPanelOrders.add(l12, c);
        JLabel v12 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 12;
        mainPanelOrders.add(v12, c);
        c.insets.left = 0;

        JButton b1 = new JButton("<");
        c.insets.top = 10;
        c.gridx = 0; c.gridy = 13;
        mainPanelOrders.add(b1, c);
        JButton b2 = new JButton(">");
        c.insets.left = 0;
        c.gridx = 1; c.gridy = 13;
        mainPanelOrders.add(b2, c);
        c.insets.top = 0; c.insets.left = 0;

        // CREATE LAYOUT FOR GENERAL ORDER STATISTICS

        JLabel title2 = new JLabel("GERAL");
        c.insets.top = 10; c.insets.left = 200;
        c.gridwidth = 2;
        c.gridx = 2; c.gridy = 0;
        mainPanelOrders.add(title2, c);
        c.gridwidth = 1;

        JLabel subtitle1 = new JLabel("---| Ordens |---");
        c.insets.left = 200;
        c.gridwidth = 2;
        c.gridx = 2; c.gridy = 1;
        mainPanelOrders.add(subtitle1, c);
        c.gridwidth = 1; c.insets.top = 0;

        JLabel l13 = new JLabel("A Processar");
        c.gridx = 2; c.gridy = 2;
        c.insets.left = 200;
        mainPanelOrders.add(l13, c);
        JLabel v13 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 2;
        mainPanelOrders.add(v13, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l14 = new JLabel("Pendentes");
        c.gridx = 2; c.gridy = 3;
        c.insets.left = 200;
        mainPanelOrders.add(l14, c);
        JLabel v14 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 3;
        mainPanelOrders.add(v14, c);
        c.insets.left = 0;

        JLabel l15 = new JLabel("Prontas");
        c.gridx = 2; c.gridy = 4;
        c.insets.left = 200;
        mainPanelOrders.add(l15, c);
        JLabel v15 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 4;
        mainPanelOrders.add(v15, c);
        c.insets.left = 0;

        JLabel space = new JLabel("");
        c.insets.left = 10;
        c.gridwidth = 2;
        c.gridx = 2; c.gridy = 5;
        mainPanelOrders.add(space, c);
        c.insets.left = 0; c.gridwidth = 0;

        JLabel subtitle2 = new JLabel("---| Pecas |---");
        c.insets.left = 200; c.insets.top = 10;
        c.gridwidth = 2;
        c.gridx = 2; c.gridy = 6;
        mainPanelOrders.add(subtitle2, c);
        c.gridwidth = 1; c.insets.top = 0;

        JLabel l16 = new JLabel("A Processar");
        c.gridx = 2; c.gridy = 7;
        c.insets.left = 200;
        mainPanelOrders.add(l16, c);
        JLabel v16 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 7;
        mainPanelOrders.add(v16, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel l17 = new JLabel("Em espera");
        c.gridx = 2; c.gridy = 8;
        c.insets.left = 200;
        mainPanelOrders.add(l17, c);
        JLabel v17 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 8;
        mainPanelOrders.add(v17, c);
        c.insets.left = 0;

        JLabel l18 = new JLabel("Terminadas");
        c.gridx = 2; c.gridy = 9;
        c.insets.left = 200;
        mainPanelOrders.add(l18, c);
        JLabel v18 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 3; c.gridy = 9;
        mainPanelOrders.add(v18, c);
        c.insets.left = 0;


        // CREATING THE LAYOUT FOR THE MACHINES

        JPanel mainPanelMachines = new JPanel(new GridBagLayout());

        JLabel Mtitle1 = new JLabel("MAQUINA");
        c.insets.top = 10;
        c.gridwidth = 2;
        c.gridx = 0; c.gridy = 0;
        mainPanelMachines.add(Mtitle1, c);
        c.gridwidth = 1;

        JLabel Ml1 = new JLabel("Tipo (celula)");
        c.gridx = 0; c.gridy = 1;
        mainPanelMachines.add(Ml1, c);
        JLabel Mv1 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 1;
        mainPanelMachines.add(Mv1, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel Ml2 = new JLabel("T. Operacao ");
        c.gridx = 0; c.gridy = 2;
        mainPanelMachines.add(Ml2, c);
        JLabel Mv2 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 2;
        mainPanelMachines.add(Mv2, c);
        c.insets.left = 0;

        JLabel Ml3 = new JLabel("P. Operadas ");
        c.gridx = 0; c.gridy = 3;
        mainPanelMachines.add(Ml3, c);
        JLabel Mv3 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 3;
        mainPanelMachines.add(Mv3, c);
        c.insets.left = 0;

        JLabel Ml4 = new JLabel("P1 ");
        c.gridx = 0; c.gridy = 4;
        c.insets.top = 10;
        mainPanelMachines.add(Ml4, c);
        JLabel Mv4 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 4;
        mainPanelMachines.add(Mv4, c);
        c.insets.top = 0; c.insets.left = 0;

        JLabel Ml5 = new JLabel("P2 ");
        c.gridx = 0; c.gridy = 5;
        mainPanelMachines.add(Ml5, c);
        JLabel Mv5 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 5;
        mainPanelMachines.add(Mv5, c);
        c.insets.left = 0;

        JLabel Ml6 = new JLabel("P3 ");
        c.gridx = 0; c.gridy = 6;
        mainPanelMachines.add(Ml6, c);
        JLabel Mv6 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 6;
        mainPanelMachines.add(Mv6, c);
        c.insets.left = 0;

        JLabel Ml7 = new JLabel("P4 ");
        c.gridx = 0; c.gridy = 7;
        mainPanelMachines.add(Ml7, c);
        JLabel Mv7 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 7;
        mainPanelMachines.add(Mv7, c);
        c.insets.left = 0;

        JLabel Ml8 = new JLabel("P5 ");
        c.gridx = 0; c.gridy = 8;
        mainPanelMachines.add(Ml8, c);
        JLabel Mv8 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 8;
        mainPanelMachines.add(Mv8, c);
        c.insets.left = 0;

        JLabel Ml9 = new JLabel("P6 ");
        c.gridx = 0; c.gridy = 9;
        mainPanelMachines.add(Ml9, c);
        JLabel Mv9 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 9;
        mainPanelMachines.add(Mv9, c);
        c.insets.left = 0;

        JLabel Ml10 = new JLabel("P7 ");
        c.gridx = 0; c.gridy = 10;
        mainPanelMachines.add(Ml10, c);
        JLabel Mv10 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 10;
        mainPanelMachines.add(Mv10, c);
        c.insets.left = 0;

        JLabel Ml11 = new JLabel("P8 ");
        c.gridx = 0; c.gridy = 11;
        mainPanelMachines.add(Ml11, c);
        JLabel Mv11 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 11;
        mainPanelMachines.add(Mv11, c);
        c.insets.left = 0;

        JLabel Ml12 = new JLabel("P9 ");
        c.gridx = 0; c.gridy = 12;
        mainPanelMachines.add(Ml12, c);
        JLabel Mv12 = new JLabel("/");
        c.insets.left = 10;
        c.gridx = 1; c.gridy = 12;
        mainPanelMachines.add(Mv12, c);
        c.insets.left = 0;

        JButton Mb1 = new JButton("<");
        c.insets.top = 10;
        c.gridx = 0; c.gridy = 13;
        mainPanelMachines.add(Mb1, c);
        JButton Mb2 = new JButton(">");
        c.insets.left = 0;
        c.gridx = 1; c.gridy = 13;
        mainPanelMachines.add(Mb2, c);
        c.insets.top = 0; c.insets.left = 0;


        // GENERATE THE LAYOUT

        ordersFrame.setJMenuBar(menuBar);
        ordersFrame.getContentPane().add(mainPanelOrders, BorderLayout.NORTH);
        machinesFrame.getContentPane().add(mainPanelMachines, BorderLayout.NORTH);
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
        Mb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                machineNumber--;
            }
        });
        Mb2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                machineNumber++;
            }
        });

        // MAIN CYCLE

        while(true) {
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*-------------------------------- ORDERS -------------------------------*/
            if (orderNumber == -1) {
                b1.setVisible(false);
                b2.setVisible(false);
            }

            // UPDATE THE GENERAL FIELDS

            v13.setText(Integer.toString(Order.getProcessing()));
            v14.setText(Integer.toString(Order.getPending()));
            v15.setText(Integer.toString(Order.getReady()));

            v16.setText(Integer.toString(Order.getPiecesProcessing()));
            v17.setText(Integer.toString(Order.getPiecesPending()));
            v18.setText(Integer.toString(Order.getPiecesReady()));


            // WHEN THERE IS AT LEAST 1 ORDER

            if (Manager.orderList.size() > 0) {
                // SETUP THE NAVIGATION

                if (orderNumber == -1) {
                    orderNumber = 0;
                    b2.setVisible(true);
                } else {
                    b1.setVisible(true);
                    b2.setVisible(true);
                }
                if (orderNumber == 0)
                    b1.setVisible(false);
                if (orderNumber == (Manager.orderList.size() - 1))
                    b2.setVisible(false);

                // UPDATE THE ORDER FIELDS

                switch (Manager.orderList.get(orderNumber).id) {
                    case 'T':
                        l1.setText("NO");
                        l3.setText("P. Inicial");
                        l4.setText("P. Final");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).PO.id));
                        v4.setText(Integer.toString(Manager.orderList.get(orderNumber).PF.id));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).init != 0) {
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
                        l1.setText("NO");
                        l3.setText("P. Baixo");
                        l4.setText("P. Cima");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).PB.id));
                        v4.setText(Integer.toString(Manager.orderList.get(orderNumber).PC.id));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).init != 0) {
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
                        l1.setText("NO");
                        l3.setText("P. Origem");
                        l4.setText("Destino");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).NO));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v3.setText(Integer.toString(Manager.orderList.get(orderNumber).P.id));
                        v4.setText("PM" + Integer.toString(Manager.orderList.get(orderNumber).D));
                        v5.setText(Integer.toString(Manager.orderList.get(orderNumber).n));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).init != 0) {
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
                        l1.setText("Tapete");
                        l3.setText("P. Inicial");
                        l4.setText("P. Final");
                        v1.setText(Integer.toString(Manager.orderList.get(orderNumber).C));
                        v2.setText(Character.toString(Manager.orderList.get(orderNumber).id));
                        v6.setText(Manager.orderList.get(orderNumber).state);
                        v7.setText(formatter.format(Manager.orderList.get(orderNumber).entry));
                        if (Manager.orderList.get(orderNumber).init != 0) {
                            v8.setText(formatter.format(Manager.orderList.get(orderNumber).init));
                        } else v8.setText("/");
                        if (Manager.orderList.get(orderNumber).done != 0) {
                            v9.setText(formatter.format(Manager.orderList.get(orderNumber).done));
                        } else v9.setText("/");
                        v3.setText("/");
                        v4.setText("/");
                        v5.setText("/");
                        v10.setText("/");
                        v11.setText("/");
                        v12.setText("/");
                        break;
                }

                // VALIDATE CHANGES

                ordersFrame.validate();
                ordersFrame.repaint();
            }

            /*-------------------------------- ORDERS -------------------------------*/

            Mb1.setVisible(true);
            Mb2.setVisible(true);

            if (machineNumber == 0)
                Mb1.setVisible(false);
            if (machineNumber == (Manager.machineList.size()-1))
                Mb2.setVisible(false);

            // UPDATE THE FIELDS
            Mv1.setText(Manager.machineList.get(machineNumber).type + " (" + Integer.toString(Manager.machineList.get(machineNumber).cell.number) + ")");
            if (Manager.machineList.get(machineNumber).operationTime != 0) {
                Mv2.setText(formatterS.format(Manager.machineList.get(machineNumber).operationTime));
            } else Mv2.setText("0");
            Mv3.setText(Integer.toString(Machine.totalPieces(Manager.machineList.get(machineNumber))));
            Mv4.setText(Integer.toString(Manager.machineList.get(machineNumber).p[0]));
            Mv5.setText(Integer.toString(Manager.machineList.get(machineNumber).p[1]));
            Mv6.setText(Integer.toString(Manager.machineList.get(machineNumber).p[2]));
            Mv7.setText(Integer.toString(Manager.machineList.get(machineNumber).p[3]));
            Mv8.setText(Integer.toString(Manager.machineList.get(machineNumber).p[4]));
            Mv9.setText(Integer.toString(Manager.machineList.get(machineNumber).p[5]));
            Mv10.setText(Integer.toString(Manager.machineList.get(machineNumber).p[6]));
            Mv11.setText(Integer.toString(Manager.machineList.get(machineNumber).p[7]));
            Mv12.setText(Integer.toString(Manager.machineList.get(machineNumber).p[8]));


            // VALIDATE CHANGES

            machinesFrame.validate();
            machinesFrame.repaint();
        }
    }
}
