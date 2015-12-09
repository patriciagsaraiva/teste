
/*
    TODO: acrescentar a ordem de carga de peças
*/

public class Order {
    char id;                            // piece id
    int NO;                             // piece order number
    int n;                              // quantity to produce
    int PO, PB, P;                      // origin piece
    int PF, PC;                         // final piece
    int D;                              // destination
    String state;                       // state of the order (pending, processing, done
    int ready, processing, pending;     // number or pieces processed, in processing or pending
    long entry, init, done;             // time values associated with the order


    public Order() {
        char id = 0;
        int NO = 0;
        int n = 0;
        int PO = 0, PB = 0, P = 0;
        int PF = 0, PC = 0;
        int D = 0;
        String state = new String();
        int ready = 0, processing = 0, pending = 0;
        long entry = 0, init = 0, done = 0;
    }
}

