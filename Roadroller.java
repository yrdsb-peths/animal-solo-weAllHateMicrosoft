import greenfoot.*;

public class Roadroller extends Actor
{
    private int speed = 3;
    private boolean hasSmashed = false; 

    public Roadroller()
    {
        GreenfootImage img = new GreenfootImage("roadroller.png");
        img.scale(80,80);
        setImage(img);
    }

    public void act()
    {
        if (!hasSmashed)
        {
            setLocation(getX(), getY() + speed);
            
            if(getY() >= getWorld().getHeight() - 1)
            {
                getWorld().removeObject(this);
            }
        }
    }

    public void setSpeed(int spd)
    {
        this.speed = spd;
    }
    
    public void smash(Elephant e)
    {
        hasSmashed = true;
        
        int roadrollerHalf = getImage().getHeight() / 2;
        int elephantHalf = e.getImage().getHeight() / 2;
        
        int yOffset = 20; 

        setLocation(e.getX(), e.getY() - elephantHalf - roadrollerHalf + yOffset);
    }
}