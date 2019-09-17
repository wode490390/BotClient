package cn.wode490390.mcbe.botclient;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientPacketHandler implements BedrockPacketHandler {

    private final BedrockClientSession session;
    private final BedrockClient client;
    private final ClientManager manager;
    private ClientTaskManager tasker;

    /*public int x = 0;
    public int y = 0;
    public int z = 0;
    public int heal = 20;
    public int food = 20;
    public int foods = 20;
    public boolean dead = false;*/
    public boolean initialized = false;

    public ClientPacketHandler(BedrockClientSession session, BedrockClient client, ClientManager manager) {
        this.session = session;
        this.client = client;
        this.manager = manager;
    }

    @Override
    public boolean handle(DisconnectPacket packet) {
        log.debug("Disconnect!!");
        session.disconnect();
        manager.getClients().remove(client);
        return true;
    }

    @Override
    public boolean handle(ResourcePacksInfoPacket packet) {
        session.sendPacket(ClientPacketFactory.getClientCacheStatusPacket());
        session.sendPacket(ClientPacketFactory.getResourcePackClientResponsePacket3());
        return true;
    }

    @Override
    public boolean handle(ResourcePackStackPacket packet) {
        session.sendPacket(ClientPacketFactory.getResourcePackClientResponsePacket4());
        return true;
    }

    @Override
    public boolean handle(PlayStatusPacket packet) {
        if (packet.getStatus() == PlayStatusPacket.Status.PLAYER_SPAWN && !this.initialized) {
            this.initialized = true;
            session.sendPacket(ClientPacketFactory.getSetLocalPlayerAsInitializedPacket());
            this.tasker = new ClientTaskManager(session);
        }
        return true;
    }
}
