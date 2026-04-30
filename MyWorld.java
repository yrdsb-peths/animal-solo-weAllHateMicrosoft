import greenfoot.*;

/** 
 * The Wolrd our hero lives in
 * 
 * @author Arron
 * @version April 30, 2026
 */
public class MyWorld extends World {
   public int score = 0;
   private int level = 3;
   int maxLevel = 12;
   Label scoreLabel;
   private boolean isGameOver = false;   
   public GreenfootSound bgm = new GreenfootSound("bgm.mp3");
   
   public MyWorld() {
        //Creates a new world with 600*400 cells with a cell size of 1x1 pixels
        super(600, 400, 1, false);
        bgm.setVolume(50);
        bgm.playLoop();
        //Create the elephant object
        Elephant elephant = new Elephant();
        addObject(elephant, 300, 350);
        
        //Create a label
        scoreLabel = new Label(0,50);
        addObject(scoreLabel, 50, 50);
        createDurian();
   }
    
   public void act() {
        // If the game is over and the user presses space, go back to TitleScreen
        String key = Greenfoot.getKey(); 
        
        if (isGameOver && "space".equals(key)) {
            TitleScreen titleWorld = new TitleScreen();
            Greenfoot.setWorld(titleWorld);
        }
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
   public void createDurian() {
        // 1. ALWAYS spawn the Durian (this keeps the game loop running)
        Durian durian = new Durian();
        int durianSpeed = level;
        durian.setSpeed(durianSpeed);
        
        // (Your existing math for safe spawning)
        int elephantX = getObjects(Elephant.class).get(0).getX();
        int fallFrames = 370 / durianSpeed;
        int reactionFrames = 15;
        int availableFrames = Math.max(0, fallFrames - reactionFrames);
        int elephantSpeed = getObjects(Elephant.class).get(0).getSpeed();
        int maxDistance = (availableFrames * elephantSpeed) + 65;
        
        int minX = Math.max(0, elephantX - maxDistance);
        int maxX = Math.min(600, elephantX + maxDistance);
        if (maxX <= minX) maxX = minX + 1;
        
        int durianX = minX + Greenfoot.getRandomNumber(maxX - minX);
        addObject(durian, durianX, 30);
    
        // 2. CHANCE to spawn an EXTRA Roadroller hazard
        if (Greenfoot.getRandomNumber(100) < 90) { 
            Roadroller roller = new Roadroller();
            roller.setSpeed(durianSpeed + 1); // Make it slightly faster/dangerous
            
            // Spawn the roadroller at a random X anywhere in the world
            int rollerX = Greenfoot.getRandomNumber(600);
            int safeBuffer = 125;
            int attempts = 0; // Safety counter to prevent infinite loops
            do {
                rollerX = Greenfoot.getRandomNumber(600);
                attempts++;
            } while (Math.abs(rollerX - durianX) < safeBuffer && attempts < 50);
            
            addObject(roller, rollerX, 30);
            }
    }
    
    /**
     * End the game and draw 'Game Over'
     */
    public void gameOver()
    {
        bgm.stop();
        isGameOver = true; 
        String insult = "";
        
        if (score == 0)      insult = "Did you even touch the keyboard?";
        else if (score < 10) insult = "My grandma plays better. (Score: " + score + ")";
        else if (score < 25) insult = "Average. (Score: " + score + ")";
        else if (score < 40) insult = "Okay, sweat. (Score: " + score + ")";
        else                 insult = "womp womp(Score: " + score + ")";
        
        Label gameOverLabel = new Label("Game Over",100);
        addObject(gameOverLabel, 300,100);
        
        Label insultLabel = new Label(insult, 40); 
        gameOverLabel.setFillColor(Color.RED);
        gameOverLabel.setLineColor(Color.WHITE);
        addObject(insultLabel, 300, 200); 
        
        Label restartLabel = new Label("Press space for Menu",40);
        addObject(restartLabel, 300,300);
    }
    
}


