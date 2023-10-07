package com.example.projecte0salmar;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.projecte0salmar.model.QuestionService;
import com.example.projecte0salmar.model.Questions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static final String BASE_URL = "http://192.168.1.195:4000/";
    TextView titol;
    TextView countDown;
    CountDownTimer cTimer = null;
    int nPregunta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        titol = (TextView) findViewById(R.id.titol);
        countDown = (TextView) findViewById(R.id.countDown);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPreguntes();
        updateQuestion();
    }

    @Override
    public void onClick(View v) {
        nPregunta++;
        cTimer.cancel();
        updateQuestion();
    }

    public String loadJSONFromAsset() {

        String json = null;
        try {
            InputStream is = getAssets().open("preguntes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void updateQuestion() {
        countDownTimer();

        TextView pregActual = (TextView) findViewById(R.id.pregActual);
        Button boto1 = (Button) findViewById(R.id.opt1);
        Button boto2 = (Button) findViewById(R.id.opt2);
        Button boto3 = (Button) findViewById(R.id.opt3);
        Button boto4 = (Button) findViewById(R.id.opt4);

        boto1.setOnClickListener(this);
        boto2.setOnClickListener(this);
        boto3.setOnClickListener(this);
        boto4.setOnClickListener(this);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray questions = obj.getJSONArray("questions");
            if (nPregunta > questions.length() - 1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            JSONObject question = questions.getJSONObject(nPregunta);
            titol.setText(question.getString("title"));
            JSONArray answers = question.getJSONArray("answers");

            pregActual.setText("Pregunta actual: " + String.valueOf(nPregunta + 1) + "/" + String.valueOf(questions.length()));

            new DownloadImageFromInternet((ImageView) findViewById(R.id.poster)).execute(question.getString("poster"));

            boto1.setText(answers.get(0).toString());
            boto2.setText(answers.get(1).toString());
            boto3.setText(answers.get(2).toString());
            boto4.setText(answers.get(3).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void countDownTimer() {
        cTimer = new CountDownTimer(31000, 1000) {
            public void onTick(long millisUntilFinished) {
                countDown.setText("Segons Restants: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                nPregunta++;
                updateQuestion();
            }
        };
        cTimer.start();
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage=BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private void getPreguntes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionService questionService = retrofit.create(QuestionService.class);

        Call<Questions> call = questionService.getPreguntes();
        call.enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, Response<Questions> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                Questions questions = response.body();
                String content = "";
                for(Questions.Question quest: questions.getQuestions()) {
                    content += "answers:"+ quest.getAnswers() + "\n";
                    content += "title:"+ quest.getTitle() + "\n";
                    content += "poster:"+ quest.getPoster() + "\n";
                    content += "id:"+ quest.getId() + "\n";
                }
                Log.d("QUESTION", questions.getQuestions().toString());

            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }

}
