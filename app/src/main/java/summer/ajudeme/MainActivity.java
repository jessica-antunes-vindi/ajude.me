package summer.ajudeme;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference dado;
    private FirebaseAuth mAuth;
    private CircleImageView circleImageView;
    private TextView textView;

    private boolean ctrl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dado = mDatabase.child("notificacao");

        mAuth = FirebaseAuth.getInstance();
        String usuarioAtual = mAuth.getCurrentUser().getUid();
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        textView = (TextView) findViewById(R.id.lblUsuario);

        if(usuarioAtual.equals("EsC6mCl7Z8gUzQCcGxFyeURWo5G2")){
            textView.setText("Alisson Nunes");
            circleImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.alisson));
        }

        if(usuarioAtual.equals("2MzwC8YJoJX9SXTOKdFRnJQxqAN2")){
            textView.setText("Linkon Louvison");
            circleImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.linkon));
        }
        if(usuarioAtual.equals("K4NpzMhQa9QmzXxAHAPy1B2083J2")){
            textView.setText("Welliton Altimari");
            circleImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.welliton));
        }
        if(usuarioAtual.equals("IO1nXFaLqsQZoPQ4LYroAq0NtsM2")){
            textView.setText("Jessica Antunes");
            circleImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.jessica));
        }


        if(usuarioAtual.equals("86vlEUrZuhSjOVEytfop12dSogt1")){
            textView.setText("Thon Ataide");
            circleImageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.thon));
        }

        dado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String jessica = "IO1nXFaLqsQZoPQ4LYroAq0NtsM2";
                for (DataSnapshot mensagemSnapshot : dataSnapshot.getChildren()) {
                    String mensagem = mensagemSnapshot.getValue().toString();
                    String[] aux = mensagem.split(",");
                    String usuarioMen = aux[0].substring(9);

                    mAuth = FirebaseAuth.getInstance();
                    String usuarioAtual = mAuth.getCurrentUser().getUid();

                    if (mensagem.contains("sim")) {
                        if (!usuarioMen.equals(usuarioAtual)){
                            Intent intent = new Intent(MainActivity.this, AlertaActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}

    public void ClickAlerta(View v){
        ctrl = true;
        final DatabaseReference notifications = mDatabase.child("notificacao");

        Map notification = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        String usuarioAtual = mAuth.getCurrentUser().getUid();
        notification.put("usuario", usuarioAtual);
        notification.put("mensgem", "perigo");
        notification.put("ativo", "sim");

        notifications.push().setValue(notification);
    }
}

