package org.yaaic.view;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessageTextView extends TextView
{

    public MessageTextView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO Auto-generated method stub
        Log.d("TV", "onTouchEvent: " +event.getAction());
        super.onTouchEvent(event);
        Object parent = this.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup)parent).dispatchTouchEvent(event);
        }
        return true;
    }

}
