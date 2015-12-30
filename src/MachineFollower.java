
public class MachineFollower implements Runnable {
    int m1Working, m2Working, m3Working, m4Working, m5Working, m6Working, m7Working, m8Working;
    long m1Timer, m2Timer, m3Timer, m4Timer, m5Timer, m6Timer, m7Timer, m8Timer;

    public MachineFollower () {
        this.m1Working = 0;
        this.m2Working = 0;
        this.m3Working = 0;
        this.m4Working = 0;
        this.m5Working = 0;
        this.m6Working = 0;
        this.m7Working = 0;
        this.m8Working = 0;
    }

    public void run() {
        int type = 0;
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            // --------------------- Machine 1 ------------------------------
            if (m1Working == 0 && Manager.coils.getBit(21)) {
                m1Working = 1;

                m1Timer = System.currentTimeMillis();
                System.out.println("Machine 1 timer initiated");
                type = Manager.inputVariables.getRegisterValue(72);
            } else if (m1Working == 1 && !Manager.coils.getBit(21)) {
                m1Working = 0;
                Manager.machineList.get(0).operationTime += System.currentTimeMillis() - m1Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(0).operationTime);
                Manager.machineList.get(0).p[type]++;
            }

            // --------------------- Machine 2 ------------------------------
            if (m2Working == 0 && Manager.coils.getBit(30)) {
                m2Working = 1;

                m2Timer = System.currentTimeMillis();
                System.out.println("Machine 2 timer initiated");
                type = Manager.inputVariables.getRegisterValue(73);
            } else if (m2Working == 1 && !Manager.coils.getBit(30)) {
                m2Working = 0;
                Manager.machineList.get(1).operationTime += System.currentTimeMillis() - m2Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(1).operationTime);
                //System.out.println("teste: type=" + type);
                Manager.machineList.get(1).p[type]++;
            }

            // --------------------- Machine 3 ------------------------------
            if (m3Working == 0 && Manager.coils.getBit(57)) {
                m3Working = 1;

                m3Timer = System.currentTimeMillis();
                System.out.println("Machine 3 timer initiated");
                type = Manager.inputVariables.getRegisterValue(81);
            } else if (m3Working == 1 && !Manager.coils.getBit(57)) {
                m3Working = 0;
                Manager.machineList.get(2).operationTime += System.currentTimeMillis() - m3Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(2).operationTime);
                Manager.machineList.get(2).p[type]++;
            }

            // --------------------- Machine 4 ------------------------------
            if (m4Working == 0 && Manager.coils.getBit(68)) {
                m4Working = 1;

                m4Timer = System.currentTimeMillis();
                System.out.println("Machine 4 timer initiated");
                type = Manager.inputVariables.getRegisterValue(83);
            } else if (m4Working == 1 && !Manager.coils.getBit(68)) {
                m4Working = 0;
                Manager.machineList.get(3).operationTime += System.currentTimeMillis() - m4Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(3).operationTime);
                Manager.machineList.get(3).p[type]++;
            }

            // --------------------- Machine 5 ------------------------------
            if (m5Working == 0 && Manager.coils.getBit(89)) {
                m5Working = 1;

                m5Timer = System.currentTimeMillis();
                System.out.println("Machine 5 timer initiated");
                type = Manager.inputVariables.getRegisterValue(88);
            } else if (m5Working == 1 && !Manager.coils.getBit(89)) {
                m5Working = 0;
                Manager.machineList.get(4).operationTime += System.currentTimeMillis() - m5Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(4).operationTime);
                Manager.machineList.get(4).p[type]++;
            }

            // --------------------- Machine 6 ------------------------------
            if (m6Working == 0 && Manager.coils.getBit(100)) {
                m6Working = 1;

                m6Timer = System.currentTimeMillis();
                System.out.println("Machine 6 timer initiated");
                type = Manager.inputVariables.getRegisterValue(90);
            } else if (m6Working == 1 && !Manager.coils.getBit(100)) {
                m6Working = 0;
                Manager.machineList.get(5).operationTime += System.currentTimeMillis() - m6Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(5).operationTime);
                Manager.machineList.get(5).p[type]++;
            }

            // --------------------- Machine 7 ------------------------------
            if (m7Working == 0 && Manager.coils.getBit(121)) {
                m7Working = 1;

                m7Timer = System.currentTimeMillis();
                System.out.println("Machine 7 timer initiated");
                type = Manager.inputVariables.getRegisterValue(95);
            } else if (m7Working == 1 && !Manager.coils.getBit(121)) {
                m7Working = 0;
                Manager.machineList.get(6).operationTime += System.currentTimeMillis() - m7Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(6).operationTime);
                Manager.machineList.get(6).p[type]++;
            }

            // --------------------- Machine 8 ------------------------------
            if (m8Working == 0 && Manager.coils.getBit(132)) {
                m8Working = 1;

                m8Timer = System.currentTimeMillis();
                System.out.println("Machine 8 timer initiated");
                type = Manager.inputVariables.getRegisterValue(97);
            } else if (m8Working == 1 && !Manager.coils.getBit(132)) {
                m8Working = 0;
                Manager.machineList.get(7).operationTime += System.currentTimeMillis() - m8Timer;
                //System.out.println("Time operated: " + Manager.machineList.get(7).operationTime);
                Manager.machineList.get(7).p[type]++;
            }
        }
    }
}
