package com.neet.MapEditor.Main;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileMapEditor implements ObjectPosition {

    private int boatRow = -1;
    private int boatColumn = -1;
    private int axeRow = -1;
    private int axeColumn = -1;

    @Override
    public int getBoatRow() { return boatRow; }

    @Override
    public int getBoatColumn() { return boatColumn; }

    @Override
    public int getAxeRow() { return axeRow; }

    @Override
    public int getAxeColumn() { return axeColumn; }

    private final int BOAT = 0;
    private final int AXE = 1;

    private int tileSize = 16;
    public int numberOfColumns;
    public int numberOfRows;

    public Cursor cursor;
    public boolean cursorColor = false;

    private int currentNumberOfColumns;
    private int currentNumberOfRows;

    private int magnifyCoefficient = 1;

    private int[][] mapMatrix;
    private int[][] tileType;

    private Image tileset;
    private int numberOfTilesAcross;

    /* Main canvas that updates the entire map. */
    public Canvas canvas;

    /* Shows current part of the map with relevant magnify coefficient. */
    public Canvas currentCanvas;

    /* Stores current image of the entire map including the cursor. */
    private Image mapImage;

    /* This variable will store original image of the entire map. */
    private Image originalMapImage;

    public int offset;
    public int moveSetColumns;
    public int moveSetRows;

    public Image objects;
    public boolean axePlaced = false;
    public boolean boatPlaced = false;

    /**
     * This method reads the numbers from the map file and places them in the mapMatrix declared above
     * */
    public void loadMapFile(String mapFile){
        try{
            InputStream in = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader((in)));

            numberOfColumns = Integer.parseInt(reader.readLine());
            numberOfRows = Integer.parseInt(reader.readLine());
            currentNumberOfColumns = numberOfColumns;
            currentNumberOfRows = numberOfRows;

            mapMatrix = new int[numberOfRows][numberOfColumns];

            String delimiters = "\\s+";  // Characters that separate the individual tokens within a row
            for(int row = 0; row < numberOfRows; row++){
                String line = reader.readLine();
                String[] tokens = line.split(delimiters);
                for(int column = 0; column < numberOfColumns; column++){
                    mapMatrix[row][column] = Integer.parseInt(tokens[column]);
                }
            }

        } catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /**
     * This method retrieves image from resource.
     */
    public void loadImageFiles(String tilesetImage, String itemsImage){
        try{
            tileset = new Image(TileMapEditor.class.getResourceAsStream(tilesetImage));
            objects = new Image(TileMapEditor.class.getResourceAsStream(itemsImage));
            numberOfTilesAcross = (int) tileset.getWidth() / tileSize;

        } catch(Exception exception){
            exception.printStackTrace();
        }
    }

    /* Initialise the two canvases */
    public void initialiseCanvas(){
        canvas = new Canvas(640,640);
        currentCanvas = new Canvas(640,640);
        tileType = new int[numberOfRows][numberOfColumns];
        cursor = new Cursor();

        for(int row = 0; row < numberOfRows; row++){
            for(int column = 0; column < numberOfColumns; column++){
                if(mapMatrix[row][column] == 0)
                    continue;

                int rc = mapMatrix[row][column];

                int r = rc / numberOfTilesAcross;
                int c = rc % numberOfTilesAcross;

                if(r == 0){
                    canvas.getGraphicsContext2D().drawImage(
                            tileset,c*tileSize,0, tileSize, tileSize, column*tileSize, row*tileSize, tileSize, tileSize);
                    currentCanvas.getGraphicsContext2D().drawImage(
                            tileset, c*tileSize,0, tileSize, tileSize, column*tileSize, row*tileSize, tileSize, tileSize);
                    tileType[row][column] = 0;
                } else {
                    canvas.getGraphicsContext2D().drawImage(
                            tileset, c*tileSize, tileSize, tileSize, tileSize,column*tileSize, row*tileSize, tileSize, tileSize);
                    currentCanvas.getGraphicsContext2D().drawImage(
                            tileset, c*tileSize, tileSize, tileSize, tileSize,column * tileSize, row * tileSize, tileSize, tileSize);
                    tileType[row][column] = 1;
                }
            }
        }
        originalMapImage = canvas.snapshot(null, null);
        drawCursorOnCanvas();
        currentCanvas.getGraphicsContext2D().drawImage(
                cursor.imageOption[cursor.current],0,0, tileSize, tileSize, cursor.cursorColumns*tileSize, cursor.cursorRows*tileSize, tileSize, tileSize);
        mapImage = canvas.snapshot(null,null);
    }

    public void zoomInImage(){
        if(magnifyCoefficient < 4) {
            magnifyCoefficient *= 2;
            currentNumberOfColumns /= 2;
            currentNumberOfRows /= 2;
            setOffset(magnifyCoefficient);
            validCursor();
            updateCurrentCanvas();
        }
    }

    public void zoomOutImage(){
        if(magnifyCoefficient > 1){
            magnifyCoefficient /= 2;
            currentNumberOfColumns *= 2;
            currentNumberOfRows *= 2;
            setOffset(magnifyCoefficient);
            validCursor();
            updateCurrentCanvas();
        }
    }

    /**
     * This methods updates cursor position and include it in the current image.
     */
    private void validCursor(){
        replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

        if(cursor.cursorRows < offset){
            cursor.cursorRows = offset;
        } else if(cursor.cursorRows > offset + currentNumberOfRows - 1){
            cursor.cursorRows = offset + currentNumberOfRows - 1;
        }

        if(cursor.cursorColumns < offset){
            cursor.cursorColumns = offset;
        } else if(cursor.cursorColumns > offset + currentNumberOfColumns - 1){
            cursor.cursorColumns = offset + currentNumberOfColumns - 1;
        }

        updateItemsDraw();
        drawCursorOnCanvas();
        mapImage = canvas.snapshot(null, null);
    }

    // Delete cursor image from main canvas by redrawing the original image tile.
    private void replaceTileInCanvasToOriginal(int column, int row){
        canvas.getGraphicsContext2D().drawImage(
                originalMapImage,column * tileSize,row * tileSize, tileSize, tileSize,column * tileSize,row * tileSize, tileSize, tileSize);
    }

    // Draw cursor on current tile on the canvas
    private void drawCursorOnCanvas(){
        canvas.getGraphicsContext2D().drawImage(
                cursor.imageOption[cursor.current], 0, 0, tileSize, tileSize, cursor.cursorColumns*tileSize, cursor.cursorRows*tileSize, tileSize, tileSize);
    }

    // Updates canvas after each move
    private void updateCurrentCanvas(){
        currentCanvas.getGraphicsContext2D().drawImage(
                mapImage,moveSetColumns * tileSize, moveSetRows * tileSize,currentNumberOfColumns * tileSize, currentNumberOfRows * tileSize,0, 0, 640, 640);
    }

    // This method sets the offset when the map is zoomed in
    private void setOffset(int magnifyCoefficient){
        if (magnifyCoefficient == 1){
            offset = 0;
            moveSetColumns = offset;
            moveSetRows = offset;
        } else if( magnifyCoefficient == 2){
            offset = 10;
            moveSetColumns = offset;
            moveSetRows = offset;
        } else {
            offset = 15;
            moveSetColumns = offset;
            moveSetRows = offset;
        }
    }

    private void updateMoveSet(){
        if(moveSetColumns > cursor.cursorColumns){
            moveSetColumns--;
        } else if(moveSetRows > cursor.cursorRows){
            moveSetRows--;
        } else if(moveSetColumns + currentNumberOfColumns - 1 < cursor.cursorColumns){
            moveSetColumns++;
        } else if(moveSetRows + currentNumberOfRows - 1 < cursor.cursorRows){
            moveSetRows++;
        }
    }

    /**
     * This method will change cursor color when the user attempts to place and object in order to
     * indicate the availability of the position.
     */
    private void changeCursorColor(){
        if(cursorColor){
            cursor.current = tileType[cursor.cursorRows][cursor.cursorColumns];
        } else {
            cursor.current = 2;
        }
    }
    public void turningOnCursorColor(){
        cursorColor = true;
        changeCursorColor();
        replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);
        updateItemsDraw();
        drawCursorOnCanvas();
        mapImage = canvas.snapshot(null,null);
        updateCurrentCanvas();
    }
    /**
     * Move cursor up.
     */
    public void cursorUp(){
        if (cursor.cursorRows > 0) {
            replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

            cursor.cursorRows --;
            changeCursorColor();

            updateItemsDraw();
            drawCursorOnCanvas();

            mapImage = canvas.snapshot(null, null);

            updateMoveSet();
            updateCurrentCanvas();
        }
    }

    /**
     * Move cursor down.
     */
    public void cursorDown(){
        if (cursor.cursorRows < numberOfRows - 1 ) {
            replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

            cursor.cursorRows ++;
            changeCursorColor();

            updateItemsDraw();
            drawCursorOnCanvas();

            mapImage = canvas.snapshot(null, null);

            updateMoveSet();
            updateCurrentCanvas();
        }
    }

    /**
     * Move cursor to the left.
     */
    public void cursorLeft() {
        if (cursor.cursorColumns > 0) {
            replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

            cursor.cursorColumns--;
            changeCursorColor();

            updateItemsDraw();
            drawCursorOnCanvas();

            mapImage = canvas.snapshot(null, null);

            updateMoveSet();
            updateCurrentCanvas();
        }
    }
    /**
     * Move cursor to the right.
     */
    public void cursorRight() {
        if (cursor.cursorColumns < numberOfColumns - 1 ) {
            replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

            cursor.cursorColumns++;
            changeCursorColor();

            updateItemsDraw();
            drawCursorOnCanvas();

            mapImage = canvas.snapshot(null, null);

            updateMoveSet();
            updateCurrentCanvas();
        }
    }

    /**
     * Draw the placed items each time the map is changed.
     */
    private void updateItemsDraw() {
        if (axePlaced) {
            canvas.getGraphicsContext2D().drawImage(
                    objects,AXE *tileSize, tileSize, tileSize, tileSize, axeColumn*tileSize,axeRow*tileSize, tileSize, tileSize);
        }
        if (boatPlaced) {
            canvas.getGraphicsContext2D().drawImage(
                    objects,BOAT*tileSize, tileSize, tileSize, tileSize,boatColumn * tileSize,boatRow * tileSize, tileSize, tileSize);
        }
    }

    /**
     * This method places the axe and handles necessary cursor functions after the 'A' button is pressed.
     *
     * @return handleType Return the type of setting result.
     */
    public int handleSetAxeRequest() {
        int handleType;
        cursorColor = true;
        changeCursorColor();

        replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);

        // return type: Position invalid
        if (tileType[cursor.cursorRows][cursor.cursorColumns] == 1) {
            handleType = 1;
        }
        // return type: Axe put successful
        else {
            if (axePlaced) {
                replaceTileInCanvasToOriginal(axeColumn, axeRow);

                tileType[axeRow][axeColumn] = 0;
                tileType[cursor.cursorRows][cursor.cursorRows] = 0;

                handleType = 2;
            }
            else {
                handleType = 0;
            }

            axePlaced = true;
            tileType[cursor.cursorRows][cursor.cursorColumns] = 0;
            cursorColor = false;
            axeRow = cursor.cursorRows;
            axeColumn = cursor.cursorColumns;
        }

        updateItemsDraw();
        drawCursorOnCanvas();
        mapImage = canvas.snapshot(null, null);
        updateCurrentCanvas();
        return handleType;
    }

    /**
     * This method places the boat and handles necessary cursor functions after the 'A' button is pressed.
     *
     * @return handleType Return the type of setting result.
     */
    public int handleSetBoatRequest() {
        int handleType;
        cursorColor = true;
        changeCursorColor();
        replaceTileInCanvasToOriginal(cursor.cursorColumns, cursor.cursorRows);
        // return type: Position invalid
        if (tileType[cursor.cursorRows][cursor.cursorColumns] == 0) {
            handleType = 1;
            cursor.current = 1;
        }
        // return type: Boat put successful
        else {
            if (boatPlaced) {
                replaceTileInCanvasToOriginal(boatColumn, boatRow);
                cursor.current = 0;
                tileType[boatRow][boatColumn] = 1;
                tileType[cursor.cursorRows][cursor.cursorColumns] = 1;
                cursorColor = false;
                handleType = 2;
            }
            else {
                handleType = 0;
            }

            boatPlaced = true;
            tileType[cursor.cursorRows][cursor.cursorColumns] = 1;
            boatRow = cursor.cursorRows;
            boatColumn = cursor.cursorColumns;

        }
        updateItemsDraw();
        drawCursorOnCanvas();
        mapImage = canvas.snapshot(null, null);
        updateCurrentCanvas();
        
        return handleType;
    }
}
