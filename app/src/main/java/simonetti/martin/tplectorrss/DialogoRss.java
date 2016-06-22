package simonetti.martin.tplectorrss;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Martín on 19/06/2016.
 */
public class DialogoRss extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li= LayoutInflater.from(getActivity());
        View v= li.inflate(R.layout.cargar_rss, null);

        EditText etRss1Titulo= (EditText) v.findViewById(R.id.etRss1Titulo);
        EditText etRss1Url= (EditText) v.findViewById(R.id.etRss1Url);
        EditText etRss2Titulo= (EditText) v.findViewById(R.id.etRss2Titulo);
        EditText etRss2Url= (EditText) v.findViewById(R.id.etRss2Url);
        EditText etRss3Titulo= (EditText) v.findViewById(R.id.etRss3Titulo);
        EditText etRss3Url= (EditText) v.findViewById(R.id.etRss3Url);
        EditText etRss4Titulo= (EditText) v.findViewById(R.id.etRss4Titulo);
        EditText etRss4Url= (EditText) v.findViewById(R.id.etRss4Url);
        EditText etRss5Titulo= (EditText) v.findViewById(R.id.etRss5Titulo);
        EditText etRss5Url= (EditText) v.findViewById(R.id.etRss5Url);

        SharedPreferences preferences= getActivity().getSharedPreferences("RssGuardadas", Context.MODE_PRIVATE);

        Map preferencias= preferences.getAll();
        String[][] rss= new String[5][2];
        int i= 0;
        for (Iterator it = preferencias.keySet().iterator(); it.hasNext();){
            rss[i][0]= (String) it.next();
            rss[i][1]= (String) preferencias.get(rss[i][0]);
            i++;
        }

        if (rss[0][0]!= null) etRss1Titulo.setText(rss[0][0]);
        if (rss[0][1]!= null) etRss1Url.setText(rss[0][1]);
        if (rss[1][0]!= null) etRss2Titulo.setText(rss[1][0]);
        if (rss[1][1]!= null) etRss2Url.setText(rss[1][1]);
        if (rss[2][0]!= null) etRss3Titulo.setText(rss[2][0]);
        if (rss[2][1]!= null) etRss3Url.setText(rss[2][1]);
        if (rss[3][0]!= null) etRss4Titulo.setText(rss[3][0]);
        if (rss[3][1]!= null) etRss4Url.setText(rss[3][1]);
        if (rss[4][0]!= null) etRss5Titulo.setText(rss[4][0]);
        if (rss[4][1]!= null) etRss5Url.setText(rss[4][1]);

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle(getText(R.string.RSS_Favoritas));
        builder.setView(v);

        ListenerAlert  l= new ListenerAlert(v, getActivity());
        builder.setPositiveButton(getString(R.string.btn_guardar), l);
        builder.setNeutralButton(getString(R.string.btn_cancelar), l);
        AlertDialog ad= builder.create();

        return ad;
    }
}
