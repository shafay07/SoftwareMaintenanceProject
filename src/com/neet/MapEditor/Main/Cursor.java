package com.neet.MapEditor.Main;

import javafx.scene.image.Image;

public class Cursor {
    /**
     * The variable loads the cursor images.
     */
    public Image[] imageOption;


    /**
     * The variable decides which image to use. Gray, red or green cursor.
     */
    public int current = 2;

    public int cursorColumns;
    public int cursorRows;

    public Cursor() {
        imageOption = new Image[3];
        imageOption[0] = new Image(Cursor.class.getResourceAsStream("/Sprites/cursor_green.gif"));
        imageOption[1] = new Image(Cursor.class.getResourceAsStream("/Sprites/cursor_red.gif"));
        imageOption[2] = new Image(Cursor.class.getResourceAsStream("/Sprites/cursor_normal.gif"));
        cursorColumns = 17;
        cursorRows = 17;
    }
}
