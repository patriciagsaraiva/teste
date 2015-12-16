
/*
    TODO: acrescentar a ordem de carga de peças
*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Order {
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
    }

    public static void printOrder(Order o) {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");


        if(o.id == 'T') {
            System.out.println("ID: " + o.id + "  NO: " + o.NO +
                             "  PO, Color: (" + o.PO.id + ", " + o.PO.color +
                            ")  PF, Color: (" + o.PF.id + ", " + o.PF.color +
                            ")  N: " + o.n);
            System.out.println("Entry time: " + formatter.format(o.entry));
        }
        if(o.id == 'C') {
            System.out.println("ID: " + o.id + "  Tapete: " + o.C);
            System.out.println("Entry time: " + formatter.format(o.entry));
        }
    }
}

