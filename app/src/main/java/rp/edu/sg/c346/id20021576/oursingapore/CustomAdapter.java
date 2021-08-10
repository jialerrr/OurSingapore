package rp.edu.sg.c346.id20021576.oursingapore;

import android.content.Context;
import android.media.Rating;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Island> islandList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        islandList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvDate = rowView.findViewById(R.id.textViewDate);
//        TextView tvStars = rowView.findViewById(R.id.textViewStars);
        TextView tvSingers = rowView.findViewById(R.id.textViewSinger);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);
        RatingBar rtBarList = rowView.findViewById(R.id.ratingBarList);

        Island currentIsland = islandList.get(position);

        tvTitle.setText(currentIsland.getName());
        tvDate.setText(String.valueOf(currentIsland.getislandArea()));

//        if (currentIsland.getYearReleased() >= 2019) {
//            ivNew.setImageResource(R.drawable.newimg);
//        } else {
//            ivNew.setVisibility(View.INVISIBLE);
//        }

        rtBarList.setClickable(false);
        rtBarList.setRating(currentIsland.getStars());
        tvSingers.setText(currentIsland.getDesc());
        return rowView;
    }
}
