package jp.yokomark.widget.compound;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author yokomakukeishin
 * @since 2014/03/16
 */
public class RadioLinearLayout extends CompoundLinearLayout{
    public static final String TAG = RadioLinearLayout.class.getSimpleName();

    public RadioLinearLayout(Context context) {
        super(context);
    }

    public RadioLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }
}
