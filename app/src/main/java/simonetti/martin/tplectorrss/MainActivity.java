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
import java.util.ArrayList;
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
                byte[] imagenDescargada= (byte[])msg.obj;
                int posicion= msg.arg2;
                Noticia n= noticias.get(posicion);
                n.setImagen(imagenDescargada);
                adapter.notifyDataSetChanged();
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itAdministrar:
                DialogoRss dRss= new DialogoRss();
                dRss.show(getFragmentManager(), "Cargar RSS");
                break;
            case R.id.itFavoritos:
                DialogoMenu dMenu= new DialogoMenu();
                dMenu.show(getFragmentManager(), "RSS Favoritos");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
