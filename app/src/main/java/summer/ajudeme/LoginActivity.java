package summer.ajudeme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText txtEmail;
    private EditText txtSenha;
    private TextView lblCad;
    private ButtonRectangle btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicia o Autenticador
        mAuth = FirebaseAuth.getInstance();

        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (ButtonRectangle) findViewById(R.id.btnLogin);
        lblCad = (TextView) findViewById(R.id.lblCadastrese);
        lblCad.setText(Html.fromHtml("<u>Cadastre-se</u>"));

    }

    @Override
    public void onStart() {
        super.onStart();
        // Verifica se o usuario está logado
          FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void ClickLogin(View v) {
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        View focusView = null;
        boolean erro = false;

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "O campo email deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtEmail;
            erro = true;
        }

        if (senha.isEmpty()) {
            Toast.makeText(LoginActivity.this, "A campo senha deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtSenha;
            erro = true;
        }

        if (erro) {
            focusView.requestFocus();
        } else {
            //Todo login google para pegar foto
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        public static final String TAG = "";

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //Todo Mudar mensagen
                                //Mudar mensagem
                                Toast.makeText(LoginActivity.this, "Autenticação funcionou!!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Email e Senha invalidos!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void ClickCadastro(View v){
        Intent intent = new Intent(LoginActivity.this, CadUsuarioActivity.class);
        startActivity(intent);
    }

}
