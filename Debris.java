import greenfoot.*;

public class Debris extends Actor {
    private double vx, vy, exactX, exactY;
    private int rotationSpeed;
    private int type; // 0 = Bone, 1 = Meat, 2 = Intestine
    private int floorY;

    public Debris(int type) {
        this.type = type;
        rotationSpeed = Greenfoot.getRandomNumber(20) - 10;
        vx = (Greenfoot.getRandomNumber(30) - 15);
        vy = -(Greenfoot.getRandomNumber(25) + 10);
        floorY = 350 + Greenfoot.getRandomNumber(40);
        
        generateGoreImage();
    }

    private void generateGoreImage() {
        GreenfootImage img;
        if (type == 0) { // BONE SHARDS
            img = new GreenfootImage(12, 12);
            img.setColor(new Color(240, 240, 240)); // Off-white
            // Draw a jagged shard
            int[] xPoints = {2, 10, 8, 2};
            int[] yPoints = {0, 4, 10, 8};
            img.fillPolygon(xPoints, yPoints, 4);
        } 
        else if (type == 1) { // MEAT CHUNKS
            int size = Greenfoot.getRandomNumber(10) + 10;
            img = new GreenfootImage(size, size);
            img.setColor(new Color(120, 0, 0)); // Deep muscle red
            img.fillOval(0, 0, size, size);
            img.setColor(new Color(180, 20, 20)); // Lighter red texture
            img.fillOval(size/4, size/4, size/2, size/2);
        } 
        else { // INTESTINES (The "Gross" one)
            img = new GreenfootImage(30, 20);
            img.setColor(new Color(255, 160, 160)); // Sickly pink
            // Draw a "noodle" shape
            img.fillOval(0, 5, 15, 10);
            img.fillOval(10, 5, 15, 10);
            img.setColor(new Color(200, 120, 120));
            img.drawOval(0, 5, 15, 10);
            img.drawOval(10, 5, 15, 10);
        }
        setImage(img);
    }

    public void addedToWorld(World w) {
        exactX = getX(); exactY = getY();
    }

    public void act() {
        vy += 1.2; // Gravity
        exactX += vx;
        exactY += vy;
        setRotation(getRotation() + rotationSpeed);
        setLocation((int)exactX, (int)exactY);

        if (getY() >= floorY || getX() <= 0 || getX() >= 599) {
            stain();
        }
    }

    private void stain() {
        GreenfootImage bg = getWorld().getBackground();
        bg.drawImage(getImage(), getX() - getImage().getWidth()/2, getY() - getImage().getHeight()/2);
        getWorld().removeObject(this);
    }
}