package com.namyoon.randomchat;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
        lv_chatting.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        str_name = "User " + new Random().nextInt(9999);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                String key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference tempRef = reference.child(key);

                Map<String, Object> objMap = new HashMap<String, Object>();

                objMap.put("str_name", str_name);
                objMap.put("text", edit_msg.getText().toString());

                tempRef.updateChildren(objMap);
                edit_msg.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatListener(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatListener(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void chatListener(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            chat_user = (String) ((DataSnapshot) i.next()).getValue();
            str_msg = (String) ((DataSnapshot) i.next()).getValue();

            arrayAdapter.add(chat_user + " : " + str_msg);
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
