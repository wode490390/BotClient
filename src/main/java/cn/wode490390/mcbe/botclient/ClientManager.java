package cn.wode490390.mcbe.botclient;

import com.nukkitx.protocol.bedrock.BedrockClient;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientManager {

    private final Main main;

    private static final Set<BedrockClient> clients = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public ClientManager(Main main) {
        this.main = main;
    }

    public BedrockClient newClient() {
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000));
        BedrockClient client = new BedrockClient(bindAddress);
        clients.add(client);
        client.bind().join();
        client.connect(main.getTargetAddress()).whenComplete((session, throwable) -> {
            if (throwable == null) {
                if (main.getConfiguration().isEnableSessionLog()) {
                    log.info("RakNet client started on {}", client.getBindAddress());
                } else {
                    session.setLogging(false);
                }
                session.setPacketCodec(ClientPacketFactory.CODEC);
                session.setPacketHandler(new ClientPacketHandler(session, client, this));

                session.sendPacketImmediately(ClientPacketFactory.randomLoginPacket());
                session.sendPacket(ClientPacketFactory.getClientCacheStatusPacket());
                session.sendPacket(ClientPacketFactory.getResourcePackClientResponsePacket4());
                session.sendPacket(ClientPacketFactory.getSetLocalPlayerAsInitializedPacket());

                new ClientTaskManager(session);
            }
        });
        return client;
    }

    public Set<BedrockClient> getClients() {
        return clients;
    }
}
