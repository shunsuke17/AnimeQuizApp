package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1, answerBtn2, answerBtn3, answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"アニメタイトル", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"緋弾のアリア", "峰理子", "夏川真涼", "潮留美海", "四条桃花"},
            {"機巧少女は傷つかない", "フレイ", "レキ", "メリー・ナイトメア", "ティッタ"},
            {"桜Trick", "高山春香","神前美月", "良田胡蝶", "道楽宴"},
            {"神のみぞ知るセカイ", "高原歩美", "草薙静花", "成瀬荊", "降谷萌路"},
            {"魔法少女サイト", "奴村露乃","春原彩花", "一之瀬花名", "佐倉杏子"},
            {"城下町のダンデライオン", "櫻田奏","岬明乃", "青山七海", "山岸沙希"},
            {"新世界より", "渡辺早季", "白神葉子", "黒田砂雪", "林田奈々"},
            {"未確認で進行形", "末続このは", "柴崎芦花", "鹿賀りん", "鮎沢美咲"},
            {"恋と選挙とチョコレート", "青海衣更", "永瀬伊織", "国立凛香", "一宮塔子"},
            {"あかね色に染まる坂", "長瀬湊", "河合律", "涼月奏", "輪島巴"},
            {"AIR", "神尾観鈴", "倉橋京子", "山田真耶", "夏野霧姫"},
            {"神さまのいない日曜日", "アイ・アスティン", "エリカ・ブランデッリ", "キム・ディール", "ハイドラ・ベル"},
            {"変態王子と笑わない猫", "筒隠月子", "神長香子", "中多紗江", "吉岡双葉"},
            {"ギルティクラウン", "楪いのり", "宮内れんげ", "篠原エリカ", "槍桜ヒメ"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //idからビューを見つける　テキストビューやボタンを書き換えられる
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // quizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < quizData.length; i++) {

            // 新しいArrayListを準備
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]);  // アニメタイトル
            tmpArray.add(quizData[i][1]);  // 正解
            tmpArray.add(quizData[i][2]);  // 選択肢１
            tmpArray.add(quizData[i][3]);  // 選択肢２
            tmpArray.add(quizData[i][4]);  // 選択肢３

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);
        }

        showNextQuiz();
    }


    public void showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel.setText("Q" + quizCount);

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());   //nextInt()は0から指定した値未満

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題文（アニメタイトル）を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // クイズ配列から問題文（アニメタイトル）を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 回答ボタンに正解と選択肢３つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);    //同じ問題が出題されないように
    }


    public void checkAnswer(View view) {

        // どの回答ボタンが押されたか　answerBtn1,2,3,4
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();    //押したボタンの文字

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
        } else {
            alertTitle = "不正解...";
        }

        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("次へ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // 結果画面へ移動
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
