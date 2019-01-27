package com.siva;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.isapanah.awesomespinner.AwesomeSpinner;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInspectionActivity extends Activity {

    int receipt_quan,inspect_quality;
    List<String> userList,rejectList,symbolList,valueList;
    String cmpdid;
    APIInterface apiInterface;
    String mdlrref;
    String isexternal;
    String inspdate;
    String recqty;
    String appqty;
    String inspector;
    String id;
    ProgressDialog mProgress;
    String rej_short_name,value,planid;
    String currrec;
    TextView mPlanRef,mReceipt,mApproved;
    EditText mInspectedQuality,mShort,mDeflashing;
    ImageView mImgDate;
    TextView mDate;
    Button mProceed;
    Map value_map;
    int input;
    String ins_date;
    int approved_quality;
    int temp=0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inspection);
        Intent intent=getIntent();
        if(intent!=null){
            receipt_quan=Integer.parseInt(intent.getStringExtra("currrec"));

            apiInterface = APIClient.getClient().create(APIInterface.class);
            valueList=new ArrayList<>();
            userList= (List<String>) getIntent().getSerializableExtra("list");
            rejectList= (List<String>) getIntent().getSerializableExtra("reject_list");
            symbolList= (List<String>) getIntent().getSerializableExtra("symbol_list");
            mProgress=new ProgressDialog(this);
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.setTitle("Please wait");
            mProgress.setTitle("Loading..");

            id=getIntent().getStringExtra("id");
            //variables
            planid=getIntent().getStringExtra("issref");//
            cmpdid=getIntent().getStringExtra("cmpdid");//
            isexternal=getIntent().getStringExtra("isexternal");//
            mdlrref=getIntent().getStringExtra("sno");//
           // remaining -> inspector & approved & recqty(inspected value)

            value_map=new LinkedHashMap();


            //Log.e("list",rejectList.get(1));
            rejectList.add("Shortage");
            rejectList.add("Deflashing");

        }
        mApproved=(TextView)findViewById(R.id.approved_quality);
        mPlanRef=(TextView)findViewById(R.id.plan_reference);
        mReceipt=(TextView)findViewById(R.id.receipt_quality);
        mInspectedQuality=(EditText)findViewById(R.id.inspected_quality);
        mDate=(TextView)findViewById(R.id.txt_date);
        mImgDate=(ImageView)findViewById(R.id.img_date);
        mProceed=(Button)findViewById(R.id.btn_proceed);



        //initial changes
       setReceiptQuantity(receipt_quan,id);

        AwesomeSpinner my_spinner = (AwesomeSpinner) findViewById(R.id.my_spinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList);

        my_spinner.setAdapter(categoriesAdapter);
        my_spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                inspector=itemAtPosition;
