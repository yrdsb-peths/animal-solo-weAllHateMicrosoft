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
    private GreenfootSound juiceSound = new GreenfootSound("juice.mp3");
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
    
        
    public void checkHazard() {
        Roadroller roller = (Roadroller) getOneIntersectingObject(Roadroller.class);
        if(roller != null && !isDead) {
            isDead = true;
            MyWorld world = (MyWorld) getWorld();
            world.bgm.stop();

            // --- FIXING THE RECTANGLE: Make it a Splatter ---
            int w = getImage().getWidth() + 100;
            int h = 40;
            GreenfootImage deadImg = new GreenfootImage(w, h);
            
            // Draw a dark red base puddle (irregular)
            deadImg.setColor(new Color(100, 0, 0)); 
            deadImg.fillOval(10, 15, w - 20, 20);
            
            // Add some brighter red "blobs" on top
            deadImg.setColor(new Color(170, 0, 0));
            for(int i = 0; i < 5; i++) {
                int rx = Greenfoot.getRandomNumber(w - 40);
                int ry = 10 + Greenfoot.getRandomNumber(15);
                deadImg.fillOval(rx, ry, 30, 15);
            }
            
            // Scatter the bones (Off-white, smaller, irregular)
            deadImg.setColor(new Color(230, 230, 230));
            for(int i = 0; i < 8; i++) {
                int bx = Greenfoot.getRandomNumber(w - 20);
                int by = 15 + Greenfoot.getRandomNumber(10);
                deadImg.fillOval(bx, by, 8, 4); // Smaller bone chunks
            }
            
            setImage(deadImg);
            setLocation(getX(), getY() + 35);
            roller.smash(this);

            // Spawning Gore particles
            for (int i = 0; i < 40; i++) {
                world.addObject(new Tomato(), getX(), getY());
                if(i % 3 == 0) world.addObject(new Debris(0), getX(), getY());
                if(i % 4 == 0) world.addObject(new Debris(1), getX(), getY());
                if(i % 10 == 0) world.addObject(new Debris(2), getX(), getY());
            }

            world.addObject(new Eyeball(), getX(), getY());
            world.addObject(new Eyeball(), getX()+5, getY());
            juiceSound.play();
            //world.triggerShake(50);
            world.gameOver();
        }
    }
    
    public int getSpeed()
    {
        return speed;
    }
}
