package santafeantapplication;

import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Represents a collection of cells to form a map for the Santa Fe Ant problem.
 * @author James Clark
 */
public class SantaFeMap {
    /**
     * Represents a single cell in a map.
     * Keeps track of how many times it has been visited and whether it has food.
     */
    public class MapCell
    {
        /**
         * Number of times this cell has been visited.
         */
        public int timesVisited;
        
        /**
         * Does this cell have food?
         */
        public boolean _hasFood;
        
        /**
         * Creates a new cell. Has no food and has been visited zero times.
         */
        public MapCell ()
        {
            this.timesVisited = 0;
            this._hasFood = false;
        }
        
        /**
         * Copy constructor. Throws NullPointerException if the parameter is null.
         * @param cell MapCell to be copied.
         * @throws NullPointerException
         */
        public MapCell (MapCell cell) throws NullPointerException
        {
            if (cell == null)
                throw new NullPointerException();
            
            this.timesVisited = cell.timesVisited;
            this._hasFood = cell._hasFood;
        }
        
        /**
         * Sets whether this cell has food.
         * @param hasFood boolean
         */
        public void setFood (boolean hasFood)
        {
            this._hasFood = hasFood;
        }
        
        /**
         * 
         * @return true if this cell has food, false if not.
         */
        public boolean hasFood ()
        {
            return this._hasFood;
        }
        
        /**
         * Logs a visit to this cell. Consumes food if food is available.
         */
        public void visit ()
        {
            this.timesVisited++;
            this._hasFood = false;
        }
        
        /**
         * @return Number of times this cell has been visited.
         */
        public int timesVisited ()
        {
            return this.timesVisited;
        }
        
        /**
         * Prints a text representation of this cell.
         * @param out PrintStream to print output to.
         */
        public void printCell (PrintStream out)
        {
            if (this.hasFood())
                out.print('*');
            else out.print(this.timesVisited());
        }
        
        /**
         * Prints a text representation of this cell to System.out
         */
        public void printCell ()
        {
            this.printCell(System.out);
        }               
    }
    
    /**
     * Represents cardinal directions
     */
    public enum Direction
    {
        //Keep these in clockwise order
        NORTH(0,-1),
        EAST(1,0),
        SOUTH(0,1),
        WEST(-1,0);
        
        private final int xoff;
        private final int yoff;
        
        /**
         * Creates a direction with an x,y offset for that direction
         * @param xoff X offset
         * @param yoff Y offset
         */
        Direction(int xoff, int yoff)
        {
            this.xoff = xoff;
            this.yoff = yoff;
        }

        /**
         * X offset for a direction.
         * @return X offset
         */
        public final int xoffset() { return xoff; }
        
        /**
         * Y offset for a direction.
         * @return Y offset
         */
        public final int yoffset() { return yoff; }
    }
    
    /**
     * Map as collection of MapCells
     */
    protected MapCell map[][];
    
    /**
     * Creates a square map of size x size
     * @param size Length of each side
     */
    public SantaFeMap(int size)
    {
        this.map = new MapCell[size][size];
        
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map[i].length; j++)
                map[i][j] = new MapCell();
    }
    
    /**
     * Creates a map with information from disk with the given filename
     * @param filename Path of file to read data from
     * @throws FileNotFoundException
     * @throws InputMismatchException 
     */
    public SantaFeMap(String filename) throws FileNotFoundException, InputMismatchException
    {
        Scanner s = null;
        
        try
        {
            s = new Scanner(new FileReader(filename));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("[ERROR] Unable to read file: " + filename);
            
            try
            {
                s.close();
            }
            catch (IllegalStateException ex)
            {
                System.out.println(ex.getMessage());
            }
            
            throw e;
        }
        
        
        try
        {
            int size = s.nextInt(); //Read size
           
            //Initialize map
            map = new MapCell[size][size];
            
            for (int i = 0; i < map.length; i++)
                for (int j = 0; j < map[i].length; j++)
                    map[i][j] = new MapCell();
                
            while (s.hasNext())
            {
                int x = s.nextInt();
                int y = s.nextInt();
            
                if (this.isInMapBounds(x, y))
                    map[y][x].setFood(true);
                else throw new InputMismatchException("Coordinate out of bounds.");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("[ERROR] File input error in: " + filename);
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {
            try
            {
                s.close();
            }
            catch (IllegalStateException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }   
    
    /**
     * Copy constructor. Creates of copy of the given map.
     * @param mapToCopy Map to copy.
     */
    public SantaFeMap(SantaFeMap mapToCopy)
    {     
        int size = mapToCopy.map.length;
        this.map = new MapCell[size][size];
        
        for (int i = 0; i < this.map.length; i++)
            for (int j = 0; j < this.map.length; j++)
                this.map[i][j] = new MapCell(mapToCopy.map[i][j]);
    }
   
    /**
     * Returns true if given coordinate is within the map's bounds.
     * @param x X coordinate
     * @param y Y coordinate
     * @return 
     */
    public boolean isInMapBounds(int x, int y)
    {
        return (x >= 0 && y >= 0 && x < this.map.length && y < this.map.length);
    }

    /**
     * Returns a reference to the MapCell at x,y
     * @param x X coordinate
     * @param y Y coordinate
     * @return MapCell at given coordinate, null if outside bounds of map
     */
    public MapCell getCellAt(int x, int y)
    {
        if (this.isInMapBounds(x,y))
            return map[y][x];
        else return null;
    }
 
    /**
     * Prints a text representation of the map to a given PrintStream
     * @param out PrintStream to print output to.
     */
    public void print(PrintStream out)
    {
        for (MapCell[] row : map)
        {
            for (MapCell cell : row)
                cell.printCell(out);
            
            out.println();
        }        
    }            
    
    /**
     * Prints a text representation of the map to System.out
     */
    public void print()
    {
        this.print(System.out);
    }
}
