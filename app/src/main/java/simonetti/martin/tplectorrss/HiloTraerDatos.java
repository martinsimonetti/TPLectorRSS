package simonetti.martin.tplectorrss;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martín on 05/06/2016.
 */
public class HiloTraerDatos implements Runnable {
    String uri;
    Handler colaMensajes;

    public HiloTraerDatos(String s, Handler h){
        uri= s;
        colaMensajes= h;
    }

    @Override
    public void run() {
        HttpManager conexion = new HttpManager(uri);
        ArrayList<Noticia> lista= new ArrayList<Noticia>();

        try {
            String strXml = conexion.getStrDataByGET();

            XmlPullParser parser= Xml.newPullParser();
            Noticia n= null;
            try {
                parser.setInput(new StringReader(strXml));
                int event= parser.getEventType();
                n = new Noticia();
                while (event!=XmlPullParser.END_DOCUMENT){
                    //Log.d("Evento", String.valueOf(event));
                    switch (event){
                        case XmlPullParser.START_TAG:
                            String tag= parser.getName();
                            Log.d("Parser", tag);
                            switch (tag) {
                                case "item":
                                    n = new Noticia();
                                    break;
                                case "pubDate":
                                    n.setFecha(parser.nextText().toString());
                                    break;
                                case "title":
                                    n.setTitulo(parser.nextText().toString());
                                    break;
                                case "description":
                                    Spanned texto= Html.fromHtml(parser.nextText().toString());
                                    n.setDescripcion(texto.toString());
                                    break;
                                case "enclosure":
                                    n.setImagenPath(parser.getAttributeValue(null, "url"));
                                    break;
                                case "link":
                                    n.setLink(parser.nextText().toString());
                                    break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tag= parser.getName();
                            Log.d("EndTag", tag);
                            if (tag.equals("item")){
                                Log.d("EndTag", "Agrego Persona");
                                lista.add(n);
                            }
                            break;
                    }
                    event= parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Message mes = new Message();
            mes.obj = lista;
            mes.arg1 = 1;
            colaMensajes.sendMessage(mes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
