package pulse.com.br.pulse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    public EditText edtUsuario;
    public EditText edtSenha;

    String url = "";
    String parametros = "";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        Button ok = (Button) findViewById(R.id.monitorar);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    String email = edtUsuario.getText().toString();
                    String senha = edtSenha.getText().toString();
                    if (email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode esta vazio", Toast.LENGTH_LONG).show();
                    } else {
                        url = "http://192.168.10.13/login/logar.php";
                        parametros = "email=" + email + "$senha=" + senha;
                        //new SolicitaDados().execute(url);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão com a rede", Toast.LENGTH_LONG).show();
                }
            }

        });
    }


 //   private class SolicitaDados extends AsyncTask<String, Void, String>{
   //     @Override
   //     protected String doInBackgroud(String...urls) {
   //         return Conexao.postDados(urls[0], parametros);
   //     }
   //     @Override
   //     protected  void onPostExecute(String resultado){
            //edtUsuario.setText(resultado);
       // }
   // }

}






