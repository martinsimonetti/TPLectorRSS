package simonetti.martin.tplectorrss;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Martín on 05/06/2016.
 */
public class ListenerMainActivity implements View.OnClickListener {
    Activity act;
    Thread hiloDatos;
    Handler colaMensajes;

    public ListenerMainActivity (Activity a, Handler h){
        act= a;
        colaMensajes= h;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btnRss){
            TextView tvRss= (TextView) act.findViewById(R.id.tvRss);
            hiloDatos= new Thread(new HiloTraerDatos(tvRss.getText().toString(), colaMensajes));
            hiloDatos.start();
            Log.d("tvRss", tvRss.getText().toString());
        }
    }
}
