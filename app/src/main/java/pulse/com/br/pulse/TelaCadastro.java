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

public class TelaCadastro extends AppCompatActivity {

    EditText editNome1, editEmail1, editSenha1;
    Button btnCancelar, btnRegistrar;

    String url = "";
    String parametros = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        editNome1 = (EditText) findViewById(R.id.editNome1);
        editEmail1 = (EditText) findViewById(R.id.editEmail1);
        editSenha1 = (EditText) findViewById(R.id.edtSenha);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnRegistrar= (Button) findViewById(R.id.btnRegistrar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    String nome = editNome1.getText().toString();
                    String email = editEmail1.getText().toString();
                    String senha = editSenha1.getText().toString();

                    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode esta vazio", Toast.LENGTH_LONG).show();
                    } else {
                        url = "http://192.168.10.13/login/logar.php";
                        parametros = "nome=" + nome + "&email=" + email + "&senha=" + senha;
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

            if (resultado.contains("email_ok")) {
                Toast.makeText(getApplicationContext(), "Email já cadastrado", Toast.LENGTH_LONG).show();
            }else if (resultado.contains("registro_ok")) {
                Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();
                Intent abreInicio = new Intent(TelaCadastro.this, TelaLogin.class);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
