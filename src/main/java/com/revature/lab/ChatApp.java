package com.revature.lab;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);

        // Usage of a unique Request ID or Group ID ensures that multiple users
        // (Consumer instances)
        // each get a copy of the message (Pub-Sub/Broadcast model) rather than sharing
        // it (Queue model).
        String groupId = "chat-group-" + System.currentTimeMillis();

        new Thread(() -> {
            Properties consumerProps = new Properties();
            consumerProps.put("bootstrap.servers", "localhost:9092");
            consumerProps.put("group.id", groupId);
            consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumerProps.put("auto.offset.reset", "earliest");

            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
            consumer.subscribe(Arrays.asList("global-chat"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("\n" + record.value());
                    System.out.print("Enter message ('exit' to quit): ");
                }
            }
        }).start();

        // --- Main input loop ---
        System.out.println("Connected to global-chat. Type messages below.");
        System.out.print("Enter message ('exit' to quit): ");
        while (true) {
            String msg = scanner.nextLine();
            if (msg.equalsIgnoreCase("exit"))
                break;

            String formattedMsg = "[" + username + "]: " + msg;
            producer.send(new ProducerRecord<>("global-chat", formattedMsg));
        }

        // Cleanup
        producer.close();
        scanner.close();
        System.out.println("ChatApp closed.");
        System.exit(0);
    }
}
