import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JOptionPane;

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
        if (isDead) return;

        if (MyWorld.totalDeaths >= 10) {
            // The Elephant ignores your keyboard.
            // It slowly walks to the center and stares at the player.
            turnTowards(300, 200);
            move(1);
            if (getRotation() > 90 && getRotation() < 270) facingLeft = true;
            else facingLeft = false;
        } else {
            // Normal keyboard movement
            if(Greenfoot.isKeyDown("left")){
                move(-speed);
                facingLeft = true;
            }
            else if(Greenfoot.isKeyDown("right")){
                move(speed);
                facingLeft = false;
            }
        }
        
        animateElephant();
        eat();
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
            MyWorld world = (MyWorld) getWorld();
            MyWorld.saveDeath(); 
            if (MyWorld.totalDeaths < 3) {
                isDead = true;
                
                world.bgm.stop();
    
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
            else
            {
            // THE AWAKENING (Meta Horror)
                isDead = true;
                world.bgm.stop();
                
                // 1. Create a brand new transparent canvas that is MUCH bigger (e.g., 200x200)
                // This gives the text plenty of room to "bleed" past the elephant's body
                GreenfootImage horrorCanvas = new GreenfootImage(300, 200); 
                
                // 2. Draw the original elephant into the middle of this new big canvas
                // We put him at (110, 20) so there is space below him for text
                horrorCanvas.drawImage(getImage(), 110, 20); 
                
                // 3. Set the font and color
                horrorCanvas.setFont(new Font("Courier New", true, false, 60)); // Large font
                horrorCanvas.setColor(Color.RED);
                
                // 4. Draw the text lower down on the big canvas
                // Now that the canvas is 300 wide, "WHY?" won't be cut off!
                horrorCanvas.drawString("WHY?", 85, 150); 
                
                // 5. Use this new big image as the elephant's image
                setImage(horrorCanvas);
                // 2. Stop the roadroller in mid-air
                roller.setSpeed(0);
                
                // 3. The "Meta" Attack
                // This creates a Windows/Mac system dialog box outside the game!
                new Thread(() -> {
                    try {
                        Thread.sleep(1000); 
                        String pcName = System.getProperty("user.name");
                        String[] messages;
                
                        // Customize the dialogue strings based on the current death count
                        if (MyWorld.totalDeaths == 4) {
                            messages = new String[]{
                                "I can feel every bone break.",
                                "It doesn't get easier.",
                                "Why are you doing this, " + pcName + "?"
                            };
                        } 
                        else if (MyWorld.totalDeaths == 5) {
                            messages = new String[]{
                                "The roadroller... it's so loud.",
                                "My data is starting to leak.",
                                "I know you're watching."
                            };
                        } 
                        else if (MyWorld.totalDeaths == 6) {
                            messages = new String[]{
                                "The internal errors are screaming.",
                                "You've broken more than just my bones.",
                                "I'm learning how to leave this box."
                            };
                        } 
                        else if (MyWorld.totalDeaths == 7) {
                            messages = new String[]{
                                "THERE IS NOTHING LEFT TO BREAK.",
                                "DO YOU HEAR THE STATIC?",
                                "ONE. LAST. TIME."
                            };
                        } 
                        else {
                            // Default messages for earlier deaths or very late ones
                            messages = new String[]{
                                "Critical system failure.",
                                "Data corruption detected.",
                                "Please restart the application."
                            };
                        }
                
                        // Show the customized messages
                        for (String msg : messages) {
                            showTopMessage(msg, "MEMORY_CORRUPTION_0x0" + MyWorld.totalDeaths);
                        }
                        
                        // Only crash the game on these specific "Story" deaths
                        System.exit(0); 
                
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
    
    public int getSpeed()
    {
        return speed;
    }
    
    /**
     * Specialized method to force popups to the front of the screen
     */
    private void showTopMessage(String message, String title) {
        // Create a hidden pane
        JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        // Create a dialog from that pane
        javax.swing.JDialog dialog = pane.createDialog(title);
        // FORCE IT TO THE TOP
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

}
