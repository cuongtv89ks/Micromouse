package com.example.mm;

import android.graphics.Canvas;

public class GameThread extends Thread {

    private GameView mGameView;
    private boolean mRunning = false;

    public GameThread(GameView gameView) {
        this.mGameView = gameView;
    }

    public void setRunning(boolean run){
        mRunning = run;
    }

    @Override
    public void run() {
        while (mRunning) {
            Canvas canvas = null;
            try {
                canvas = mGameView.getHolder().lockCanvas();
                synchronized (mGameView.getHolder()) {
                    mGameView.onDraw(canvas);
                }
            } finally {
                if (canvas != null) {
                    mGameView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
