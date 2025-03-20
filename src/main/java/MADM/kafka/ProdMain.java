package MADM.kafka;

public class ProdMain {
    public static void main(String[] args) {
        System.out.println("Hello prod!");

        int message = 1000000;
        int numberOfProducers = 2;


        int t= message/numberOfProducers;

        for (int i = 0; i < numberOfProducers; i++) {
            
            int producerId = i;
            Thread producerThread = new Thread(() -> {
                Producteur p = new Producteur(t, t*producerId);
                p.runProducer(producerId);
            });
            producerThread.start();
        }
    }
}
