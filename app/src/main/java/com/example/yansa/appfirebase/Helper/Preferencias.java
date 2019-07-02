package com.example.yansa.appfirebase.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "app.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String EMAIL_USUARIO_LOGADO = "email_Usuario_Logado";
    private final String SENHA_USUARIO_LOGADO = "senha_Usuario_Logado";


    public Preferencias(Context contextoParametro) {
        context = contextoParametro;

        preferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);

        //associar o nosso preferences.edit()

        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String email, String senha){

        //Salvar dentro do nosso arquivo de preferences o email e senha do usuario logado

        editor.putString(EMAIL_USUARIO_LOGADO, email);
        editor.putString(SENHA_USUARIO_LOGADO, senha);
        editor.commit();

    }

    public String getEmailUsuarioLogado(){
        return preferences.getString(EMAIL_USUARIO_LOGADO, null);

    }

    public String getSenhaUsuarioLogado(){
        return preferences.getString(SENHA_USUARIO_LOGADO, null);

    }
}
