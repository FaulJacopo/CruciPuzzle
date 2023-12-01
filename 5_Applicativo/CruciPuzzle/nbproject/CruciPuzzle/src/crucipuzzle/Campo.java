package crucipuzzle;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * ***** DESCRIZIONE *****
 * 
 * Questa classe è la classe che si occupa di generare il campo,
 * essa controlla che le parole possano essere inserite all'interno
 * del campo.
 * 
 * Questa classe contiene anche l'array con le parole selezionate e le 
 * soluzioni del gioco.
 * 
 * Parole in quattro direzioni:
 *      Verticale |;
 *      Orizzontale -;
 *      ObliquaS /;
 *      ObliquaD \;
 *
 * @author Jacopo Faul
 * @version 01.12.2023
 */
public class Campo {

    private final int VERTICALE = 1;
    private final int ORIZZONTALE = 10;
    private final int OBLIQUA_SINISTRA = 100;
    private final int OBLIQUA_DESTRA = 1000;
    
    private final int NPAROLE_FACILE = 10;
    private final int NPAROLE_MEDIO = 15;
    private final int NPAROLE_DIFFICILE = 20;
    private final int NUMERO_CAMBI_DISPONIBILI = 30;

    private int numeroCambi = 0;
    private int numeroSpazi = 0;
    private int[] incrementoCelle = new int[4];
    private ArrayList<String> arrayParole;
    private ArrayList<String> arrayParoleSoluzioni;
    private int difficolta;
    private Parole parole;
    private Lettera[][] campo;
    private final int grandezzaCampo;
    private final String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * ***** METODI GETTER *****
     */
    
    /**
     * Metodo che ritorna l'arraylist delle parole.
     * 
     * @return arrayParola.
     */
    public ArrayList<String> getArrayParole() {
        return arrayParole;
    }

    /**
     * Metodo che ritorna la difficolta del gioco
     * 
     * @return difficolta.
     */
    public int getDifficolta() {
        return difficolta;
    }
    
    /**
     * Metodo che ritorna il campo del gioco.
     * 
     * @return campo.
     */
    public Lettera[][] getCampo() {
        return campo;
    }

    /**
     * Metodo che ritorna la grandezza del campo.
     *
     * @return grandezzaCampo.
     */
    public int getGrandezzaCampo() {
        return grandezzaCampo;
    }
    
    public ArrayList<String> getArraySoluzioni() {
        return arrayParoleSoluzioni;
    }
    
    /**
     * Metodo che ritorna il numero di spazi.
     *
     * @return numeroSpazi.
     */
    public int getNumeroSpazi() {
        return numeroSpazi;
    }

    /**
     * ***** METODI COSTRUTTORI *****
     */
    
    public Campo(int grandezzaCampo, Parole parole, int difficolta) {
        this.grandezzaCampo = grandezzaCampo;
        this.parole = parole;
        this.difficolta = difficolta;
        this.arrayParole = new ArrayList<String>();
        this.arrayParoleSoluzioni = new ArrayList<String>();
        this.parole.setCampo(this);
        campo = new Lettera[grandezzaCampo][grandezzaCampo];
        inizializzaCampo();
    }

    /**
     * ***** METODI PUBBLICI *****
     */

