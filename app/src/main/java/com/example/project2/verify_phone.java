package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class verify_phone extends AppCompatActivity {

    String ID, Phone, Password, Username, Name, jobtype;
    UserType userType;
    JobType jobType;
    User user;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, usersref;

    EditText codeEdit;

    Button verifybtn;
    String codetosend;
    SMSMessage smsMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("type").equals("W"))
                userType = UserType.Worker;
            else
                userType = UserType.Customer;
            Phone = bundle.getString("Phone");
            Username = bundle.getString("User");
            Password = bundle.getString("Pass");
            Name = bundle.getString("Name");
            if (userType.equals(UserType.Worker))
            {
                jobtype = bundle.getString("jobtype");
                jobType = values.getjob(jobtype);
            }

        }


        user = new User(Username, Password, userType, 0.0F, null, 0);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();
        usersref = databaseReference.child(values.user_table);

        codetosend = "";
        Random rand = new Random(); //instance of random class
        int upperbound = 10;
        for (int i = 0; i<6; i= i+1)
        {
            int int_random = rand.nextInt(upperbound);
            codetosend = codetosend + Integer.toString(int_random);
        }
        String Body = codetosend;
        smsMessage = new SMSMessage(Phone, Body, false);



        DatabaseReference smsref = databaseReference.child("sms").push();
        String smsID = smsref.getKey();
        //DatabaseReference cursmsref = smsref.push();
        smsref.setValue(smsMessage);
//        cursmsref.setValue(smsMessage);

        TextView phonetext = (TextView) findViewById(R.id.code_message);
        phonetext.setText("Enter the code sent to "+ Phone);

        codeEdit = (EditText) findViewById(R.id.editTextNumber);

        verifybtn = (Button) findViewById(R.id.verifybtn);

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeEdit.getText().toString().equals(codetosend))
                    addcustomerorworker();
                else
                {
                    Toast.makeText(verify_phone.this, "Incorrect code entered", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private void addcustomerorworker() {

        DatabaseReference curuserref = databaseReference.child(values.user_table).push();
        String userID = curuserref.getKey();
        user.setID(userID);
        user.setType(userType);
        curuserref.setValue(user);
        if (user.getType() == UserType.Customer)
        {
            Customer customer = new Customer(user, Name, Phone);
            DatabaseReference customersref = databaseReference.child(values.customers_table);
            Map<String, Object> m = new HashMap();
            m.put(user.getID(), customer);
            customersref.updateChildren(m);
            Intent i = new Intent(verify_phone.this, customer_main.class);
            i.putExtra("ID", customer.getID());
            startActivity(i);
        }
        else
        {
            Worker worker = new Worker(user,jobType ,  Name, Phone);
            DatabaseReference workersref = databaseReference.child(values.workers_table);
            Map<String, Object> m = new HashMap();
            m.put(user.getID(), worker);
            workersref.updateChildren(m);
            Intent i = new Intent(verify_phone.this, workers_main.class);
            i.putExtra("ID", worker.getID());
            startActivity(i);
        }
    }
}