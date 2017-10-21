package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author youjungkim
 */

public class PlayerStatAdapter extends ArrayAdapter<PlayerTable>{

    int mResource;
    private Context mContext;

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

        TextView tvPlayerName = convertView.findViewById(R.id.playerName);
        TextView tvNumSolved = convertView.findViewById(R.id.numSolved);
        TextView tvNumCreated = convertView.findViewById(R.id.numCreated);
        TextView tvAvgSolved = convertView.findViewById(R.id.avgSolvedCreated);

        tvPlayerName.setText(player);
        tvNumSolved.setText(numSolved);
        tvNumCreated.setText(numCreated);
        tvAvgSolved.setText(avgSolved);
        return convertView;

    }
}
