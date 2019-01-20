package com.example.smk.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextPercent;
    private ProgressBar mProgressbar;
    private Button mBtnStop;

    private DownloadTask mDownloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextPercent = findViewById(R.id.txtPercent);
        mProgressbar = findViewById(R.id.progressBar);
        mBtnStop = findViewById(R.id.btnStop);

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDownloadTask != null && !mDownloadTask.isCancelled()) {
                    mDownloadTask.cancel(true);
                }
            }
        });
    }

    public void download(View view) {
        mDownloadTask = new DownloadTask();
        mDownloadTask.execute();

    }

    // AsyncTask<doInBackground 타입, onProgress 타입, onPost 타입>
    class DownloadTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // 백그라운드 쓰레드
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // UI 업데이트 (메인쓰레드)
            mTextPercent.setText("진행률" + values[0] + "%");
            mProgressbar.setProgress(values[0]);

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 백그라운드 쓰레드 완료
            Toast.makeText(MainActivity.this, "완료 되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
