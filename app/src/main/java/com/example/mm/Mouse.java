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

    private Matrix mMatrix;

    public Mouse(GameView game_view) {
        mGameView = game_view;
        mMaze = new Maze();
        mBitMapMouse = BitmapFactory.decodeResource(mGameView.getResources(), R.drawable.mouse);
        mMatrix = new Matrix();
    }

    public void mouseTurnLeft(Canvas canvas){
        float angle = (float) x;
        mMatrix.reset();
        mMatrix.postTranslate(- mBitMapMouse.getWidth()/2,  - mBitMapMouse.getHeight()/2);
        mMatrix.postRotate(angle);
        mMatrix.postTranslate(mMaze.mCenterPoints[0][0].x,  mMaze.mCenterPoints[0][0].y);
        canvas.drawBitmap(mBitMapMouse, mMatrix, null);
    }

    public void mouseTurnRight(){

    }

    public void mouseForward() {

    }

    private void update() {
        if ( x >= 0 &x <= 90) {
            x = x + mXSpeed;
        } else if (x > 90){
            x = x - mXSpeed;
        }

        y = y + mYSpeed;
    }

    public void onDraw(Canvas canvas){
        update();
        //canvas.drawBitmap(mouseTurnLeft(), x, y, null);
        mouseTurnLeft(canvas);
    }
}
