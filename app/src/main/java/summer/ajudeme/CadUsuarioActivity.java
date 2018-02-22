package summer.ajudeme;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public static final String TAG = "";
    private Usuario usuario;
    private EditText txtNome;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtSenha;
    private ButtonRectangle btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        final EditText campo_telefone = (EditText) findViewById(R.id.edtTelefone);
        campo_telefone.addTextChangedListener(Mask.insert("(##)#####-####", campo_telefone));

        //Inicia o Autenticador
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtNome = findViewById(R.id.edtNome);
        txtTelefone = findViewById(R.id.edtTelefone);
        txtEmail = findViewById(R.id.edtEmail);
        txtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

    }

    public void ClickCadastrar(View v) {

        String nome = txtNome.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        usuario = new Usuario(nome, telefone, email, senha);

        View focusView = null;
        boolean erro = false;

       if (nome.isEmpty()) {
            Toast.makeText(CadUsuarioActivity.this, "O campo nome deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtNome;
            erro = true;
        }

        if (telefone.isEmpty()) {
            Toast.makeText(CadUsuarioActivity.this, "O campo telefone deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtEmail;
            erro = true;
        }

        if (email.isEmpty()) {
            Toast.makeText(CadUsuarioActivity.this, "O campo email deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtEmail;
            erro = true;
        }

        if (senha.isEmpty()) {
            Toast.makeText(CadUsuarioActivity.this, "A campo senha deve contem informação!!",
                    Toast.LENGTH_SHORT).show();
            focusView = txtSenha;
            erro = true;
        }

        if (erro) {
            focusView.requestFocus();
        } else {
           //Todo Foto do usuario
            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CadUsuarioActivity.this, "Usuario criado!!",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                mDatabase.child("usuario").child(user.getUid()).setValue(usuario);
                                //TODO Logar e ir para tela principal
                            } else {
                                Toast.makeText(CadUsuarioActivity.this, "Email e Senha invalidos!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
