package jp.yokomark.widget.compound;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by KeishinYokomaku on 2017/08/07.
 */
@SuppressWarnings("unused")
public class RadioGroup2 extends LinearLayout {
	// holds the checked id; the selection is empty by default
	private int mCheckedId = -1;
	// tracks children radio buttons checked state
	private OnCheckedChangeListener mChildOnCheckedChangeListener;
	// when true, mOnCheckedChangeListener discards events
	private boolean mProtectFromCheckedChange = false;
	private OnGroupCheckedChangeListener mOnCheckedChangeListener;
	private PassThroughHierarchyChangeListener mPassThroughListener;

	/**
	 * {@inheritDoc}
	 */
	public RadioGroup2(Context context) {
		super(context);
		setOrientation(VERTICAL);
		init();
	}

	/**
	 * {@inheritDoc}
	 */
	public RadioGroup2(Context context, AttributeSet attrs) {
		super(context, attrs);

		// retrieve selected radio button as requested by the user in the
		// XML layout file
		TypedArray attributes = context.obtainStyledAttributes(
				attrs, R.styleable.RadioGroup2, R.attr.radioButtonStyle, 0);

		int value = attributes.getResourceId(R.styleable.RadioGroup2_checkedButton, View.NO_ID);
		if (value != View.NO_ID) {
			mCheckedId = value;
		}

		final int index = attributes.getInt(R.styleable.RadioGroup2_orientation, VERTICAL);
		setOrientation(index);

		attributes.recycle();
		init();
	}

