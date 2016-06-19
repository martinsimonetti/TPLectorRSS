package simonetti.martin.tplectorrss;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;

/**
 * Created by Martín on 05/06/2016.
 */
public class ListenerMainActivity implements View.OnClickListener {
    Activity act;
    Thread hiloDatos;
    Handler colaMensajes;
    ExecutorService executorService;

    public ListenerMainActivity (Activity a, Handler h, ExecutorService e){
        act= a;
        colaMensajes= h;
        executorService= e;
    }

    @Override
    public void onClick(View v) {
        Log.d("Click", "Click sobre el botón");
        if (v.getId()== R.id.btnRss){
            TextView tvRss= (TextView) act.findViewById(R.id.tvRss);
            hiloDatos= new Thread(new HiloTraerDatos(tvRss.getText().toString(), colaMensajes, false));
            executorService.execute(hiloDatos);
            Log.d("tvRss", tvRss.getText().toString());
        }
    }
}
