package simonetti.martin.tplectorrss;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Martín on 19/06/2016.
 */
public class ListenerAlert implements DialogInterface.OnClickListener {
    View view;
    Activity activity;

    public ListenerAlert(View v, Activity a){
        view=v;
        activity= a;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        EditText etRss1Titulo= (EditText) view.findViewById(R.id.etRss1Titulo);
        EditText etRss1Url= (EditText) view.findViewById(R.id.etRss1Url);
        EditText etRss2Titulo= (EditText) view.findViewById(R.id.etRss2Titulo);
        EditText etRss2Url= (EditText) view.findViewById(R.id.etRss2Url);
        EditText etRss3Titulo= (EditText) view.findViewById(R.id.etRss3Titulo);
        EditText etRss3Url= (EditText) view.findViewById(R.id.etRss3Url);
        EditText etRss4Titulo= (EditText) view.findViewById(R.id.etRss4Titulo);
        EditText etRss4Url= (EditText) view.findViewById(R.id.etRss4Url);
        EditText etRss5Titulo= (EditText) view.findViewById(R.id.etRss5Titulo);
        EditText etRss5Url= (EditText) view.findViewById(R.id.etRss5Url);

        switch (which){
            case AlertDialog.BUTTON_POSITIVE:
                SharedPreferences preferences= activity.getSharedPreferences("RssGuardadas", Context.MODE_PRIVATE);
                Editor editor= preferences.edit();
                editor.clear();
                editor.putString(etRss1Titulo.getText().toString(), etRss1Url.getText().toString());
                editor.putString(etRss2Titulo.getText().toString(), etRss2Url.getText().toString());
                editor.putString(etRss3Titulo.getText().toString(), etRss3Url.getText().toString());
                editor.putString(etRss4Titulo.getText().toString(), etRss4Url.getText().toString());
                editor.putString(etRss5Titulo.getText().toString(), etRss5Url.getText().toString());
                editor.commit();

                Message msg= new Message();
                msg.arg1= 3;
                MainActivity.colaMensajes.sendMessage(msg);
                break;
            case AlertDialog.BUTTON_NEUTRAL:
                break;
        }
    }
}
