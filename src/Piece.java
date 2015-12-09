/**
 * Created by Ruben on 08-12-2015.
 */
public class Piece {
    int id;
    String color = new String();

    public static void setPiece(Piece p, int id) {
        p.id = id;
        switch (id) {
            case 1:
                p.color = "Castanho";
                break;
            case 2:
                p.color = "Vermelho";
                break;
            case 3:
                p.color = "Laranja";
                break;
            case 4:
                p.color = "Amarelo";
                break;
            case 5:
                p.color = "Verde";
                break;
            case 6:
                p.color = "Azul";
                break;
            case 7:
                p.color = "Violeta";
                break;
            case 8:
                p.color = "Cinza";
                break;
            case 9:
                p.color = "Branco";
                break;
        }
    }

}
