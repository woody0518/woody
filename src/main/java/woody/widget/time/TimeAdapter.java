package woody.widget.time;

import android.support.annotation.NonNull;
import android.widget.BaseAdapter;

/**
 * Created by DELL on 2015/12/11.
 */
public abstract  class TimeAdapter implements TimeWrapper{

    private BaseAdapter mBaseAdapter;

    public TimeAdapter(@NonNull final BaseAdapter baseAdapter) {
        mBaseAdapter = baseAdapter;
    }
}
