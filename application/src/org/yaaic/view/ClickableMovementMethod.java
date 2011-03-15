package org.yaaic.view;

import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class ClickableMovementMethod extends LinkMovementMethod {
    private static ClickableMovementMethod sInstance;
    private static boolean isLongPress = false;
    private BackgroundColorSpan backgroundSpan = null;
    private final GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
            isLongPress = true;
        }
    });

    public static MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ClickableMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer,
        MotionEvent event) {
        int action = event.getAction();
        mGestureDetector.onTouchEvent(event);
        Log.d("CMM", "onTouchEvent: "+action);
        if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_DOWN ||
            action == MotionEvent.ACTION_CANCEL) {
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
                    buffer.removeSpan(backgroundSpan);
                    backgroundSpan = null;

                    if (isLongPress) {
                        isLongPress = false;
                        link[0].onLongClick(widget);
                    }
                    else {
                        link[0].onClick(widget);
                    }
                } else if (action == MotionEvent.ACTION_DOWN) {
                    if (backgroundSpan == null) {
                        backgroundSpan = new BackgroundColorSpan(Color.RED);
                        buffer.setSpan(backgroundSpan,buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), 0);
                    }
                }
                else if (action == MotionEvent.ACTION_CANCEL) {
                    if (backgroundSpan != null) {
                        buffer.removeSpan(backgroundSpan);
                    }
                    backgroundSpan = null;
                    isLongPress = false;
                }
                return true;
            }
        }
        return false;
    }

}
