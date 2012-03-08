package week4.mandel;

/**
 * Klasse voor het parallelliseren van het tekenen van de Mandelbrot set.
 * Practicumopdracht Programmeren 2.
 * @author Martin Kalin, aangepast door Arend Rensink
 * @version 15-01-2002
 */
class MandelThread extends Thread {
    MandelThread( MandelPanel mp ) { 
        this.mp = mp; 
    }

    // overrides Thread.run
    // draws the fractal on the MandelPanel
    public void run() {
        mp.drawMandel();
    }

    private MandelPanel mp;
}
