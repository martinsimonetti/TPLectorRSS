package simonetti.martin.tplectorrss;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ClickItem, Handler.Callback {
    ArrayList<Noticia> noticias;
    MyAdapter adapter;
    Handler colaMensajes;
    ExecutorService ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticias = new ArrayList<Noticia>();

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
                break;
            case 2:
                //Log.d("Main", "2");
                byte[] imagenDescargada= (byte[])msg.obj;
                int posicion= msg.arg2;
                //Log.d("Posicion", String.valueOf(posicion));
                Noticia n= noticias.get(posicion);
                n.setImagen(imagenDescargada);
                break;
        }
        adapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String url= "";
        if (item.getItemId()== R.id.itRss1){
            url= "http://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss";
        }
        if (item.getItemId()== R.id.itRss2){
            url= "http://www.telam.com.ar/rss2/ultimasnoticias.xml";
        }
        if (item.getItemId()== R.id.itRss3){
            url= "http://www.clarin.com/rss/lo-ultimo/";
        }
        if (item.getItemId()== R.id.itRss4){
            //url= "http://www.clarin.com/rss/lo-ultimo/";
        }
        if (item.getItemId()== R.id.itRss5){
            //url= "http://www.clarin.com/rss/lo-ultimo/";
        }
        if (item.getItemId()== R.id.itAdministrar){
            //url= "http://www.clarin.com/rss/lo-ultimo/";
        }
        if (!url.equals("")) {
            Thread hiloDatos = new Thread(new HiloTraerDatos(url, colaMensajes, false));
            ex.execute(hiloDatos);
        }
        return super.onOptionsItemSelected(item);
    }
}
