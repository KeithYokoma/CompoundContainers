package jp.yokomark.widget.compound;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author yokomakukeishin
 * @since 2014/03/16
 */
public class RadioGridLayout extends CompoundGridLayout {
    public static final String TAG = RadioGridLayout.class.getSimpleName();

    public RadioGridLayout(Context context) {
        super(context);
    }

    public RadioGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }
}
