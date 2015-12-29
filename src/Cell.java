import java.util.ArrayList;

public class Cell {
    int number;
    boolean isFree;


    public Cell(int id) {
        this.number = id;
        this.isFree = true;
    }

    public static void printMachines() {
        for (int i = 0; i < Manager.cellList.size(); i++) {
            System.out.print("Cell " + (i+1) + ": ");
            for (int j = 0; j < Manager.machineList.size(); j++) {
                if(Manager.machineList.get(j).cell.number == i+1) {
                    System.out.print(Manager.machineList.get(j).type + " ");
                }
            }
            System.out.print("\n");
        }
    }
}
