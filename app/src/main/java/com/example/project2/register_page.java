package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class register_page extends AppCompatActivity implements View.OnClickListener {

    EditText NameEdit, UsernameEdit, PasswordEdit, RePasswordEdit, PhoneEdit;
    Spinner spin;
    UserType type;
    String Name, Username, Phone, Password, RePassword;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, usersref;


    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        type = UserType.Customer;

        spin = (Spinner) findViewById(R.id.workers_dropdown);

        NameEdit = (EditText) findViewById(R.id.NameEdit);
        UsernameEdit = (EditText) findViewById(R.id.usernameEdit);
        PasswordEdit = (EditText) findViewById(R.id.password1);
        RePasswordEdit = (EditText) findViewById(R.id.password2);
        PhoneEdit = (EditText) findViewById(R.id.editTextPhone);

        Button b = (Button) findViewById(R.id.register);
        b.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();
        usersref = databaseReference.child(values.user_table);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register)
        {
            Name = NameEdit.getText().toString();
            Username = UsernameEdit.getText().toString();
            Password = PasswordEdit.getText().toString();
            RePassword = RePasswordEdit.getText().toString();
            Phone = PhoneEdit.getText().toString();


            if (TextUtils.isEmpty(Name))
            {
                Toast.makeText(this, "Name field is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Username))
            {
                Toast.makeText(this, "Username field is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Password))
            {
                Toast.makeText(this, "Password field is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(RePassword))
            {
                Toast.makeText(this, "Re-enter Password field is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Phone))
            {
                Toast.makeText(this, "Phone field is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (RePassword.equals(Password) == false)
            {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }


            user = new User(Username, Password, type, 0.0F, null, 0);




            usersref.orderByChild("username").equalTo(user.getUsername()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null)
                    {
                        addcustomerorworker();


                    }
                    else
                    {
                        /*User checkuserexist;
                        checkuserexist = new User();
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            checkuserexist = snapshot1.getValue(User.class);
                            if (checkuserexist.getUsername().equals(user.getUsername())) {
                                Toast.makeText(register_page.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        addcustomerorworker();*/

                        Toast.makeText(register_page.this, "Username already exists", Toast.LENGTH_SHORT).show();
                        return;

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            //startActivity(new Intent(this,verify_phone.class) );
        }
    }

    private void addcustomerorworker() {

        /*DatabaseReference curuserref = databaseReference.child(values.user_table).push();
        String userID = curuserref.getKey();
        user.setID(userID);
        user.setType(type);
        curuserref.setValue(user);*/
        if (type == UserType.Customer)
        {
            /*Customer customer = new Customer(user, Name, Phone);
            DatabaseReference customersref = databaseReference.child(values.customers_table);
            Map<String, Object> m = new HashMap();
            m.put(user.getID(), customer);
            customersref.updateChildren(m);*/
            Intent i = new Intent(register_page.this, verify_phone.class);
            i.putExtra("type", "C");
            i.putExtra("Phone", Phone);
            i.putExtra("User", Username);
            i.putExtra("Pass", Password);
            i.putExtra("Name", Name);
            startActivity(i);
        }
        else
        {
            String jobtype = spin.getSelectedItem().toString();
            /*Worker worker = new Worker(user,values.getjob(jobtype) ,  Name, Phone);
            DatabaseReference workersref = databaseReference.child(values.workers_table);
            Map<String, Object> m = new HashMap();
            m.put(user.getID(), worker);
            workersref.updateChildren(m);*/
            Intent i = new Intent(register_page.this, verify_phone.class);
//            i.putExtra("ID", worker.getID());
            i.putExtra("type", "W");
            i.putExtra("Phone", Phone);
            i.putExtra("User", Username);
            i.putExtra("Pass", Password);
            i.putExtra("Name", Name);
            i.putExtra("jobtype", jobtype);
            startActivity(i);
        }
    }

    public void switch_user_type(View view) {
        Switch s = (Switch) view;
        TextView tv = (TextView) findViewById(R.id.workastext);

        if (s.isChecked())
        {
            type = UserType.Worker;
            spin.setVisibility(View.VISIBLE);

            tv.setVisibility(View.VISIBLE);
        }
        else
        {
            type = UserType.Customer;
            spin.setVisibility(View.INVISIBLE);

            tv.setVisibility(View.INVISIBLE);
        }
    }
}

