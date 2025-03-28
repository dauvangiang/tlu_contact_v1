package com.dvgiang.tlucontact.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dvgiang.tlucontact.R;
import com.dvgiang.tlucontact.adapter.UnitAdapter;
import com.dvgiang.tlucontact.model.Unit;
import com.dvgiang.tlucontact.utils.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitFragment extends Fragment {
    private RecyclerView rcvUnit;
    private List<Unit> dataUnits, currData;
    private UnitAdapter unitAdapter;
    private ImageView sortUnitBtn;
    private EditText edtSearch;
    private Spinner spinner;
    private boolean isAscending = true;
    private DBHelper dbHelper;


    public UnitFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit, container, false);

        referenceToViews(view);
        initData(getContext());
        setupSearching();

        return view;
    }

    private void referenceToViews(View view) {
        rcvUnit = view.findViewById(R.id.recycler_unit);
        sortUnitBtn = view.findViewById(R.id.button_sort_unit);
        edtSearch = view.findViewById(R.id.edit_text_search);
        spinner = view.findViewById(R.id.unit_dropdown);
    }

    private void initData(Context context) {
        dataUnits = dbHelper.getAllUnits();
        if (dataUnits.isEmpty()) {
            insertDummyData();
            dataUnits = dbHelper.getAllUnits();
        }

        currData = new ArrayList<>(dataUnits);

        rcvUnit.setLayoutManager(new LinearLayoutManager(context));
        unitAdapter = new UnitAdapter(context, currData);
        rcvUnit.setAdapter(unitAdapter);
        sortUnitBtn.setOnClickListener(v -> {
            isAscending = !isAscending;
            unitAdapter.setSortedList(currData, isAscending);
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.dropdown_units,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(selectedListener());
    }

    private AdapterView.OnItemSelectedListener selectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Tất cả")) {
                    currData = new ArrayList<>(dataUnits);
                } else if (selectedItem.equals("Khác")) {
                    List<String> excludeTypes = Arrays.asList("Khoa", "Phòng", "Trung tâm");
                    currData = dataUnits.stream()
                            .filter(unit -> excludeTypes.stream().noneMatch(type -> unit.getName().toLowerCase().startsWith(type.toLowerCase())))
                            .collect(Collectors.toList());
                } else {
                    currData = dataUnits.stream()
                            .filter(unit -> unit.getName().toLowerCase().startsWith(selectedItem.toLowerCase()))
                            .collect(Collectors.toList());
                }

                unitAdapter.filterList(currData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void setupSearching() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String text) {
        List<Unit> units = new ArrayList<>();
        for (Unit unit : dataUnits) {
            if (unit.getName().toLowerCase().contains(text.toLowerCase()) ||
                    unit.getPhone().contains(text) ||
                    unit.getEmail().toLowerCase().contains(text.toLowerCase())) {
                units.add(unit);
            }
        }
        currData = units;
        unitAdapter.filterList(units);
    }

    private void insertDummyData() {
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Phòng Đào tạo", "0987654321", "daotao@university.edu.vn", "Tòa A, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Phòng Công tác Sinh viên", "0912345678", "ctsv@university.edu.vn", "Tòa B, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Phòng Khoa học Công nghệ", "0901122334", "khcn@university.edu.vn", "Tòa C, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Phòng Hành chính - Quản trị", "0933445566", "hcqt@university.edu.vn", "Tòa D, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Khoa Công nghệ Thông tin", "0977889900", "cntt@university.edu.vn", "Tòa E, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Khoa Điện - Điện tử", "0966123456", "dientu@university.edu.vn", "Tòa F, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Khoa Cơ khí", "0944556677", "cokhi@university.edu.vn", "Tòa G, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Khoa Kinh tế", "0922334455", "kinhte@university.edu.vn", "Tòa H, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Thư viện", "0911223344", "thuvien@university.edu.vn", "Tòa I, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Trung tâm Ngoại ngữ", "0988997766", "ngoaingu@university.edu.vn", "Tòa J, Trường Đại học X"));
        dbHelper.addUnit(new Unit(R.drawable.avatar, "Trung tâm Tin học", "0977112233", "tinhoc@university.edu.vn", "Tòa K, Trường Đại học X"));
    }
}