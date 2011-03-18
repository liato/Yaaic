package org.yaaic.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaaic.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;


public class Smilies {
    public final static int TYPE_SMILIES = 1;
    public final static int TYPE_RAGE_FACES = 2;

    private Smilies() {}

    /**
     * Converts all smilies in a string to graphical smilies.
     * 
     * @param text     A string with smilies.
     * @param context  Activity context
     * @return         A SpannableString with graphical smilies.
     */
    public static SpannableString toSpannable(SpannableString text, Context context) {
        return toSpannable(text, context, getMappings(TYPE_SMILIES));
    }

    /**
     * Converts all smilies in a string to graphical smilies.
     * 
     * @param text      A string with smilies.
     * @param context   Activity context
     * @param mappings  HashMap<String, Integer> with smilie code to drawable id mappings as returned by getMappings()
     * @return          A SpannableString with graphical smilies.
     */
    public static SpannableString toSpannable(SpannableString text, Context context, HashMap<String, Integer> mappings) {
        StringBuilder regex = new StringBuilder("(");
        String[] smilies = mappings.keySet().toArray(new String[mappings.size()]);
        for (int i = 0; i < smilies.length; i++) {
            regex.append(Pattern.quote(smilies[i]));
            regex.append("|");
        }
        regex.deleteCharAt(regex.length()-1);
        regex.append(")");
        Pattern smiliematcher = Pattern.compile(regex.toString());
        Matcher m = smiliematcher.matcher(text);
        while (m.find()) {
            Log.d("Smilies", "SID: "+mappings.get(m.group(1)).intValue());
            Log.d("Smilies", "OID: "+R.drawable.smiley_smile);
            Drawable smilie = context.getResources().getDrawable(mappings.get(m.group(1)).intValue());
            smilie.setBounds(0, 0, smilie.getIntrinsicWidth(), smilie.getIntrinsicHeight());
            ImageSpan span = new ImageSpan(smilie, ImageSpan.ALIGN_BOTTOM);
            text.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return text;
    }

    /**
     * Converts all smilies in a string to graphical smilies.
     * 
     * @param text  A string with smilies.
     * @return      A SpannableString with graphical smilies.
     */
    public static SpannableString toSpannable(String text, Context context) {
        return toSpannable(new SpannableString(text), context);
    }

    /**
     * Returns a HashMap with smilie code to drawable id mappings to use with toSpan()
     * 
     * @param id    ID of predefined mappings. Either TYPE_SMILIES or TYPE_RAGE_FACES.
     * @return      HashMap with smilie code to drawable id mappings.
     */
    public static HashMap<String, Integer> getMappings(int id) {
        HashMap<String, Integer> mappings = new HashMap<String, Integer>();
        switch(id) {
            case TYPE_SMILIES:
                mappings.put(">:o", R.drawable.smiley_yell);
                mappings.put(">:-o", R.drawable.smiley_yell);
                mappings.put("O:)", R.drawable.smiley_innocent);
                mappings.put("O:-)", R.drawable.smiley_innocent);
                mappings.put(":)", R.drawable.smiley_smile);
                mappings.put(":-)", R.drawable.smiley_smile);
                mappings.put(":(", R.drawable.smiley_frown);
                mappings.put(":-(", R.drawable.smiley_frown);
                mappings.put(";)", R.drawable.smiley_wink);
                mappings.put(";-)", R.drawable.smiley_wink);
                mappings.put(":p", R.drawable.smiley_tongue_out);
                mappings.put(":-p", R.drawable.smiley_tongue_out);
                mappings.put(":P", R.drawable.smiley_tongue_out);
                mappings.put(":-P", R.drawable.smiley_tongue_out);
                mappings.put(":D", R.drawable.smiley_laughing);
                mappings.put(":-D", R.drawable.smiley_laughing);
                mappings.put(":[", R.drawable.smiley_embarassed);
                mappings.put(":-[", R.drawable.smiley_embarassed);
                mappings.put(":\\", R.drawable.smiley_undecided);
                mappings.put(":-\\", R.drawable.smiley_undecided);
                mappings.put(":o", R.drawable.smiley_surprised);
                mappings.put(":-o", R.drawable.smiley_surprised);
                mappings.put(":O", R.drawable.smiley_surprised);
                mappings.put(":-O", R.drawable.smiley_surprised);
                mappings.put(":*", R.drawable.smiley_kiss);
                mappings.put(":-*", R.drawable.smiley_kiss);
                mappings.put("8)", R.drawable.smiley_cool);
                mappings.put("8-)", R.drawable.smiley_cool);
                mappings.put(":$", R.drawable.smiley_money_mouth);
                mappings.put(":-$", R.drawable.smiley_money_mouth);
                mappings.put(":!", R.drawable.smiley_foot_in_mouth);
                mappings.put(":-!", R.drawable.smiley_foot_in_mouth);
                mappings.put(":'(", R.drawable.smiley_cry);
                mappings.put(":'-(", R.drawable.smiley_cry);
                mappings.put(":´(", R.drawable.smiley_cry);
                mappings.put(":´-(", R.drawable.smiley_cry);
                mappings.put(":X", R.drawable.smiley_sealed);
                mappings.put(":-X", R.drawable.smiley_sealed);
                break;
            case TYPE_RAGE_FACES:
                mappings.put("[troll]", R.drawable.rage_troll);
                mappings.put("[megusta]", R.drawable.rage_megusta);
                mappings.put("[yuno]", R.drawable.rage_yuno);
                mappings.put("[awyeah]", R.drawable.rage_awyeah);
                mappings.put("[fap]", R.drawable.rage_fap);
                mappings.put("[forever]", R.drawable.rage_forever_alone);
                mappings.put("[foreveralone]", R.drawable.rage_forever_alone);
                mappings.put("[wtf]", R.drawable.rage_wtf);
                mappings.put("[okay]", R.drawable.rage_okay);
                mappings.put("[lol]", R.drawable.rage_lol);
                mappings.put("[happy]", R.drawable.rage_happy);
                mappings.put("[high]", R.drawable.rage_high);
                mappings.put("[what]", R.drawable.rage_what);
                mappings.put("[dumbbitch]", R.drawable.rage_dumbbitch);
                break;
        }
        return mappings;
    }
}