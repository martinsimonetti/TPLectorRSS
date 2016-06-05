package simonetti.martin.tplectorrss;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Noticia> listaNoticias;
    MyViewHolder holder;
    ClickItem objClick;

    public MyAdapter(ArrayList<Noticia> lista, ClickItem c){
        listaNoticias= lista;
        objClick= c;
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
        holder.ivNoticia.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }
}
