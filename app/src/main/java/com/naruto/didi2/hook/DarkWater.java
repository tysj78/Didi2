package com.naruto.didi2.hook;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzl on 11/16/20.
 */

public class DarkWater {
    static DarkWater self=null;
    final int max = 511;
    Paint paint = new Paint();
    final int point_space = 20;
    final int point_num = 9;
    final int point_row = 3;
    final int point_col = 3;
    int x_start = 50;

    private DarkWater(){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
    }

    public static DarkWater getInstance(){
        synchronized (DarkWater.class){
            if(self == null)
                self = new DarkWater();
            return self;
        }
    }

    private void draw_point(Canvas canvas, int x, int y){
        //canvas.drawRect(x, y, x+1, y+1, paint);
        canvas.drawPoint(x, y, paint);
    }

    private void draw_matrix(Canvas canvas, int x, int y, int data){
        List<Boolean> matrix = new ArrayList<>();
        int i = 0;
        do{
            if((data & 0x1) == 1)
                matrix.add(true);
            else
                matrix.add(false);

            ++i;
            data = data >>> 1;
        }while (i<point_num);

        int lx = x;
        int ly = y;
        int point = point_num-1;
        for (i=0; i<point_row; ++i){
            for (int j=0; j<point_col; ++j){
                if(matrix.get(point))
                    draw_point(canvas, lx, ly);
                else
                    ;
                --point;

                lx = lx + point_space;
            }

            lx=x;
            ly = ly + point_space;
        }

    }

    public void draw_content(Canvas canvas, byte[] data){
        x_start = 80;
        int height = canvas.getHeight();
        int width = canvas.getWidth();

        int n = width/point_space;
        int n1 = n/(point_col+2);
        int n2 = n%(point_col+2);

        x_start = (n2*point_space+x_start)/2;
        int x = x_start;
        int y = x_start;

        while (true){
            for (int i=0; i<data.length; ++i){
                //有效位8位，最高为设置为1, 用于识别定位
                draw_matrix(canvas, x, y, (int)data[i] | 0x100);
                x = x + point_col * point_space + point_space;
                if(x > width-point_col * point_space){
                    y = y + point_row * point_space + point_space;
                    x = x_start;
                }
            }

            if(y > height-point_row * point_space)
                break;
        }
    }
}