    public void inizializzaCampo() {
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                campo[i][j] = new Lettera("-",0,0,0,0, false);
            }
        }
    }

    /**
     * Questo metodo genera il campo di gioco, quindi seleziona le parole, le
     * inserisce nel campo, e le assegna alla variabile "campoLettere".
     */
    public void generaCampo() {
        // Generazione Campo
        
        // Con modalità Bambini
        
        if (difficolta < 3) {
            int numeroParole = 0;
            // Facile
            if (difficolta == 0) numeroParole = NPAROLE_FACILE;
            // Medio
            if (difficolta == 1) numeroParole = NPAROLE_MEDIO;
            // Difficile
            if (difficolta == 2) numeroParole = NPAROLE_DIFFICILE;
            // Generazione
            for (int i = 0; i < numeroParole; i++) {
                System.out.println(this.arrayParole.size() + " il numero di parole attuale!");
                String parola = parole.selezionaParole();
                // Se non ho più parole interrompo.
                if (parola.equals("nonHoPiuParole")) 
                    break;
                if (arrayParole.contains(parole))
                    numeroParole++;
                else
                    generaTutteLeParole(parola);          
            }
            inserisciLettereMancanti();
        } else {
        // Con modalità normale
        
            while (contaLettereMancanti() > ((grandezzaCampo*grandezzaCampo)/100*10)) {
                numeroCambi = 0;
                String parola = parole.selezionaParole();
                // Se la parola selezionata è già all'interno del campo oppure se non ho più parole interrompo.
                if (parola.equals("nonHoPiuParole")) 
                    break;
                if (!arrayParole.contains(parola))
                    generaTutteLeParole(parola);           
            }
            controllaLeCelleRestanti();
            controllaLeCelleRestanti();
            if (grandezzaCampo > 19)
                controllaLeCelleRestanti();
            numeroSpazi = contaLettereMancanti();
        }
    }
    
    public void stampa() {
        // Stampa Campo
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                System.out.print(campo[i][j].lettera + "  ");
            }
            System.out.println("");
        }
        System.out.println(numeroSpazi);
        
        // Stampa Parole
        
        arrayParole.sort(Comparator.naturalOrder());
        for (int i = 0; i < arrayParole.size() ; i++) {
            if (arrayParole.get(i) != "")
                System.out.println(i + ") \t " + arrayParole.get(i));  
        }
    }
    
    public void sostituisciParola() {
        numeroCambi++;
        generaTutteLeParole(parole.selezionaParole());
    }
    
    public void generaTutteLeParole(String parola) {
        if ((numeroCambi >= NUMERO_CAMBI_DISPONIBILI) && (difficolta == 3))
            return;
        if (parola.equals("nonHoPiuParole"))
            return;
        int direzione = (int) (Math.random() * 3) + 1;
        if (direzione == 1) {
            inserisciParolaVerticale(parola);
        } else if (direzione == 2) {
            inserisciParolaOrizzontale(parola);
        } else {
            inserisciParolaObliqua(parola);
        }
    }

    public void inserisciParolaVerticale(String parola) {
        int numeroMassimo = 0;
        boolean girata = false;
        int riga = 0, colonna = 0;

        // Ho il 25% di possibilità che la parola esca al contrario
        if ((int) (Math.random() * 4) == 3) {
            parola = new StringBuffer(parola).reverse().toString();
            girata = true;
        }
        
        do {
            // Se è la terza volta che ripeto il ciclo, cambio la parola.
            if (numeroMassimo == 3) {
                sostituisciParola();
                return;
            }
            riga = (int) (Math.random() * (grandezzaCampo - parola.length()));
            colonna = (int) (Math.random() * (grandezzaCampo));
            numeroMassimo++;

        } while (!controllaCelle(parola, VERTICALE, riga, colonna));
        
        // Inserisci la parola
        inserisciParolaVerticale(parola,riga,colonna,girata);
    }

    public void inserisciParolaOrizzontale(String parola) {
        int numeroMassimo = 0;
        String parolaInizio = parola;
        boolean girata = false;
        int riga = 0, colonna = 0;

        if ((int) (Math.random() * 4) == 3) {
            parola = new StringBuffer(parola).reverse().toString();
            girata = true;
        }

        do {
            if (numeroMassimo == 3) {
                sostituisciParola();
                return;
            }
            riga = (int) (Math.random() * (grandezzaCampo));
            colonna = (int) (Math.random() * (grandezzaCampo - parola.length()));
            numeroMassimo++;

        } while (!controllaCelle(parola, ORIZZONTALE, riga, colonna));
        
        // Inserisci la parola
        inserisciParolaOrizzontale(parola,riga,colonna, girata);
    }
    
    public void inserisciParolaObliqua(String parola) {
        int numeroMassimo = 0;
        String parolaInizio = parola;
        boolean girata = false;
        int direzioneParola = (int)(Math.random() * 2);

        if (direzioneParola == 0)
            direzioneParola = OBLIQUA_SINISTRA;
        else
            direzioneParola = OBLIQUA_DESTRA;
        
        int riga = 0, colonna = 0;
        
        if ((int) (Math.random() * 4) == 3) {
            parola = new StringBuffer(parola).reverse().toString();
            girata = true;
        }
        /*
         * Direzione: OBLIQUA_SINISTRA = \ => Con riga tra:    0 e (grandezzaCampo - parola.length());
         * Direzione: OBLIQUA_DESTRA = / => Con riga tra:      parola.length() e grandezzaCampo;
         */
        do {
            if (numeroMassimo == 3) {
                sostituisciParola();
                return;
            }
            if (direzioneParola == OBLIQUA_SINISTRA)
                riga = (int)(Math.random() * (grandezzaCampo - parola.length()));
            else
                riga = parola.length() + (int)(Math.random() * (grandezzaCampo - parola.length()));
            colonna = (int) (Math.random() * (grandezzaCampo - parola.length()));
            numeroMassimo++;
        } while (!controllaCelle(parola, direzioneParola, riga, colonna) );

        // Inserisci la parola
        if (direzioneParola == OBLIQUA_SINISTRA)
            inserisciParolaObliquaS(parola,riga,colonna,girata);
        else
            inserisciParolaObliquaD(parola,riga,colonna,girata);
    }
    
    public void inserisciParolaVerticale(String parola, int riga, int colonna, boolean girata) {
        inserisciParolaInArray(parola,riga,colonna,"Verticale", girata);
        for (int i = 0; i < parola.length(); i++) {
            if (campo[riga + i][colonna].lettera.equals("-")) {
                campo[riga + i][colonna] = new Lettera((parola.charAt(i) + "").toUpperCase(), 1, 0, 0, 0, girata);
            } else {
                campo[riga + i][colonna].verticale = 1;
            }
        }
    }
    
    public void inserisciParolaOrizzontale(String parola, int riga, int colonna, boolean girata) {
        inserisciParolaInArray(parola,riga,colonna,"Orizzontale", girata);
        for (int i = 0; i < (parola.length()); i++) {
            if (campo[riga][colonna + i].lettera.equals("-"))
                campo[riga][colonna + i] = new Lettera((parola.charAt(i) + "").toUpperCase(), 0, 1, 0, 0, girata);
            else
                campo[riga][colonna + i].orizzontale = 1;
        }
    }
    
    public void inserisciParolaObliquaS(String parola, int riga, int colonna, boolean girata) {
        inserisciParolaInArray(parola,riga,colonna,"ObliquaS", girata);
        for (int i = 0; i < parola.length(); i++) {
            if (campo[riga + i][colonna + i].lettera.equals("-")) {
                campo[riga + i][colonna + i] = new Lettera((parola.charAt(i) + "").toUpperCase(), 0, 0, 1, 0, girata);
            } else {
                campo[riga + i][colonna + i].obliquaSinistra = 1;
            }
        }
        
    }
    
    public void inserisciParolaObliquaD(String parola, int riga, int colonna, boolean girata) {
        inserisciParolaInArray(parola,riga,colonna,"ObliquaD", girata);
        for (int i = 0; i < parola.length(); i++) {
            if (campo[riga - i][colonna + i].lettera.equals("-"))
                campo[riga - i][colonna + i] = new Lettera((parola.charAt(i) + "").toUpperCase(), 0, 0, 0, 1, girata);
            else
                campo[riga - i][colonna + i].obliquaDestra = 1;
        }
    }

    public void inserisciLettereMancanti() {
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                if (campo[i][j].lettera.equals("-")) {
                    campo[i][j] = new Lettera(alfabeto.charAt((int)(Math.random() * alfabeto.length())) + "",0,0,0,0,false);
                }
            }
        }
    }
    
    public int contaLettereMancanti() {
        int f = 0;
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                if (campo[i][j].lettera.equals("-"))
                    f++;
            }
        }
        return f;
    }
    
    /**
     * Una volta finito di generare il campo, questo metodo
     * si preoccupa di controllare che esso sia completo, ovvero
     * che non ci siano spazi con più di 3 caselle vuote consecutive
     * in tutte e quattro le direzioni.
     */
    private void controllaLeCelleRestanti() {
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                if (campo[i][j].lettera.equals("-")) {
                    incrementoCelle[0] = 1; 
                    incrementoCelle[1] = 1; 
                    incrementoCelle[2] = 1; 
                    incrementoCelle[3] = 1;
                    controllaCellaSuccessivaVerticale(i, j);
                    controllaCellaSuccessivaOrizzontale(i, j);
                    controllaCellaSuccessivaObliquaS(i, j);
                    controllaCellaSuccessivaObliquaD(i, j);
                    if (incrementoCelle[0] > 2) 
                        inserisciParolaVerticale(parole.selezionaParole(incrementoCelle[0]), i, j, false);
                    else if (incrementoCelle[1] > 2) 
                        inserisciParolaOrizzontale(parole.selezionaParole(incrementoCelle[1]), i, j, false);
                    else if (incrementoCelle[2] > 2) 
                        inserisciParolaObliquaS(parole.selezionaParole(incrementoCelle[2]), i, j, false);
                    else if (incrementoCelle[3] > 2) 
                        inserisciParolaObliquaD(parole.selezionaParole(incrementoCelle[3]), i, j, false);
                }
            }
        }
    }

    private void controllaCellaSuccessivaVerticale(int i, int j) {
        if (i >= grandezzaCampo-1)
            return;
        if (campo[i + 1][j].lettera.equals("-")) {
            incrementoCelle[0]++;
            controllaCellaSuccessivaVerticale(i + 1, j);
        }
    }

    private void controllaCellaSuccessivaOrizzontale(int i, int j) {
        if (j >= grandezzaCampo-1)
            return;
        if (campo[i][j + 1].lettera.equals("-")) {
            incrementoCelle[1]++;
            controllaCellaSuccessivaOrizzontale(i, j + 1);
        }
    }

    private void controllaCellaSuccessivaObliquaS(int i, int j) {
        if ((j >= grandezzaCampo-1)||(i >= grandezzaCampo-1))
            return;
        if (campo[i + 1][j + 1].lettera.equals("-")) {
            incrementoCelle[2]++;   
            controllaCellaSuccessivaObliquaS(i + 1, j + 1);
        }
    }

    private void controllaCellaSuccessivaObliquaD(int i, int j) {
        if ((i == 0)||(j >= grandezzaCampo-1)) 
            return;
        if (campo[i - 1][j + 1].lettera.equals("-")) {
            incrementoCelle[3]++;
            controllaCellaSuccessivaObliquaD(i - 1, j + 1);
        }
    }
    
    /**
     * Controlla se la parola si possa inserire 
     * all'interno del campo.
     * 
     * @param parola la parola
     * @param direzione la direzione della parola
     * @param riga la riga d'inizio
     * @param colonna la colonna d'inizio
     * @return true se inseribile, false se non inseribile
     */
    public boolean controllaCelle(String parola, int direzione, int riga, int colonna) {
        
        boolean disponibile = true;
        
        if (direzione == VERTICALE) {

            for (int i = riga; i < (riga + parola.length()); i++) {
                if (!disponibile) // Se non va bene...
                    return false; // Successivamente devo controllare se una cella è già occupata, nella stessa direzione o meno o da uno stesso carattere.
                boolean temporaly = (campo[i][colonna].lettera.equals(parola.charAt(i-riga) + "") || (campo[i][colonna].lettera.equals("-")));
                disponibile = (temporaly && (campo[i][colonna].verticale == 0));
            }

        } else if (direzione == ORIZZONTALE) {

            for (int i = colonna; i < (colonna + parola.length()); i++) {
                if (!disponibile) // Se non va bene...
                    return false; // Successivamente devo controllare se una cella è già occupata, nella stessa direzione o meno o da uno stesso carattere.
                boolean temporaly = (campo[riga][i].lettera.equals(parola.charAt(i-colonna) + "") || (campo[riga][i].lettera.equals("-")));
                disponibile = (temporaly && (campo[riga][i].orizzontale == 0));
            }

        } else if (direzione == OBLIQUA_SINISTRA) {

            for (int i = 0; i < parola.length(); i++) {
                if (!disponibile) // Se non va bene...
                    return false; // Successivamente devo controllare se una cella è già occupata, nella stessa direzione o meno o da uno stesso carattere.
                boolean temporaly = (campo[riga + i][colonna + i].lettera.equals(parola.charAt(i) + "") || (campo[riga + i][colonna + i].lettera.equals("-")));
                disponibile = (temporaly && (campo[riga + i][colonna + i].obliquaSinistra == 0));
            }

        } else if (direzione == OBLIQUA_DESTRA) {

            for (int i = 0; i < parola.length(); i++) {
                if (!disponibile) // Se non va bene...
                    return false; // Successivamente devo controllare se una cella è già occupata, nella stessa direzione o meno o da uno stesso carattere.
                boolean temporaly = (campo[riga - i][colonna + i].lettera.equals(parola.charAt(i) + "") || (campo[riga - i][colonna + i].lettera.equals("-")));
                disponibile = (temporaly && (campo[riga - i][colonna + i].obliquaDestra == 0));
            }
        }
        return disponibile;
    }
    
    public void inserisciParolaInArray(String parola, int riga, int colonna, String direzione, boolean girata) {
        if (girata)
            parola = new StringBuffer(parola).reverse().toString();
        arrayParole.add(parola);
        arrayParoleSoluzioni.add(parola + " \t Riga: " + riga + " \t Colonna: " + colonna + " \t Direzione: " + direzione);            
    }
    
    public void inserisciParolaNascosta(String parolaNascosta) {
        int indice = 0;
        for (int i = 0; i < grandezzaCampo; i++) {
            for (int j = 0; j < grandezzaCampo; j++) {
                if (campo[i][j].lettera.equals("-")) {
                    campo[i][j] = new Lettera((parolaNascosta.charAt(indice) + "").toUpperCase(),0,0,0,0,false);
                    indice++;
                }
            }
        }
    }
}