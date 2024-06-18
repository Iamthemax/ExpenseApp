package com.example.myexpenseapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myexpenseapp.model.Category;
import com.example.myexpenseapp.model.Expense;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ExpenseManager.db";
    // Table Names
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_CATEGORY = "category";

    // Common Column
    private static final String COLUMN_IS_DELETED = "is_deleted";

    // Category Table Columns
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";

    // Expense Table Columns
    private static final String COLUMN_EXPENSE_ID = "id";
    private static final String COLUMN_EXPENSE_NAME = "name";
    private static final String COLUMN_EXPENSE_DATE = "date";
    private static final String COLUMN_EXPENSE_AMOUNT = "amount";
    private static final String COLUMN_EXPENSE_CATEGORY_ID = "categoryId";
    private static final String COLUMN_EXPENSE_CATEGORY_NAME = "categoryName";

    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY_NAME + " TEXT NOT NULL, "
            + COLUMN_IS_DELETED + " INTEGER DEFAULT 0);";


    // Create Expense Table
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE + "("
            + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXPENSE_NAME + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_DATE + " TEXT NOT NULL, "
            + COLUMN_EXPENSE_AMOUNT + " REAL NOT NULL, "
            + COLUMN_EXPENSE_CATEGORY_ID + " INTEGER, "
            + COLUMN_EXPENSE_CATEGORY_NAME + " TEXT NOT NULL, "
            + COLUMN_IS_DELETED + " INTEGER DEFAULT 0"
            + ");";


    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("`mytag",CREATE_TABLE_EXPENSE);
        Log.d("`mytag",CREATE_TABLE_CATEGORY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_CATEGORY);
        // insertCategory("Default");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        // Create tables again
        // onCreate(db);
    }

    public long insertCategory(String categoryName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_NAME, categoryName);
        long id = sqLiteDatabase.insert(TABLE_CATEGORY, null, contentValues);
        return id;
    }

    public long insertExpense(Expense expense) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_CATEGORY_NAME, expense.getCategoryName());
        contentValues.put(COLUMN_EXPENSE_CATEGORY_ID, expense.getCategoryId());
        contentValues.put(COLUMN_EXPENSE_AMOUNT, expense.getAmount());
        contentValues.put(COLUMN_EXPENSE_NAME, expense.getName());
        contentValues.put(COLUMN_EXPENSE_DATE, expense.getDate());
        long id = sqLiteDatabase.insert(TABLE_EXPENSE, null, contentValues);
        return id;
    }

    public ArrayList<Expense> getAllExpenses() {
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_IS_DELETED + "=0 ORDER BY id  DESC ", null);
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_NAME)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_DATE)));
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_ID)));
                expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_AMOUNT)));
                expense.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
                expense.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
                list.add(expense);
            } while (cursor.moveToNext());
        }
        Log.d("mytag", "" + list.size());
        return list;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_IS_DELETED + "=0 ORDER BY id DESC ", null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
                category.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
                list.add(category);
            } while (cursor.moveToNext());
        }
        Log.d("mytag", "" + list.size());
        return list;
    }

    public double getSum() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT SUM(amount) FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_IS_DELETED + "=0", null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        return 0;
    }

    public ArrayList<CategoryAverage> getAverageExpensesByCategory() {
        ArrayList<CategoryAverage> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_EXPENSE_CATEGORY_ID + ", " + COLUMN_EXPENSE_CATEGORY_NAME + ", AVG(" + COLUMN_EXPENSE_AMOUNT + ") as average_amount "
                + "FROM " + TABLE_EXPENSE + " WHERE " + COLUMN_IS_DELETED + " = 0 "
                + "GROUP BY " + COLUMN_EXPENSE_CATEGORY_ID + ", " + COLUMN_EXPENSE_CATEGORY_NAME;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CATEGORY_ID));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CATEGORY_NAME));
                double averageAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("average_amount"));
                list.add(new CategoryAverage(categoryId, categoryName, averageAmount));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Other existing methods...

    public static class CategoryAverage {
        private int categoryId;
        private String categoryName;
        private double averageAmount;

        public CategoryAverage(int categoryId, String categoryName, double averageAmount) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.averageAmount = averageAmount;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public double getAverageAmount() {
            return averageAmount;
        }
    }

    public ArrayList<CategoryWeeklyExpense> getWeeklyCategoryExpenses() {
        ArrayList<CategoryWeeklyExpense> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        // Query to fetch sum of expenses for each category for the past week
        String query = "SELECT " + COLUMN_EXPENSE_CATEGORY_NAME + ", SUM(" + COLUMN_EXPENSE_AMOUNT + ") as total_amount "
                + "FROM " + TABLE_EXPENSE
                + " WHERE " + COLUMN_IS_DELETED + " = 0 "
                + "AND " + COLUMN_EXPENSE_DATE + " >= date('now', '-7 days') "
                + "GROUP BY " + COLUMN_EXPENSE_CATEGORY_NAME;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPENSE_CATEGORY_NAME));
                double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount"));
                list.add(new CategoryWeeklyExpense(categoryName, totalAmount));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public static class CategoryWeeklyExpense {
        private String categoryName;
        private double totalAmount;

        public CategoryWeeklyExpense(String categoryName, double totalAmount) {
            this.categoryName = categoryName;
            this.totalAmount = totalAmount;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }


}
