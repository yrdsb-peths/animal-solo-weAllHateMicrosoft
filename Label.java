import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Label here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Label extends Actor
{
    private String value;//Text shown on label
    private int fontSize;
    private Color fillColor = Color.BLACK;//Text color
    /**
     * Constructor: Creates a new label with text vale and font size
     */
    public Label(String value, int fontSize) {
        this.value = value;
        this.fontSize = fontSize;
        updateImage();
    }
    
    public Label(int num, int fontSize){
        this(String.valueOf(num), fontSize);
    }
    
    /**
     * Changes the text display of the label
     */
    public void setValue(String value) {
        this.value = value;
        updateImage();
    }
    
    public void setValue(int num) {
       setValue(String.valueOf(num));
    }
    
    private void updateImage() {
        // Paramaters: Text, Font size, Text color, background color (0,0,0,0) is transparent
        setImage(new GreenfootImage(value, fontSize, fillColor, new Color(0, 0, 0, 0)));
    }
}
