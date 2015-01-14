package santafeantapplication;

import java.io.FileNotFoundException;

/**
 * Application to solve the Santa Fe Ant Trail exercise.
 * 
 * @author James Clark
 */
public class SantaFeAntApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //final int size = 5;
        
        SantaFeMap map = null;
        
        try
        {
            map = new SantaFeMap("map.txt");
        }
        catch (FileNotFoundException e)
        {
            return;
        }
        
        map.print();
       
    }
    
}
