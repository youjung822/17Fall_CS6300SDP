package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author John Youngblood
 */

public class WordScrambleStatAdapter extends ArrayAdapter<WordScrambleTable> {

    int mResource;
    List<String> solvedScrambles;
    private Context mContext;

    /**
     * Default constructor for the WordScrambleStatAdapter
     *
     * @param context
     * @param resource
     * @param objects
     */
    public WordScrambleStatAdapter(Context context, int resource, ArrayList<WordScrambleTable> objects, List<String> solved) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        solvedScrambles = solved;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String uid = getItem(position).uniqueIdentifier.toString();
        String numSolved = getItem(position).numOfTimesSolved.toString();
        String creator = getItem(position).createdBy.toString();
        String clue = getItem(position).clue.toString();
        String solved;
        if (solvedScrambles.contains(uid)) {
            solved = "yes";
        } else {
            solved = "no";
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvScrambleUID = convertView.findViewById(R.id.scrambleUniqueIdentifier);
        TextView tvNumTimesSolved = convertView.findViewById(R.id.numTimesSolved);
        TextView tvCreator = convertView.findViewById(R.id.creator);
        TextView tvSolved = convertView.findViewById(R.id.solved);

        tvScrambleUID.setText(getItem(position).uniqueIdentifier.toString());
        tvNumTimesSolved.setText(numSolved);
        tvCreator.setText(creator);
        tvSolved.setText(solved);
        return convertView;

    }
}
