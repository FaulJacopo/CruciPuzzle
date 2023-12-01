package crucipuzzle;

/**
 * Classe con cui sarà creata la matrice del campo.
 * 
 * @author Jacopo Faul
 * @version 01.12.2023
 */
public class Lettera {

    public String lettera;
    public int verticale;
    public int orizzontale;
    public int obliquaSinistra;
    public int obliquaDestra;
    public boolean girata;


    public Lettera(String lettera, int verticale, int orizzontale,
                   int obliquSinistra, int obliquaDestra, boolean girata) {
        this.lettera = lettera;
        this.verticale = verticale;
        this.orizzontale = orizzontale;
        this.obliquaSinistra = obliquSinistra;
        this.obliquaDestra = obliquaDestra;
        this.girata = girata;
    }
}

