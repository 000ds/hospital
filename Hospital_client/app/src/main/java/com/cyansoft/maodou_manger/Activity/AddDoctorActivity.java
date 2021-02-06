package com.cyansoft.maodou_manger.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.Config;
import com.cyansoft.maodou_manger.Service.FileUtils2;
import com.cyansoft.maodou_manger.Service.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * @author 曹晓东 ,Created on 2020/5/1
 * 联系： 2806029294@qq.com
 */
public class AddDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> adapter = null;
    private EditText xm, zc, ssks, ccly, lxfs, grjl, yyfy;
    private Button btn;
    private ImageView mPictureIv;
    private ProgressDialog rDialog;
    private static final String[] chose = {"就诊", "停诊"};
    private String text;
    private static int i = 0;
    private ImageView iBack;
    String url2;
    String img_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_doctor);

        mPictureIv=(ImageView)findViewById(R.id.main_frag_picture_iv);
        xm = (EditText) findViewById(R.id.xingming);
        zc = (EditText) findViewById(R.id.zhicheng);
        ssks = (EditText) findViewById(R.id.suoshukeshi);

        ccly = (EditText) findViewById(R.id.chanshanglingyu);
        lxfs = (EditText) findViewById(R.id.lianxifangshi);
        grjl = (EditText) findViewById(R.id.gerenjianli);
        yyfy = (EditText) findViewById(R.id.yuyuefeiyong);
        btn = (Button) findViewById(R.id.tianjia);
        iBack = (ImageView) findViewById(R.id.im_back);
        iBack.setOnClickListener(this);
        mPictureIv.setOnClickListener(this);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.main_frag_picture_iv:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,1);
                break;
            case R.id.im_back:
                finish();
                break;
            case R.id.tianjia:
                if (xm.getText().toString() == null || "".equals(xm.getText().toString().trim())) {
                    ShowToask("医生姓名不能为空");
                    break;
                }
                if (zc.getText().toString() == null || "".equals(zc.getText().toString().trim())) {
                    ShowToask("职称不能为空");
                    Log.e("tag", "======Password");
                    break;
                }
                if (ssks.getText().toString() == null || "".equals(ssks.getText().toString().trim())) {
                    ShowToask("所属科室不能为空");
                    break;
                }
                if (ccly.getText().toString() == null || "".equals(ccly.getText().toString().trim())) {
                    ShowToask("擅长领域不能为空");
                    break;
                }
                if (lxfs.getText().toString() == null || "".equals(lxfs.getText().toString().trim())) {
                    ShowToask("联系方式不能为空");
                    break;
                }
                if (grjl.getText().toString() == null || "".equals(grjl.getText().toString().trim())) {
                    ShowToask("个人简介不能为空");
                    break;
                }
                if (yyfy.getText().toString() == null || "".equals(yyfy.getText().toString().trim())) {
                    ShowToask("预约费用不能为空");
                    break;
                }
                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在添加医生信息,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();
                JSONObject job=new JSONObject();
                try {
                    job.put("flag","add");
                    job.put("name",xm.getText().toString());
                    job.put("zhicheng",zc.getText().toString());
                    job.put("classes",ssks.getText().toString());
                    job.put("special",ccly.getText().toString());

                    job.put("phone",lxfs.getText().toString());
                    job.put("resume",grjl.getText().toString());
                    job.put("money",yyfy.getText().toString());
                    job.put("photo",url2);
                    getDataFromPost(job.toString());
                } catch (JSONException e) {
                }

        }
    }
    private void getDataFromPost(final String json) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = HttpUtils.okPost(Config.DOCTOR_URL, json);
                    rDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){//是否选择，没选择就不会继续
            try {
                Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
                String url = FileUtils2.getPath(AddDoctorActivity.this,uri);
                url2 = url.trim();
                new AlertDialog.Builder(AddDoctorActivity.this)
                        .setTitle("提示框")
                        .setMessage("是否发布?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        uploadImage(url2);
                                    }
                                })
                        .setNegativeButton("no",null).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 上传图片
     * @param imagePath
     */
    private void uploadImage(String imagePath) {
        new NetworkTask().execute(imagePath);
    }

    /**
     * 访问网络AsyncTask,访问网络在子线程进行并返回主线程通知访问的结果
     */
    class NetworkTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return doPost(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if(!"error".equals(result)) {
                Glide.with(AddDoctorActivity.this)
                        .load(Config.BASE_URL + result)
                        .into(mPictureIv);
            }
        }
    }

    private String doPost(String imagePath) {
        OkHttpClient mOkHttpClient = new OkHttpClient();

        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        // 这里演示添加用户ID
//        builder.addFormDataPart("userId", "20160519142605");
        builder.addFormDataPart("image", imagePath,
                RequestBody.create(MediaType.parse("*/*"), new File(imagePath)));

        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        Request request = reqBuilder
                .url(Config.BASE_URL + "/uploadimage")
                .post(requestBody)
                .build();

        try{
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String resultValue = response.body().string();
                String url=Config.BASE_URL;

                url=url+resultValue;

                    img_url=url;
                return resultValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void ShowToask(String name) {
        Toast.makeText(AddDoctorActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}
