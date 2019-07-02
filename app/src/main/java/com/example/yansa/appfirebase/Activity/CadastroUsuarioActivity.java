package com.example.yansa.appfirebase.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yansa.appfirebase.Classes.Usuario;
import com.example.yansa.appfirebase.DAO.ConfiguracaoFirebase;
import com.example.yansa.appfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText email;
    private EditText Senha1;
    private EditText Senha2;
    private EditText nome;
    private RadioButton rbAdmin;
    private RadioButton rbAtend;
    private Button btnCadastrar;
    private Button btnCancelar;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

     email =  (EditText) findViewById(R.id.edtCadEmail);
     Senha1 = (EditText) findViewById(R.id.edtCadSenha1);
     Senha2 = (EditText) findViewById(R.id.edtCadSenha2);
     nome =   (EditText) findViewById(R.id.edtCadNome);
     rbAdmin = (RadioButton) findViewById(R.id.rbAdmin);
     rbAtend = (RadioButton) findViewById(R.id.rbAtend);
     btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
     btnCancelar = (Button) findViewById(R.id.btnCancelar);

    btnCadastrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (Senha1.getText().toString().equals(Senha2.getText().toString())) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(Senha1.getText().toString());
                usuario.setNome(nome.getText().toString());

                if (rbAdmin.isChecked()) {
                    usuario.setTipoUsuario("Administrador");

                } else if (rbAtend.isChecked()) {

                    usuario.setTipoUsuario("Atendente");
                }
                //Chamada de método para cadastro de Usuários.
                cadastrarUsuario();
            }

            else{
                Toast.makeText(CadastroUsuarioActivity.this, "As senhas não correspodem!", Toast.LENGTH_LONG).show();
            }
        }
    });

    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    insereUsuario(usuario);

                }

                else {

                String erroExcecao = "";

                try{
                 throw task.getException();
                  }

                  catch (FirebaseAuthWeakPasswordException e) {
                     erroExcecao = "Digite Uma senha Mais forte Contendo no Mínimo 8 Caracteres e que Contenha Letras e Números";
                  }

                  catch (FirebaseAuthInvalidCredentialsException e) {
                    erroExcecao = "O e-mail digitado é invalido";
                }

                  catch (FirebaseAuthUserCollisionException e) {
                    erroExcecao = "Esse e-mail já está sendo utilizado em nossos servidores!";
                    }

                    catch (Exception e) {
                    erroExcecao = "Erro ao efetuar o cadastro";
                    e.printStackTrace();
                }


                Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();


                }

            }
        });

    }


    private Boolean insereUsuario(Usuario usuario) {
        try{
        reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
        reference.push().setValue(usuario);
        Toast.makeText(CadastroUsuarioActivity.this, "Usuario Cadastrado Com sucesso!", Toast.LENGTH_LONG).show();
        return true;
        }

        catch (Exception e) {
        Toast.makeText(CadastroUsuarioActivity.this, "Erro ao gravar usuário!", Toast.LENGTH_LONG).show();
        e.printStackTrace();
        return false;
        }
    }

}
