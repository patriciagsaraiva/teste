
/*
    TODO: acrescentar a ordem de carga de peças
*/

public class Order {
    char id;                            // piece id
    int NO;                             // piece order number
    int n;                              // quantity to produce
    Piece PO, PB, P;                    // origin piece
    Piece PF, PC;                       // final piece
    int D;                              // destination
    String state;                       // state of the order (pending, processing, done
    int ready, processing, pending;     // number or pieces processed, in processing or pending
    long entry, init, done;             // time values associated with the order


    public Order() {
        this.id = 0;
        this.NO = 0;
        this.n = 0;
        this.PO = null; this.PB = null; this.P = null;
        this.PF = null; this.PC = null;
        this.D = 0;
        this.state = "pendente";
        this.ready = 0; this.processing = 0; this.pending = 0;
        this.entry = 0; this.init = 0; this.done = 0;
    }
}

