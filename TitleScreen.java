import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
/**
 * The Title Screen
 * 
 * @author Arron
 * @version April 29 2026
 */
public class TitleScreen extends World
{
    String titleText = "Hungry Elephant";
    Label titleLabel = new Label(titleText, 60);
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels
        super(600, 400, 1); 
        MyWorld.loadDeaths(); 
            
        prepare();
        
        // 2. React to the deaths if they are high
        if (MyWorld.totalDeaths >= 3) {
            titleLabel.setValue("DEAD ELEPHANT");
            titleLabel.setFillColor(Color.RED);
        }
        
        addObject(titleLabel, getWidth()/2, getHeight()/2);
    }

    /**
     * The main world act loop
     */
    public void act()
    {
        // Capture the key press event
        String key = Greenfoot.getKey();
        
        // Start the game if user presses the space bar
        if("space".equals(key))
        {
            MyWorld gameWorld = new MyWorld();
            Greenfoot.setWorld(gameWorld);
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Durian durian = new Durian();
        addObject(durian,298,82);
        Durian durian2 = new Durian();
        addObject(durian2,138,316);
        Durian durian3 = new Durian();
        addObject(durian3,468,304);
        Durian durian4 = new Durian();
        addObject(durian4,122,111);
        Durian durian5 = new Durian();
        addObject(durian5,513,141);
        durian.setLocation(307,90);
        durian.setLocation(292,54);
        durian4.setLocation(111,145);
        durian5.setLocation(470,158);
        durian2.setLocation(243,315);
        durian3.setLocation(358,336);
        durian2.setLocation(208,329);
        durian3.setLocation(395,327);
        durian4.setLocation(115,145);
        durian5.setLocation(475,139);
        durian4.setLocation(101,147);
        durian4.setLocation(107,135);
        Label label = new Label("Press \u2190 and \u2192 to move", 30);
        addObject(label,281,270);
        label.setLocation(301,270);
        Label label2 = new Label("Press space to start", 30);
        addObject(label2,292,341);
        label2.setLocation(296,341);
        // --- EVOLVING MESSAGES ---
        String user = System.getProperty("user.name");
        if (MyWorld.totalDeaths == 4) {
            titleLabel.setValue("Elephant is tired.");
            titleLabel.setFillColor(Color.YELLOW);
        } 
        else if (MyWorld.totalDeaths == 5) {
            titleLabel.setValue("I see you, " + user);
            titleLabel.setFillColor(new Color(255, 100, 0)); // Orange
        } 
        else if (MyWorld.totalDeaths == 6) {
            titleLabel.setValue("THE BOX IS SMALL");
            titleLabel.setFillColor(Color.RED);
            Label warning = new Label("DO NOT PRESS SPACE", 40);
            warning.setFillColor(Color.RED);
            addObject(warning, 300, 50); // Warning at the top
        } 
        else if (MyWorld.totalDeaths == 7) {
            titleLabel.setValue("FINAL WARNING");
            titleLabel.setFillColor(Color.BLACK);
            // Paint the menu background red to show danger
            getBackground().setColor(Color.RED);
            getBackground().fill();
            
            Label warning = new Label("ONE MORE DEATH AND IT'S OVER", 30);
            addObject(warning, 300, 380);
        }
    }
}