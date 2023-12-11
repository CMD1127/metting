package com.metting.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    private static final String ELASTICSEARCH_HOST = "localhost";
    private static final int ELASTICSEARCH_PORT = 9200;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(ELASTICSEARCH_HOST, ELASTICSEARCH_PORT))
        );
        return restHighLevelClient;
    }
}
