package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    User user;

    EditText UsernameEdit, PasswordEdit;

    String Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this,customer_main.class) );
        TextView tv = (TextView) findViewById(R.id.donthaveacc);
        tv.setOnClickListener(this);
        Button b = (Button) findViewById(R.id.submit);
        b.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        UsernameEdit = (EditText) findViewById(R.id.usernameEdit);
        PasswordEdit = (EditText) findViewById(R.id.password1);


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.donthaveacc)
        {
            startActivity(new Intent(this,register_page.class) );
        }
        else if (v.getId() == R.id.submit)
        {
//            startActivity(new Intent(this,customer_main.class) );

            DatabaseReference userref = databaseReference.child(values.user_table);

            Username = UsernameEdit.getText().toString();
            Password = PasswordEdit.getText().toString();

            if (TextUtils.isEmpty(Username))
            {
                Toast.makeText(this, "Username field is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Password))
            {
                Toast.makeText(this, "Password field is empty!", Toast.LENGTH_SHORT).show();
                return;
            }


            userref.orderByChild("username").equalTo(Username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getValue() == null)
                    {
                        Toast.makeText(MainActivity.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            user = snapshot1.getValue(User.class);
                        }
                        if (user.getPassword().equals(Password) == false)
                        {
                            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (user.getType().equals(UserType.Customer))
                        {
                            Intent i = new Intent(MainActivity.this, customer_main.class);
                            i.putExtra("ID", user.getID());
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(MainActivity.this, workers_main.class);
                            i.putExtra("ID", user.getID());
                            startActivity(i);
                        }

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

