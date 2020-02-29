package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    class MyTask extends AsyncTask<Integer, Void, Void>{
        @Override
        protected void onPostExecute(Void result){
            Toast.makeText(MainActivity.this, "MyTask finished", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute(){
            Toast.makeText(MainActivity.this, "MyTask started", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void ... values){
            showMessage("!");
        }

        @Override
        protected Void doInBackground(Integer ... values){
            for (int i = 0; i < values[0]; i++){
                publishProgress();
                try{
                    Thread.sleep(values[1]);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button1);
        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView msgBox = (TextView) findViewById(R.id.msg);
                msgBox.setText(msgBox.getText() + "\n");
                restartThread();
            }
        };
        btn.setOnClickListener(oclBtn);
        restartThread();
    }

    void restartThread(){
        new MyTask().execute(30,700);
        new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        }).start();

    }

    void showMessage(final String msg){
        TextView msgBox = (TextView) findViewById(R.id.msg);
        msgBox.setText(msgBox.getText() + msg);
    }
    public void work(){
        for (int i=122; i>= 97; i--){
            try{
                final char k = (char)i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage(k + "");
                    }
                });
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
