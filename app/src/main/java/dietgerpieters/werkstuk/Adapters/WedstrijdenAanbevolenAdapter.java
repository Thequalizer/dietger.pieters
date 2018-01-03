package dietgerpieters.werkstuk.Adapters;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dietgerpieters.werkstuk.Activities.InschrijvingenActivity;
import dietgerpieters.werkstuk.Database.AppDatabase;
import dietgerpieters.werkstuk.Models.TussenTabel;
import dietgerpieters.werkstuk.Models.Wedstrijd;
import dietgerpieters.werkstuk.R;

/**
 * Created by User on 13/12/2017.
 */

public class WedstrijdenAanbevolenAdapter extends ArrayAdapter<Wedstrijd> {
    ArrayList<Wedstrijd> list;
    Context context;

    public WedstrijdenAanbevolenAdapter(Context context, int resource, ArrayList<Wedstrijd> objects) {
        super(context, resource, objects);
        list = objects;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.wedstrijd_row_aanbevolen, parent, false);

        final Wedstrijd wed = list.get(position);

        TextView afstandView = (TextView) rowView.findViewById(R.id.afstandValue);
        TextView titelView = (TextView) rowView.findViewById(R.id.titelValue);

        final Button addBtn = (Button) rowView.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                AppDatabase mDb = Room.databaseBuilder(getContext(), AppDatabase.class, "wedstrijdDB").allowMainThreadQueries().build();
                ArrayList<Wedstrijd> myList = (ArrayList<Wedstrijd>) mDb.wedstrijdDAO().loadAllWedstrijden();

                mDb.userDAO().loadActiveUser().getIngeschrevenWedstrijden().add(list.get(position));
                mDb.usersRacesDAO().insertRelation(new TussenTabel(mDb.userDAO().loadActiveUser().getId(), wed.getId()));

                notifyDataSetChanged();

                Toast.makeText(getContext(), "Wedstrijd toegevoegd", Toast.LENGTH_SHORT).show();


                ((InschrijvingenActivity) context).recreate();


            }
        });


        afstandView.setText(Double.toString(wed.getAfstand()));
        titelView.setText(wed.getTitel());

        return rowView;
    }
}
