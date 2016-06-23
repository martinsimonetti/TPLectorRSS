package simonetti.martin.tplectorrss;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Martín on 22/06/2016.
 */
public class DialogoMenu extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        SharedPreferences preferences= getActivity().getSharedPreferences("RssGuardadas", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= preferences.edit();
        editor.remove("");
        editor.commit();

        Map preferencias= preferences.getAll();

        ArrayList<String> titulos= new ArrayList<String>();
        for (Iterator it = preferencias.keySet().iterator(); it.hasNext();){
            titulos.add((String) it.next());
        }

        int cant= titulos.size();
        String[]items= new String[cant];
        items= titulos.toArray(items);

        ListenerMenu listener= new ListenerMenu(getActivity());
        builder.setItems(items, listener);
        builder.setNeutralButton(getString(R.string.btn_cancelar), listener);

        AlertDialog ad= builder.create();

        return ad;
    }
}
