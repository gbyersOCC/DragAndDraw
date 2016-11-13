package edu.orangecoastcollege.cs273.gabyers.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabye on 11/8/2016.
 */

public class BoxDrawingView extends View {
    public static final String TAG = "BoxDrawingView";

    private Box mCurrentBox;
    private List<Box> boxList = new ArrayList<>();
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;


    //used when creatinng view in code
    public BoxDrawingView(Context context){
        this(context, null);

    }
    //used when inflating view from xml
    public BoxDrawingView(Context context, AttributeSet attr){
        super(context, attr);
        //paint boxes semiTransperen red
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        //paint background off-white
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //reset drawing state
                mCurrentBox = new Box(current);
                boxList.add(mCurrentBox);
                action = "action down";
                break;
            case MotionEvent.ACTION_MOVE:
                if(mCurrentBox != null){
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                action = "action move";
                break;
            case MotionEvent.ACTION_UP:
                action = "action up";
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;
                action = "action cancel";
                break;
        }
            Log.i(TAG, action + " at x=" + current.x+ ", y=" + current.y);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //fill the background
        canvas.drawPaint(mBackgroundPaint);

        for(Box box: boxList){
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }
}
