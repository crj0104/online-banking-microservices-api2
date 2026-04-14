package com.example.account.config;

import com.example.account.event.AccountEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration // 标记这是一个 Spring 配置类，Spring 会自动扫描并加载
public class KafkaConfig {

    @Bean // 标记此方法返回的对象会被 Spring 管理，成为 Bean
    public ProducerFactory<String, AccountEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>(); // 创建配置参数 Map
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 配置 Kafka 服务器地址
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 配置 Key 的序列化方式
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // 配置 Value 的序列化方式
        configProps.put(JsonSerializer.TYPE_MAPPINGS, "accountEvent:com.example.account.event.AccountEvent"); // 配置 JSON 类型映射
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // 不添加类型信息头
        return new DefaultKafkaProducerFactory<>(configProps); // 创建并返回生产者工厂
    }

    @Bean // 标记此方法返回的对象会被 Spring 管理，成为 Bean
    public KafkaTemplate<String, AccountEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory()); // 使用生产者工厂创建 Kafka 模板
    }
}