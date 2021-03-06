package com.usuario.campitos.springandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class Actividad extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actividad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast toast=Toast.makeText(getApplicationContext(),"Oprimiste regresar a inicio!!!",Toast.LENGTH_LONG);
            toast.show();
         Intent intento =new Intent(getApplicationContext(),ActividadInicio.class);
            startActivity(intento);
        }
        if(id==R.id.accion_algo){
           // Toast toast = Toast.makeText(getApplicationContext(), "Haz seleccionado la baderita de México", Toast.LENGTH_LONG);
          //  toast.show();
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    /*
    Este metodo es sumamente importante
     */
    public void buscar(View v){
        final TextView texto=(TextView)findViewById(R.id.resultados);
        texto.setText("Pronto muy pronto");
        new AsyncTask<String, Integer, String>(){
             String mensaje="nada";
            ArrayList<Usuario> usuarios;
            Usuario u=new Usuario();
            //PRIMER METODO
            @Override
            protected void onPreExecute() {
                texto.setText("Cargando datos, espera...");
            }
            //sEGUNDO METODO EL QUE CARGA Y SE CONECTA
            public String doInBackground(String...x){


                try {
                    usuarios=leerJasonTodos("nada");
                } catch (Exception e) {
                 mensaje="no hay internet";
                }


                return mensaje;
            }
            //TERCER METODO, EL QUE MUESTRA LOS RESULTADOS
            public void onPostExecute(String mensaje){
                try {
                  int  ultimo= usuarios.size();
                    Usuario u1=usuarios.get(0);
                    Usuario u2=usuarios.get(ultimo-1);
                    texto.setText("Usuarios leidos:" +usuarios.size()+ " .El" +
                            " primer se llama "+u1.getNombre()+ ", y el último es "
                    +u2.getNombre());

                }catch(Exception e){
                    texto.setText("No hay internet, checa la conexion de wi-fi de tu cel o activa transmisión de datos");
                }
            }
        }.execute(null,null,null);
    }
    //EL METODO MAS IMPORTANTE
    public ArrayList<Usuario> leerJasonTodos(String miurl)throws Exception {
        String leido="nada se leyo :(";
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application", "json")));
        HttpEntity<?> requestEntity=new HttpEntity<Object>(requestHeaders);

        String url="http://campitos-ley.whelastic.net/campitos/servicios/usuario";
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        // leido=restTemplate.getForObject(url,String.class,"Android");
        ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.GET, requestEntity,String.class);
        leido= responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, ArrayList<Usuario>> mapausuarios=   mapper.readValue(leido,new TypeReference<Map<String,ArrayList<Usuario>>>(){});
        ArrayList<Usuario> usuarios=      (ArrayList<Usuario>) mapausuarios.get("usuarios");
        return usuarios;
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
