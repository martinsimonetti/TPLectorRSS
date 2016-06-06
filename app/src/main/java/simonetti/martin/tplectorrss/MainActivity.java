package simonetti.martin.tplectorrss;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickItem, Handler.Callback {
    ArrayList<Noticia> noticias;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticias = new ArrayList<Noticia>();
        Noticia n= new Noticia();
        n.setFecha("02/06/2016");
        n.setTitulo("Ganó Boca");
        n.setDescripcion("Goleó a Independiente 20.012 a 0");

        noticias.add(n);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rvRss);
        adapter= new MyAdapter(noticias, this);
        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        Handler colaMensajes= new Handler(this);
        ListenerMainActivity listener= new ListenerMainActivity(this, colaMensajes);
        Button btnLeer= (Button) findViewById(R.id.btnRss);
        btnLeer.setOnClickListener(listener);
    }

    @Override
    public void click(int posicion) {
        Noticia n= noticias.get(posicion);
        Intent i= new Intent(this, NoticiaActivity.class);
        i.putExtra("url", n.getLink());
        startActivity(i);
        Log.d("Algo", String.valueOf(posicion));
    }

    @Override
    public boolean handleMessage(Message msg) {
        noticias.clear();
        noticias.addAll((ArrayList<Noticia>)msg.obj);
        Log.d("TamañoLista", String.valueOf(noticias.size()));

        /*for (Noticia not: noticias){
            Log.d("Fecha", not.getFecha());
            Log.d("Titulo", not.getTitulo());
            Log.d("Descripcion", not.getDescripcion());
            //Log.d("Imagen", not.getImagenPath());
            Log.d("Link", not.getLink());
        }*/

        adapter.notifyDataSetChanged();
        return false;
    }
}
