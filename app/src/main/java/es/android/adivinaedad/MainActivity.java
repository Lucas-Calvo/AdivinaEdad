package es.android.adivinaedad;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView edadTxt;
    private EditText editTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxt = findViewById(R.id.nombre);
        edadTxt = findViewById(R.id.edad);

        findViewById(R.id.consultarEdad).setOnClickListener(view -> {
            mostrarEdad();
        });
    }

    private void mostrarEdad() {

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("https://api.agify.io/")
                        .addConverterFactory(GsonConverterFactory.create(
                                new GsonBuilder().serializeNulls().create()
                        ))
                        .build();
        EdadAPIService edades =
                retrofit.create(EdadAPIService.class);
        Call<EdadEstimada> call = edades.getEdad(editTxt.getText());
        call.enqueue(new Callback<EdadEstimada>() {
            @Override
            public void onResponse(Call<EdadEstimada> call, Response<EdadEstimada> response) {
                if (response.isSuccessful()) {
                    int edad = response.body().getAge();
                    edadTxt.setText(Integer.toString(edad));
                } else {
                    Log.d("Error", "Something happened");
                    return;
                }
            }

            @Override
            public void onFailure(Call<EdadEstimada> call, Throwable t) {

            }
        });
    }
}