package com.dvgiang.tlucontact.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dvgiang.tlucontact.R;
import com.dvgiang.tlucontact.activities.staff.StaffDetailActivity;
import com.dvgiang.tlucontact.model.Staff;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StaffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> items;
    private final Context context;
    private boolean isAscending = true;

    public StaffAdapter(Context context, List<Staff> staffs) {
        this.context = context;
        setSortedList(staffs, isAscending);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSortedList(List<Staff> staffs, boolean ascending) {
        isAscending = ascending;

        // Sắp xếp danh sách theo tên
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.PRIMARY);
        staffs.sort((s1, s2) ->
                isAscending ? collator.compare(s1.getName(), s2.getName()) : collator.compare(s2.getName(), s1.getName()));

        // Gom nhóm theo chữ cái đầu tiên
        Map<String, List<Staff>> groupedData = new LinkedHashMap<>();
        for (Staff staff : staffs) {
            String firstLetter = staff.getName().substring(0, 1).toUpperCase();
            groupedData.putIfAbsent(firstLetter, new ArrayList<>());
            groupedData.get(firstLetter).add(staff);
        }

        // Chuyển đổi thành danh sách với header + item
        items = new ArrayList<>();
        for (Map.Entry<String, List<Staff>> entry : groupedData.entrySet()) {
            items.add(entry.getKey()); // Header
            items.addAll(entry.getValue()); // Danh sách nhân viên
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof String ? TYPE_HEADER : TYPE_ITEM;
    }

    public void filterList(List<Staff> filteredList) {
        setSortedList(filteredList, isAscending); // Sắp xếp & nhóm lại danh sách
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new StaffViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind((String) items.get(position));
        } else {
            Staff staff = (Staff) items.get(position);
            ((StaffViewHolder) holder).bind(staff);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, StaffDetailActivity.class);
                intent.putExtra("itemSelected", staff);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewHeader;

        public HeaderViewHolder(@NonNull View view) {
            super(view);
            textViewHeader = view.findViewById(R.id.header_text);
        }

        public void bind(String header) {
            textViewHeader.setText(header);
        }
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName, textViewPhone, textViewEmail;

        public StaffViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            textViewName = view.findViewById(R.id.name);
            textViewPhone = view.findViewById(R.id.phone);
            textViewEmail = view.findViewById(R.id.email);
        }

        public void bind(Staff staff) {
            imageView.setImageResource(staff.getImage());
            textViewName.setText(staff.getName());
            textViewPhone.setText(staff.getPhone());
            textViewEmail.setText(staff.getEmail());
        }
    }
}
