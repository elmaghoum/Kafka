package MADM.kafka;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class Producteur {
    private static final String TOPIC = "test1";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private int nbmessage;
    private int debut;

    public Producteur(int nb, int start){
        this.nbmessage=nb;
        this.debut = start;
    }

    public void runProducer(int producerId) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.ACKS_CONFIG ,"all") ;
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {

            int fin = this.debut + this.nbmessage;
            System.out.println("Il commence de "+this.debut+" et fini à: "+fin);
            for (int i = this.debut; i < fin; i++) {
                String value = "Message-" + i + " from producer-" + producerId;
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, Integer.toString(i), value);
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println("Producer " + producerId + " sent: " + value);
                    } else {
                        exception.printStackTrace();
                    }
                });
                Thread.sleep(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
