package MADM.kafka;


public class ConsMain {
    public static void main(String[] args) {
        System.out.println("Hello  cons! ");
        Consommateur c = new Consommateur();
        c.runConsumer();

    }

}
