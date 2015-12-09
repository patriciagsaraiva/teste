import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class udpEscravo {


    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(54321);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Listening...");
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            String b = new String();
            for (int i = 0; i < 9; i++) {

                b += sentence.charAt(i);
            }
            System.out.println("RECEIVED: " +b);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            udpEscravo.decodeMessage(sentence);
        }
    }

    /*
        carater-1 byte-trans
        numero de ordem-3 bytes-no
        peça de origem-1byte-pIni
        peça final-1byte-pFin
        quantidade a produzir-2bytes-n
    */


    public static void decodeMessage (String sentence /*, char trans, int no, int pIni, int pFin, int n*/) {


        List<String> parts = new ArrayList<String>();

        parts.add(sentence.substring(1,2));
        parts.add(sentence.substring(2, 5));
        parts.add(sentence.substring(5, 6));
        parts.add(sentence.substring(6, 7));
        parts.add(sentence.substring(7, 9));
        String[] partsArray = parts.toArray(new String[0]);
        System.out.println(parts);


    }
}