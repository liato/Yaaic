package org.yaaic.listener;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class MessageTouchListener implements View.OnTouchListener {
    private static MessageTouchListener sInstance;
    private final static int CLICK = 0;
    private final static int LONG_CLICK = 1;
    private View view;
    private boolean clickStarted = false;
    private GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("lv", " longpress detected");
            onClick(LONG_CLICK);
        }
    });
    /*
    private final GestureDetector mGestureDetector = new GestureDetector(this);
    @Override
    public void onLongPress(MotionEvent e)
    {
        Log.d("onlongpressss", "detected");
    }
     */

    public boolean onClick(int clicktype) {
        if (clicktype == CLICK) {
            Log.d("LV", "onclick: click!");
        }
        else {
            Log.d("LV", "onclick: click!");
        }
        clickStarted = false;
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.view = v;

        int action = event.getAction();
        Log.d("LV", "onTouch: "+action);
        mGestureDetector.onTouchEvent(event);
        if (action == MotionEvent.ACTION_UP && clickStarted) {
            return onClick(CLICK);
        }
        else if (action == MotionEvent.ACTION_DOWN) {
            clickStarted = true;
        }
        //return ((MessageListView)v.getParent()).dispatchTouchEvent(event);
        return false;
        /*TextView widget = (TextView)v;
        Object text = widget.getText();
        if (text instanceof Spanned) {
            Spanned buffer = (Spanned)text;

            if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
                Log.d("CCM2", "onTouchEvent: "+action);
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                LongClickableSpan[] link = buffer.getSpans(off, off, LongClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        if (!isLongPress && !isClicked) {
                            link[0].onClick(widget);
                            return true;
                        }
                        isClicked = false;
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        if (isLongPress) {
                            isLongPress = false;
                            isClicked = true;
                            link[0].onLongClick(widget);
                            return true;
                        }
                    }
                }
            }
        }
        isLongPress = false;
        return false;*/
    }

    public static MessageTouchListener getInstance()
    {
        if (sInstance == null) {
            sInstance = new MessageTouchListener();
        }
        return sInstance;
    }

}
