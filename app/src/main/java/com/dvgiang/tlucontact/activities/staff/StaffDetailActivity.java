package com.dvgiang.tlucontact.activities.staff;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dvgiang.tlucontact.R;
import com.dvgiang.tlucontact.model.Staff;
import com.dvgiang.tlucontact.utils.ContactHelper;

public class StaffDetailActivity extends AppCompatActivity {
    private ImageView imgvAvatar, copyPhoneAction, copyEmailAction;
    private TextView tvName, tvPhone, tvEmail, tvPosition, tvCurrUnit, tvBackButton;
    private CardView chatAction, callAction, emailAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);

        findViews();
        tvBackButton.setText(R.string.back_detail_text);

        // Hiển thị thông tin chi tiết cho item được chọn có key tương ứng
        Staff staff = getIntent().getParcelableExtra("itemSelected");
        if (staff != null) {
            showData(staff);
        }

        String phoneNumber = tvPhone.getText().toString();
        String email = tvEmail.getText().toString();

        chatAction.setOnClickListener(v -> ContactHelper.sendSms(this, phoneNumber));
        callAction.setOnClickListener(v -> ContactHelper.makeCall(this, phoneNumber));
        emailAction.setOnClickListener(v -> ContactHelper.sendEmail(this, email));

        copyPhoneAction.setOnClickListener(v -> ContactHelper.copyToClipboard(this, "Số điện thoại", phoneNumber));
        copyEmailAction.setOnClickListener(v -> ContactHelper.copyToClipboard(this, "Email", email));
    }

    public void back(View view) {
        finish();
    }

    private void findViews() {
        View view = findViewById(R.id.detail_common_layout);

        this.imgvAvatar = view.findViewById(R.id.avatar_image_detail);
        this.tvName = view.findViewById(R.id.text_name_detail);
        this.tvPhone = view.findViewById(R.id.text_phone_detail);
        this.tvEmail = view.findViewById(R.id.text_email_detail);
        this.tvPosition = findViewById(R.id.text_position_detail);
        this.tvCurrUnit = findViewById(R.id.text_curr_unit_detail);
        this.tvBackButton = findViewById(R.id.back_button_text);
        this.chatAction = findViewById(R.id.chat_action);
        this.callAction = findViewById(R.id.call_action);
        this.emailAction = findViewById(R.id.email_action);
        this.copyPhoneAction = findViewById(R.id.copy_phone_action);
        this.copyEmailAction = findViewById(R.id.copy_email_action);
    }

    private void showData(Staff staff) {
        this.imgvAvatar.setImageResource(staff.getImage());
        this.tvName.setText(staff.getName());
        this.tvPhone.setText(staff.getPhone());
        this.tvEmail.setText(staff.getEmail());
        this.tvPosition.setText(staff.getPosition());
        this.tvCurrUnit.setText(staff.getCurrUnit());
    }
}