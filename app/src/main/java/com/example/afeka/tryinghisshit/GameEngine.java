package com.example.afeka.tryinghisshit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afeka.tryinghisshit.util.Generator;
import com.example.afeka.tryinghisshit.util.PrintGrid;
import com.example.afeka.tryinghisshit.views.grid.Cell;


public class GameEngine {
    private static GameEngine instance;

    public static int BOMB_NUMBER = 4;
    public static int WIDTH = 10;
    public static int HEIGHT = 10;

    int time = 0;
    TextView timer;
    long startTime = 0L;
    Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private int bombs, width, height;

    public GameEngine(int bombs, int width, int height, int time){
        BOMB_NUMBER = bombs;
        this.width = width;
        this.height = height;
        this.time = time;

        setTimer();
    }

    public void setTimer(){
        try {
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 1000);
        }
        catch (Exception ex){
            Log.e("setTimer", ex.getMessage());
        }
    }

    public void stopTimer(){
        try {
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
        }
        catch (Exception ex){
            Log.e("stopTimer", ex.getMessage());
        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            try {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                timer.setText(String.format("%02d", mins) + ":" + String.format("%02d", secs));
                customHandler.postDelayed(this, 1000);
            }
            catch (Exception ex){
                Log.e("runnable", ex.getMessage());
            }
        }
    };

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private Context context;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];

    public static GameEngine getInstance() {
        if( instance == null ){
            instance = new GameEngine();
        }
        return instance;
    }


    private GameEngine(){ }

    public void createGrid(Context context){
        Log.e("GameEngine","createGrid is working");
        this.context = context;

        // create the grid and store it
        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER,WIDTH, HEIGHT);
        //PrintGrid.print(GeneratedGrid,WIDTH,HEIGHT);
        setGrid(context,GeneratedGrid);
    }

    private void setGrid( final Context context, final int[][] grid ){
        for( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( MinesweeperGrid[x][y] == null ){
                    MinesweeperGrid[x][y] = new Cell( context , x,y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position) {
        int x = position % WIDTH;
        int y = position / WIDTH;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt( int x , int y ){
        return MinesweeperGrid[x][y];
    }

    public void click( int x , int y ){
        if( x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x,y).isClicked() ){
            getCellAt(x,y).setClicked();

            if( getCellAt(x,y).getValue() == 0 ){
                for( int xt = -1 ; xt <= 1 ; xt++ ){
                    for( int yt = -1 ; yt <= 1 ; yt++){
                        if( xt != yt ){
                            click(x + xt , y + yt);
                        }
                    }
                }
            }

            if( getCellAt(x,y).isBomb() ){
                onGameLost();
            }
        }

        checkEnd();
    }

    private boolean checkEnd(){
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        for ( int x = 0 ; x < WIDTH ; x++ ){
            for( int y = 0 ; y < HEIGHT ; y++ ){
                if( getCellAt(x,y).isRevealed() || getCellAt(x,y).isFlagged() ){
                    notRevealed--;
                }

                if( getCellAt(x,y).isFlagged() && getCellAt(x,y).isBomb() ){
                    bombNotFound--;
                }
            }
        }

        if( bombNotFound == 0 && notRevealed == 0 ){
            Toast.makeText(context,"Game won", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void flag( int x , int y ){
        boolean isFlagged = getCellAt(x,y).isFlagged();
        getCellAt(x,y).setFlagged(!isFlagged);
        getCellAt(x,y).invalidate();
    }



    private void getOutOfLevel() {
    }

    private void onGameLost(){
        // handle lost game
        Toast.makeText(context,"Game lost", Toast.LENGTH_SHORT).show();
        try {
            stopTimer();
        }
        catch (Exception ex){
            Log.e("finishGame", ex.getMessage());
        }
        for ( int x = 0 ; x < WIDTH ; x++ ) {
            for (int y = 0; y < HEIGHT; y++) {
                getCellAt(x,y).setRevealed();
            }
        }
    }
}