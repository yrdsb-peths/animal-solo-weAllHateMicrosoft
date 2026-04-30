import greenfoot.*;

public class Eyeball extends Actor {
    private double vx = (Greenfoot.getRandomNumber(10) - 5);
    private double vy = -15;
    private double exactX, exactY;

    public Eyeball() {
        GreenfootImage img = new GreenfootImage(16, 16);
        img.setColor(Color.WHITE);
        img.fillOval(0, 0, 16, 16);
        img.setColor(Color.BLUE); // Iris
        img.fillOval(8, 4, 6, 6);
        img.setColor(Color.BLACK); // Pupil
        img.fillOval(10, 6, 2, 2);
        setImage(img);
    }

    public void act() {
        vy += 1.0; // Gravity
        exactX += vx;
        exactY += vy;
        
        // --- NEW: Wall Bounce Logic ---
        if (exactX <= 0 || exactX >= getWorld().getWidth() - 1) {
            vx = -vx; // Reverse horizontal direction
            // Nudge it back inside so it doesn't get stuck
            if (exactX <= 0) exactX = 1;
            else exactX = getWorld().getWidth() - 2;
        }

        setRotation(getRotation() + (int)vx * 2); // Spin based on speed
        setLocation((int)exactX, (int)exactY);

        // Floor Bounce Logic
        if (getY() >= 385) {
            exactY = 385; // Reset to floor height
            vy = -vy * 0.5; // Bounce with energy loss
            vx *= 0.9;     // Friction on floor
            
            // If it's barely moving, stop and stain
            if (Math.abs(vy) < 1 && Math.abs(vx) < 1) { 
                GreenfootImage bg = getWorld().getBackground();
                // Draw the eye onto the background
                bg.drawImage(getImage(), getX() - 8, getY() - 8);
                getWorld().removeObject(this);
            }
        }
    }
}