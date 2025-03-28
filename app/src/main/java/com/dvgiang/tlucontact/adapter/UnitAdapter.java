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
import com.dvgiang.tlucontact.activities.unit.UnitDetailActivity;
import com.dvgiang.tlucontact.model.Unit;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UnitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> items;
    private final Context context;
    private boolean isAscending = true;

    public UnitAdapter(Context context, List<Unit> units) {
        this.context = context;
        setSortedList(units, isAscending);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSortedList(List<Unit> units, boolean ascending) {
        isAscending = ascending;

        // Sắp xếp danh sách theo tên
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.PRIMARY);
        units.sort((s1, s2) ->
                isAscending ? collator.compare(s1.getName(), s2.getName()) : collator.compare(s2.getName(), s1.getName()));

        // Gom nhóm theo chữ cái đầu tiên
        Map<String, List<Unit>> groupedData = new LinkedHashMap<>();
        for (Unit unit : units) {
            String firstLetter = unit.getName().substring(0, 1).toUpperCase();
            groupedData.putIfAbsent(firstLetter, new ArrayList<>());
            groupedData.get(firstLetter).add(unit);
        }

        // Chuyển đổi thành danh sách với header + item
        items = new ArrayList<>();
        for (Map.Entry<String, List<Unit>> entry : groupedData.entrySet()) {
            items.add(entry.getKey()); // Header
            items.addAll(entry.getValue()); // Danh sách nhân viên
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof String ? TYPE_HEADER : TYPE_ITEM;
    }

    public void filterList(List<Unit> filteredList) {
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
            return new UnitViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UnitAdapter.HeaderViewHolder) {
            ((UnitAdapter.HeaderViewHolder) holder).bind((String) items.get(position));
        } else {
            Unit unit = (Unit) items.get(position);
            ((UnitViewHolder) holder).bind(unit);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, UnitDetailActivity.class);
                intent.putExtra("itemSelected", unit);
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

    public static class UnitViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textViewName, textViewPhone, textViewEmail;

        public UnitViewHolder(@NonNull View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.textViewName = view.findViewById(R.id.name);
            this.textViewPhone = view.findViewById(R.id.phone);
            this.textViewEmail = view.findViewById(R.id.email);
        }

        public void bind(Unit unit) {
            imageView.setImageResource(unit.getImage());
            textViewName.setText(unit.getName());
            textViewPhone.setText(unit.getPhone());
            textViewEmail.setText(unit.getEmail());
        }
    }
}
