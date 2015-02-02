package epitech.epiandroid.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
            TextView end = (TextView) convertView.findViewById(R.id.end);
            TextView module = (TextView) convertView.findViewById(R.id.title_module);

            try {
                text.setText(list.get(position).get("project").toString());
                text.setText(list.get(position).get("project").toString());

                start.setText(getActivity().getResources().getString(R.string.from) + " " + tmpboj.getString("begin_acti").substring(0, 10) );
                end.setText(getActivity().getResources().getString(R.string.to) + " " + tmpboj.getString("end_acti").substring(0, 10) );

                module.setText(tmpboj.getString("title_module"));

            } catch (JSONException e) {
                e.printStackTrace();
            }


//            if(convertView == null){
//                LayoutInflater inflater = LayoutInflater.from(context);        // only context can also be used
//                convertView = inflater.inflate(R.layout.project_item, null);
//                viewHolder = new ViewHolder();
//                convertView.setTag(viewHolder);
//
//            }else{
//                viewHolder = (ViewHolder) convertView.getTag();
//            }

            return (convertView);
        }
    }
}
