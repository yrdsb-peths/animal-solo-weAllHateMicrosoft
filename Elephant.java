import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Elephant the elephant is the most elephantine elephant an elephant has seen, he is also our hero the player controls. All hail the elephatine elephant, elephant.
 * 
 * @author Arron
 * @version April 27, 2026
 */
public class Elephant extends Actor
{
    /**
     * Act - do whatever the Elephant wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private GreenfootSound elephantSound = new GreenfootSound("elephant.wav");
    private GreenfootImage[] idleLeft = new GreenfootImage[8];
    private GreenfootImage[] idleRight = new GreenfootImage[8];
    private int speed = 8;
    private boolean facingLeft = false;
    SimpleTimer animationTimer = new SimpleTimer();
    
    /*
     * Consturctor of elephant
     */
    public Elephant()
    {
        for(int i = 0; i < 8; i++)
        {
            idleLeft[i] = new GreenfootImage("elephantIdle/Idle" + i + ".png");
            idleLeft[i].scale(80,80);
            idleLeft[i].mirrorHorizontally();
        }
        
        for(int i = 0; i < 8; i++)
        {
            idleRight[i] = new GreenfootImage("elephantIdle/Idle" + i + ".png");
            idleRight[i].scale(80,80);
        }
        
        animationTimer.mark();
        
        setImage(idleRight[0]);
        elephantSound.setVolume(65);
    }
    
    /*
     * Animate the elephant
     */
    int imageIndex = 0;
    public void animateElephant()
    {
        if(animationTimer.millisElapsed() < 100)
        {
            return;
        }
        animationTimer.mark();
        if (facingLeft)
        {
            setImage(idleLeft[imageIndex%8]);
        }
        else
        {
            setImage(idleRight[imageIndex%8]);
        }
        imageIndex++;
    }
    
    public void act()
    {
        animateElephant();
        if(Greenfoot.isKeyDown("left")){
            move(-speed);
            facingLeft = true;
        }
        else if(Greenfoot.isKeyDown("right")){
            move(speed);
            facingLeft = false;
        }
        
        // Remove DURIAN if elephant eats it 
        eat();
    }
    
    public void eat()
    {
        if(isTouching(Durian.class))
        {
            removeTouching(Durian.class);
            MyWorld world = (MyWorld) getWorld();
            world.createDurian();
            world.increaseScore();
  
            if (elephantSound.isPlaying()) {
                elephantSound.stop();
            }
            elephantSound.play();
        }
    }
}
