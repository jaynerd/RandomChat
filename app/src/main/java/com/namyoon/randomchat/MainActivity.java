package com.namyoon.randomchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private Button btn_send;
    private EditText edit_msg;
    private ListView lv_chatting;

    private ArrayAdapter<String> arrayAdapter;

    private String str_name;
    private String str_msg;
    private String chat_user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_chatting = (ListView) findViewById(R.id.lv_chatting);
        btn_send = (Button) findViewById(R.id.btn_send);
        edit_msg = (EditText) findViewById(R.id.edit_msg);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv_chatting.setAdapter(arrayAdapter);
    }
}
