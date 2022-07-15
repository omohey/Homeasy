package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project2.Report;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportPage extends AppCompatActivity {

    private Button submitReport;
    private String reportDetails, reporterID, reportedID, appID;
    EditText reportDetailsEdit;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reporterID = bundle.getString("reporterID"); //handle in previous page
            reportedID = bundle.getString("reportedID");
            appID = bundle.getString("appID");
            type = bundle.getString("type");
        }


        submitReport = (Button) findViewById(R.id.submitReport);
        reportDetailsEdit = (EditText) findViewById(R.id.reportDetailsEdit);

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportDetails = reportDetailsEdit.getText().toString();

                Report report = new Report(reporterID, reportedID, reportDetails, appID);

                if (TextUtils.isEmpty(reportDetails))
                {
                    Toast.makeText(ReportPage.this, "Report description field is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(ReportPage.this, "Your report has been submitted", Toast.LENGTH_SHORT).show();
                }

                DatabaseReference reportsRef = databaseReference.child(values.reports_table).push();
                String reportID = reportsRef.getKey();
                report.setReportID(reportID);
                reportsRef.setValue(report);

                Intent gotoScreenVar;
                if (type.equals("C"))
                {
                    gotoScreenVar = new Intent(ReportPage.this, CustomerViewAppDetail.class);
                    gotoScreenVar.putExtra("AppID", appID);
                }
                else
                {
                    gotoScreenVar = new Intent(ReportPage.this, workers_main.class);
                    gotoScreenVar.putExtra("ID", reporterID);
                }

                gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(gotoScreenVar);


            }
        });


    }
}