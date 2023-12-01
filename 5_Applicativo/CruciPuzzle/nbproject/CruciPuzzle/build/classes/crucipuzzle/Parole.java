package crucipuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parole {

    private String pathFile;
    private File file;
    private Scanner scannerFile;
    private Scanner scannerPiccole;
    private ArrayList<String> aList;
    private int grandezzaCampo;
    private Campo campo;

    public Parole(String pathFile, int grandezzaCampo, int difficolta) {
        this.grandezzaCampo = grandezzaCampo;       // La grandezza del campo di gioco
        this.pathFile = pathFile;                   // La path del file con le parole
        aList = new ArrayList<String>();            // Tutte le parole del file sono salvate quì
        file = new File(pathFile);           // Il file

        // INSERENDO IL FILE NELLO SCANNER \\

        try {
            scannerFile = new Scanner(file);
        } catch(FileNotFoundException fne) {
            System.err.println("File non trovato! \n\r" + fne);
        }

        // INSERENDO TUTTE LE PAROLE NELLA LISTA DI PAROLE (aList) \\

        while (scannerFile.hasNextLine()) {
            aList.add(scannerFile.nextLine());
        }
    }
    
    public void setCampo(Campo c) {
        this.campo = c;
    }

    public String selezionaParole() {
        if (aList.isEmpty())
            return "nonHoPiuParole";

        String interessata = "";
        int indice = 0;
        do {
            if (aList.isEmpty())
                return "nonHoPiuParole";
            indice = (int) (Math.random() * aList.size());
            interessata = pulisciParola(aList.get(indice));
            aList.remove(indice);
        } while (verificaParola(interessata));
        
        return interessata;
    }
    
    public boolean verificaParola(String interessata) {
        int lParola = interessata.length();
        boolean temporaly = lParola >= grandezzaCampo || lParola <= 2;
        return temporaly || campo.getArrayParole().contains(interessata);
    }
    
    public String selezionaParole(int nChar) {
        String interessata = "";
        for (String word : aList) {
            if ((word.length() == nChar)&&(!campo.getArrayParole().contains(word))) {
                interessata = word;
                break;
            }
        }
        aList.remove(interessata);
        interessata = pulisciParola(interessata);
        return interessata;
    } 
    
    public String pulisciParola(String parola) {
        try {
            parola = parola.replaceAll("à", "a");
            parola = parola.replaceAll("é", "e");
            parola = parola.replaceAll("è", "e");
            parola = parola.replaceAll("ì", "i");
            parola = parola.replaceAll("ù", "u");
            parola = parola.replaceAll("ò", "o");
            parola = parola.replaceAll("â", "a");
        } catch (Exception e) {}
        return parola;
    }
}

