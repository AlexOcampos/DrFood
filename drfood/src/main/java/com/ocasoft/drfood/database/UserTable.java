package com.ocasoft.drfood.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by alejandroov on 02/07/2015.
 */
public class UserTable {
    // Foods table name
    public static final String TABLE_NAME_USER = "user";

    // Foods Table Columns names
    public static final String COLUMN_NAME_USER_ID = "userId"; // Id_usuario_profile
    public static final String COLUMN_NAME_USER_LOGIN = "login"; // login_usuario
    public static final String COLUMN_NAME_USER_PASSWORD = "password"; // contrasena_ususario
    public static final String COLUMN_NAME_USER_NAME = "name"; // nombre_usuario
    public static final String COLUMN_NAME_USER_SURNAME = "surname"; // apellidos_usuario
    public static final String COLUMN_NAME_USER_PICTURE_NAME = "pictureName"; // nombre_imagen_usuario
    public static final String COLUMN_NAME_USER_PICTURE = "picture"; // imagen_usuario
    public static final String COLUMN_NAME_USER_DNI = "dni"; // dni_usuario
    public static final String COLUMN_NAME_USER_BIRTHDAY = "birthday"; // fecha_nacimiento_usuario
    public static final String COLUMN_NAME_USER_BLOOD_GROUP = "bloodGroup"; // grupo_sanguineo_usuario
    public static final String COLUMN_NAME_USER_GENDER = "gender"; // sexo_usuario
    public static final String COLUMN_NAME_USER_PHONE = "phone"; // telefono_usuario
    public static final String COLUMN_NAME_USER_PROFESSION = "profession"; // profesion_usuario
    public static final String COLUMN_NAME_USER_STUDIES = "studies"; // estudios_usuario
    public static final String COLUMN_NAME_USER_CIVIL_STATUS = "civilStatus"; // estado_civil_usuario
    public static final String COLUMN_NAME_USER_ADDRESS = "address"; // direccion_usuario
    public static final String COLUMN_NAME_USER_POSTCODE = "postCode"; // codPostal_usuario
    public static final String COLUMN_NAME_USER_CITY = "city"; // ciudad_usuario
    public static final String COLUMN_NAME_USER_STATE = "state"; // provincia_usuario
    public static final String COLUMN_NAME_USER_COUNTRY = "country"; // pais_usuario


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    // Food create table sql
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + TABLE_NAME_USER + " (" +
                    COLUMN_NAME_USER_ID			    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_LOGIN		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_PASSWORD	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_NAME		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_SURNAME	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_PICTURE_NAME   + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_PICTURE	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_DNI		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_BIRTHDAY	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_BLOOD_GROUP    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_GENDER		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_PHONE		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_PROFESSION	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_STUDIES	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_CIVIL_STATUS   + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_ADDRESS	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_POSTCODE	    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_CITY		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_STATE		    + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_USER_COUNTRY        + TEXT_TYPE +
                    " )";


    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USER;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_USER);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(TrackFoodTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL(SQL_DELETE_USER);
        onCreate(database);
    }

    public static String addPrefix(String column) {
        return TABLE_NAME_USER + "." + column;
    }

    public static String removePrefix(String colum) {
        return colum.replace(TABLE_NAME_USER,"");
    }
}