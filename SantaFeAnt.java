package santafeantapplication;

/**
 * Represents an ant searching for food.
 * @author James Clark
 */
public class SantaFeAnt {
    protected int x, y;
    protected int foodEaten;
    protected int movesMade;
    
    /**
     * Represents cardinal directions.
     */
    protected enum Direction
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
        Direction (int xoff, int yoff)
        {
            this.xoff = xoff;
            this.yoff = yoff;
        }

        /**
         * X offset for a direction.
         * @return X offset
         */
        public final int xoff() { return xoff; }
        
        /**
         * Y offset for a direction.
         * @return Y offset
         */
        public final int yoff() { return yoff; }
    };
    
    /**
     * Direction the ant is facing.
     */
    protected Direction direction;
    
    /**
     * Map the ant is searching for food.
     */
    protected SantaFeMap map;
    
    /**
     * Creates an ant to travel in a given map.
     * @param map SantaFeMap instance where the map searches for food.
     */
    public SantaFeAnt(SantaFeMap map)
    {
        this.foodEaten = 0;
        this.movesMade = 0;
        this.x = 0;
        this.y = 0;
        this.direction = Direction.EAST;
        
        this.map = new SantaFeMap(map);
    }
    
    /**
     * Turns the ant clockwise.
     */
    public void turnRight()
    {
        /*
          Cycle through enumerated directions. Assumes they are
           defined in clockwise order
        */
        int index = (this.direction.ordinal() + 1) % Direction.values().length;
        this.direction = Direction.values()[index];
    }
    
    /**
     * Turns the ant counter-clockwise.
     */
    public void turnLeft()
    {
        /*
          Cycle through enumerated directions. Assumes they are
           defined in clockwise order
          Using "three rights make a left" to avoid check for negative values
        */
        int index = (this.direction.ordinal() + 3) % Direction.values().length;
        this.direction = Direction.values()[index];
    }
    
    /**
     * Moves the ant forward if the destination is available.
     */
    public void moveForward()
    {
        int tempx = this.x + this.direction.xoff;
        int tempy = this.y + this.direction.yoff;
        
        if (map.isInMapBounds(tempx, tempy))
        {
            this.x = tempx;
            this.y = tempy;
        }
    }
    
    /**
     * Checks the cell in front of the ant for food.
     * @return True if the next cell has food, false if none is found or if the
     *         ant is facing an adjacent boundary
     */
    public boolean lookAhead ()
    {
        int tempx = this.x + this.direction.xoff;
        int tempy = this.y + this.direction.yoff;
        
        if (map.isInMapBounds(tempx, tempy))
        {
            return map.getCellAt(tempx, tempy).hasFood();
        }
        else return false;
    }
    
    /**
     * Ant visits current cell - eats food if food is available and then
     * continues its search for food.
     */
    public void visit ()
    {
        SantaFeMap.MapCell cell = this.map.getCellAt(x, y);
        
        if (cell.hasFood())
        {
            this.foodEaten++;
            cell.visit(); //Log visit from ant
        }
        else
        {
            //Seek more food...
            if (this.lookAhead())
                this.moveForward();
            else
            {
                //Perform ant's genetic algorithm
            }
        }
    }
}
