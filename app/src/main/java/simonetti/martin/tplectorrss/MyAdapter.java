package simonetti.martin.tplectorrss;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Noticia> listaNoticias;
    MyViewHolder holder;
    ClickItem objClick;
    Handler colaMensajes;

    public MyAdapter(ArrayList<Noticia> lista, ClickItem c, Handler h){
        listaNoticias= lista;
        objClick= c;
        colaMensajes= h;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia,parent,false);
        holder= new MyViewHolder(viewItem, objClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Noticia n= listaNoticias.get(position);
        holder.tvFecha.setText(n.getFecha());
        holder.tvTitulo.setText(n.getTitulo());
        holder.tvDescripcion.setText(n.getDescripcion());
        if (n.getImagen() != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(n.getImagen(), 0, n.getImagen().length);
            holder.ivNoticia.setImageBitmap(bmp);
        } else {
            if (n.getImagenPath() != null) {
                holder.ivNoticia.setImageResource(R.drawable.logo_rss);
                Thread hiloImagen = new Thread(new HiloTraerDatos(n.getImagenPath(), colaMensajes, true, position));
                hiloImagen.start();
                //holder.ivNoticia.setImageResource(R.drawable.logo_rss);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }
}
