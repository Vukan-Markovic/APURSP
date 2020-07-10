package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conv {
    private List<Message> lista;


    public Conv() {
      lista=new ArrayList<Message>();
    }

  public List<Message> getLista() {
    return lista;
  }

  public void setListaMesg(Message m) {
    this.lista.add(m);
  }



  public String writeAll() {
    String all="";
  for(Message m:this.lista) {
    all+=m.getContent();
    all+="  ";
  }

  return all;

  }
}
