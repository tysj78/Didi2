package com.naruto.didi2.hook;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import com.naruto.didi2.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by wzl on 10/12/20.
 */

public class Hook_Drawable_draw extends XC_MethodHook implements IHook {
    private Paint paint = new Paint();

    private void drawMark(Canvas canvas) {
        try {
            if (canvas == null)
                return;
            LogUtils.e("开始设置水印");
            WatermarkCfg watermarkCfg = ControlManager.getInstance().getWatermarkCfg();

            //"S_WATER_MARK_CFG":{"wm_switch":0, "content":"版权水印", "bind_time":1, "alpha":191,
            // "rgb":[200, 210, 200], "style":1, "fontSize":46, "view_regex":"[^\\s]+((id/content|FileReaderContentView))$",
            // "view_log":0},
            //初始化水印
            watermarkCfg.bindTime = 1;
            watermarkCfg.content = "靖儿";
            watermarkCfg.style = 1;
            watermarkCfg.fontSize = 46;
            watermarkCfg.alpha = 191;
            ArrayList<Integer> list = new ArrayList<>();
            list.add(200);
            list.add(210);
            list.add(200);
            watermarkCfg.rgb = list;


            String new_content = "";

            int width = canvas.getWidth();
            int height = canvas.getHeight();

            if (1 == watermarkCfg.bindTime) {
                SimpleDateFormat curTime = new SimpleDateFormat("yyyy.MM.dd");
//                new_content = watermarkCfg.content + curTime.format(new Date());
                new_content = watermarkCfg.content + "❤"+"杨勇";
            } else
                new_content = watermarkCfg.content;

            MsmLog.print("draw water mark, " + new_content + " " + width + "," + height);

            canvas.drawColor(Color.parseColor("#00F3F5F9"));
            //抗锯齿
            paint.setAntiAlias(true);
            //让画出的图形是空心的 : Paint.Style.STROKE
            paint.setStyle(Paint.Style.values()[watermarkCfg.style]);
            //设置画出的线的 粗细程度
            paint.setStrokeWidth(1);

            paint.setTextSize(watermarkCfg.fontSize);

            paint.setColor(Color.argb(watermarkCfg.alpha, watermarkCfg.rgb.get(0), watermarkCfg.rgb.get(1), watermarkCfg.rgb.get(2)));

            canvas.save();
            canvas.rotate(-30);
            float textWidth = paint.measureText(new_content);
            int index = 0;
            for (int positionY = height / 10; positionY <= height; positionY += height / 10 + 80) {
                float fromX = -width + (index++ % 2) * textWidth;
                for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                    int spacing = 0;//间距
                    canvas.drawText(new_content, positionX, positionY + spacing, paint);
                    spacing = spacing + 50;
                }
            }
            canvas.restore();
        } catch (Exception e) {
            MsmLog.print(e);
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        LogUtils.e("Hook_Drawable_draw:afterHookedMethod");
        ControlManager.getInstance().getWatermarkCfg().watermarkSwitch = 1;
        if (ControlManager.getInstance().getWatermarkCfg().watermarkSwitch == 1) {
            WatermarkCfg watermarkCfg = ControlManager.getInstance().getWatermarkCfg();
            if (watermarkCfg.dark)
                DarkWater.getInstance().draw_content((Canvas) param.args[0],
                        new byte[]{(byte) 255, 110, 125, 99, 81, 36, 49, 25, 37, 56, 88, (byte) 222});
            else
                drawMark((Canvas) param.args[0]);
        }
    }

    @Override
    public void setListener(IHookListener listener) {

    }

    @Override
    public String description() {
        return "绘制水印";
    }
}
