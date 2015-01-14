package epitech.epiandroid.Activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import epitech.epiandroid.R;

import java.util.List;

/**
 * Created by poliveira on 24/10/2014.
 */
/*
public class DrawerNavigationAdapter extends RecyclerView.Adapter<DrawerNavigationAdapter.ViewHolder> {
    private static final int TYPE_HEADER = Integer.MIN_VALUE;
    private List<DrawerItem> mData;
    private DrawerNavigationCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public DrawerNavigationAdapter(List<DrawerItem> data) {
        mData = data;
    }

    public DrawerNavigationCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(DrawerNavigationCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public DrawerNavigationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row_first, viewGroup, false);
        }
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DrawerNavigationAdapter.ViewHolder viewHolder, final int i) {
        if (i != 0) {
            viewHolder.textView.setText(mData.get(i).getText());
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(i).getDrawable(), null, null, null);

            viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                                                       @Override
                                                       public boolean onTouch(View v, MotionEvent event) {

                                                           switch (event.getAction()) {
                                                               case MotionEvent.ACTION_DOWN:
                                                                   touchPosition(i);
                                                                   return false;
                                                               case MotionEvent.ACTION_CANCEL:
                                                                   touchPosition(-1);
                                                                   return false;
                                                               case MotionEvent.ACTION_MOVE:
                                                                   return false;
                                                               case MotionEvent.ACTION_UP:
                                                                   touchPosition(-1);
                                                                   return false;
                                                           }
                                                           return true;
                                                       }
                                                   }
            );
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           if (mNavigationDrawerCallbacks != null)
                                                               mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(i);
                                                       }
                                                   }
            );
        }

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}

*/



























/**
 * Created by khaled bakhtiari on 10/26/2014.
 * <a href="http://about.me/kh.bakhtiari">
 */
public class DrawerNavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<DrawerItem> mData;
    private DrawerNavigationCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public DrawerNavigationAdapter(List<DrawerItem> data) {
        mData = data;
    }

    public DrawerNavigationCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(DrawerNavigationCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row, parent, false);
            return new VHItem(v);
        } else if (viewType == TYPE_HEADER) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row_first, parent, false);
            return new VHHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHItem) {
            DrawerItem dataItem = getItem(position);
            ((VHItem) holder).textView.setText(mData.get(position).getText());
            ((VHItem) holder).textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(position).getDrawable(), null, null, null);

            ((VHItem) holder).itemView.setOnTouchListener(new View.OnTouchListener() {
                                                       @Override
                                                       public boolean onTouch(View v, MotionEvent event) {

                                                           switch (event.getAction()) {
                                                               case MotionEvent.ACTION_DOWN:
                                                                   touchPosition(position);
                                                                   return false;
                                                               case MotionEvent.ACTION_CANCEL:
                                                                   touchPosition(-1);
                                                                   return false;
                                                               case MotionEvent.ACTION_MOVE:
                                                                   return false;
                                                               case MotionEvent.ACTION_UP:
                                                                   touchPosition(-1);
                                                                   return false;
                                                           }
                                                           return true;
                                                       }
                                                   }
            );
            ((VHItem) holder).itemView.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           if (mNavigationDrawerCallbacks != null)
                                                               mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(position);
                                                       }
                                                   }
            );
            //TODO: selected menu position, change layout accordingly
            if (mSelectedPosition == position || mTouchedPosition == position) {
                ((VHItem) holder).itemView.setBackgroundColor(((VHItem) holder).itemView.getContext().getResources().getColor(R.color.selected_gray));
            } else {
                ((VHItem) holder).itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        } else if (holder instanceof VHHeader) {
            ((VHHeader) holder).textView.setText(mData.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private DrawerItem getItem(int position) {
        return mData.get(position);
    }

    class VHItem extends RecyclerView.ViewHolder {
        public TextView textView;

        public VHItem(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView textView;

        public VHHeader(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.side_user_login);
        }
    }
}