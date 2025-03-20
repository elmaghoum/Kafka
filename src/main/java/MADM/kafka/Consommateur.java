package MADM.kafka;

import org.apache.kafka.clients.consumer.*;
import java.time.Duration;
import java.util.*;

public class Consommateur {
    private static final String TOPIC = "test1";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void runConsumer() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test3");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();

        // Compteurs globaux
        long totalPollDuration = 0;
        long totalMessages = 0;

        // Compteurs de session
        long sessionPollDuration = 0;
        long sessionMessages = 0;
        long sessionStartTime = 0;
long totalDuration = 0;


        boolean totauxAffiches = false;

        while (true) {
            long startPollTime = System.currentTimeMillis();
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            long pollDuration = System.currentTimeMillis() - startPollTime;

            if (records.isEmpty() && sessionMessages > 0 && !totauxAffiches) {
                long sessionEndTime = System.currentTimeMillis();
                long sessionDuration = sessionEndTime - sessionStartTime;
                totalDuration += sessionDuration;
            
                System.out.println("\n====== Fin de la consommation courante ======");
                System.out.println("Messages consommés cette session : " + sessionMessages);
                System.out.println("Durée réelle de cette session (s) : " + (sessionDuration / 1000.0));
                System.out.println("---------------------------------------------");
                System.out.println("TOTAL cumulatif depuis le début : ");
                System.out.println("Messages totaux : " + totalMessages);
                System.out.println("Durée totale cumulée réelle (s) : " + (totalDuration / 1000.0));
                System.out.println("====================================\n");
            
                totauxAffiches = true;
                sessionMessages = 0;
                sessionPollDuration = 0;
                sessionStartTime = 0; // prêt pour la prochaine vague
            }
            

            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }

            if (!records.isEmpty()) {
                totauxAffiches = false;
                sessionPollDuration += pollDuration;
                if (sessionStartTime == 0) {
                    sessionStartTime = System.currentTimeMillis(); // début de cette vague
                }
            }
            

            if (buffer.size() >= minBatchSize) {
                long startProcessTime = System.currentTimeMillis();
                for (ConsumerRecord<String, String> record : buffer) {
                    System.out.println(record.value());
                }
                consumer.commitSync();
                long endProcessTime = System.currentTimeMillis();

                long processingTime = endProcessTime - startProcessTime;
                long totalBatchTime = endProcessTime - startPollTime;
                int batchSize = buffer.size();
                double throughput = batchSize / (processingTime / 1000.0);

                // Mise à jour des compteurs de session et cumulatif
                sessionMessages += batchSize;
                totalMessages += batchSize;
                totalPollDuration += pollDuration;

                System.out.println("Taille du lot : " + batchSize);
                System.out.println("Durée du poll (ms) : " + pollDuration);
                System.out.println("Temps de traitement (ms) : " + processingTime);
                System.out.println("Durée totale du lot (ms) : " + totalBatchTime);
                System.out.println("Débit : " + String.format("%.2f", throughput) + " messages/seconde");
                System.out.println("---------------------------------------");

                buffer.clear();
            }
        }
    }
}
