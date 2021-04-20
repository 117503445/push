package com.wizzstudio.push.service;

import com.wizzstudio.push.config.StaticFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

// https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.12/index.html
public class ESClient {
    public static RestHighLevelClient client;

    final static CredentialsProvider credentialsProvider =
            new BasicCredentialsProvider();

    static {
        if (StaticFactory.getEsConfig().getEnabled()) {
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(StaticFactory.getEsConfig().getUsername(), StaticFactory.getEsConfig().getPassword()));

            client = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(StaticFactory.getEsConfig().getHost(), StaticFactory.getEsConfig().getPort(), StaticFactory.getEsConfig().getScheme())).
                            setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
        }
    }
}
