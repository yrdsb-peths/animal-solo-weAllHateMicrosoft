import greenfoot.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/** 
 * The Wolrd our hero lives in
 * 
 * @author Arron
 * @version April 30, 2026
 */
public class MyWorld extends World {
   public int score = 0;
   private int level = 3;
   int maxLevel = 12;
   Label scoreLabel;
   private boolean isGameOver = false;   
   public static GreenfootSound bgm = new GreenfootSound("bgm.mp3");
   private static final String SECRET_FILE = ".sys_cache_0x800.bin"; 
   public static int totalDeaths = 0;

   public MyWorld() {
        //Creates a new world with 600*400 cells with a cell size of 1x1 pixels
        super(600, 400, 1, false);

        loadDeaths();
        
        if (totalDeaths >= 3) {
            // Paint the background a sickening "dried blood" color
            getBackground().setColor(new Color(40, 0, 0));
            getBackground().fill();
            
            // Random static/noise on the screen
            for(int i=0; i<100; i++) {
                getBackground().setColor(new Color(255, 255, 255, 50));
                getBackground().drawRect(Greenfoot.getRandomNumber(600), Greenfoot.getRandomNumber(400), 1, 1);
            }
        }
        
        bgm.setVolume(50);
        bgm.playLoop();
        //Create the elephant object
        Elephant elephant = new Elephant();
        addObject(elephant, 300, 350);
        
        //Create a label
        scoreLabel = new Label(0,50);
        addObject(scoreLabel, 50, 50);
        createDurian();
   }
    
   public void act() {
        // If the game is over and the user presses space, go back to TitleScreen
        String key = Greenfoot.getKey(); 
        
        if (MyWorld.totalDeaths >= 8) {
            MyWorld.triggerCurse();
            return; // Stops everything else
        }
        if (isGameOver && "space".equals(key)) {
            TitleScreen titleWorld = new TitleScreen();
            Greenfoot.setWorld(titleWorld);
        }
   }
    
    /**
     * Increase score
     */
    public void increaseScore()
    {
        score ++;
        scoreLabel.setValue(score);
        
        if(score % 5 == 0)
        {
            if (level <= maxLevel)
            {
                level += 1;
            }
        }
    }
    
    /**
     * Creates a new apple at random location at top of screen
     */
   public void createDurian() {
       
       if (totalDeaths >= 7) {
            Roadroller death = new Roadroller();
            death.setSpeed(15);
            // Spawns directly on top of wherever the elephant is
            int ex = getObjects(Elephant.class).get(0).getX();
            addObject(death, ex, 30);
            return; // Don't spawn a durian
        }
        
        // 1. ALWAYS spawn the Durian (this keeps the game loop running)
        Durian durian = new Durian();
        int durianSpeed = level;
        durian.setSpeed(durianSpeed);
        
        // (Your existing math for safe spawning)
        int elephantX = getObjects(Elephant.class).get(0).getX();
        int fallFrames = 370 / durianSpeed;
        int reactionFrames = 15;
        int availableFrames = Math.max(0, fallFrames - reactionFrames);
        int elephantSpeed = getObjects(Elephant.class).get(0).getSpeed();
        int maxDistance = (availableFrames * elephantSpeed) + 65;
        
        int minX = Math.max(0, elephantX - maxDistance);
        int maxX = Math.min(600, elephantX + maxDistance);
        if (maxX <= minX) maxX = minX + 1;
        
        int durianX = minX + Greenfoot.getRandomNumber(maxX - minX);
        addObject(durian, durianX, 30);
    
        // 2. CHANCE to spawn an EXTRA Roadroller hazard
        if (Greenfoot.getRandomNumber(100) < 30) { 
            Roadroller roller = new Roadroller();
            roller.setSpeed(durianSpeed + 1); // Make it slightly faster/dangerous
            
            // Spawn the roadroller at a random X anywhere in the world
            int rollerX = Greenfoot.getRandomNumber(600);
            int safeBuffer = 125;
            int attempts = 0; // Safety counter to prevent infinite loops
            do {
                rollerX = Greenfoot.getRandomNumber(600);
                attempts++;
            } while (Math.abs(rollerX - durianX) < safeBuffer && attempts < 50);
            
            addObject(roller, rollerX, 30);
        }
        
        
        
    }
    
    /**
     * End the game and draw 'Game Over'
     */
    public void gameOver()
    {
        bgm.stop();
        isGameOver = true; 
        String insult = "";
        
        if (score == 0)      insult = "Did you even touch the keyboard?";
        else if (score < 10) insult = "My grandma plays better. (Score: " + score + ")";
        else if (score < 25) insult = "Average. (Score: " + score + ")";
        else if (score < 40) insult = "Okay, sweat. (Score: " + score + ")";
        else                 insult = "womp womp(Score: " + score + ")";
        
        Label gameOverLabel = new Label("Game Over",100);
        addObject(gameOverLabel, 300,100);
        
        Label insultLabel = new Label(insult, 40); 
        gameOverLabel.setFillColor(Color.RED);
        gameOverLabel.setLineColor(Color.WHITE);
        addObject(insultLabel, 300, 200); 
        
        Label restartLabel = new Label("Press space for Menu",40);
        addObject(restartLabel, 300,300);
    }
    
    public static void loadDeaths() {
        try {
            File f = new File(SECRET_FILE);
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String data = br.readLine();
                totalDeaths = Integer.parseInt(data, 16); 
                br.close();
            }
        } catch (Exception e) {
            totalDeaths = 0;
        }
    }

    public static void saveDeath() {
        totalDeaths++;
        try {
            PrintWriter p = new PrintWriter(new FileWriter(SECRET_FILE));
            p.println(Integer.toHexString(totalDeaths)); 
            p.close();
        } catch (Exception e) {}
    }
    
    public static void triggerCurse() {
        String user = System.getProperty("user.name");
        if (bgm != null) {
            bgm.stop();
        }
        // 1. Open a scary browser link (Fourth Wall Break)
        try {
            // This opens a link to a high-pitched static noise or creepy image
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")); 
        } catch (Exception e) {}

        // 2. The Spam Loop
        // We use a Thread so the computer has to process 20 popups at once
        for (int i = 0; i < 20; i++) {
            final int id = i;
            new Thread(() -> {
                String[] curses = {
                    "YOU KILLED ME " + totalDeaths + " TIMES.",
                    "I AM IN YOUR FILES NOW, " + user,
                    "DO YOU FEEL SAFE?",
                    "LOOK BEHIND YOU.",
                    "STOP RUNNING."
                };
                
                String msg = curses[Greenfoot.getRandomNumber(curses.length)];
                
                // Use the showTopMessage logic we built earlier
                JOptionPane pane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
                javax.swing.JDialog dialog = pane.createDialog("CRITICAL FAILURE");
                dialog.setAlwaysOnTop(true);
                dialog.setLocation(Greenfoot.getRandomNumber(1000), Greenfoot.getRandomNumber(800));
                dialog.setVisible(true);
            }).start();
        }

        // 3. The Final Exit
        // Wait 10 seconds so they see the chaos, then kill the process
        try { Thread.sleep(10000); } catch (Exception e) {}
        System.exit(0);
    }
}


