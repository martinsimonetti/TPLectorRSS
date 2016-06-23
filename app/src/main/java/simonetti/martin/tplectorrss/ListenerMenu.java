package simonetti.martin.tplectorrss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Martín on 22/06/2016.
 */
public class ListenerMenu implements DialogInterface.OnClickListener {
    Activity activity;

    public ListenerMenu(Activity a){
        activity= a;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences preferences= activity.getSharedPreferences("RssGuardadas", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= preferences.edit();
        editor.remove("");
        editor.commit();

        Map preferencias= preferences.getAll();
        String[][] rss= new String[5][2];
        int i= 0;
        for (Iterator it = preferencias.keySet().iterator(); it.hasNext();){
            rss[i][0]= (String) it.next();
            rss[i][1]= (String) preferencias.get(rss[i][0]);
            i++;
        }

        String url="";
        switch (which){
            case 0:
                url= rss[0][1];
                break;
            case 1:
                url= rss[1][1];
                break;
            case 2:
                url= rss[2][1];
                break;
            case 3:
                url= rss[3][1];
                break;
            case 4:
                url= rss[4][1];
                break;
            case AlertDialog.BUTTON_NEUTRAL:
                Log.d("ListenerMenu", "Cancelar");
                break;
        }
        if (!url.equals("")) {
            Thread hiloDatos = new Thread(new HiloTraerDatos(url, MainActivity.colaMensajes, false));
            hiloDatos.start();
        }
    }
}
