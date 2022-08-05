package GE.GestoreEventi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Eventi{
    private HashMap<String, Integer> list = new HashMap<String, Integer>();

    public Eventi() {
    }
    
    //CREATE POST
    public synchronized void Crea(String nome, int posti) {
        if (!this.list.isEmpty()) {
            if (this.list.get(nome) != null) throw new IllegalArgumentException("Non puoi crearne un evento con lo stesso nome di uno esistente ");
        }
        this.list.put(nome, posti);
    }
    
    //READ GET
    public HashMap<String, Integer> ListaEventi() {
        return this.list;
    }
    //System.out.println("Key = " + k + ", Value = " + v)
    public List<Evento> ListaEventiString() {
    	List<Evento> l = new ArrayList<Evento>();
    	list.forEach((k,v)->
    		l.add(new Evento(k,String.valueOf(v))));
    	return l;
    }
    
    //UPDATE++ PUT++
    public void Aggiungi(String nome, Integer posti) {
        if (!this.list.isEmpty()) {
            synchronized (this) {
                if (this.list.get(nome) != null) {
                    this.list.replace(nome, this.list.get(nome) + posti);
                } else throw new IllegalArgumentException("Evento non trovato\n");
            }
        } else throw new IllegalArgumentException("Lista vuota\n");
    }
    
    //UPDATE-- PUT--
    public String Prenota(String nome, int posti) {
        if (!this.list.isEmpty()) {
            synchronized (this) {
                if (this.list.get(nome) != null) {
                    if (this.list.get(nome) >= Math.abs(posti)) {
                        this.list.replace(nome, this.list.get(nome) - Math.abs(posti));
                        return "OK";
                    } else return "Posti non disponibili in questo evento";
                } else return "Evento non trovato, inesistente";
            }
        } else return "Non ci sono eventi disponibili";
    }

    //DELETE
    public void Chiudi(String nome) {
        if (this.list.isEmpty()) {
            throw new IllegalArgumentException("Lista vuota\n");
        }
        synchronized (this) {
            if (this.list.get(nome) != null) {
                list.remove(nome);
            }
        }
    }

}