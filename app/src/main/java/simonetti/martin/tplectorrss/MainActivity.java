package simonetti.martin.tplectorrss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        Noticia n= new Noticia();
        n.setFecha("02/06/2016");
        n.setTitulo("Ganó Boca");
        n.setDescripcion("Goleó a Independiente 20.012 a 0");

        noticias.add(n);

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.rvRss);
        MyAdapter adapter= new MyAdapter(noticias, this);
        LinearLayoutManager linearLayout= new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);
    }

    @Override
    public void click(int posicion) {
        Intent i= new Intent(this, NoticiaActivity.class);
        i.putExtra("url", "www.ole.com.ar");
        startActivity(i);
        Log.d("Algo", String.valueOf(posicion));
    }
}
