import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Order implements Runnable {
    char id;                            // piece id
    int NO;                             // piece order number
    int n;                              // quantity to produce
    Piece PO, PB, P;                    // origin piece
    Piece PF, PC;                       // final piece
    int D;                              // destination
    int C;                              // conveyor (from load piece)
    String state;                       // state of the order (pending, processing, done)
    int ready, processing, pending;     // number or pieces processed, in processing or pending
    long entry, init, done;             // time values associated with the order
    int cell;                           // number of the cell which will deal with the order


    public void run() {
        int init = 0;
        int cmd;
        long startTime = 0; // timer to control time difference between sending piece and it appearing on sensor
        this.state = "a processar";
        this.init = System.currentTimeMillis();
        switch  (this.id) {
            case 'T':
                cmd = getCommand(this);
                while (this.pending != 0) {
                    // CHECK IF PLANT IS READY TO RECEIVE PIECE
                    if (init == 0 && Manager.checkIfFree(Manager.sensors, Manager.coils, init)) {
                        init++;
                        tcpMaster.sendPiece(Manager.con_plant, Manager.con_plc, this.PO.id, cmd);
                        System.out.println("Piece sent [order " + this.NO + "]");
                    }
                    else if (init == 1 && Manager.sensors.getBit(0)) {
                        init--;
                        updatePieces(this);
                        tcpMaster.resetPiece(Manager.con_plant, 0, 0);
                    }
                }
                break;
            case 'M':
                int piece = 0;
                while (this.pending != 0) {
                    // CHECK IF PLANT IS READY TO RECEIVE PIECE
                    if (init == 0 && Manager.checkIfFree(Manager.sensors, Manager.coils, init)) {
                        init++;
                        if(piece == 0)
                            tcpMaster.sendPiece(Manager.con_plant, Manager.con_plc, this.PC.id, 20);
                        else if(piece == 1)
                            tcpMaster.sendPiece(Manager.con_plant, Manager.con_plc, this.PB.id, 19);
                        System.out.println("Piece sent [order " + this.NO + "]");
                    }
                    else if (init == 1 && Manager.sensors.getBit(0)) {
                        init--;
                        piece++;
                        updatePieces(this);
                        tcpMaster.resetPiece(Manager.con_plant, 0, 0);
                    }
                }
                break;
            case 'U':
                cmd = 21;
                while (this.pending != 0) {
                    // CHECK IF PLANT IS READY TO RECEIVE PIECE
                    if (init == 0 && Manager.checkIfFree(Manager.sensors, Manager.coils, init)) {
                        init++;
                        if(this.D == 1)
                            cmd = 21;
                        else if(this.D == 2)
                            cmd = 22;
                        tcpMaster.sendPiece(Manager.con_plant, Manager.con_plc, this.P.id, cmd);
                        System.out.println("Piece sent [order " + this.NO + "]");
                    }
                    else if (init == 1 && Manager.sensors.getBit(0)) {
                        init--;
                        updatePieces(this);
                        tcpMaster.resetPiece(Manager.con_plant, 0, 0);
                    }
                }
                break;
        }
        this.done = System.currentTimeMillis();
        System.out.println("No more pieces pending in Order " + this.NO + ".");

    }

    public Order() {
        this.id = 0;
        this.NO = 0;
        this.n = 0;
        this.PO = null; this.PB = null; this.P = null;
        this.PF = null; this.PC = null;
        this.D = 0;
        this.C = -1;
        this.state = "pendente";
        this.ready = 0; this.processing = 0; this.pending = 0;
        this.entry = 0; this.init = 0; this.done = 0;
        this.cell = -1;
    }

    public static void printOrder(Order o) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        switch (o.id) {
            case 'T':
                System.out.println("ID: " + o.id + "  NO: " + o.NO +
                        "  PO, Color: (" + o.PO.id + ", " + o.PO.color +
                        ")  PF, Color: (" + o.PF.id + ", " + o.PF.color +
                        ")  Qtd: " + o.n);
                System.out.println("Entry time: " + formatter.format(o.entry));
                break;
            case 'M':
                System.out.println("ID: " + o.id + "  NO: " + o.NO +
                        "  PB, Color: (" + o.PB.id + ", " + o.PB.color +
                        ")  Pc, Color: (" + o.PC.id + ", " + o.PC.color +
                        ")  Qtd: " + o.n);
                System.out.println("Entry time: " + formatter.format(o.entry));
                break;
            case 'U':
                System.out.println("ID: " + o.id + "  NO: " + o.NO +
                        "  P, Color: (" + o.P.id + ", " + o.P.color +
                        ")  D: PM" + o.D +
                        "  Qtd: " + o.n);
                System.out.println("Entry time: " + formatter.format(o.entry));
                break;
            case 'C':
                System.out.println("ID: " + o.id + "  Tapete: " + o.C);
                System.out.println("Entry time: " + formatter.format(o.entry));
                break;
        }
    }
    public static int getProcessing() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
            if(Manager.orderList.get(i).state == "a processar") {
                n++;
            }
        }

        return n;
    }
    public static int getPending() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
            if(Manager.orderList.get(i).state == "pendente") {
                n++;
            }
        }

        return n;
    }
    public static int getReady() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
            if(Manager.orderList.get(i).state == "pronta") {
                n++;
            }
        }

        return n;
    }
    public static int getPiecesProcessing() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
            n += Manager.orderList.get(i).processing;
        }

        return n;
    }
    public static int getPiecesPending() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
            n += Manager.orderList.get(i).pending;
        }

        return n;
    }
    public static int getPiecesReady() {
        int n = 0;

        for (int i = 0; i < Manager.orderList.size(); i++) {
                n += Manager.orderList.get(i).ready;
        }

        return n;
    }
    private static void updatePieces(Order o) {
        o.pending--;
        o.processing++;
    }
    public static int getCommand(Order order) {
        int cmd = 0;

        if(order.PO.id == 1)
            switch(order.PF.id) {
                case 2:
                    return 1;
                case 3:
                    return 2;
                case 4:
                    return 3;
                case 5:
                    return 4;
                case 6:
                    return 5;
                case 7:
                    return 6;
                case 8:
                    return 7;
                case 9:
                    return 8;
            }

        else if(order.PO.id == 2)
            switch (order.PF.id) {
                case 3:
                    return 9;
                case 4:
                    return 10;
            }

        else if(order.PO.id == 3)
            return 11;

        else if(order.PO.id == 5)
            switch (order.PF.id) {
                case 6:
                    return 12;
                case 7:
                    return 13;
                case 8:
                    return 14;
                case 9:
                    return 15;
            }

        else if(order.PO.id == 6)
            return 16;

        else if(order.PO.id == 8)
            switch (order.PF.id) {
                case 7:
                    return 17;
                case 9:
                    return 18;
            }

        return cmd;
    }
}


/* COMANDOS PARA O PLC
1:     1 -> 2
2:     1 -> 3
3:     1 -> 4
4:     1 -> 5
5:     1 -> 6
6:     1 -> 7
7:     1 -> 8
8:     1 -> 9
9:     2 -> 3
10:    2 -> 4
11:    3 -> 4
12:    5 -> 6
13:    5 -> 7
14:    5 -> 8
15:    5 -> 9
16:    6 -> 7
17:    8 -> 7
18:    8 -> 9
19:    peça de montagem (baixo)
20:    peça de montagem (cima)
21:    descarga (tapete 1)
22:    descarga (tapete 2)
*/
