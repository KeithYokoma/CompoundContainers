package jp.yokomark.widget.compound;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author yokomakukeishin
 * @since 2014/03/16
 */
public class RadioRelativeLayout extends CompoundRelativeLayout {
    public static final String TAG = RadioRelativeLayout.class.getSimpleName();

    public RadioRelativeLayout(Context context) {
        super(context);
    }

    public RadioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }
}
