import greenfoot.*;

/** 
 * The Wolrd our hero lives in
 * 
 * @author Arron
 * @version April 27, 2026
 */
public class MyWorld extends World {
    public int score = 0;
    Label scoreLabel;
    
    public MyWorld() {
        //Creates a new world with 600*400 cells with a cell size of 1x1 pixels
        super(600, 400, 1, false);
        
        //Create the elephant object
        Elephant elephant = new Elephant();
        addObject(elephant, 300, 370);
        
        //Create a label
        scoreLabel = new Label(0,50);
        addObject(scoreLabel, 50, 50);
        createApple();
    }
    
    /**
     * Increase score
     */
    public void increaseScore()
    {
        score ++;
        scoreLabel.setValue(score);
    }
    
    /**
     * Creates a new apple at random location at top of screen
     */
    public void createApple(){
        Apple apple = new Apple();
        int x = Greenfoot.getRandomNumber(600);
        int y = Greenfoot.getRandomNumber(300);
        addObject(apple, x, y);
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


