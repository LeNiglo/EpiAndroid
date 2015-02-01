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

import epitech.epiandroid.R;


public class ProjetsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_projet, container, false);
        ListView l1=(ListView)rootView.findViewById(R.id.listView);
        l1.setAdapter(new dataListAdapter(getActivity().getApplicationContext()));

        return rootView;
    }


    static class ViewHolder {
        // TODO add view holder
    }

    class dataListAdapter extends BaseAdapter {
        Context context;
        String[] Title;

        dataListAdapter(Context ctx) {
            this.context = ctx;
            Title = null;
        }

        public int getCount() {
            return 10;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.project_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);


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
