package com.revature.lab;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = input.nextLine();

        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);

        Thread myThread = new Thread(new MyRunnable(username));
        myThread.start();
        System.out.println("Enter messages (type 'exit' to quit):");
        while (true) {
           
            String msg = input.nextLine();
            if (msg.equalsIgnoreCase("exit"))
                break;

            String formattedMsg = "[" + username + "]: " + msg;
            producer.send(new ProducerRecord<>("global-chat", formattedMsg));
        }
        // Cleanup
        producer.close();
        input.close();
        System.out.println("ChatApp closed.");
    }
}
