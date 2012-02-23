package week3.bke;

/**
 * Bord voor het Boter-Kaas-Eieren spel.
 * Practicumopdracht Programmeren 1.
 * @author  Theo Ruys en Arend Rensink
 * @version 2002.01.22
 */
public class Bord {

    // -- Constants --------------------------------------------------

    public  static final int      DIM       = 3 ;
    private static final String[] NUMBERING = { " 0 | 1 | 2 ",
                                                "---+---+---",
                                                " 3 | 4 | 5 ",
                                                "---+---+---",
                                                " 6 | 7 | 8 " } ; 
    private static final String   LINE      = NUMBERING[1];
    private static final String   DELIM     = "     " ;

    // -- Instance variables -----------------------------------------

    /**
     * De DIM*DIM vakjes die het Boter-Kaas-Eieren bord vormen.
     * Zie NUMBERING voor de codering van de vakjes.
     * @invariant vakjes.length = DIM*DIM
     *            forall i : int, 0<=i && i <vakjes.length:
     *              vakjes[i] == Mark.LEEG ||
     *              vakjes[i] == Mark.XX ||
     *              vakjes[i] == Mark.OO
     */
    private Mark[] vakjes;

    // -- Constructors -----------------------------------------------

    /**
     * Creeert een leeg bord.
     * @ensure forall i : int, <code>0<=i && i <DIM*DIM: this.getVakje(i) == Mark.LEEG</code>
     */
    public Bord() {
        vakjes = new Mark[DIM*DIM];
        reset();
    }

    // -- Queries ----------------------------------------------------

    /**
     * Creeert een deepcopy van dit bord.
     * @ensure <code>result != this</code><br>
     *         forall i : int, <code>0 <= i && i < DIM*DIM:
     *             result.getVakje(i) == this.getVakje(i)</code>
     */
    public Bord deepCopy() {
        Bord copy = new Bord();
        for (int i=0; i<vakjes.length; i++) {
            copy.vakjes[i] = this.vakjes[i];
        }
        return copy;
    }

    /**
     * Berekent de index in de lineaire array van vakjes van 
     * een (rij,kol)-paar.
     * @require <code>0 <= i && i < DIM && 0 <= j && j < DIM</code>
     * @return  de index behorende bij het (rij,kol)-vakje
     */
    public int index(int rij, int kol) {
        return DIM*rij+kol;
    }

    /**
     * Levert true op als de <code>ix</code> een geldige index 
     * is van een vakje op dit speelbord.
     * @ensure  <code>result == (0 <= ix && ix < DIM*DIM)</code>
     * @return  <code>true</code> als <code>0 <= ix < DIM*DIM</code>
     */
    public boolean isVakje(int ix) {
        return (0 <= ix) && (ix < DIM*DIM);
    }

    /**
     * Levert true op als de (rij,kol)-addressering geldig is voor
     * een vakje op het speelbord.
     * @ensure  <code>result == (0 <= rij && rij < DIM && 0 <= kol && kol < DIM)</code>
     * @return  true als <code>0 <= rij < DIM && 0 <= kol < DIM</code>
     */
    public boolean isVakje(int rij, int kol) {
        return (0 <= rij) && (rij < DIM) &&
               (0 <= kol) && (kol < DIM);
    }

    /**
     * Levert de inhoud van vakje <code>i</code>.
     * @require <code>this.isVakje(i)</code>
     * @ensure  <code>result == Mark.LEEG || result == Mark.XX || result == Mark.OO</code>
     * @param   i het nummer van het vakje (zie NUMBERING)
     * @return  de markering van het i-de vakje
     */
    public Mark getVakje(int i) {
        return vakjes[i];
    }

    /**
     * Levert de inhoud van vakje van rij <code>rij</code> en 
     * kolom <code>kol</code>.
     * @require <code>this.isVakje(rij,kol)</code>
     * @ensure  <code>result == Mark.LEEG || result == Mark.XX || result == Mark.OO</code>
     * @param   rij de rij van het vakje
     * @param   kol de kolom van het vakje
     * @return  de markering van het (rij,kol)-de vakje
     */
    public Mark getVakje(int rij, int kol) {
        return vakjes[index(rij,kol)];
    }

