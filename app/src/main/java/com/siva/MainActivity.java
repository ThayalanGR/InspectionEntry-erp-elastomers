package com.siva;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;
import com.siva.Model.Common;
import com.siva.Model.Inspection;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;
    EditText mId;
    int counter = 0;
    ProgressDialog mProgress;
    ImageView mImage;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mId = (EditText) findViewById(R.id.id_input);
        Paper.init(this);
        String base_url = Paper.book().read("url");
        if (!TextUtils.isEmpty(base_url)) {
            Common.BASE_URL = base_url;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String result = dataSnapshot.child("shutdown").getValue().toString();
                if (result.equals("true")) {
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Loading..");
        mProgress.setMessage("Please wait  ");
        mProgress.setCanceledOnTouchOutside(false);
        mImage = (ImageView) findViewById(R.id.imageView);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;

                if (counter == 3) {
                    Intent intent = new Intent(getApplicationContext(), URLActivity.class);
                    startActivity(intent);
                }
            }
        });
        mId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String id = mId.getText().toString();
                mProgress.show();
                if (!TextUtils.isEmpty(id)) {
                    Call<Inspection> call = apiInterface.doGetDetails(id);
                    call.enqueue(new Callback<Inspection>() {
                        @Override
                        public void onResponse(Call<com.siva.Model.Inspection> call,
                                final Response<com.siva.Model.Inspection> response) {
                            mProgress.dismiss();
                            if (response.code() == 200) {
                                final com.siva.Model.Inspection data = response.body();
                                new FancyAlertDialog.Builder(MainActivity.this).setTitle("Details")
                                        .setBackgroundColor(Color.parseColor("#008577")) // Don't pass
                                                                                         // R.color.colorvalue
                                        .setMessage("Key reference: " + data.getResponse().getIssref() + '\n'
                                                + "Def.rec.data: " + data.getResponse().getDefrecdate() + '\n'
                                                + "Part no :" + data.getResponse().getCmpdname() + '\n'
                                                + "Part description :" + data.getResponse().getCmpdrefno() + '\n'
                                                + "Receipt quantity :" + data.getResponse().getCurrrec())

                                        .setNegativeBtnText("Cancel")
                                        .setPositiveBtnBackground(Color.parseColor("#008577")) // Don't pass
                                                                                               // R.color.colorvalue
                                        .setPositiveBtnText("Proceed")
                                        .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8")) // Don't pass
                                                                                                 // R.color.colorvalue
                                        .setAnimation(Animation.POP).isCancellable(true)
                                        .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
                                        .OnPositiveClicked(new FancyAlertDialogListener() {
                                            @Override
                                            public void OnClick() {

                                                List<String> list = data.getUserList();
                                                List<Inspection.RejectionList> rejectedList = data.getRejectionList();
                                                List<String> resultReject = new ArrayList<>();
                                                List<String> symbolReject = new ArrayList<>();
                                                if (rejectedList != null) {
                                                    for (int i = 0; i < rejectedList.size(); i++) {
                                                        resultReject.add(rejectedList.get(i).getRejType());
                                                        symbolReject.add(rejectedList.get(i).getRejShortName());

                                                    }
                                                }
                                                Intent intent = new Intent(getApplicationContext(),
                                                        EditInspectionActivity.class);
                                                intent.putExtra("currrec", data.getResponse().getCurrrec());
                                                intent.putExtra("isexternal", data.getResponse().getIsexternal());
                                                intent.putExtra("cmpdid", data.getResponse().getCmpdid());
                                                intent.putExtra("sno", data.getResponse().getSno());
                                                intent.putExtra("defrecdate", data.getResponse().getDefrecdate());
                                                intent.putExtra("defrecdatef", data.getResponse().getDefrecdatef());
                                                intent.putExtra("cmpdname", data.getResponse().getCmpdname());
                                                intent.putExtra("cmpdrefno", data.getResponse().getCmpdrefno());

                                                intent.putExtra("id", data.getResponse().getIssref());
                                                intent.putExtra("list", (Serializable) list);
                                                intent.putExtra("symbol_list", (Serializable) symbolReject);
                                                intent.putExtra("reject_list", (Serializable) resultReject);

                                                startActivity(intent);
                                            }
                                        }).OnNegativeClicked(new FancyAlertDialogListener() {
                                            @Override
                                            public void OnClick() {

                                            }
                                        }).build();
                            } else if (response.code() == 204) {
                                mProgress.dismiss();
                                mId.setText("");
                                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.siva.Model.Inspection> call, Throwable t) {
                            Log.e("CANCELLEd", t.getMessage());
                            mProgress.dismiss();
                            mId.setText("");
                            Toast.makeText(getApplicationContext(), "Something wrong ..!", Toast.LENGTH_LONG).show();
                            call.cancel();

                        }

                    });
                } else {
                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Empty field..!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
