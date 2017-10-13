package edu.gatech.seclass.sdpscramble;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by tamur on 10/12/17.
 */

public class SDPDatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "SDP.db";
        private static final int DATABASE_VERSION = 1;

        public SDPDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        static {
            // register models
            cupboard().register(PlayerTable.class);
            cupboard().register(WordScrambleTable.class);
            cupboard().register(ProgressTrackerTable.class);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            // ensure all tables are created
            cupboard().withDatabase(db).createTables();
            // add indexes and other database tweaks in this method if needed

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // upgrade tables, add columns and new tables
            //  existing columns will not be converted
            cupboard().withDatabase(db).upgradeTables();
            // migration work if we have an alteration to make to schema

        }

}
