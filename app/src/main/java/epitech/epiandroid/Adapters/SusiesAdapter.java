package epitech.epiandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import epitech.epiandroid.Items.SubmissionsItem;
import epitech.epiandroid.Items.SusiesItem;
import epitech.epiandroid.R;

/**
 * Created by Styve on 14/01/2015.
 */

public class SusiesAdapter extends ArrayAdapter<SusiesItem> {
    private List<SusiesItem> items;

    public SusiesAdapter(Context context, int resource, List<SusiesItem> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.susie_item, parent, false);
        }

        SusiesItem p = getItem(position);

        if (p != null) {
            TextView title = (TextView) v.findViewById(R.id.susie_description);
            TextView start = (TextView) v.findViewById(R.id.susie_date);
            TextView susie = (TextView) v.findViewById(R.id.susie_login);
            TextView type = (TextView) v.findViewById(R.id.susie_type);

            if (title != null) {
                title.setText(p.getTitle());
            }
            if (start != null) {
                try {
                    SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
                    Date MyDate = newDateFormat.parse(p.getStart());
                    newDateFormat.applyPattern("EEEE dd MMMM");
                    String MyDate2 = newDateFormat.format(MyDate);
                    start.setText(MyDate2);
                } catch (Exception e) {
                    start.setText("Date error");
                }
            }
            if (susie != null) {
                susie.setText(p.getSusie());
            }
            if (type != null) {
                type.setText("(" + p.getType() + ")");
            }
        }
        return v;
    }
}