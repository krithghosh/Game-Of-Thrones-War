package com.got.krith.gameofthrones.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.got.krith.gameofthrones.model.King;

import rx.functions.Func1;

/**
 * Created by krith on 04/03/17.
 */

public class KingTable {
    public static final String TABLE = "king";
    public static final String ID = "ID";
    private static final String NAME = "name";
    private static final String RATING = "rating";
    private static final String STRENGTH = "strength";
    private static final String BATTLES_WON = "battles_won";
    private static final String BATTLES_LOST = "battles_lost";
    private static final String STRENGTH_IN_BATTLE_TYPE = "strength_battle_type";

    public static final String QUERY_ALL_KINGS = "SELECT " + ID + ", " + NAME + ", "
            + RATING + ", " + STRENGTH + ", " + BATTLES_WON + ", " + BATTLES_LOST + ", "
            + STRENGTH_IN_BATTLE_TYPE + " FROM " + TABLE + " ORDER BY " + NAME + " ASC";

    public static final String QUERY_KINGS_BY_STRENGTH = "SELECT " + ID + ", " + NAME + ", "
            + RATING + ", " + STRENGTH + ", " + BATTLES_WON + ", " + BATTLES_LOST + ", "
            + STRENGTH_IN_BATTLE_TYPE + " FROM " + TABLE + " ORDER BY " + STRENGTH + " ASC, "
            + NAME + " ASC";

    public static final String QUERY_KINGS_BY_RATING = "SELECT " + ID + ", " + NAME + ", "
            + RATING + ", " + STRENGTH + ", " + BATTLES_WON + ", " + BATTLES_LOST + ", "
            + STRENGTH_IN_BATTLE_TYPE + " FROM " + TABLE + " ORDER BY " + RATING + " ASC";

    public static final String QUERY_A_KING = "SELECT " + ID + ", " + NAME + ", "
            + RATING + ", " + STRENGTH + ", " + BATTLES_WON + ", " + BATTLES_LOST + ", "
            + STRENGTH_IN_BATTLE_TYPE + " FROM " + TABLE + " WHERE " + ID + "=?";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT NOT NULL,"
            + RATING + " INTEGER DEFAULT 0,"
            + STRENGTH + " TEXT DEFAULT NULL,"
            + BATTLES_WON + " INTEGER DEFAULT 0,"
            + BATTLES_LOST + " INTEGER DEFAULT 0,"
            + STRENGTH_IN_BATTLE_TYPE + " TEXT DEFAULT NULL"
            + ")";

    public static final class Builder {

        private final ContentValues contentValues = new ContentValues();

        public KingTable.Builder setId(int id) {
            contentValues.put(ID, id);
            return this;
        }

        public KingTable.Builder setName(String value) {
            contentValues.put(NAME, value);
            return this;
        }

        public KingTable.Builder setRating(int value) {
            contentValues.put(RATING, value);
            return this;
        }

        public KingTable.Builder setStrength(String value) {
            contentValues.put(STRENGTH, value);
            return this;
        }

        public KingTable.Builder setBattlesWon(int value) {
            contentValues.put(BATTLES_WON, value);
            return this;
        }

        public KingTable.Builder setBattlesLost(int value) {
            contentValues.put(BATTLES_LOST, value);
            return this;
        }

        public KingTable.Builder setStrengthBattleType(String value) {
            contentValues.put(STRENGTH_IN_BATTLE_TYPE, value);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }

    public static final Func1<Cursor, King> MAPPER = new Func1<Cursor, King>() {

        @Override
        public King call(Cursor cursor) {

            King contact = new King();
            contact.setId(Db.getInt(cursor, ID));
            contact.setName(Db.getString(cursor, NAME));
            contact.setRating(Db.getInt(cursor, RATING));
            contact.setStrength(Db.getString(cursor, STRENGTH));
            contact.setBattlesWon(Db.getInt(cursor, BATTLES_WON));
            contact.setBattlesLost(Db.getInt(cursor, BATTLES_LOST));
            contact.setStrengthBattleType(Db.getString(cursor, STRENGTH_IN_BATTLE_TYPE));
            return contact;
        }
    };
}
