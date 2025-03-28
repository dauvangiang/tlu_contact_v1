package com.dvgiang.tlucontact.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dvgiang.tlucontact.R;
import com.dvgiang.tlucontact.fragments.StaffFragment;
import com.dvgiang.tlucontact.fragments.UnitFragment;

public class ContactActivity extends AppCompatActivity {
    private TextView tvUnit, tvStaff, tvBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        findViews();
        tvBackButton.setText(R.string.contact_text);
        showUnits(tvUnit);
    }

    private void findViews() {
        tvUnit = findViewById(R.id.button_unit);
        tvStaff = findViewById(R.id.button_staff);
        tvBackButton = findViewById(R.id.back_button_text);
    }

    // Thực hiện back lại màn hình khác
    public void back(View view) {
        finish();
    }

    // Hiển thị danh sách các đơn vị
    public void showUnits(View view) {
        resetUnit(); //Đặt lại trạng thái layout
        replaceFragment(new UnitFragment());
    }

    public void showStaffs(View view) {
        resetStaff(); //Đặt lại trạng thái layout
        replaceFragment(new StaffFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment); // Thay thế Fragment hiện tại
        transaction.addToBackStack(null); // Cho phép quay lại Fragment trước đó bằng nút Back
        transaction.commit();
    }

    //Đặt lại trạng thái layout unit
    private void resetUnit() {
        tvUnit.setBackground(ContextCompat.getDrawable(this, R.drawable.border_blue_selected));
        tvUnit.setTextColor(ContextCompat.getColor(this, R. color. white));
        tvStaff.setBackground(ContextCompat.getDrawable(this, R.drawable.border_blue));
        tvStaff.setTextColor(ContextCompat.getColor(this, R. color. black));
    }

    //Đặt lại trạng thái layout staff
    private void resetStaff() {
        tvStaff.setBackground(ContextCompat.getDrawable(this, R.drawable.border_blue_selected));
        tvStaff.setTextColor(ContextCompat.getColor(this, R. color. white));
        tvUnit.setBackground(ContextCompat.getDrawable(this, R.drawable.border_blue));
        tvUnit.setTextColor(ContextCompat.getColor(this, R. color. black));
    }
}