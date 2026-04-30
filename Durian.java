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
    private int dx = 2+Greenfoot.getRandomNumber(3);
    private int dy = 2+Greenfoot.getRandomNumber(3);
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
        if(getWorld() instanceof TitleScreen)
        {
            bounceLogic();
            if (getWorld() == null) {
                return; 
            }
            checkDurianCollision();
        }
        if(getWorld() instanceof MyWorld)
        {
            fallLogic();
        }
    }
    
    private void fallLogic()
    {
        setLocation(getX(), getY() + speed);
            MyWorld world = (MyWorld) getWorld();
            if(getY() >= world.getHeight())
            {
                world.gameOver();
                world.removeObject(this);
            }
    }
    
    private void bounceLogic()
    {
        if (Greenfoot.mouseClicked(this)) {
            getWorld().removeObject(this);
            return; 
        }
        setLocation(getX() + dx, getY() + dy);

        if (getX() <= 0 || getX() >= getWorld().getWidth() - 1) {
            dx = -dx;
        }
        if (getY() <= 0 || getY() >= getWorld().getHeight() - 1) {
            dy = -dy;
        }
    }
    
    private void checkDurianCollision()
    {

        Durian other = (Durian) getOneIntersectingObject(Durian.class);

        if (other != null) {
            dx = -dx;
            dy = -dy;
            setLocation(getX() + dx * 2, getY() + dy * 2);
        }
    }
    
    public void setSpeed(int spd)
    {
        this.speed = spd;
    }
}
