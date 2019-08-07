package cn.wode490390.mcbe.botclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ClientConfiguration {

    private static final YAMLMapper YAML_MAPPER = (YAMLMapper) new YAMLMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static ClientConfiguration load(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return YAML_MAPPER.readValue(reader, ClientConfiguration.class);
        }
    }

    public static ClientConfiguration load(InputStream stream) throws IOException {
        return YAML_MAPPER.readValue(stream, ClientConfiguration.class);
    }

    public static void save(Path path, ClientConfiguration configuration) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            YAML_MAPPER.writerWithDefaultPrettyPrinter().writeValue(writer, configuration);
        }
    }

    @JsonProperty("server-host")
    private String serverHost = "127.0.0.1";
    @JsonProperty("server-port")
    private int serverPort = 19132;
    @JsonProperty("max-client")
    private int maxClient = 1;
    @JsonProperty("log-level")
    private int logLevel = 1;
    @JsonProperty("session-log")
    private boolean sessionLog = false;

    public int getMaxClient() {
        return maxClient;
    }

    public boolean isEnableSessionLog() {
        return sessionLog;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress(serverHost, serverPort);
    }
}
