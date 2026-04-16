package com.example.coffeeshopapp.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        // 👇 NÚT THÊM NHÂN VIÊN
        FloatingActionButton btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(v -> showAddUserDialog());

        loadUsers();
    }

    private void loadUsers() {
        userList = db.getAllUsers();
        adapter = new AdminUserAdapter(userList,
                user -> showEditUserDialog(user),
                user -> confirmDeleteUser(user));
        recyclerView.setAdapter(adapter);
    }

    // ================== THÊM NHÂN VIÊN ==================
    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm người dùng");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        EditText edtUsername = new EditText(this);
        edtUsername.setHint("Username");

        EditText edtPassword = new EditText(this);
        edtPassword.setHint("Password");

        // 👇 ROLE CHỌN
        String[] roles = {"ADMIN", "STAFF", "CUSTOMER"};
        final int[] selectedRole = {1}; // mặc định STAFF

        builder.setSingleChoiceItems(roles, 1, (dialog, which) -> {
            selectedRole[0] = which;
        });

        layout.addView(edtUsername);
        layout.addView(edtPassword);

        builder.setView(layout);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String role = roles[selectedRole[0]];

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.checkUserExists(username)) {
                Toast.makeText(this, "Username đã tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = db.insertUser(username, password, role);

            if (result) {
                Toast.makeText(this, "Thêm thành công (" + role + ")", Toast.LENGTH_SHORT).show();
                loadUsers();
            } else {
                Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Huỷ", null);

        builder.show();
    }

    // ================== ĐỔI ROLE ==================
    private void showEditUserDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa người dùng");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32, 32, 32, 32);

        EditText edtUsername = new EditText(this);
        edtUsername.setHint("Username");
        edtUsername.setText(user.getUsername());

        EditText edtPassword = new EditText(this);
        edtPassword.setHint("Password");
        edtPassword.setText(user.getPassword());

        layout.addView(edtUsername);
        layout.addView(edtPassword);

        String[] roles = {"ADMIN", "STAFF", "CUSTOMER"};

        int checkedItem = 0;
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals(user.getRole())) {
                checkedItem = i;
                break;
            }
        }

        final int[] selectedRole = {checkedItem};

        builder.setSingleChoiceItems(roles, checkedItem, (dialog, which) -> {
            selectedRole[0] = which;
        });

        builder.setView(layout);

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String newUsername = edtUsername.getText().toString().trim();
            String newPassword = edtPassword.getText().toString().trim();
            String newRole = roles[selectedRole[0]];

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = db.updateUser(
                    user.getId(),
                    newUsername,
                    newPassword,
                    newRole
            );

            if (result) {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                loadUsers();
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Huỷ", null);

        builder.show();
    }

    // ================== XOÁ USER ==================
    private void confirmDeleteUser(User user) {
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