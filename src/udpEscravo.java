import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class udpEscravo implements Runnable {

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(54321);
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //System.out.println("Listening...");
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());

                udpEscravo.decodeMessage(sentence);
            }
        } catch (Exception e) {}
    }

    public static void decodeMessage (String sentence /*, char trans, int no, int pIni, int pFin, int n*/) {
        List<String> parts = new ArrayList<String>();

        parts.add(sentence.substring(1,2));
        parts.add(sentence.substring(2, 5));
        parts.add(sentence.substring(5, 6));
        parts.add(sentence.substring(6, 7));
        parts.add(sentence.substring(7, 9));
        System.out.println("Order received: " + parts);


        Order order = new Order();
        Manager.orderList.add(order);

    }

}