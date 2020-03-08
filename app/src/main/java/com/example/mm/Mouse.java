package com.example.mm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Mouse {
    private GameView mGameView;
    private Maze mMaze;
    private Bitmap mBitMapMouse;
    private int mXSpeed = 1;
    private int mYSpeed = 1;
    private int x = 25;
    private int y = 25;

    public Mouse(GameView game_view) {
        mGameView = game_view;
        mMaze = new Maze();
        mBitMapMouse = BitmapFactory.decodeResource(mGameView.getResources(), R.drawable.mouse);
    }

    public Bitmap mouseTurnLeft(){
        Bitmap temp;
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        temp = Bitmap.createBitmap(mBitMapMouse, 0, 0, mBitMapMouse.getWidth(), mBitMapMouse.getHeight(), matrix, true);
        return temp;
    }

    public void mouseTurnRight(){

    }

    public void mouseForward() {

    }

    private void update() {
        x = x + mXSpeed;
        y = y + mYSpeed;
    }

    public void onDraw(Canvas canvas){
        //update();
        canvas.drawBitmap(mouseTurnLeft(), x, y, null);
    }
}
