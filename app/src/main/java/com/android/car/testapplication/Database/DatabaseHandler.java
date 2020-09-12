package com.android.car.testapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.car.testapplication.Models.EmpSub;
import com.android.car.testapplication.Models.Employee;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TestAppDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EMP = "emp_table";
    private static final String TABLE_ADR = "adr_table";
    private static final String TABLE_CMP = "emp_table";
    private static final String TABLE_GEO = "geo_table";

    public String id = "id";
    public String name = "name";
    public String username = "username";
    public String email = "email";
    public String profile_image = "profile_image";
    public String phone = "phone";
    public String website = "website";

    public String street = "street";
    public String suite = "suite";
    public String city = "city";
    public String zipcode = "zipcode";

    public String lat = "lat";
    public String lng = "lng";

    public String cmp_name = "name";
    public String catchPhrase = "catchPhrase";
    public String bs = "bs";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_EMP = "CREATE TABLE IF NOT EXISTS " + TABLE_EMP + "("
                + id + " INTEGER PRIMARY KEY,"
                + name + " TEXT,"
                + username + " TEXT,"
                + email + " TEXT,"
                + profile_image + " TEXT,"
                + phone + " TEXT,"
                + website + " TEXT" + ");";

        String CREATE_TABLE_ADR = "CREATE TABLE IF NOT EXISTS " + TABLE_ADR + "("
                + id + " INTEGER PRIMARY KEY,"
                + street + " TEXT,"
                + suite + " TEXT,"
                + city + " TEXT,"
                + zipcode + " TEXT" + ");";

        String CREATE_TABLE_GEO = "CREATE TABLE IF NOT EXISTS " + TABLE_GEO + "("
                + id + " INTEGER PRIMARY KEY,"
                + lat + " TEXT,"
                + lng + " TEXT" + ");";

        String CREATE_TABLE_CMP = "CREATE TABLE IF NOT EXISTS " + TABLE_CMP + "("
                + id + " INTEGER PRIMARY KEY,"
                + cmp_name + " TEXT,"
                + catchPhrase + " TEXT,"
                + bs + " TEXT" + ");";

        db.execSQL(CREATE_TABLE_EMP);
        db.execSQL(CREATE_TABLE_ADR);
        db.execSQL(CREATE_TABLE_GEO);
        db.execSQL(CREATE_TABLE_CMP);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public List<EmpSub> getEmployeeSubList() {
        ArrayList<EmpSub> models = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EMP;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    EmpSub empSub = new EmpSub();
                    empSub.setId(cursor.getInt(0));
                    empSub.setName(cursor.getString(1));
                    empSub.setImage(cursor.getString(4));
                    // Adding contact to list
                    models.add(empSub);
                } while (cursor.moveToNext());
                cursor.close();
            }
        return getEmployeeSubListCompany(models);
    }

    public List<EmpSub> getEmployeeSubListCompany(List<EmpSub> empSubList) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CMP;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {
                    int pId = cursor.getInt(0);
                    for (int i = 0; i < empSubList.size(); i++) {
                        if (pId == empSubList.get(i).getId()) {
                            empSubList.get(i).setCmp_name(cursor.getString(1));
                            break;
                        }
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        return empSubList;
    }

    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, employee.getId());
        values.put(name, employee.getName());
        values.put(username, employee.getUsername());
        values.put(email, employee.getEmail());
        values.put(profile_image, employee.getProfile_image());
        values.put(phone, employee.getPhone());
        values.put(website, employee.getWebsite());

        db.insertWithOnConflict(TABLE_EMP, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        addAddress(employee);

    }

    private void addAddress(Employee employee) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(id, employee.getId());
        values.put(street, employee.getAddress().getStreet());
        values.put(suite, employee.getAddress().getSuite());
        values.put(city, employee.getAddress().getCity());
        values.put(zipcode, employee.getAddress().getZipcode());

        db.insertWithOnConflict(TABLE_ADR, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        addCompany(employee);
    }

    private void addCompany(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, employee.getId());
        values.put(cmp_name, employee.getCompany().getName());
        values.put(catchPhrase, employee.getCompany().getCatchPhrase());
        values.put(bs, employee.getCompany().getBs());
        values.put(zipcode, employee.getAddress().getZipcode());

        db.insertWithOnConflict(TABLE_CMP, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

        addGeo(employee);
    }

    private void addGeo(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, employee.getId());
        values.put(lat, employee.getAddress().getGeo().getLat());
        values.put(lng, employee.getAddress().getGeo().getLng());

        db.insertWithOnConflict(TABLE_GEO, null,
                values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close(); // Closing database connection

    }

    public int getEmpCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_EMP + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

}
