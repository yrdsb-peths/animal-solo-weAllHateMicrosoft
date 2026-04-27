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
    private int speed = 8;
    public void act()
    {
        if(Greenfoot.isKeyDown("left")){
            move(-speed);
        }
        else if(Greenfoot.isKeyDown("right")){
            move(speed);
        }
        
        // Remove apple if elephant eats it 
        eat();
    }
    
    public void eat()
    {
        if(isTouching(Apple.class))
        {
            removeTouching(Apple.class);
            MyWorld world = (MyWorld) getWorld();
            world.createApple();
            world.increaseScore();
        }
    }
}
