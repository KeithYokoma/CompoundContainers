package jp.yokomark.widget.compound;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * Port implementation of checkable for {@link android.widget.RelativeLayout}.
 * @author KeithYokoma
 * @since 2014/03/16
 */
@SuppressWarnings("unused") // public APIs
public class CompoundFrameLayout extends FrameLayout implements Checkable, CompoundViewGroup {
    private static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };
    private boolean mChecked;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;

    public CompoundFrameLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CompoundFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CompoundFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.CompoundFrameLayout, defStyle, 0);
            boolean checked = a.getBoolean(R.styleable.CompoundFrameLayout_defaultChecked, false);
            setChecked(checked);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }

            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
            if (mOnCheckedChangeWidgetListener != null) {
                mOnCheckedChangeWidgetListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ownState = (SavedState) state;
        super.onRestoreInstanceState(ownState.getSuperState());
        setChecked(ownState.checked);
        requestLayout();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ownState = new SavedState(superState);
        ownState.checked = mChecked;
        return ownState;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }

    /* package */ static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean checked;

        private SavedState(Parcel source) {
            super(source);
            checked = source.readInt() == 1;
        }

        /* package */ SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(checked ? 1 : 0);
        }
    }
}
