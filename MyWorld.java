import greenfoot.*;

/** 
 * The Wolrd our hero lives in
 * 
 * @author Arron
 * @version April 27, 2026
 */
public class MyWorld extends World {
    public int score = 0;
    private int level = 3;
    private int maxLevel = 12;
    Label scoreLabel;
    
    
    public MyWorld() {
        //Creates a new world with 600*400 cells with a cell size of 1x1 pixels
        super(600, 400, 1, false);
        
        //Create the elephant object
        Elephant elephant = new Elephant();
        addObject(elephant, 300, 350);
        
        //Create a label
        scoreLabel = new Label(0,50);
        addObject(scoreLabel, 50, 50);
        createDurian();
    }
    
    /**
     * Increase score
     */
    public void increaseScore()
    {
        score ++;
        scoreLabel.setValue(score);
        
        if(score % 5 == 0)
        {
            if (level <= maxLevel)
            {
                level += 1;
            }
        }
    }
    
    /**
     * Creates a new apple at random location at top of screen
     */
    public void createDurian(){
        Durian durian = new Durian();
        int durianSpeed = level;
        durian.setSpeed(durianSpeed); 
        
        int elephantX = getObjects(Elephant.class).get(0).getX();
        int fallFrames = 370 / durianSpeed;
        int reactionFrames = 15;//1/4 seconds 
        // Frames the player actually has to move the elephant
        int availableFrames = Math.max(0, fallFrames - reactionFrames);
        
        int elephantSpeed = getObjects(Elephant.class).get(0).getSpeed();
        int maxTravelDistance = availableFrames * elephantSpeed;
        int hitboxTolerance = 65;   
        
        // The absolute maximum distance the durian can spawn from the elephant
        int maxDistance = maxTravelDistance + hitboxTolerance;
        //Calculate safe spawn boundaries
        int minX = Math.max(0, elephantX - maxDistance);
        int maxX = Math.min(600, elephantX + maxDistance);
        // Safety fallback just in case the math ever overlaps perfectly
        if (maxX <= minX) {
            maxX = minX + 1;
        }
        
        //Spawn the Durian within the calculated safe zone
        int x = minX + Greenfoot.getRandomNumber(maxX - minX);
        int y = 30;
        addObject(durian, x, y);
    }
    
    /**
     * End the game and draw 'Game Over'
     */
    public void gameOver()
    {
        Label gameOverLabel = new Label("Game Over",100);
        addObject(gameOverLabel, 300,200);
    }
    
}


