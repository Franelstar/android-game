package vn.ifi.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOk;
    private EditText editTextNumber;
    private TextView textViewIndication;
    private ProgressBar progressBarTentatives;
    private TextView textViewTentatives;
    private TextView textViewScoreValue;
    private ListView listViewHistorique;
    private int secret;
    private int counter;
    private int score = 0;
    private List<String> listHistorique = new ArrayList<String>();
    private int maxTentative = 6;
    ArrayAdapter<String> model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOk = findViewById(R.id.buttonOk);
        editTextNumber = findViewById(R.id.editTextNumber);
        textViewIndication = findViewById(R.id.textViewIndication);
        progressBarTentatives = findViewById(R.id.progressBarTentatives);
        textViewTentatives = findViewById(R.id.textViewTentatives);
        listViewHistorique = findViewById(R.id.listViewHistorique);
        textViewScoreValue = findViewById(R.id.textViewScoreValue);

        model = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, listHistorique);
        listViewHistorique.setAdapter(model);

        initialisation();

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(editTextNumber.getText().toString());
                if(number > secret){
                    textViewIndication.setText(R.string.str_valeur_sup);
                }else if(number < secret){
                    textViewIndication.setText(R.string.str_valeur_inf);
                }else{
                    textViewIndication.setText(R.string.str_bravo);
                    score += 5;
                    textViewScoreValue.setText(String.valueOf(score));
                    rejouer();
                }
                listHistorique.add("Tentative " + counter + " => " + number);
                model.notifyDataSetChanged();

                ++counter;
                textViewTentatives.setText(String.valueOf(counter));
                progressBarTentatives.setProgress(counter);
                if(counter > maxTentative)
                    rejouer();
            }
        });
    }

    private void rejouer() {
        Log.i("LyLog", "Rejour........");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.str_nouvel_essai);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialisation();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.show();
    }

    private void initialisation() {
        secret = 1 + ((int) (Math.random() * 100));
        counter = 0;
        listHistorique.clear();
        model.notifyDataSetChanged();
        textViewTentatives.setText(String.valueOf(counter));
        progressBarTentatives.setProgress(counter);
        progressBarTentatives.setMax(maxTentative);
        textViewScoreValue.setText(String.valueOf(score));
        }
}
