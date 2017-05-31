package com.example.kmj.week13;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText et;
    Button bt;
    TextView tv1, tv2;
    Boolean started = false;
    Boolean chooseMode = false;
    ImageView img;
    int interval = 1, index = 0;
    int images[] = {R.drawable.aquarius, R.drawable.pisces, R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn};
    String names[] = {"물병자리", "물고기자리", "양자리", "황소자리", "쌍둥이자리", "게자리", "사자자리", "처녀자리", "천칭자리", "전갈자리", "사수자리", "염소자리"};
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        et = (EditText) findViewById(R.id.interval);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                started = false;
                chooseMode = false;
                interval = 1;
                index = 0;
                tv2.setText("");
                img.setImageResource(R.drawable.start);
            }
        });
        tv1 = (TextView) findViewById(R.id.constellation);
        tv2 = (TextView) findViewById(R.id.time);
        img = (ImageView) findViewById(R.id.image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started) {
                    chooseMode = true;
                    task.onPostExecute(task.getSec());
                } else {
                    started = true;
                    interval = Integer.parseInt(et.getText().toString());
                    task = new Task();
                    task.execute(interval);
                }
            }
        });
    }

    class Task extends AsyncTask<Integer, Integer, Integer> {

        int sec = 0;

        int getSec() {
            return sec;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for (int i = 0; ; i++) {
                try {
                    sec = i;
                    if (isCancelled()) break;
                    if (chooseMode) return i - 1;
                    publishProgress(i, i % interval - 1);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv2.setText("시작부터 " + values[0] + "초");
            if (values[1] == 0) {
                img.setImageResource(images[index]);
                index = (index + 1) % 12;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            int i = (index + 11) % 12;
            tv1.setText("당신의 별자리는" + names[i] + "입니다");
            tv2.setText(integer + "초");
        }
    }
}
