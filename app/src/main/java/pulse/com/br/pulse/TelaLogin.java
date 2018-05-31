package pulse.com.br.pulse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static pulse.com.br.pulse.R.id.edtSenha;


public class TelaLogin extends AppCompatActivity {

    EditText editUsuario, editSenha;
    Button btnLogar;
    TextView txtCadastro;

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        editUsuario = (EditText) findViewById(R.id.edtUsuario);
        editSenha = (EditText) findViewById(edtSenha);
        btnLogar = (Button) findViewById(R.id.btnlogar);
        txtCadastro = (TextView) findViewById(R.id.txtCadastro);

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    String email = editUsuario.getText().toString();
                    String senha = editSenha.getText().toString();

                    if (email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode esta vazio", Toast.LENGTH_LONG).show();
                    } else {
                        url = "http://192.168.10.13/login/registrar.php";
                        parametros = "email=" + email + "&senha=" + senha;
                        new SolicitaDados().execute(url);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão com a rede", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackgroud(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado.contains("login_ok")) {
                Intent abreInicio = new Intent(TelaLogin.this, monitoramento.class);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Usúario ou senha incorreto", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}


