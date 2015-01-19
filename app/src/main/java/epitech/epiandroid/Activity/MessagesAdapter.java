package epitech.epiandroid.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import epitech.epiandroid.R;

/**
 * Created by Styve on 14/01/2015.
 */

public class MessagesAdapter extends ArrayAdapter<MessagesItem> {

    public MessagesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MessagesAdapter(Context context, int resource, List<MessagesItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.profil_message, null);
        }

        MessagesItem p = getItem(position);

        if (p != null) {
            TextView date = (TextView) v.findViewById(R.id.message_date);
            TextView content = (TextView) v.findViewById(R.id.message_content);
            TextView login = (TextView) v.findViewById(R.id.message_login);
            TextView title = (TextView) v.findViewById(R.id.message_title);
            ImageView image = (ImageView) v.findViewById(R.id.message_picture);

            if (date != null) {
                date.setText(p.getDate());
            }
            if (content != null) {
                content.setText(p.getContent());
            }
            if (login != null) {
                login.setText(p.getLogin());
            }
            if (title != null) {
                title.setText(p.getTitle());
            }
            if (image != null) {
                if (p.getDrawable() != null)
                    image.setImageDrawable(p.getDrawable());
                else
                    image.setImageBitmap(p.getBitmap());
            }
        }
        return v;
    }
}