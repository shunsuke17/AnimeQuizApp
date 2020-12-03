package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultLabel = findViewById(R.id.resultLabel);
        TextView totalScoreLabel = findViewById(R.id.totalScoreLabel);

        //正解数取得
        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);

        // トータルスコアの読み出し
        SharedPreferences prefs = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int totalScore = prefs.getInt("totalScore", 0);

        // トータルスコアに今回のスコアを加算
        totalScore += score;

        //表示
        resultLabel.setText("正解数 :　" + score + " / 5");
        totalScoreLabel.setText("トータルスコア : " + totalScore);

        // トータルスコアを保存
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("totalScore", totalScore);
        editor.apply();
    }


    public void returnTop(View view) {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}
