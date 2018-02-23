package summer.ajudeme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
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
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicia o Autenticador
        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.edtEmail);
        txtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        lblCad = findViewById(R.id.lblCadastrese);
        lblCad.setText(Html.fromHtml("<u>Cadastre-se</u>"));


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Verifica se o usuario está logado
        FirebaseUser currentUser = mAuth.getCurrentUser();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

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
                                //Todo Vai para a tela principal
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Email e Senha invalidos!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void LoginGoogle(View v) {
        String email = txtEmail.getText().toString();
        switch (v.getId()) {
            case R.id.sign_in_button:
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

                break;
        }

        View focusView = null;
        boolean erro = false;

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Não desiste de mim!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtEmail;
            erro = true;
        }
    }




    public void ClickCadastro(View v) {
        Intent intent = new Intent(LoginActivity.this, CadUsuarioActivity.class);
        startActivity(intent);
    }
}
