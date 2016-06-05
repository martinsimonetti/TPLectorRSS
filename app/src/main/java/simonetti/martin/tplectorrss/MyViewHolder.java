package simonetti.martin.tplectorrss;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alumno on 02/06/2016.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView ivNoticia;
    TextView tvFecha;
    TextView tvTitulo;
    TextView tvDescripcion;
    ClickItem objClick;

    public MyViewHolder(View itemView, ClickItem c) {
        super(itemView);
        tvFecha= (TextView) itemView.findViewById(R.id.tvFecha);
        tvTitulo= (TextView) itemView.findViewById(R.id.tvTitulo);
        tvDescripcion= (TextView) itemView.findViewById(R.id.tvDescripcion);
        ivNoticia= (ImageView) itemView.findViewById(R.id.ivNoticia);
        itemView.setOnClickListener(this);
        objClick= c;
    }

    @Override
    public void onClick(View v) {
        objClick.click(getAdapterPosition());
    }
}
