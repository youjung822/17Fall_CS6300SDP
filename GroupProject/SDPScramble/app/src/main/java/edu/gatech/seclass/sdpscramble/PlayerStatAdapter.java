package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static edu.gatech.seclass.sdpscramble.R.id.avgSolvedCreated;
import static edu.gatech.seclass.sdpscramble.R.id.playerName;

/**
 * Created by youjungkim on 10/13/17.
 */

public class PlayerStatAdapter extends ArrayAdapter<PlayerTable>{

    private Context mContext;
    int mResource;

    /**
     * Default constructor for the PlayerStatAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PlayerStatAdapter(Context context, int resource, ArrayList<PlayerTable> objects){
        super(context,resource,objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get the players information
        String player = getItem(position).firstName + ' ' + getItem(position).lastName;
        String numSolved = getItem(position).numOfScramblesSolved.toString();
        String numCreated= getItem(position).numOfScramblesCreated.toString();
        String avgSolved=  getItem(position).avgCreationsSolved.toString();

        //PlayerTable player = new PlayerTable(player,numSolved,numCreated,avgSolved)

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPlayerName = (TextView)convertView.findViewById(R.id.playerName);
        TextView tvNumSolved = (TextView)convertView.findViewById(R.id.numSolved);
        TextView tvNumCreated = (TextView)convertView.findViewById(R.id.numCreated);
        TextView tvAvgSolved = (TextView)convertView.findViewById(R.id.avgSolvedCreated);

        tvPlayerName.setText(player);
        tvNumSolved.setText(numSolved);
        tvNumCreated.setText(numCreated);
        tvAvgSolved.setText(avgSolved);
        return convertView;

    }
}
