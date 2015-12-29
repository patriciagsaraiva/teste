
public class Machine {
    Cell cell;              // cell in which the machine is
    String type;
    boolean isFree;
    long operationTime;
    int p[];                // number of operated pieces

    public Machine(String type, int id) {
        this.cell = new Cell(id);
        this.type = type;
        this.isFree = true;
        operationTime = 0;
        p = new int[9];
        for (int i = 0; i < 9; i++) {
            p[i] = 0;
        }
    }

    public static int totalPieces(Machine m) {
        int total = 0;
        for (int i = 0; i < 9; i++) {
            total += m.p[i];
        }
        return total;
    }
}