//                Toast.makeText(getApplicationContext(),itemAtPosition,Toast.LENGTH_LONG).show();

            }
        });
        RelativeLayout rl=(RelativeLayout) findViewById(R.id.relativelayout1);
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
        LinearLayout ll = new LinearLayout(this);

        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams margin=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        margin.setMargins(15,10,15,10);
        sv.addView(ll);


        if(rejectList!=null) {
            for (int i = 0; i < rejectList.size(); i++) {
                TextInputLayout txt=new TextInputLayout(new ContextThemeWrapper(EditInspectionActivity.this,R.style.TextLabelWhite));
                ll.addView(txt);
                EditText b = new EditText(this);
               // b.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                b.setLayoutParams(margin);
                b.setHint(" " + rejectList.get(i));
                b.setHintTextColor(getResources().getColor(R.color.hint));
                b.setTextSize(18);
                ColorStateList colorStateList = ColorStateList.valueOf(R.color.white);
                ViewCompat.setBackgroundTintList(b, colorStateList);
                b.setTextColor(getResources().getColor(R.color.white));
                b.setId(100 + i);
                b.setInputType(InputType.TYPE_CLASS_NUMBER);
                b.setText("");
                b.setSingleLine(true);
                txt.addView(b);
            }
        }

        rl.addView(sv);


        mInspectedQuality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.equals("")){
                    if(!mInspectedQuality.getText().toString().equals("")) {
                        Log.e("TEXT",mInspectedQuality.getText().toString());
                        input = Integer.parseInt(mInspectedQuality.getText().toString());
                        Log.e("MEDIUM ", String.valueOf(Integer.parseInt(mInspectedQuality.getText().toString())));
                        approved_quality=input;
                        mApproved.setText("Approved Quality : "+approved_quality);
                        for (int i = 0; i < rejectList.size(); i++) {
                            EditText editText = (EditText) findViewById(100 + i);
                            editText.setText("");
                        }
                        Log.e("INPUT", String.valueOf(input));





                    }

                }
            }
        });

        mDate.setText(getDateTime());
        mImgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(EditInspectionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        ins_date="" + selectedday + "/" + selectedmonth + "/" + selectedyear;
                        mDate.setText(ins_date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();

            }
        });

        //Toast.makeText(getApplicationContext(), rejectList.get(0),Toast.LENGTH_LONG).show();


        for (int i=0;i<rejectList.size();i++) {

            EditText ed=(EditText)findViewById(100+i);
            ed.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                //dont change
                @Override
                public void afterTextChanged(Editable s) {
                    input=0;
                    for (int i = 0; i < rejectList.size(); i++) {
                        EditText editText = (EditText) findViewById(100 + i);
                        if(editText.getText().toString().equals("")){
                            input=0;
                        }else if (!editText.getText().toString().equals("")){
                            input=Integer.parseInt(editText.getText().toString());
                        }
                        temp += input;

                    }

                    mApproved.setText("Approved Quality : " + (approved_quality - temp));
                    temp = 0;
                }
            });

        }

        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.show();
                appqty= String.valueOf(getApprovedQuality(mApproved.getText().toString()));
                inspdate=mDate.getText().toString();
                recqty= String.valueOf(mInspectedQuality.getText().toString());

                for (int i = 0; i < rejectList.size(); i++) {
                    EditText editText = (EditText) findViewById(100 + i);
                    if(editText.getText().toString().equals("")){
                        input=0;
                    }else if (!editText.getText().toString().equals("")){
                        input=Integer.parseInt(editText.getText().toString());
                    }
                   value_map.put(i,input);
                }


            if(Integer.parseInt(appqty)>0) {
                if (!TextUtils.isEmpty(cmpdid) &&
                        !TextUtils.isEmpty(mdlrref) &&
                        !TextUtils.isEmpty(isexternal) &&
                        !TextUtils.isEmpty(inspdate) &&
                        !TextUtils.isEmpty(recqty) &&
                        !TextUtils.isEmpty(appqty) &&
                        !TextUtils.isEmpty(inspector) &&
                        !TextUtils.isEmpty(id)) {

                    JsonObject result=generateJsonResponse();
                    Log.e("RESPONSE",result.toString());
                    Call<JsonObject> call = apiInterface.postRawJSON(result);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.code()==202){
                                Toast.makeText(getApplicationContext(),"Successfully submitted",Toast.LENGTH_LONG).show();
                                sendToStart();
                            }else if (response.code()==400){
                                Toast.makeText(EditInspectionActivity.this, "Bad Request", Toast.LENGTH_SHORT).show();
                                sendToStart();
                            }
                            mProgress.dismiss();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Toast.makeText(EditInspectionActivity.this, "Something went wrong ! ->"+t.getMessage(), Toast.LENGTH_LONG).show();
                            mProgress.dismiss();
                            sendToStart();

                        }
                    });

                } else {
                    Toast.makeText(EditInspectionActivity.this, "Something null", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
            }else {
                    Toast.makeText(getApplicationContext(),"Approved Quality should be Positive",Toast.LENGTH_LONG).show();
                    mProgress.dismiss();

            }

            }
        });
    }

    private void sendToStart() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    private JsonObject generateJsonResponse() {




        JsonObject object=new JsonObject();
        object.addProperty("cmpdid",cmpdid);
        object.addProperty("mdlrref",mdlrref);
        object.addProperty("isexternal",isexternal);
        object.addProperty("inspdate",inspdate);
        object.addProperty("recqty",mInspectedQuality.getText().toString());
        object.addProperty("appqty",appqty);
        object.addProperty("inspector",inspector);
        object.addProperty("planid",id);
        object.add("rejection",generateJSONArray());

        //Toast.makeText(this, ""+object.toString(), Toast.LENGTH_SHORT).show();
        return object;

    }

    private JsonArray generateJSONArray() {
        JsonObject jsonObject=null;
        JsonArray array=new JsonArray();
      //  Log.e("LIST",symbolList.get(2));
        for(int i=0;i<symbolList.size();i++){
           // Log.e(i+" "+valueList.get(i),symbolList.get(i));

            jsonObject=new JsonObject();
            jsonObject.addProperty("rej_short_name",symbolList.get(i));
            jsonObject.addProperty("value", String.valueOf(value_map.get(i)));
            array.add(jsonObject);

        }

       return array;


    }


    private void setReceiptQuantity(int receipt_quan, String id) {
        mReceipt.setText("Receipt Quality : "+receipt_quan);
        mApproved.setText("Approved Quality : "+receipt_quan);
        mPlanRef.setText("Plan Reference : "+id);
        inspect_quality=receipt_quan;
        approved_quality=receipt_quan;
        mInspectedQuality.setText(""+receipt_quan);
    }

    private String getDateTime() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }
    public int getApprovedQuality(String input){
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        String output="0";
        while(m.find()) {
            output =m.group();
        }
        return Integer.parseInt(output);
    }
}
