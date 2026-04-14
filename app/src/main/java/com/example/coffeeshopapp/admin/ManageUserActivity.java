package com.example.coffeeshopapp.admin;


import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.User;

import java.util.List;

public class ManageUserActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private AdminUserAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadUsers();
    }

    private void loadUsers() {
        userList = db.getAllUsers();
        adapter = new AdminUserAdapter(userList,
                user -> showRoleDialog(user),    // Đổi role
                user -> confirmDeleteUser(user)); // Xoá user
        recyclerView.setAdapter(adapter);
    }

    private void showRoleDialog(User user) {
        String[] roles = {"CUSTOMER", "STAFF", "ADMIN"};
        new AlertDialog.Builder(this)
                .setTitle("Đổi quyền cho: " + user.getUsername())
                .setItems(roles, (dialog, which) -> {
                    String newRole = roles[which];
                    if (db.updateUserRole(user.getId(), newRole)) {
                        Toast.makeText(this,
                                "Đã cập nhật quyền: " + newRole, Toast.LENGTH_SHORT).show();
                        loadUsers();
                    }
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

    private void confirmDeleteUser(User user) {
        // Không cho xoá chính mình hoặc admin duy nhất
        if (user.getRole().equals("ADMIN")) {
            Toast.makeText(this, "Không thể xoá tài khoản Admin!", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xoá")
                .setMessage("Xoá tài khoản \"" + user.getUsername() + "\"?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    if (db.deleteUser(user.getId())) {
                        Toast.makeText(this, "Đã xoá tài khoản!", Toast.LENGTH_SHORT).show();
                        loadUsers();
                    }
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
}
