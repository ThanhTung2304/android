package com.example.coffeeshopapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

import com.example.coffeeshopapp.model.Cart;
import com.example.coffeeshopapp.model.Order;
import com.example.coffeeshopapp.model.Product;
import com.example.coffeeshopapp.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AuthDB";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USER = "User";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng User
        String createTable = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "role TEXT)";
        db.execSQL(createTable);

        // Tạo sẵn tài khoản admin
        db.execSQL("INSERT INTO User(username,password,role) VALUES('admin','123','ADMIN')");


        String createProduct = "CREATE TABLE Product (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price INTEGER," +
                "category TEXT," +
                "isHot INTEGER)";
        db.execSQL(createProduct);

        // TABLE CART
        db.execSQL("CREATE TABLE Cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "price INTEGER," +
                "quantity INTEGER)");


        // TABLE ORDER
        db.execSQL("CREATE TABLE Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "customerName TEXT," +
                "phone TEXT," +
                "address TEXT," +
                "total INTEGER)");

// TABLE ORDER DETAIL
        db.execSQL("CREATE TABLE OrderDetail (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER," +
                "productName TEXT," +
                "price INTEGER," +
                "quantity INTEGER)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    // ================= REGISTER =================

    public boolean register(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", "CUSTOMER");

        long result = db.insert(TABLE_USER, null, cv);
        return result != -1;
    }

    // ================= LOGIN =================

    public String login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT role FROM User WHERE username=? AND password=?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            return cursor.getString(0); // trả về role
        }

        return null;
    }

    // ================= CHECK USER EXISTS =================

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM User WHERE username=?",
                new String[]{username}
        );

        return cursor.moveToFirst();
    }

    // ================= GET USER ROLE =================

    public String getRole(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT role FROM User WHERE username=?",
                new String[]{username}
        );

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }

        return null;
    }


    //Lộc
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM User", null);

        if (c.moveToFirst()) {
            do {
                list.add(new User(
                        c.getInt(0),      // id
                        c.getString(1),   // username
                        c.getString(2),   // password
                        c.getString(3)    // role
                ));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Orders", null);

        if (c.moveToFirst()) {
            do {
                list.add(new Order(
                        c.getInt(0),     // id
                        c.getString(1),  // customerName
                        c.getString(2),  // phone
                        c.getString(3),  // address
                        c.getInt(4)      // total
                ));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    public boolean updateUserRole(int id, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("role", role);

        int result = db.update("User", cv, "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
    public boolean deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("User", "id=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
// ================= PRODUCT =================

    // ➕ INSERT
    public boolean insertProduct(String name, int price, String category, boolean isHot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("price", price);
        cv.put("category", category);
        cv.put("isHot", isHot ? 1 : 0);

        return db.insert("Product", null, cv) != -1;
    }

    // 📋 GET ALL
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Product", null);

        if (c.moveToFirst()) {
            do {
                list.add(new Product(
                        c.getInt(0),     // id
                        c.getString(1),  // name
                        c.getInt(2),     // price
                        c.getString(3),  // category
                        c.getInt(4) == 1 // isHot
                ));
            } while (c.moveToNext());
        }

        c.close();
        return list;
    }

    // ❌ DELETE
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Product", "id=?", new String[]{String.valueOf(id)});
    }

    // ✏️ UPDATE
    public void updateProduct(int id, String name, int price, String category, boolean isHot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("price", price);
        cv.put("category", category);
        cv.put("isHot", isHot ? 1 : 0);

        db.update("Product", cv, "id=?", new String[]{String.valueOf(id)});
    }

    // ================= CART =================

    // ➕ ADD
    public void addToCart(String name, int price) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Cart WHERE name=?", new String[]{name});

        if (c.moveToFirst()) {
            int qty = c.getInt(3) + 1;
            db.execSQL("UPDATE Cart SET quantity=? WHERE name=?", new Object[]{qty, name});
        } else {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("price", price);
            cv.put("quantity", 1);
            db.insert("Cart", null, cv);
        }
        c.close();
    }

    // 📋 GET
    public ArrayList<com.example.coffeeshopapp.model.Cart> getCart() {
        ArrayList<com.example.coffeeshopapp.model.Cart> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Cart", null);

        while (c.moveToNext()) {
            list.add(new com.example.coffeeshopapp.model.Cart(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3)
            ));
        }
        c.close();
        return list;
    }

    // 🔄 UPDATE
    public void updateCartQuantity(int id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Cart SET quantity=? WHERE id=?", new Object[]{quantity, id});
    }

    // ❌ DELETE
    public void deleteCart(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Cart", "id=?", new String[]{String.valueOf(id)});
    }
// ================= ORDER =================

    // ➕ CREATE ORDER
    public void createOrder(String name, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Lấy giỏ hàng
        ArrayList<com.example.coffeeshopapp.model.Cart> cartList = getCart();

        int total = 0;
        for (com.example.coffeeshopapp.model.Cart c : cartList) {
            total += c.getPrice() * c.getQuantity();
        }

        // Insert Order
        ContentValues cv = new ContentValues();
        cv.put("customerName", name);
        cv.put("phone", phone);
        cv.put("address", address);
        cv.put("total", total);

        long orderId = db.insert("Orders", null, cv);

        // Insert OrderDetail
        for (com.example.coffeeshopapp.model.Cart c : cartList) {
            ContentValues detail = new ContentValues();
            detail.put("orderId", orderId);
            detail.put("productName", c.getName());
            detail.put("price", c.getPrice());
            detail.put("quantity", c.getQuantity());

            db.insert("OrderDetail", null, detail);
        }

        // Clear Cart sau khi đặt hàng
        db.execSQL("DELETE FROM Cart");
    }

}

