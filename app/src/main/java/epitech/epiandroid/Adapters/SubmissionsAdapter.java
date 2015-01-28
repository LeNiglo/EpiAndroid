package epitech.epiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.List;

import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.R;

/**
 * Created by Styve on 14/01/2015.
 */

public class SubmissionsAdapter extends ArrayAdapter<SubmissionsItem> {
    private List<SubmissionsItem> items;

    public SubmissionsAdapter(Context context, int resource, List<SubmissionsItem> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public SubmissionsItem getItem(int position) {
        return items.get(items.size() - 1 - position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.submission_item, parent, false);
        }

        SubmissionsItem p = getItem(position);

        if (p != null) {
            CircleProgress progress = (CircleProgress) v.findViewById(R.id.circle_progress);
            TextView title = (TextView) v.findViewById(R.id.submission_title);
            TextView date = (TextView) v.findViewById(R.id.submission_date);

            if (progress != null) {
                progress.setProgress(p.getProgress());
            }
            if (title != null) {
                title.setText(p.getProjectName());
            }
            if (date != null) {
                date.setText(p.getDate());
            }
        }
        return v;
    }
}