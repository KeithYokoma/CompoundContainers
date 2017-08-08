package jp.yokomark.widget.compound;

import android.support.annotation.IdRes;

/**
 * Marker interface to create type hierarchy of the compound view groups.
 * @author KeithYokoma
 * @since 2014/03/16
 */
public interface CompoundViewGroup {
	@IdRes int getId();
	boolean isChecked();
	void setChecked(boolean checked);
	void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);
}