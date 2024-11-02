package com.example.akasztofa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button pluszgomb;
    private Button minuszgomb;
    private TextView betuk;
    private TextView kitalalando;
    private ImageView akasztokep;
    private int megjelenitettBetu = 0;
    private Button tippelgomb;
    private String kitalalandoSzo = "";
    private String megjelenitettSzo = "";
    private int hibak = 0;
    private boolean gyozelem = false;

    String[] szavak = {
            "elsoszo",
            "alma",
            "korte",
            "dinnye",
            "banan",
            "hagyma",
            "kutya",
            "macska",
            "galamb",
            "hattyu"
    };

String[] betukLista = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        sorsolSzot();

        pluszgomb.setOnClickListener(v->{
            if(megjelenitettBetu == betukLista.length-1){
                megjelenitettBetu = 0;
            }
            else megjelenitettBetu++;
            megjelenit();
        });
        minuszgomb.setOnClickListener(v->{
            if(megjelenitettBetu == 0){
                megjelenitettBetu = betukLista.length-1;
            }
            else megjelenitettBetu--;
            megjelenit();
        });

        tippelgomb.setOnClickListener(v->{
            tippel();
        });

    }
    private void init(){
        betuk = findViewById(R.id.betuk);
        pluszgomb = findViewById(R.id.pluszgomb);
        minuszgomb = findViewById(R.id.minuszgomb);
        kitalalando = findViewById(R.id.kitalalando);
        akasztokep = findViewById(R.id.akasztokep);
        tippelgomb = findViewById(R.id.tippelgomb);
    }

    private void megjelenit(){
        betuk.setText(betukLista[megjelenitettBetu]);

    }

    private void sorsolSzot(){
        Random r = new Random();
        kitalalandoSzo = szavak[r.nextInt(szavak.length)];
        for(int i = 0; i < kitalalandoSzo.length(); i++){
            megjelenitettSzo += "_";
        }
        szoMegjelenit();
    }

    private void szoMegjelenit(){
        kitalalando.setText(megjelenitettSzo);
    }

    private void tippel(){
        StringBuilder buffer = new StringBuilder();
        if(kitalalandoSzo.contains(betukLista[megjelenitettBetu])){
            for (int i = 0; i < kitalalandoSzo.length(); i++){
                if (kitalalandoSzo.charAt(i)== betukLista[megjelenitettBetu].charAt(0)){
                    buffer.append(betukLista[megjelenitettBetu]);
                }
                else{
                    buffer.append(megjelenitettSzo.charAt(i));
                }
            }
        }
        else{
            buffer = new StringBuilder(megjelenitettSzo);
            hibazas();
        }
        megjelenitettSzo = buffer.toString();
        szoMegjelenit();
        if (hibak >= 13) {
            jatekVege();
        } else if (!megjelenitettSzo.contains("_")) {
            gyozelem = true;
            jatekVege();
        }
    }

    private void hibazas() {

            hibak++;
            StringBuilder kepszam = new StringBuilder();
            kepszam.append("akasztofa");
            if (hibak < 10) {
                kepszam.append("0" + hibak);
            } else {
                kepszam.append(hibak);
            }
            String kepszamString = kepszam.toString();
            int resId = getResources().getIdentifier(kepszamString, "drawable", getPackageName());
            akasztokep.setImageResource(resId);

    }

    private void ujjatek(){
        megjelenitettSzo = "";
        sorsolSzot();
        hibak = 0;
        akasztokep.setImageResource(R.drawable.akasztofa00);
    }

    private void jatekVege(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(gyozelem){
            builder.setTitle("Nyertél!");
        }
        else{
            builder.setTitle("Vesztettél!");
        }
        builder.setMessage("Akarsz új játékot kezdeni?");
        builder.setPositiveButton("Igen", (dialog, which)->{ujjatek();});
        builder.setNegativeButton("Nem", (dialog, which)->{finish();});
        builder.show();
    }
}