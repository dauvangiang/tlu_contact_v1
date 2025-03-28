package com.dvgiang.tlucontact.activities.unit;

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
import com.dvgiang.tlucontact.model.Unit;
import com.dvgiang.tlucontact.utils.ContactHelper;

public class UnitDetailActivity extends AppCompatActivity {
    private ImageView imgvAvatar, copyPhoneAction, copyEmailAction;
    private TextView tvName, tvPhone, tvEmail, tvAddress, tvBackButton;
    private CardView chatAction, callAction, emailAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unit_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        tvBackButton.setText(R.string.back_detail_text);

        // Hiển thị thông tin chi tiết cho item được chọn có key tương ứng
        Unit unit = getIntent().getParcelableExtra("itemSelected");
        if (unit != null) {
            showData(unit);
        }

        String phoneNumber = tvPhone.getText().toString();
        String email = tvEmail.getText().toString();

        chatAction.setOnClickListener(v -> ContactHelper.sendSms(this, phoneNumber));
        callAction.setOnClickListener(v -> ContactHelper.makeCall(this, phoneNumber));
        emailAction.setOnClickListener(v -> ContactHelper.sendEmail(this, email));

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
        this.tvAddress = findViewById(R.id.text_address_detail);
        this.tvBackButton = findViewById(R.id.back_button_text);
        this.chatAction = findViewById(R.id.chat_action);
        this.callAction = findViewById(R.id.call_action);
        this.emailAction = findViewById(R.id.email_action);
        this.copyPhoneAction = findViewById(R.id.copy_phone_action);
        this.copyEmailAction = findViewById(R.id.copy_email_action);
    }

    private void showData(Unit unit) {
        this.imgvAvatar.setImageResource(unit.getImage());
        this.tvName.setText(unit.getName());
        this.tvPhone.setText(unit.getPhone());
        this.tvEmail.setText(unit.getEmail());
        this.tvAddress.setText(unit.getAddress());
    }
}