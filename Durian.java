import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Foor for our elephant
 * 
 * @author Arron
 * @version April 27, 2026
 */
public class Durian extends Actor
{
    private int speed = 3;
    GreenfootImage durianImg = new GreenfootImage("durian.png");
    
    public Durian()
    {
        durianImg.scale(50,70);
        setImage(durianImg);
    }
    /**
     * Act - do whatever the Apple wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        setLocation(getX(), getY() + speed);
         MyWorld world = (MyWorld) getWorld();
        if(getY() >= world.getHeight())
        {
            world.gameOver();
            world.removeObject(this);
        }
    }
}
