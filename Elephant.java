import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Elephant the elephant is the most elephantine elephant an elephant has seen, he is also our hero the player controls. All hail the elephatine elephant, elephant.
 * 
 * @author Arron
 * @version April 30, 2026
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
    private int speed = 14;
    private boolean facingLeft = false;
    SimpleTimer animationTimer = new SimpleTimer();
    private boolean isDead = false; 
    
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
        
        GreenfootImage baseFrame = facingLeft ? idleLeft[imageIndex%8] : idleRight[imageIndex%8];//Turn left or right
        GreenfootImage fatFrame = new GreenfootImage(baseFrame); //Make a copy of the frame
        
        int extraFat = 0;
        if (getWorld() instanceof MyWorld) {
            int currentScore = ((MyWorld)getWorld()).score;
            extraFat = Math.min(120, currentScore * 4);  // Grows 4 pixels wider per durian, max 200 pixels wide
        }
        
        fatFrame.scale(80 + extraFat, 80);
        setImage(fatFrame); 
        imageIndex++;
    }
    
    public void act()
    {
        if (isDead) {
            return; 
        }
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
        //Get killed if crashes into a hazard
        checkHazard();
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
    
        
    public void checkHazard()
    {
        Roadroller roller = (Roadroller) getOneIntersectingObject(Roadroller.class);
        if(roller != null)
        {
            isDead = true; 
            
            // 1. Get the current width (so we keep the "fatness" from the score)
            int currentWidth = getImage().getWidth();
            
            // 2. Create the squashed image
            GreenfootImage squashedImg = new GreenfootImage(getImage());
            
            // 3. Scale it: Width = current fatness + 30px splatter, Height = 15px flat
            squashedImg.scale(currentWidth + 30, 15); 
            setImage(squashedImg);
    
            // 4. Move the actor down so it stays on the ground
            // Since height goes from 80 to 15, we move down by roughly 32 pixels
            setLocation(getX(), getY() + 32);
     
            // 5. Tell the roller to land on us
            roller.smash(this);
            
            MyWorld world = (MyWorld) getWorld();
            world.gameOver();
        }
    }
    
    public int getSpeed()
    {
        return speed;
    }
}
