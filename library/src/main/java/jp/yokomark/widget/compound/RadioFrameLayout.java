package jp.yokomark.widget.compound;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author yokomakukeishin
 * @since 2014/03/16
 */
public class RadioFrameLayout extends CompoundFrameLayout {
    public static final String TAG = RadioFrameLayout.class.getSimpleName();

    public RadioFrameLayout(Context context) {
        super(context);
    }

    public RadioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }
}
