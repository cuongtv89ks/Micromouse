package com.example.mm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameThread mGameThread;
    private Bitmap mBitMapMouse;
    private Bitmap mBitMapMaze;
    private Maze mMaze;
    private Mouse mMouse;

    public GameView(Context context) {
        super(context);
//        this.getHolder().set
        mGameThread = new GameThread(this);
        mMaze = new Maze();
        mMouse = new Mouse(this);

        mBitMapMaze = mMaze.drawMaze();
        //mBitMapMouse = BitmapFactory.decodeResource(getResources(), R.drawable.mouse);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mGameThread.setRunning(true);
                mGameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mGameThread.setRunning(false);
                while (retry) {
                    try {
                        mGameThread.join();
                        retry = false;
                    } catch (InterruptedException e){

                    }
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(mBitMapMaze, 0, 0, null);
        mMouse.onDraw(canvas);
    }
}
