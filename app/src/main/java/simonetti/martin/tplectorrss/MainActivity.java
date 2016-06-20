package simonetti.martin.tplectorrss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ClickItem, Handler.Callback {
    ArrayList<Noticia> noticias;
    MyAdapter adapter;
    static Handler colaMensajes;
    ExecutorService ex;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticias = new ArrayList<Noticia>();
        preferences= getSharedPreferences("RssGuardadas", Context.MODE_PRIVATE);
        Editor editor= preferences.edit();
        editor.remove("");
        editor.commit();

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rvRss);
        colaMensajes= new Handler(this);
        ex= Executors.newFixedThreadPool(3);
        adapter= new MyAdapter(noticias, this, colaMensajes, ex);

        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        ListenerMainActivity listener= new ListenerMainActivity(this, colaMensajes, ex);
        Button btnLeer= (Button) findViewById(R.id.btnRss);
        btnLeer.setOnClickListener(listener);
    }

    @Override
    public void click(int posicion) {
        Noticia n= noticias.get(posicion);
        Intent i= new Intent(this, NoticiaActivity.class);
        i.putExtra("url", n.getLink());
        startActivity(i);
        //Log.d("Algo", String.valueOf(posicion));
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1){
            case 1:
                noticias.clear();
                noticias.addAll((ArrayList<Noticia>) msg.obj);
                Log.d("TamañoLista", String.valueOf(noticias.size()));
                adapter.notifyDataSetChanged();
                break;
            case 2:
                //Log.d("Main", "2");
                byte[] imagenDescargada= (byte[])msg.obj;
                int posicion= msg.arg2;
                //Log.d("Posicion", String.valueOf(posicion));
                Noticia n= noticias.get(posicion);
                n.setImagen(imagenDescargada);
                adapter.notifyDataSetChanged();
                break;
            case 3:
                Log.d("MainActivity", "Actualizar Menú");
                /*Map preferencias= preferences.getAll();
                String[] titulos= new String[5];
                int i= 0;
                for (Iterator it= preferencias.keySet().iterator(); it.hasNext();){
                    titulos[i]= (String) it.next();
                    i++;
                }

                Button item1= (Button) findViewById(R.id.btnRss);
                MenuItem item2= (MenuItem) findViewById(R.id.itRss2);
                MenuItem item3= (MenuItem) findViewById(R.id.itRss3);
                MenuItem item4= (MenuItem) findViewById(R.id.itRss4);
                MenuItem item5= (MenuItem) findViewById(R.id.itRss5);

                if (titulos[0]!= null) item1.setText(titulos[0]);
                if (titulos[1]!= null) item2.setTitle(titulos[1]);
                if (titulos[2]!= null) item3.setTitle(titulos[2]);
                if (titulos[3]!= null) item4.setTitle(titulos[3]);
                if (titulos[4]!= null) item5.setTitle(titulos[4]);
                break;*/
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lateral, menu);

        Map preferencias= preferences.getAll();
        String[] titulos= new String[5];
        int i= 0;
        for (Iterator it= preferencias.keySet().iterator(); it.hasNext();){
            titulos[i]= (String) it.next();
            i++;
        }

        MenuItem item1= menu.findItem(R.id.itRss1);
        MenuItem item2= menu.findItem(R.id.itRss2);
        MenuItem item3= menu.findItem(R.id.itRss3);
        MenuItem item4= menu.findItem(R.id.itRss4);
        MenuItem item5= menu.findItem(R.id.itRss5);

        if (titulos[0]!= null) item1.setTitle(titulos[0]);
        if (titulos[1]!= null) item2.setTitle(titulos[1]);
        if (titulos[2]!= null) item3.setTitle(titulos[2]);
        if (titulos[3]!= null) item4.setTitle(titulos[3]);
        if (titulos[4]!= null) item5.setTitle(titulos[4]);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url= "";

        switch (item.getItemId()){
            case R.id.itRss1:
            case R.id.itRss2:
            case R.id.itRss3:
            case R.id.itRss4:
            case R.id.itRss5:
                url= preferences.getString(item.getTitle().toString(),"");
                break;
            case R.id.itAdministrar:
                DialogoRss drss= new DialogoRss();
                drss.show(getFragmentManager(), "Cargar RSS");
        }
        if (!url.equals("")) {
            Thread hiloDatos = new Thread(new HiloTraerDatos(url, colaMensajes, false));
            ex.execute(hiloDatos);
        }
        return super.onOptionsItemSelected(item);
    }
}
