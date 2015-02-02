package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Databases.LoginTable;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.MarksTask;


public class NotesFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        long MILISECONDS_BEFORE_FORCE_REFRESH = 5 * 1000; // 60 secondes

        rootView = inflater.inflate(R.layout.fragment_section_marks, container, false);

        Boolean isMarksDisplayed = Marks.listAll(Marks.class).size() > 0;
        LoginTable user = LoginTable.listAll(LoginTable.class).get(0);

        try {
            rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.marks_list).setVisibility(View.GONE);
        } catch (Exception ignored) {
        }

        if (!isMarksDisplayed || (user.getMarksUpdatedAt() + MILISECONDS_BEFORE_FORCE_REFRESH) < System.currentTimeMillis()) {
            new MarksTask(getActivity(), this).execute((Void) null);
        } else {
            DisplayMarks();
        }
        return rootView;
    }

    public void DisplayMarks() {
        try {
            List<MarksItem> items = new ArrayList<>();
            List<Marks> marks = Marks.listAll(Marks.class);

            for (int i = 0; i < marks.size(); ++i) {
                Marks mark = marks.get(i);
                items.add(new MarksItem(mark.getNote(), mark.getModuleName(), mark.getProjectName(), mark.getContainerColor(), mark.getModuleImage()));
            }
            onMarksDisplayed(items);
        } catch (Exception e) {
            Log.e("Displaymarks", e.getMessage());
        }
    }

    public void onMarksDisplayed(List<MarksItem> items) {
        try {
            ListView marksList = (ListView) rootView.findViewById(R.id.marks_list);
            final ListAdapter customAdapter = new MarksAdapter(rootView.getContext(), R.layout.marks_item, items);
            marksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    MarksItem item = (MarksItem) customAdapter.getItem(pos);
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getResources().getString(R.string.share_body, item.getNote(), item.getProjectName());
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_title));
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_via)));
                    return true;
                }
            });
            marksList.setAdapter(customAdapter);
            rootView.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e("Displaymarks", e.getMessage());
        }
    }
}
