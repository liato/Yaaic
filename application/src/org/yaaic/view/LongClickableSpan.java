package org.yaaic.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LongClickableSpan extends ClickableSpan {
    private final String mURL;

    public LongClickableSpan(String url) {
        mURL = url;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Log.d("LongClickableSpan", "Click on: " + mURL);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mURL));
        context.startActivity(intent);
    }

    public void onLongClick(View v) {
        Log.d("LongClickableSpan", "LongClick on: " + mURL);
        Toast.makeText(v.getContext(), "LongClick on: " + mURL, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(0xFF8484FF);
    }
    public String getUrl()
    {
        return mURL;
    }

}