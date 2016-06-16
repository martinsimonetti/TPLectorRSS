package simonetti.martin.tplectorrss;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements ClickItem, Handler.Callback {
    ArrayList<Noticia> noticias;
    MyAdapter adapter;
    Handler colaMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticias = new ArrayList<Noticia>();

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rvRss);
        colaMensajes= new Handler(this);
        ExecutorService ex= Executors.newFixedThreadPool(3);
        adapter= new MyAdapter(noticias, this, colaMensajes, ex);

        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

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
}
