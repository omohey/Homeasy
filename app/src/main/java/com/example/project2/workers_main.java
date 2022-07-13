package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workers_main extends AppCompatActivity {

    String ID;

    Worker currentWorker;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, workersref;

    TextView WelcomeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workers_main);

        WelcomeTextView = (TextView) findViewById(R.id.welcome);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ID = bundle.getString("ID"); }

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();
        workersref = databaseReference.child(values.workers_table);

        currentWorker = new Worker();
        workersref.orderByChild("id").equalTo(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null)
                {
                    Toast.makeText(workers_main.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    for (DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        currentWorker = snapshot1.getValue(Worker.class);
                    }
                    WelcomeTextView.setText("Welcome "+currentWorker.getName()+"!");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}