	private void init() {
		mChildOnCheckedChangeListener = new CheckedStateTracker();
		mPassThroughListener = new PassThroughHierarchyChangeListener();
		super.setOnHierarchyChangeListener(mPassThroughListener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		// the user listener is delegated to our pass-through listener
		mPassThroughListener.mOnHierarchyChangeListener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		// checks the appropriate radio button as requested in the XML file
		if (mCheckedId != -1) {
			mProtectFromCheckedChange = true;
			setCheckedStateForView(mCheckedId, true);
			mProtectFromCheckedChange = false;
			setCheckedId(mCheckedId);
		}
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (child instanceof RadioButton) {
			final RadioButton button = (RadioButton) child;
			if (button.isChecked()) {
				mProtectFromCheckedChange = true;
				if (mCheckedId != -1) {
					setCheckedStateForView(mCheckedId, false);
				}
				mProtectFromCheckedChange = false;
				setCheckedId(button.getId());
			}
		}

		super.addView(child, index, params);
	}

	/**
	 * <p>Sets the selection to the radio button whose identifier is passed in
	 * parameter. Using -1 as the selection identifier clears the selection;
	 * such an operation is equivalent to invoking {@link #clearCheck()}.</p>
	 *
	 * @param id the unique id of the radio button to select in this group
	 *
	 * @see #getCheckedRadioButtonId()
	 * @see #clearCheck()
	 */
	public void check(@IdRes int id) {
		// don't even bother
		if (id != -1 && (id == mCheckedId)) {
			return;
		}

		if (mCheckedId != -1) {
			setCheckedStateForView(mCheckedId, false);
		}

		if (id != -1) {
			setCheckedStateForView(id, true);
		}

		setCheckedId(id);
	}

	private void setCheckedId(@IdRes int id) {
		mCheckedId = id;
		if (mOnCheckedChangeListener != null) {
			mOnCheckedChangeListener.onCheckedChanged(this, mCheckedId);
		}
	}

	private void setCheckedStateForView(int viewId, boolean checked) {
		View checkedView = findViewById(viewId);
		if (checkedView != null && checkedView instanceof RadioButton) {
			((RadioButton) checkedView).setChecked(checked);
		}
	}

	/**
	 * <p>Returns the identifier of the selected radio button in this group.
	 * Upon empty selection, the returned value is -1.</p>
	 *
	 * @return the unique id of the selected radio button in this group
	 *
	 * @see #check(int)
	 * @see #clearCheck()
	 *
	 */
	@IdRes
	public int getCheckedRadioButtonId() {
		return mCheckedId;
	}

	/**
	 * <p>Clears the selection. When the selection is cleared, no radio button
	 * in this group is selected and {@link #getCheckedRadioButtonId()} returns
	 * null.</p>
	 *
	 * @see #check(int)
	 * @see #getCheckedRadioButtonId()
	 */
	public void clearCheck() {
		check(-1);
	}

	/**
	 * <p>Register a callback to be invoked when the checked radio button
	 * changes in this group.</p>
	 *
	 * @param listener the callback to call on checked state change
	 */
	public void setOnCheckedChangeListener(OnGroupCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new RadioGroup2.LayoutParams(getContext(), attrs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
		return p instanceof RadioGroup.LayoutParams;
	}

	@Override
	protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	public CharSequence getAccessibilityClassName() {
		return RadioGroup.class.getName();
	}

	/**
	 * <p>This set of layout parameters defaults the width and the height of
	 * the children to {@link #WRAP_CONTENT} when they are not specified in the
	 * XML file. Otherwise, this class ussed the value read from the XML file.</p>
	 *
	 */
	public static class LayoutParams extends LinearLayout.LayoutParams {
		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(Context c, AttributeSet attrs) {
			super(c, attrs);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(int w, int h) {
			super(w, h);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(int w, int h, float initWeight) {
			super(w, h, initWeight);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(ViewGroup.LayoutParams p) {
			super(p);
		}

		/**
		 * {@inheritDoc}
		 */
		public LayoutParams(MarginLayoutParams source) {
			super(source);
		}

		/**
		 * <p>Fixes the child's width to
		 * {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT} and the child's
		 * height to  {@link android.view.ViewGroup.LayoutParams#WRAP_CONTENT}
		 * when not specified in the XML file.</p>
		 *
		 * @param a the styled attributes set
		 * @param widthAttr the width attribute to fetch
		 * @param heightAttr the height attribute to fetch
		 */
		@Override
		protected void setBaseAttributes(TypedArray a,
										 int widthAttr, int heightAttr) {

			if (a.hasValue(widthAttr)) {
				width = a.getLayoutDimension(widthAttr, "layout_width");
			} else {
				width = WRAP_CONTENT;
			}

			if (a.hasValue(heightAttr)) {
				height = a.getLayoutDimension(heightAttr, "layout_height");
			} else {
				height = WRAP_CONTENT;
			}
		}
	}

	/**
	 * <p>Interface definition for a callback to be invoked when the checked
	 * radio button changed in this group.</p>
	 */
	public interface OnGroupCheckedChangeListener {
		/**
		 * <p>Called when the checked radio button has changed. When the
		 * selection is cleared, checkedId is -1.</p>
		 *
		 * @param group the group in which the checked radio button has changed
		 * @param checkedId the unique identifier of the newly checked radio button
		 */
		void onCheckedChanged(RadioGroup2 group, @IdRes int checkedId);
	}

	private class CheckedStateTracker implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(CompoundViewGroup compoundView, boolean isChecked) {
			// prevents from infinite recursion
			if (mProtectFromCheckedChange) {
				return;
			}

			mProtectFromCheckedChange = true;
			if (mCheckedId != -1) {
				setCheckedStateForView(mCheckedId, false);
			}
			mProtectFromCheckedChange = false;

			int id = compoundView.getId();
			setCheckedId(id);
		}
	}

	/**
	 * <p>A pass-through listener acts upon the events and dispatches them
	 * to another listener. This allows the table layout to set its own internal
	 * hierarchy change listener without preventing the user to setup his.</p>
	 */
	private class PassThroughHierarchyChangeListener implements
			ViewGroup.OnHierarchyChangeListener {
		private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewAdded(View parent, View child) {
			if (parent == RadioGroup2.this && child instanceof CompoundViewGroup) {
				int id = child.getId();
				// generates an id if it's missing
				if (id == View.NO_ID) {
					id = child.hashCode();
					child.setId(id);
				}
				((CompoundViewGroup) child).setOnCheckedChangeWidgetListener(
						mChildOnCheckedChangeListener);
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewAdded(parent, child);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewRemoved(View parent, View child) {
			if (parent == RadioGroup2.this && child instanceof CompoundViewGroup) {
				((CompoundViewGroup) child).setOnCheckedChangeWidgetListener(null);
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
			}
		}
	}
}
