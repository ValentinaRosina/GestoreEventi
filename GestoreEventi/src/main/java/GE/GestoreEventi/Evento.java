package GE.GestoreEventi;

public class Evento {
	
	private String nome = "";
	
	private String posti ="";
	
	public Evento(String nome, String posti) {
		this.nome=nome;
		this.posti = posti;
	}

	public Evento() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPosti() {
		return posti;
	}

	public void setPosti(String posti) {
		this.posti = posti;
	}
	
	

}
