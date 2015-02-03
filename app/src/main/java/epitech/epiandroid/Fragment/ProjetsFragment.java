package epitech.epiandroid.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.MessagesTask;
import epitech.epiandroid.Tasks.ProjectTask;


public class ProjetsFragment extends Fragment {
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_projet, container, false);
        v = rootView.findViewById(R.id.progress);
        v.setVisibility(View.VISIBLE);
        new ProjectTask(getActivity(), rootView, this).execute((Void) null);
        return rootView;
    }

    public void completelistview(View rootView, ArrayList<JSONObject> list, Context context) {
        ListView l1=(ListView)rootView.findViewById(R.id.listView);
        l1.setAdapter(new dataListAdapter(context, list));
        v.setVisibility(View.GONE);
    }


    static class ViewHolder {
        // TODO add view holder
    }



    class dataListAdapter extends BaseAdapter {
        Context context;
        private ArrayList<JSONObject> list;
        String[] Title;

        dataListAdapter(Context ctx, ArrayList<JSONObject> list) {
            this.context = ctx;
            this.list = list;
            Title = null;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {


            JSONObject tmpboj = list.get(position);
            ViewHolder viewHolder;

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.project_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);

            TextView text = (TextView) convertView.findViewById(R.id.submission_title);
            TextView start = (TextView) convertView.findViewById(R.id.start);
            TextView difftext = (TextView) convertView.findViewById(R.id.difftext);
            TextView module = (TextView) convertView.findViewById(R.id.title_module);

            try {
                text.setText(list.get(position).get("project").toString());
                text.setText(list.get(position).get("project").toString());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Date d = new Date();
                Date d2 = new Date();

                try {
                    d = sdf.parse(tmpboj.getString("begin_acti").substring(0, 10));
                    d2 = sdf.parse(tmpboj.getString("end_acti").substring(0, 10));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date date= new java.util.Date();
                long now = System.currentTimeMillis();
                long total = d2.getTime() - d.getTime();
                long diffnow =   d2.getTime() - now;
                long days = TimeUnit.MILLISECONDS.toDays(diffnow);
                long progress = diffnow * 100 / total;

                if (progress < 0)
                    progress = 0;

                float st = (float) progress / 100;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = st;
                start.setLayoutParams(params);


                if (d.getTime() < now)
                    difftext.setText(String.valueOf(getResources().getString(R.string.rest, String.valueOf(days))));
                else {
                    difftext.setText(String.valueOf(getResources().getString(R.string.start_proj, String.valueOf(days))));
                    difftext.setTextColor(Color.parseColor("#42A5F5"));
                }

                module.setText(tmpboj.getString("title_module"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return (convertView);
        }
    }
}
