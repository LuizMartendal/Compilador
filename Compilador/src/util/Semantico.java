package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import view.Application;

public class Semantico implements Constants {

    private HashMap<String, Simbolo> tabela_simbolos = new HashMap<>();
    private ArrayList<Token> list_id = new ArrayList<>(); 

    private Stack<String> pilha_tipos = new Stack<>();
    private Stack<String> pilha_rotulos = new Stack<>();
    private String operadorRelacional;
    public static String codigo = "";
    private int rotulo = 0;

    public void executeAction(int action, Token token)	throws SemanticError
    {
        switch (action) {
            case 100:
                acao100();
                break;
            case 101:
                acao101();
                break;
            case 131:
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
            case 117:
                acao117();
                break;
            case 110:
                acao110();
                break;
            case 111:
                acao111();
                break;
            case 112:
                acao112();
                break;
            case 113:
                acao113();
                break;
            case 108:
                acao108(token);
                break;
            case 109:
                acao109();
                break;
            case 107:
                acao107();
                break;
            case 103:
                acao103();
                break;
            case 104:
                acao104();
                break;
            case 102:
                acao102();
                break;
            case 125:
                acao125(token);
                break;
            case 126:
                acao126(token);
                break;
            case 127:
                acao127(token);
                break;
            case 128:
                acao128(token);
                break;
            case 130:
                acao130(token);
                break;
            case 129:
                acao129(token);
                break;
            case 118:
                acao118(token);
                break;
            case 120:
                acao120();
                break;
            case 119:
                acao119();
                break;
            case 121:
                acao121();
                break;
            case 122:
                acao122(token);
                break;
            case 123:
                acao123();
                break;
            case 124:
                acao124(token);
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
    }

    public void acao131(Token token) throws SemanticError {
        if (this.tabela_simbolos.containsKey(token.getLexeme())) {
            Simbolo simbolo =  this.tabela_simbolos.get(token.getLexeme());
            if (!simbolo.isConstante()) {
                this.adicionaVariavelPilha(token, simbolo);
            } else {
                this.adicionaConstantePilha(simbolo);
            }
            return;
        }
        throw new SemanticError(token.getLexeme() + " nao declarado", token.getPosition());
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
        if (simbolo.getTipo().equals("int64")) {
            codigo += "conv.r8\n";
        }
        pilha_tipos.push(simbolo.getTipo());
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

    public void acao117() {
        codigo += "ldc.i8 -1\n";
        codigo += "conv.r8\n";
        codigo += "mul\n";
    }

    public void acao110() {
        tabelaTipos();
        codigo += "add\n";
    }

    public void acao111() {
        tabelaTipos();
        codigo += "sub\n";
    }

    public void acao112() {
        tabelaTipos();
        codigo += "mul\n";
    }

    public void acao113() {
        tabelaTipos();
        codigo += "div\n";
    }

    public void tabelaTipos() {
        String operador2 = pilha_tipos.pop();
        String operador1 = pilha_tipos.pop();


        if (operador1.equals("float64") || operador2.equals("float64")) {
            pilha_tipos.push("float64");
            return;
        }
        pilha_tipos.push("int64");
        return;
    }

    public void acao108(Token token) {
        operadorRelacional = "" + token.getLexeme();
    }

    public void acao109() {
        pilha_tipos.pop();
        pilha_tipos.pop();
        pilha_tipos.push("bool");
        this.operadorRelacional();
    }

    public void operadorRelacional() {
        switch (operadorRelacional) {
            case "==":
                codigo += "ceq\n";
                break;
            case "!=":
                codigo += "ceq\n" + "ldc.i4.0\n" + "ceq\n";
                break;
            case ">":
                codigo += "cgt\n";
                break;
            case "<":
                codigo += "clt\n";
                break;
        }
    }

    public void acao107() {
        codigo += "ldc.i4.1\nxor\n";
    }

    public void acao103() {
        pilha_tipos.pop();
        pilha_tipos.pop();
        pilha_tipos.push("bool");
        codigo += "and\n";
    }

    public void acao104() {
        pilha_tipos.pop();
        pilha_tipos.pop();
        pilha_tipos.push("bool");
        codigo += "or\n";
    }

    public void acao102() {
        String tipo = pilha_tipos.pop();
        if (tipo.equals("int64")) {
            codigo += "conv.i8\n";
        }
        this.print(tipo);
    }

    public void print(String tipo) {
        codigo += "call void [mscorlib]System.Console::Write(" + tipo + ")\n";
    }

    public void acao125(Token token) {
        list_id.add(token);
    }

    public void acao126(Token token) throws SemanticError {
        for (Token t : this.list_id) {
            if (tabela_simbolos.containsKey(t.getLexeme())) {
                throw new SemanticError(t.getLexeme() + " ja declarado", t.getPosition());
            }
            Simbolo simbolo = this.createSimbolo(t, token);
            tabela_simbolos.put(t.getLexeme(), simbolo);
        }
        this.list_id.clear();
    }

    public Simbolo createSimbolo(Token token1, Token token2) {
        Simbolo simbolo = new Simbolo();
        if (token1.getLexeme().startsWith("_i")) {
            simbolo.setTipo("int64");
        } else if (token1.getLexeme().startsWith("_f")) {
            simbolo.setTipo("float64");
        } else if (token1.getLexeme().startsWith("_s")) {
            simbolo.setTipo("string");
        } else {
            simbolo.setTipo("bool");
        }
        simbolo.setValor(token2.getLexeme());
        simbolo.setIdentifidor(token1.getLexeme());
        simbolo.setConstante(true);
        return simbolo;
    }

    public void acao127(Token token) throws SemanticError {
        for (Token t : this.list_id) {
            if (tabela_simbolos.containsKey(t.getLexeme())) {
                throw new SemanticError(t.getLexeme() + " ja declarado", t.getPosition());
            }
            Simbolo simbolo = this.createSimbolo(t, token);
            simbolo.setConstante(false);
            tabela_simbolos.put(t.getLexeme(), simbolo);
            codigo += ".locals (" + simbolo.getTipo() + " " + simbolo.getIdentifidor() + ")\n";
        }
        this.list_id.clear();
    }

    public void acao128(Token token) throws SemanticError {
        String tipo = this.pilha_tipos.pop();
        for (int i = 0; i < this.list_id.size() - 1; i++) {
            codigo += "dup\n";
        }

        for (Token t : this.list_id) {
            if (!tabela_simbolos.containsKey(t.getLexeme())) {
                throw new SemanticError(t.getLexeme() + " nao declarado", t.getPosition());
            }
            if (tipo.equals("int64")) {
                codigo += "conv.i8\n";
            }
            codigo += "stloc " + t.getLexeme() + "\n";
        }
        this.list_id.clear();
    }

    public void acao130(Token token) {
        codigo += "ldstr " + token.getLexeme() + "\n";
        this.print("string");
    }

    public void acao129(Token token) throws SemanticError {
        for (Token t : this.list_id) {
            if (!tabela_simbolos.containsKey(t.getLexeme())) {
                throw new SemanticError(t.getLexeme() + " nao declarado", t.getPosition());
            }
            this.input(tabela_simbolos.get(t.getLexeme()));
            codigo += "stloc " + t.getLexeme() + "\n";
        }
        this.list_id.clear();
    }

    public void input(Simbolo simbolo) {
        codigo += "call string [mscorlib]System.Console::ReadLine()\n";
        if (!simbolo.getTipo().equals("string")) {
            codigo += "call " + simbolo.getTipo() + " [mscorlib]System." + this.getClasse(simbolo.getTipo()) + "::Parse(string)\n";
        }
    }

    public String getClasse(String tipo) {
        switch (tipo) {
            case "int64":
                return "Int64";
            case "bool":
                return "Boolean";
            default:
                return "Double";
        }
    }

    public void acao118(Token token) throws SemanticError {
        if (!pilha_tipos.pop().equals("bool")) {
            throw new SemanticError("expressao incompativel em comando de selecao", token.getPosition());
        }
        String novoRotulo = this.criarNovoRotulo();
        codigo += "brfalse " + novoRotulo + "\n";
        pilha_rotulos.push(novoRotulo);
    }

    public String criarNovoRotulo() {
        rotulo++;
        String novoRotulo = "novo_rotulo" + rotulo;
        return novoRotulo;
    }

    public void acao120() {
        String novoRotulo = this.criarNovoRotulo();
        codigo += "br " + novoRotulo + "\n";
        codigo += "" + pilha_rotulos.pop() + ":\n";
        pilha_rotulos.push(novoRotulo);
    }

    public void acao119() {
        codigo += "" + pilha_rotulos.pop() + ":\n";
    }

    public void acao121() {
        String novoRotulo = this.criarNovoRotulo();
        codigo += "" + novoRotulo + ":\n";
        pilha_rotulos.push(novoRotulo);
    }

    public void acao122(Token token) throws SemanticError {
        if (!pilha_tipos.pop().equals("bool")) {
            throw new SemanticError("expressao incompativel em comando de repeticao", token.getPosition());
        }
        String novoRotulo = this.criarNovoRotulo();
        codigo += "brfalse " + novoRotulo + "\n";
        pilha_rotulos.push(novoRotulo);
    }

    public void acao123() {
        String rotulo2 = pilha_rotulos.pop();
        String rotulo1 = pilha_rotulos.pop();
        codigo += "br " + rotulo1 + "\n";
        codigo += "" + rotulo2 + ":\n";
    }

    public void acao124(Token token) throws SemanticError {
        if (!pilha_tipos.pop().equals("bool")) {
            throw new SemanticError("expressao incompativel em comando de repeticao", token.getPosition());
        }
        String rotulo1 = pilha_rotulos.pop();
        codigo += "brtrue " + rotulo1 + "\n";
    }
}
