package summer.ajudeme;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference dado;
    private FirebaseAuth mAuth;

    private boolean ctrl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dado = mDatabase.child("notificacao");

        dado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String jessica = "IO1nXFaLqsQZoPQ4LYroAq0NtsM2";
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    String[] semideia = location.split(",");
                    String aux = semideia[0].substring(9);
                    mAuth = FirebaseAuth.getInstance();
                    String usuario = mAuth.getCurrentUser().getUid();
                    if (location.contains("sim")) {
                        if (!aux.equals(usuario)){
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
        notification.put("usuario", "IO1nXFaLqsQZoPQ4LYroAq0NtsM2");
        notification.put("mensgem", "Perigo");
        notification.put("ativo", "sim");

        notifications.push().setValue(notification);
    }
}

