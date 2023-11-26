package util;

import java.util.HashMap;
import java.util.Stack;

public class Semantico implements Constants {

    private HashMap<String, Simbolo> tabela_simbolos = new HashMap<>();

    private Stack<String> pilha_tipos = new Stack<>();
    private static String codigo = "";

    public void executeAction(int action, Token token)	throws SemanticError
    {
        System.out.println("Ação #"+action+", Token: "+token);

        switch (action) {
            case 100:
                acao100();
                break;
            case 101:
                acao101();
                break;
            case 103:
                acao131(token);
                break;
            case 114:
                acao114(token);
                break;
            case 115:
                acao115(token);
                break;
            case 105:
                acao105();
                break;
            case 106:
                acao106();
                break;
            case 116:
                acao116(token);
                break;
        }
    }

    public void acao100() {
        codigo += ".assembly extern mscorlib {}\n" +
                  ".assembly _exemplo{}\n" +
                  ".module _exemplo.exe\n" +
                  ".class public _exemplo{\n" +
                  ".method static public void _principal(){\n" +
                  ".entrypoint\n";
    }

    public void acao101() {
        codigo += "ret\n" +
                  "}\n" +
                  "}\n";
        System.out.println(codigo);
    }

    public void acao131(Token token) throws SemanticError {
        if (this.tabela_simbolos.containsKey(token.getLexeme())) {
            Simbolo simbolo =  this.tabela_simbolos.get(token.getLexeme());
            this.adicionaConstantePilha(simbolo);
            if (!simbolo.isConstante()) {
                this.adicionaVariavelPilha(token, simbolo);
            }
            return;
        }
        throw new SemanticError(token.getLexeme() + " não declarado", token.getPosition());
    }

    public void adicionaConstantePilha(Simbolo simbolo) {
        switch (simbolo.getTipo()) {
            case "int64":
                adicionaInt64(simbolo.getValor());
                break;
            case "float64":
                adicionaFloat64(simbolo.getValor());
                break;
            case "string":
                adicionaString(simbolo.getValor());
                break;
            case "bool":
                adicionaBool(simbolo.getValor());
                break;
        }
    }

    public void adicionaInt64(String valor) {
        codigo += "ldc.i8 " + valor + "\n" +
                  "conv.r8\n";
        pilha_tipos.push("int64");
    }

    public void adicionaFloat64(String valor) {
        codigo += "ldc.r8 " + valor + "\n";
        pilha_tipos.push("float64");
    }

    public void adicionaString(String valor) {
        codigo += "ldstr " + valor + "\n";
        pilha_tipos.push("string");
    }

    public void adicionaBool(String valor) {
        codigo += "ldc.i4." + valor + "\n";
        pilha_tipos.push("bool");
    }

    public void adicionaVariavelPilha(Token token, Simbolo simbolo) {
        codigo += "ldloc " + token.getLexeme() + "\n";
    }

    public void acao114(Token token) {
        adicionaInt64(token.getLexeme());
    }

    public void acao115(Token token) {
        adicionaFloat64(token.getLexeme());
    }

    public void acao105() {
        adicionaBool("1");
    }

    public void acao106() {
        adicionaBool("0");
    }

    public void acao116(Token token) {
        adicionaString(token.getLexeme());
    }
}
