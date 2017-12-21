package dietgerpieters.werkstuk.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

/**
 * Created by User on 13/12/2017.
 */

public class WedstrijdenAdapter extends ArrayAdapter<Wedstrijd> {
    ArrayList<Wedstrijd> list;
    Context context;

    public WedstrijdenAdapter(Context context, int resource, ArrayList<Wedstrijd> objects) {
        super(context, resource, objects);
        list = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.wedstrijd_row, parent, false);

        Wedstrijd wed = list.get(position);

        TextView afstandView = (TextView) rowView.findViewById(R.id.afstandValue);
        TextView titelView = (TextView) rowView.findViewById(R.id.titelValue);

        afstandView.setText(Double.toString(wed.getAfstand()));
        titelView.setText(wed.getTitel());

        return rowView;
    }
}
