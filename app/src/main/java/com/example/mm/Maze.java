package com.example.mm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Maze {
    private int MAZE_SIZE = 16;
    private int TILE_WIDTH = 40;
    private final int MARGIN = 15;
    private final int W = 0b1000;
    private final int S = 0b0100;
    private final int E = 0b0010;
    private final int N = 0b0001;
    private int MAZE_HEIGHT = 750;
    private int MAZE_WIDTH = 750;

    public int[][] mWolrdMaze = {
            {0x0E, 0x0A, 0x09, 0x0C, 0x0A, 0x0A, 0x0A, 0x0A, 0x0A, 0x0A, 0x08, 0x0A, 0x0A, 0x0A, 0x08, 0x09},
            {0x0C, 0x09, 0x05, 0x06, 0x08, 0x0A, 0x0A, 0x0A, 0x0A, 0x0B, 0x06, 0x0A, 0x0A, 0x0A, 0x03, 0x05},
            {0x05, 0x05, 0x05, 0x0C, 0x02, 0x0B, 0x0E, 0x08, 0x0A, 0x0A, 0x08, 0x0A, 0x08, 0x08, 0x09, 0x05},
            {0x05, 0x04, 0x01, 0x06, 0x08, 0x0A, 0x09, 0x04, 0x0A, 0x0A, 0x00, 0x0A, 0x03, 0x05, 0x05, 0x05},
            {0x05, 0x05, 0x04, 0x09, 0x06, 0x09, 0x05, 0x04, 0x0A, 0x0A, 0x02, 0x0A, 0x0B, 0x05, 0x05, 0x05},
            {0x05, 0x04, 0x03, 0x06, 0x0A, 0x02, 0x03, 0x06, 0x0A, 0x0A, 0x0A, 0x0A, 0x09, 0x05, 0x05, 0x05},
            {0x05, 0x05, 0x0D, 0x0D, 0x0D, 0x0C, 0x08, 0x0A, 0x0A, 0x0A, 0x0A, 0x09, 0x05, 0x05, 0x05, 0x05},
            {0x06, 0x03, 0x04, 0x01, 0x04, 0x01, 0x05, 0x0C, 0x09, 0x0C, 0x08, 0x01, 0x05, 0x05, 0x05, 0x05},
            {0x0C, 0x08, 0x01, 0x06, 0x01, 0x05, 0x04, 0x02, 0x03, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05},
            {0x05, 0x05, 0x05, 0x0D, 0x06, 0x01, 0x05, 0x0C, 0x0A, 0x01, 0x05, 0x05, 0x05, 0x05, 0x05, 0x05},
            {0x05, 0x05, 0x05, 0x04, 0x09, 0x06, 0x03, 0x06, 0x0A, 0x02, 0x00, 0x03, 0x05, 0x04, 0x03, 0x05},
            {0x05, 0x04, 0x03, 0x05, 0x05, 0x0C, 0x0A, 0x0A, 0x08, 0x09, 0x04, 0x0A, 0x01, 0x05, 0x0D, 0x05},
            {0x05, 0x05, 0x0D, 0x05, 0x05, 0x04, 0x0A, 0x08, 0x03, 0x05, 0x06, 0x0A, 0x03, 0x05, 0x04, 0x01},
            {0x05, 0x05, 0x04, 0x01, 0x04, 0x03, 0x0C, 0x02, 0x0B, 0x06, 0x08, 0x0A, 0x0A, 0x03, 0x05, 0x05},
            {0x05, 0x06, 0x01, 0x07, 0x06, 0x08, 0x02, 0x0A, 0x0A, 0x0B, 0x06, 0x08, 0x0A, 0x0A, 0x00, 0x01},
            {0x06, 0x0A, 0x02, 0x0A, 0x0A, 0x02, 0x0B, 0x0E, 0x0A, 0x0A, 0x0A, 0x02, 0x0A, 0x0A, 0x03, 0x07}
    };

    public class CoordinateXY {
        public int x;
        public int y;
    }

    public CoordinateXY[][] mCenterPoints;

    private Bitmap mBitMap;
    private Paint mPaint;
    private Canvas mCanvas;
    private GameView mGameView;

    public Maze() {
        // create Bitmap
        mBitMap = Bitmap.createBitmap(MAZE_WIDTH, MAZE_HEIGHT, Bitmap.Config.ARGB_8888);
        // create a canvas on Bitmap
        mCanvas = new Canvas(mBitMap);
        mCanvas.drawColor(Color.BLACK);

        // create a paint tool to draw on canvas
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        initCoordinate();

    }

    Bitmap drawMaze(){
        for (int x = 0; x < MAZE_SIZE; x++){
            for (int y = 0; y < MAZE_SIZE; y++){
                int direction = mWolrdMaze[x][y];
                if ((direction & W) == W) {
                    drawWestWall(x, y);
                }
                if ((direction & S) == S) {
                    drawSouthWall(x, y);
                }
                if ((direction & E) == E) {
                    drawEastWall(x, y);
                }
                if ((direction & N) == N) {
                    drawNorthWall(x, y);
                }
                saveCenterPoints(x, y);
            }
        }
        return mBitMap;
    }

    void drawWestWall(int x, int y){
        mPaint.setColor(Color.RED);
        mCanvas.drawLine(
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);

        mPaint.setColor(Color.WHITE);
        mCanvas.drawLine(
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN - 1,
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN + 1,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);
    }

    void drawSouthWall(int x, int y){
        mPaint.setColor(Color.RED);
        mCanvas.drawLine(
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);

        mPaint.setColor(Color.WHITE);
        mCanvas.drawLine(
                x * TILE_WIDTH + MARGIN - 1,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + MARGIN + 1,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);
    }

    void drawEastWall(int x, int y){
        mPaint.setColor(Color.RED);
        mCanvas.drawLine(
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);

        mPaint.setColor(Color.WHITE);
        mCanvas.drawLine(
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN - 1,
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + TILE_WIDTH + MARGIN + 1,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);
    }

    void drawNorthWall(int x, int y){
        mPaint.setColor(Color.RED);
        mCanvas.drawLine(
                x * TILE_WIDTH + TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + MARGIN,
                (15-y) * TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);

        mPaint.setColor(Color.WHITE);
        mCanvas.drawLine(
                x * TILE_WIDTH + TILE_WIDTH + MARGIN - 1,
                (15-y) * TILE_WIDTH + MARGIN,
                x * TILE_WIDTH + TILE_WIDTH + MARGIN + 1,
                (15-y) * TILE_WIDTH + MARGIN,
                mPaint
        );
        //mImgViewMaze.setImageBitmap(mBitMap);
    }

    void saveCenterPoints(int x, int y) {
        mCenterPoints[x][y].x = x*TILE_WIDTH + TILE_WIDTH / 2 + MARGIN;
        mCenterPoints[x][y].y = (15 - y)*TILE_WIDTH + TILE_WIDTH / 2 + MARGIN;
        //System.out.printf("(x%d, y%d) = (%d, %d)\n", x, y, mCenterPoints[x][y].x, mCenterPoints[x][y].y);
    }

    void initCoordinate() {
        mCenterPoints = new CoordinateXY[16][16];
        for (int x = 0; x < MAZE_SIZE; x++) {
            for (int y = 0; y < MAZE_SIZE; y++){
                mCenterPoints[x][y] = new CoordinateXY();
                saveCenterPoints(x,y);
            }
        }
    }
}
