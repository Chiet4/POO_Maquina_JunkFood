import java.text.DecimalFormat;
import java.util.*;
class Slot{
    private String name;
    private double preco;
    private int quantidade;

    Slot(String name, int quantidade, double preco){
        this.name=name;
        this.preco=preco;
        this.quantidade=quantidade;
    }

    public String getName() {
        return name;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade -= quantidade;
    }


    public String toString() {
        String SS="";
                DecimalFormat fo = new DecimalFormat("0.00");
                SS+=String.format("%8s",getName())+" : "+getQuantidade()+" U : "+fo.format(getPreco())+" RS";

        return SS;
    }

    //    public String toString() {
//        String SS="";
//                DecimalFormat dinhe = new DecimalFormat("0.00");
//                SS+=" "+getName()+" : "+getQuantidade()+" U : " + dinhe.format(getPreco())+" RS";
//        return SS;
//    }
}


class Maquina{
    private ArrayList<Slot> Slots;
    private double lucro;
    private double dinheiro;
    private int capacidade;


    Maquina( int capacidade ) {
        this.Slots = new ArrayList<Slot>(capacidade);
        this.capacidade = capacidade;
        for(int i=0; i<this.capacidade;i++){
            this.Slots.add(i, new Slot("empty", 0, 0.00) );
        }
    }

    public Slot getSlots(int index) {
        for(int i=0;i<Slots.size();i++){
            if( i == index && Slots.get(i) != null ){
                return Slots.get(i);
            }
        }
        System.out.println("fail: indice nao existe");
        return null;
    }

    public void setSlot(int index, Slot slot) {
        if(index > Slots.size()){
            System.out.println("fail: indice nao existe");
        }
        for(int i=0;i<Slots.size();i++){
            if(i == index){
                Slots.set(i, slot );
            }
        }
    }

    public void clearSlot(int index){
        Slots.set(index, new Slot("empty", 0, 0.00));
    }

    public void inserirDinheiro(double dinheiro){
        this.dinheiro += dinheiro;
    }

    public double baixarDinheiro(){
        double saldo = getDinheiro();
        this.dinheiro =0.00;
        DecimalFormat m = new DecimalFormat("0.00");
        System.out.println("voce recebeu " +m.format(saldo)+" RS");
        return saldo;
    }

    public double getDinheiro() {
        return dinheiro;
    }

    public double getLucro() {
        DecimalFormat n = new DecimalFormat("0.00");
        System.out.println( "apurado total: "+ n.format(lucro) );
        return lucro;
    }

    public void comprarItem(int index){
        if(getSlots(index) != null){
            if(getDinheiro() >= Slots.get(index).getPreco()){
                if(Slots.get(index).getQuantidade() > 0){
                    Slots.get(index).setQuantidade(1);
                    this.dinheiro -= Slots.get(index).getPreco();
                    this.lucro += Slots.get(index).getPreco();
                    System.out.println("voce comprou um "+Slots.get(index).getName());
                }
                else {  System.out.println("fail: espiral sem produtos");}
            }
            else {System.out.println("fail: saldo insuficiente");}
        }

    }


    public String toString() {
        String SS="saldo: ";
                DecimalFormat format = new DecimalFormat("0.00");
                SS+=format.format(getDinheiro())+"\n";
                for(int i=0;i<Slots.size();i++){
                    SS+=i+" [";

//                    if(i == Slots.size()-1){
//                        SS+=getSlots(i).getName().equals("empty") ? getSlots(i)+"]" : getSlots(i)+"]";
//                        break;
//                    }
                    SS+= i == Slots.size()-1 ? getSlots(i)+"]" : getSlots(i)+"]\n";
                    //SS+=getSlots(i).getName().equals("empty") ? getSlots(i)+"]\n" : getSlots(i)+"]\n";
                }

        return SS;
    }

    //    public String toString() {
//        String SS="";
//                DecimalFormat format = new DecimalFormat("0.00");
//                SS+="saldo: "+format.format(getDinheiro())+"\n";
//                for(int i=0;i<Slots.size();i++){
//                    SS+=i+" [";
//                    if(i == Slots.size()-1){
//                        SS+=(getSlots(i).getName().equals("empty")) ? "  "+getSlots(i)+"]" : getSlots(i)+"]";
//                        break;
//                    }
//                    SS+= (getSlots(i).getName().equals("empty")) ? "  "+getSlots(i)+"]\n" : getSlots(i)+"]\n";
//
//                }
//        return SS;
//    }
}





public class JunkFood {
    public static void main(String[] args) {
        Maquina japon = new Maquina(0);

        while (true){
            String linha = input();
            write("$"+linha);
            String[] argsL = linha.split(" ");

            if("init".equals(argsL[0])) { japon = new Maquina((int) number(argsL[1])); }
            else if("show".equals(argsL[0])) { write(japon.toString()); }
            else if("set".equals(argsL[0])) {int i=(int) number(argsL[1]);
                Slot a = new Slot(argsL[2], (int) number(argsL[3]) , number(argsL[4])  );
                japon.setSlot(i, a);
            }
            else if("limpar".equals(argsL[0])) { japon.clearSlot((int) number(argsL[1])); }
            else if("dinheiro".equals(argsL[0])) { japon.inserirDinheiro((int)number(argsL[1])); }
            else if("end".equals(argsL[0])) { break; }
            else if("troco".equals(argsL[0])) { japon.baixarDinheiro(); }
            else if("comprar".equals(argsL[0])) { japon.comprarItem((int)number(argsL[1]) ); }
            else if("apurado".equals(argsL[0])) { japon.getLucro();}
            else {write("fail: comando invalido"); }
        }

    }

    static Scanner l = new Scanner(System.in);
    static String input() { return l.nextLine(); }
    static double number(String valor) { return Double.parseDouble(valor); }
    static void write(String valor) {System.out.println(valor);}


}