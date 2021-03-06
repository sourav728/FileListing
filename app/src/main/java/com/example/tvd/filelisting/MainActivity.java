package com.example.tvd.filelisting;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final String FTP_HOST = "45.114.246.216";
    private static final String FTP_USER = "TVDDEMO1";
    private static final String FTP_PASS = "123123";
    public static int FTP_PORT = 21;
    public static String port = "21";
    int length = 0;
    RecyclerView recyclerView;
    GetSetValues getSetValues;
    ArrayList<GetSetValues> arrayList;
    private FileAdapter fileAdapter;
    Button upload;
    String path = "";
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload = (Button) findViewById(R.id.btn_Upload);

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.mrtrack_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fileAdapter = new FileAdapter(this, arrayList);

        //setting the adapter
        recyclerView.setAdapter(fileAdapter);

        path = Environment.getExternalStorageDirectory().toString() + "/Aaa" + File.separator + "CollectionData";
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        length = file.length;
        Log.d("Files", "Size: " + file.length);
        for (int i = 0; i < file.length; i++) {
            getSetValues = new GetSetValues();
            //here populate your listview
            Log.d("Files", "FileName:" + file[i].getName());
            getSetValues.setFilename(file[i].getName());
            getSetValues.setFilecount(i + 1);
            arrayList.add(getSetValues);
            fileAdapter.notifyDataSetChanged();
        }

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFile uploadFile = new UploadFile();
                uploadFile.execute();
            }
        });
    }


    public class UploadFile extends AsyncTask<String, String, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("File Uploading to FTP");
            dialog.setMessage("Please Wait");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
          /*  runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    path = Environment.getExternalStorageDirectory().toString() + "/Aaa" + File.separator + "CollectionData";
                    Log.d("Files", "Path: " + path);
                    File f = new File(path);
                    File file[] = f.listFiles();
                    length = file.length;
                    Log.d("Files", "Size: " + file.length);
                    for (int i = 0; i < file.length; i++) {
                        getSetValues = new GetSetValues();
                        //here populate your listview
                        Log.d("Files", "FileName:" + file[i].getName());
                        getSetValues.setFilename(file[i].getName());
                        getSetValues.setFilecount(i + 1);
                        arrayList.add(getSetValues);
                        fileAdapter.notifyDataSetChanged();
                    }
                }
            });*/

            FTPClient client = new FTPClient();
            Log.d("Debugg","Main_Upload 1");
            try {
                client.connect(FTP_HOST, FTP_PORT);
                Log.d("Debugg","Main_Upload 2");
                int reply_from_server = client.getReplyCode();
                Log.d("Debugg","Main_Upload 3");
                if (FTPReply.isPositiveCompletion(reply_from_server)) {
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Log.d("Debugg","Main_Upload 4");
                client.login(FTP_USER, FTP_PASS);
                Log.d("Debugg","Main_Upload 5");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Log.d("Debugg","Main_Upload 6");
                client.setFileType(FTP.BINARY_FILE_TYPE);
                Log.d("Debugg","Main_Upload 7");
                client.enterLocalPassiveMode();
                Log.d("Debugg","Main_Upload 8");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Log.d("Debugg","Main_Upload 9");
                client.changeWorkingDirectory("/sourav/");
                Log.d("Debugg","Main_Upload 10");
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            try
            {
                for (int i=0;i<length;i++)
                {
                    getSetValues = arrayList.get(i);
                    File file2 = new File(path + File.separator + getSetValues.getFilename());

                    Log.d("Debugg","selectionStatus:"+getSetValues.isSelected());
                    if (file2.isFile()&& getSetValues.isSelected())
                    {
                        String name = file2.getName();
                        FileInputStream fis = new FileInputStream(file2);
                        Boolean result = client.storeFile(name,fis);
                        Log.d("Debugg","UploadedFIleName"+name);
                    }

                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                client.logout();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
            Toast.makeText(MainActivity.this, "File Uploaded Successfully..", Toast.LENGTH_SHORT).show();
            arrayList.clear();
            super.onPostExecute(s);
        }
    }
}
