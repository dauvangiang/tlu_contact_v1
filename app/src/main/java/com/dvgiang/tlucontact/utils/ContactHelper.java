package com.dvgiang.tlucontact.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.dvgiang.tlucontact.model.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class ContactHelper {

    public static LinkedHashMap<String, List<Contact>> groupContactsByInitial(List<Contact> contacts) {
        LinkedHashMap<String, List<Contact>> groupedContacts = new LinkedHashMap<>();

        // Sắp xếp danh sách theo tên
        Collections.sort(contacts, Comparator.comparing(Contact::getName));

        // Nhóm theo chữ cái đầu tiên
        for (Contact contact : contacts) {
            String initial = contact.getName().substring(0, 1).toUpperCase();

            if (!groupedContacts.containsKey(initial)) {
                groupedContacts.put(initial, new ArrayList<>());
            }
            groupedContacts.get(initial).add(contact);
        }
        return groupedContacts;
    }

    public static void sendSms(Context context, String phoneNumber) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("sms:" + phoneNumber));
        context.startActivity(smsIntent);
    }

    public static void makeCall(Context context, String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(callIntent);
    }

    public static void sendEmail(Context context, String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        context.startActivity(emailIntent);
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, label + " đã được sao chép!", Toast.LENGTH_SHORT).show();
    }
}