    /**
     * Levert true op als het vakje <code>i</code> leeg is.
     * @require <code>this.isVakje(i)</code>
     * @ensure  <code>result == (this.getVakje(i) == LEEG)</code>
     * @param   i het nummer van het vakje (zie NUMBERING)
     * @return  true als het vakje leeg is.
     */
    public boolean isLeegVakje(int i) {
        return getVakje(i) == Mark.LEEG;
    }

    /**
     * Levert true op als het vakje van rij <code>rij</code> en 
     * kolom <code>kol</code> leeg is.
     * @require <code>this.isVakje(rij,kol)</code>
     * @ensure  <code>result == Mark.LEEG || result == Mark.XX || result == Mark.OO</code>
     * @param   rij de rij van het vakje
     * @param   kol de kolom van het vakje
     * @return  true als het (rij,kol)-de vakje leeg is.
     */
    public boolean isLeegVakje(int rij, int kol) {
        return isLeegVakje(index(rij,kol));
    }

    /**
     * Test of het gehele bord vol is.
     * @ensure <code>result == forall i : int, 0 <= i && i < DIM*DIM: 
     *                                 this.getVakje(i) != LEEG</code>
     * @return true als alle vakjes bezet zijn.
     */
    public boolean isVol() {
        for (int i=0; i<vakjes.length; i++) {
            if (getVakje(i) == Mark.LEEG) 
                return false;
        }
        return true;
    }

    /**
     * Levert true op als het spel afgelopen is. <br>
     * Het is spel is afgelopen als er een winnaar is of als 
     * het gehele bord vol is.
     * @ensure result == this.isVol() || this.heeftWinnaar()
     * @return true als het spel afgelopen is.
     */
    public boolean gameOver() {
        return isVol() || heeftWinnaar();
    }

    /**
     * Controleert of er een <b>rij</b> is die geheel uit 
     * <code>m</code>'s bestaat.
     * @require <code>m == Mark.LEEG || m == Mark.XX || m == Mark.OO</code>
     * @ensure  <code>result == true</code> als er een rij is die 
     *          alleen uit <code>m</code>'s bestaat.
     * @param   m de markering die gecontroleerd wordt
     * @return  true als er een rij is met alleen maar <code>m</code>'s
     */
    public boolean heeftRij(Mark m) {
        boolean hRij = false;
        for (int i=0; i<DIM && !hRij; i++) {
            hRij = true;
            for (int j=0; j<DIM && hRij; j++) {
                hRij = hRij && (getVakje(i,j) == m);
            }
        }
        return hRij;
    }

    /**
     * Controleert of er een <b>kolom</b> is die geheel uit 
     * <code>m</code>'s bestaat.
     * @require <code>m == Mark.LEEG || m == Mark.XX || m == Mark.OO</code>
     * @ensure  <code>result == true</code> als er een kolom is die 
     *          alleen uit <code>m</code>'s bestaat.
     * @param   m de markering die gecontroleerd wordt
     * @return  true als er een kolom is met alleen maar <code>m</code>'s
     */
    public boolean heeftKolom(Mark m) {
        boolean hKol = false;
        for (int j=0; j<DIM && !hKol; j++) {
            hKol = true;
            for (int i=0; i<DIM && hKol; i++) {
                hKol = hKol && (getVakje(i,j) == m) ;
            }
        }
        return hKol;
    }

    /**
     * Controleert of er <b>diagonaal</b> is die geheel uit
     * <code>m</code>'s bestaat.
     * @require <code>m == Mark.LEEG || m == Mark.XX || m == Mark.OO</code>
     * @ensure  <code>result == true</code> als er een diagonaal is die 
     *          alleen uit <code>m</code>'s bestaat.
     * @param   m de markering die gecontroleerd wordt
     * @return  true als er een diagonaal is met alleen maar <code>m</code>'s
     */
    public boolean heeftDiagonaal(Mark m) {
        boolean hDia1 = true;
        boolean hDia2 = true;
        for (int i=0; i<DIM && (hDia1 || hDia2); i++) {
            hDia1 = hDia1 && (getVakje(i,i) == m);
            hDia2 = hDia2 && (getVakje(i,DIM-i-1) == m);
        }
        return hDia1 || hDia2;
    }

