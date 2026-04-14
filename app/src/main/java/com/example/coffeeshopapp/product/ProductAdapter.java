package com.example.coffeeshopapp.product;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopapp.R;
import com.example.coffeeshopapp.database.DatabaseHelper;
import com.example.coffeeshopapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> list;
    String role;
    DatabaseHelper db;

    public ProductAdapter(Context context, List<Product> list, String role) {
        this.context = context;
        this.list = list;
        this.role = role;
        db = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price, category, hot;
        ImageButton edit, delete;

        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgProduct);
            name = v.findViewById(R.id.txtName);
            price = v.findViewById(R.id.txtPrice);
            category = v.findViewById(R.id.txtCategory);
            hot = v.findViewById(R.id.txtHot);
            edit = v.findViewById(R.id.btnEdit);
            delete = v.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup p, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, p, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        Product p = list.get(i);

        h.name.setText(p.getName());
        h.price.setText(p.getPrice() + " đ");
        h.category.setText("Thể loại: " + p.getCategory());
        h.hot.setText("Nổi bật: " + (p.isHot() ? "Có" : "Không"));
//        h.img.setImageResource(R.drawable.ic_launcher_background);
        h.img.setImageResource(R.drawable.ic_coffee);
        h.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("name", p.getName());
            intent.putExtra("price", p.getPrice());
            intent.putExtra("category", p.getCategory());
            intent.putExtra("hot", p.isHot());
            context.startActivity(intent);
        });

        if (role == null || !role.equals("ADMIN")) {
            h.edit.setVisibility(View.GONE);
            h.delete.setVisibility(View.GONE);
        }

        h.delete.setOnClickListener(v -> {
            db.deleteProduct(p.getId());
            list.remove(i);
            notifyDataSetChanged();
        });

        h.edit.setOnClickListener(v -> showUpdateDialog(p));
    }

    private void showUpdateDialog(Product p) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_product, null);

        EditText name = view.findViewById(R.id.edtName);
        EditText price = view.findViewById(R.id.edtPrice);
        EditText cate = view.findViewById(R.id.edtCategory);
        CheckBox hot = view.findViewById(R.id.chkHot);

        name.setText(p.getName());
        price.setText(p.getPrice() + "");
        cate.setText(p.getCategory());
        hot.setChecked(p.isHot());

        new AlertDialog.Builder(context)
                .setTitle("Sửa sản phẩm")
                .setView(view)
                .setPositiveButton("Update", (d, w) -> {
                    db.updateProduct(
                            p.getId(),
                            name.getText().toString(),
                            Integer.parseInt(price.getText().toString()),
                            cate.getText().toString(),
                            hot.isChecked()
                    );
                    notifyDataSetChanged();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
