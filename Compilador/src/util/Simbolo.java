package util;

public class Simbolo {
    
    private String identifidor;
    private String tipo;
    private String valor;
    private boolean constante;

    public Simbolo() {}

    public Simbolo(String identificador, String tipo, String valor, boolean constante) {
        this.identifidor = identificador;
        this.tipo = tipo;
        this.valor = valor;
        this.constante = constante;
    }

    public String getIdentifidor() {
        return identifidor;
    }

    public void setIdentifidor(String identifidor) {
        this.identifidor = identifidor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isConstante() {
        return constante;
    }

    public void setConstante(boolean constate) {
        this.constante = constate;
    }
}