    /**
     * Controleert of de markering <code>m</code> gewonnen heeft. <br>
     * Een markering <code>m</code> heeft gewonnen dit Bord minstens
     * een rij, een kolom, of diagonaal heeft die alleen maar uit
     * <code>m</code>'s bestaat.
     * @require <code>m == Mark.XX || m == Mark.OO</code>
     * @ensure  <code>result == this.heeftRij(m) || 
     *                          this.heeftKolom(m) ||
     *                          this.heeftDiagonaal(m)</code>
     * @param   m de markering die gecontroleerd gaat worden
     * @return  true als de markering <code>m</code> gewonnen heeft
     */
    public boolean isWinnaar(Mark m) {
        return heeftRij(m) || heeftKolom(m) || heeftDiagonaal(m) ;
    }

    /**
     * Levert true op als het spel een winnaar heeft. <br>
     * Er is een winnaar als er een markering is die minstens
     * een rij, kolom of diagonaal in zijn bezit heeft.
     * @ensure <code>result == isWinnaar(XX) || isWinnaar(OO)</code>
     * @return true als dit Bord een winnaar heeft.
     */
    public boolean heeftWinnaar() {
        return isWinnaar(Mark.XX) || isWinnaar(Mark.OO);
    }

    /**
     * Levert een String-representatie van dit Bord op. <br>
     * Naast de huidige toestand van het Bord, schrijft de methode
     * ook de <code>NUMBERING</code> van de vakjes naast het Bord.
     * @return de toestand van dit Bord als String.
     */
    public String toString() {
        String s = "";
        for (int i=0; i<DIM; i++) {
            String rij = "";
            for (int j=0; j<DIM; j++) {
                rij = rij + " " + getVakje(i,j).toString() + " " ;
                if (j<DIM-1) rij = rij + "|" ;
            }
            s = s + rij + DELIM + NUMBERING[i*2] ;
            if (i<DIM-1)
                s = s + "\n" + LINE + DELIM + NUMBERING[i*2+1] + "\n";
        }
        return s;
    }

    // -- Commands ---------------------------------------------------

    /**
     * Maakt alle vakjes van dit bord leeg (d.w.z. LEEG).
     * @ensure forall i : int, <code>0 <= i && i < DIM*DIM: this.getVakje(i) == LEEG</code>
     */
    public void reset() {
        for (int i=0; i<vakjes.length; i++) {
            setVakje(i, Mark.LEEG);
        }
    }

    /**
     * Zet de inhoud van vakje <code>i</code> op markering <code>m</code>.
     * @require <code>this.isVakje(i)</code>
     *          <code>m == Mark.LEEG || m == Mark.XX || m == Mark.OO</code><br>
     * @ensure  <code>this.getVakje(i) == m</code>
     * @param   i het nummer van het vakje (zie NUMBERING)
     * @param   m de gewenste markering van het i-de vakje
     */
    public void setVakje(int i, Mark m) {
        vakjes[i] = m ;
    }

    /**
     * Zet de inhoud van het vakje van rij <code>rij</code> en kolom
     * <code>kol</code> op markering <code>m</code>. <br>
     * @require <code>this.isVakje(rij,kol)</code>
     *          <code>m == Mark.LEEG || m == Mark.XX || m == Mark.OO</code><br>
     * @ensure  <code>this.getVakje(i) == m</code>
     * @param   rij de rij van het vakje
     * @param   kol de kolom van het vakje
     * @param   m de gewenste markering van het (rij,kol)-de vakje
     */
    public void setVakje(int rij, int kol, Mark m) {
        setVakje(index(rij,kol),m);
    }
} // end of class Bord
