package epitech.epiandroid.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.Adapters.MarksAdapter;
import epitech.epiandroid.Databases.Marks;
import epitech.epiandroid.Items.MarksItem;
import epitech.epiandroid.R;
import epitech.epiandroid.Tasks.MarksTask;


public class NotesFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_section_marks, container, false);
        Boolean isMarksDisplayed = Marks.listAll(Marks.class).size() > 0;

        if (!isMarksDisplayed) {
            rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            new MarksTask(getActivity(), rootView).execute((Void) null);
        } else {
            rootView.findViewById(R.id.marks_progress).setVisibility(View.VISIBLE);
            List<MarksItem> items = new ArrayList<>();
            List<Marks> marks = Marks.listAll(Marks.class);

            for (int i = 0; i < marks.size(); ++i) {
                Marks mark = marks.get(i);
                items.add(new MarksItem(mark.getNote(), mark.getModuleName(), mark.getProjectName(), mark.getContainerColor(), mark.getModuleImage()));
            }

            ListView marksList = (ListView) rootView.findViewById(R.id.marks_list);
            ListAdapter customAdapter = new MarksAdapter(rootView.getContext(), R.layout.marks_item, items);
            marksList.setAdapter(customAdapter);
            rootView.findViewById(R.id.marks_progress).setVisibility(View.GONE);
        }
        return rootView;
    }
}
