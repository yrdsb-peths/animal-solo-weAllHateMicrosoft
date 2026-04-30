import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Tomato extends Actor
{
    private double vx; // X velocity
    private double vy; // Y velocity
    private double exactX;
    private double exactY;
    private int floorY; // The randomly assigned Y coordinate where this drop hits the ground

    public Tomato()
    {
        // Randomize blood drop size
        int size = Greenfoot.getRandomNumber(10) + 5; 
        
        // Draw a dark red circle dynamically
        GreenfootImage img = new GreenfootImage(size, size);
        img.setColor(new Color(180, 0, 0)); // Dark red
        img.fillOval(0, 0, size, size);
        setImage(img);
        
        // Randomize explosion speed and direction
        vx = (Greenfoot.getRandomNumber(30) - 15); 
        vy = -(Greenfoot.getRandomNumber(20) + 5); 
        
        // Randomize floor level between Y=340 and Y=395 so they don't form a perfect straight line
        floorY = 340 + Greenfoot.getRandomNumber(55);
    }
    
    // We use addedToWorld to get the exact starting X and Y
    public void addedToWorld(World w)
    {
        exactX = getX();
        exactY = getY();
    }

    public void act()
    {
        // 1. Apply gravity
        vy += 1.5; 
        
        // 2. Move the blood
        exactX += vx;
        exactY += vy;
        setLocation((int)exactX, (int)exactY);
        
        // 3. If it hits the left or right walls of the screen, stain the wall!
        if (getX() <= 0 || getX() >= getWorld().getWidth() - 1) 
        {
            stain();
            return;
        }
        
        // 4. If it hits the floor, make it a puddle and stain!
        if (getY() >= floorY) 
        {
            // Flatten the image to look like a splattered puddle
            GreenfootImage img = getImage();
            int newWidth = img.getWidth() * 2 + Greenfoot.getRandomNumber(10);
            int newHeight = Math.max(2, img.getHeight() / 2);
            img.scale(newWidth, newHeight); 
            
            stain();
            return;
        }
    }
    
    /**
     * Stamps the blood image permanently onto the background and deletes the actor
     */
    private void stain()
    {
        // Get the world's permanent background
        GreenfootImage bg = getWorld().getBackground();
        
        // Draw the blood at its exact current location
        int drawX = getX() - getImage().getWidth() / 2;
        int drawY = getY() - getImage().getHeight() / 2;
        bg.drawImage(getImage(), drawX, drawY);
        
        // Remove the actor so it doesn't cause lag
        getWorld().removeObject(this);
    }
}