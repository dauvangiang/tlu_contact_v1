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
import com.dvgiang.tlucontact.adapter.StaffAdapter;
import com.dvgiang.tlucontact.model.Staff;
import com.dvgiang.tlucontact.utils.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StaffFragment extends Fragment {
    private RecyclerView rcvStaff;
    private List<Staff> dataStaffs, currData;
    private StaffAdapter staffAdapter;
    private ImageView sortStaffBtn;
    private EditText edtSearch;
    private Spinner spinner;
    private boolean isAscending = true;
    private DBHelper dbHelper;

    public StaffFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        referenceToViews(view);
        initData(getContext());
        setupSearching();

        return view;
    }

    private void referenceToViews(View view) {
        rcvStaff = view.findViewById(R.id.recycler_staff);
        sortStaffBtn = view.findViewById(R.id.button_sort_staff);
        edtSearch = view.findViewById(R.id.edit_text_search);
        spinner = view.findViewById(R.id.staff_dropdown);
    }

    private void initData(Context context) {
        dataStaffs = dbHelper.getAllStaffs();
        if (dataStaffs.isEmpty()) {
            insertDummyData();
            dataStaffs = dbHelper.getAllStaffs();
        }

        currData = new ArrayList<>(dataStaffs);

        rcvStaff.setLayoutManager(new LinearLayoutManager(context));
        staffAdapter = new StaffAdapter(context, currData);
        rcvStaff.setAdapter(staffAdapter);
        sortStaffBtn.setOnClickListener(v -> {
            isAscending = !isAscending;
            staffAdapter.setSortedList(currData, isAscending);
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
                R.array.dropdown_staffs,
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
                    currData = new ArrayList<>(dataStaffs);
                } else if (selectedItem.equals("Khác")) {
                    List<String> excludeTypes = Arrays.asList("Khoa Công nghệ thông tin", "Khoa Cơ khí", "Khoa Kinh tế", "Phòng Đào tạo", "Thư viện", "Trung tâm Ngoại ngữ", "Trung tâm Tin học");
                    currData = dataStaffs.stream()
                            .filter(staff -> excludeTypes.stream().noneMatch(type -> staff.getCurrUnit().toLowerCase().equals(type.toLowerCase())))
                            .collect(Collectors.toList());
                } else {
                    currData = dataStaffs.stream()
                            .filter(staff -> staff.getCurrUnit().toLowerCase().equals(selectedItem.toLowerCase()))
                            .collect(Collectors.toList());
                }

                staffAdapter.filterList(currData);
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
        List<Staff> staffs = new ArrayList<>();
        for (Staff staff : dataStaffs) {
            if (staff.getName().toLowerCase().contains(text.toLowerCase()) ||
                    staff.getPhone().contains(text) ||
                    staff.getEmail().toLowerCase().contains(text.toLowerCase())) {
                staffs.add(staff);
            }
        }
        currData = staffs;
        staffAdapter.filterList(staffs);
    }

    private void insertDummyData() {
        dbHelper.addStaff(new Staff(R.drawable.avatar, "TS. Nguyễn Văn An", "0987654321", "nguyenvana@university.edu.vn", "Phòng Đào tạo", "Trưởng phòng"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "ThS. Trần Thị Bích", "0978123456", "tranthibich@university.edu.vn", "Thư viện", "Giám đốc"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "PGS. TS. Lê Văn Cường", "0969988776", "levancuong@university.edu.vn", "Khoa Công nghệ thông tin", "Trưởng khoa"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "GS. TS. Phạm Minh Đức", "0956677889", "phamminhduc@university.edu.vn", "Khoa Công nghệ thông tin", "Phó khoa"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "TS. Đỗ Thị Hằng", "0945566778", "dothihang@university.edu.vn", "Khoa Công nghệ thông tin", "Giảng viên"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "ThS. Bùi Quang Huy", "0934455667", "buiquanghuy@university.edu.vn", "Khoa Cơ khí", "Giảng viên"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "TS. Hoàng Thị Lan", "0923344556", "hoangthilan@university.edu.vn", "Khoa Cơ khí", "Giảng viên"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "ThS. Nguyễn Minh Phúc", "0912233445", "nguyenminhphuc@university.edu.vn", "Khoa Cơ khí", "Giảng viên"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "PGS. TS. Võ Thị Quỳnh", "0901122334", "vothiquynh@university.edu.vn", "Khoa Cơ khí", "Giảng viên"));
        dbHelper.addStaff(new Staff(R.drawable.avatar, "TS. Đặng Thanh Sơn", "0890011223", "dangthanhson@university.edu.vn", "Khoa Cơ khí", "Giảng viên"));
    }
}