import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ActionRequestFailureHandler;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch7.ElasticsearchSink;
import org.apache.flink.util.ExceptionUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.common.util.concurrent.EsRejectedExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaToEs {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String kafkaServer = "100.89.0.105:9092";
//        String kafkaServer = "42.157.195.140:11003";
        String kafkaTopic = "germany_party_fdp";
        Properties prop = new Properties();
        prop.setProperty("partition.discovery.interval.ms", "10000");
        prop.setProperty("auto.commit.enable", "true");
        prop.setProperty("serializer.encoding", "UTF8");
//        prop.setProperty("security.protocol", "SASL_PLAINTEXT");
//        prop.setProperty("sasl.mechanism", "SCRAM-SHA-256");
//        prop.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"bdd_user\" password=\"Jnubdd123!@#\";");
        String kafkaGroupId = "zhax_test";
        String[] kafkaTopics = kafkaTopic.split(",");

        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(kafkaServer)
                .setTopics(kafkaTopics)
                .setGroupId(kafkaGroupId)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setProperties(prop)
                .build();
        DataStreamSource<String> kafka_source = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        List<HttpHost> httpHosts = new ArrayList<>();
        String esServers = "100.89.0.105:9100";
        for (String ipAndPortStr : esServers.split(",")) {
            String[] ipAndPortArr = ipAndPortStr.split(":");
            String ip = ipAndPortArr[0];
            int port = Integer.parseInt(ipAndPortArr[1]);
            httpHosts.add(new HttpHost(ip, port, "http"));
        }


        ElasticsearchSink.Builder<String> esSinkBuilder = new ElasticsearchSink.Builder<>(
                httpHosts,
                new ElasticsearchSinkFunction<String>() {
                    public IndexRequest createIndexRequest(String element) {
                        System.out.println("==数据处理==>");
                        Map<String, Object> elementInnerMap = JSONObject.parseObject(element).getInnerMap();
                        System.out.println("elementInnerMap = " + elementInnerMap);
                        if
                        String esIndex = "";
//                        for (Map.Entry<String, Object> entry : elementInnerMap.entrySet()) {
//                            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                        }

                        return Requests.indexRequest()
                                .index(esIndex)
                                .type("_doc")
                                .id((String) elementInnerMap.get("id_str"))
                                .source(elementInnerMap);
                    }

                    public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
                        indexer.add(createIndexRequest(element));
                    }
                }
        );

        esSinkBuilder.setRestClientFactory(
                restClientBuilder -> {
                    BasicHeader[] arrayOfBasicHeader = {new BasicHeader("Content-Type", "application/json")};
                    restClientBuilder.setDefaultHeaders(arrayOfBasicHeader);
                    restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                            BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
                            basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "123456"));
                            return httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
                        }
                    });
//                    restClientBuilder.setMaxRetryTimeoutMillis(...)
//                    restClientBuilder.setPathPrefix(...)
                }
        );
        esSinkBuilder.setBulkFlushMaxActions(1);
        esSinkBuilder.setBulkFlushInterval(1);
        esSinkBuilder.setFailureHandler((ActionRequestFailureHandler) (action, failure, restStatusCode, indexer) -> {
            if (ExceptionUtils.findThrowable(failure, EsRejectedExecutionException.class).isPresent()) {
                // 队列已满；重新添加文档进行索引
//                    indexer.add(action);
                System.out.println("队列已满");
            } else if (ExceptionUtils.findThrowable(failure, ElasticsearchParseException.class).isPresent()) {
                // 文档格式错误；简单地删除请求避免 sink 失败
                System.out.println("文档格式错误");
            } else {
                // 对于所有其他失败的请求，失败的 sink
                // 这里的失败只是简单的重新抛出，但用户也可以选择抛出自定义异常
                System.out.println("失败");
                System.out.println(action);
                throw failure;
            }
            failure.printStackTrace();
        });

        kafka_source.addSink(esSinkBuilder.build());
        env.execute("test");
    }
}

