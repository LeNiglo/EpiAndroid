package epitech.epiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.R;

/**
 * Created by Styve on 14/01/2015.
 */

public class MarksAdapter extends ArrayAdapter<MarksItem> {
    private List<MarksItem> items;

    public MarksAdapter(Context context, int resource, List<MarksItem> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public MarksItem getItem(int position) {
        return items.get(items.size() - 1 - position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.marks_item, parent, false);
        }

        MarksItem p = getItem(position);

        if (p != null) {
            FrameLayout container = (FrameLayout) v.findViewById(R.id.note_container);
            TextView note = (TextView) v.findViewById(R.id.project_note);
            TextView corrector = (TextView) v.findViewById(R.id.project_corrector);
            TextView projectName = (TextView) v.findViewById(R.id.project_name);
            TextView moduleName = (TextView) v.findViewById(R.id.module_name);
            ImageView image = (ImageView) v.findViewById(R.id.module_image);

            if (container != null) {
                container.setBackgroundColor(p.getContainerColor());
            }
            if (note != null) {
                note.setText(p.getNote());
            }
            if (projectName != null) {
                projectName.setText(p.getProjectName());
            }
            if (moduleName != null) {
                moduleName.setText(p.getModuleName());
            }
            if (image != null) {
                image.setImageDrawable(getContext().getResources().getDrawable(p.getModuleImage()));
            }
            if (corrector != null) {
                corrector.setText(p.getCorrector());
            }
        }
        return v;
    }
}