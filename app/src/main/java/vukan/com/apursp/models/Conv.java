package vukan.com.apursp.models;

import java.util.ArrayList;
import java.util.List;

public class Conv {
    private List<Message> lista;
    private String productName;
    private String userName;

    public Conv() {
        lista = new ArrayList<Message>();
        productName = "";
        userName = "";
    }

    public void setLista(List<Message> lista) {
        this.lista = lista;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Message> getLista() {
        return lista;
    }

    public void setListaMesg(Message m) {
        this.lista.add(m);
    }

    public String writeAll() {
        StringBuilder all = new StringBuilder();
        for (Message m : this.lista) {
            all.append(m.getContent());
            all.append("  ");
        }

        return all.toString();
    }
}
