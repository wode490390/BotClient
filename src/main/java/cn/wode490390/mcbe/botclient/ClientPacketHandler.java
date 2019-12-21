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
    public int air = 300;
    public boolean dead = false;
    public int viewDistance = 32;*/
    public boolean initialized = false;

    public ClientPacketHandler(BedrockClientSession session, BedrockClient client, ClientManager manager) {
        this.session = session;
        this.client = client;
        this.manager = manager;
    }

    public ClientTaskManager getTaskManager() {
        return tasker;
    }

    @Override
    public boolean handle(DisconnectPacket packet) {
        log.debug("Disconnect!!");
        tasker.getTimer().cancel();
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
    public boolean handle(StartGamePacket packet) {
        session.sendPacket(ClientPacketFactory.getRequestChunkRadiusPacket32());
        return true;
    }

    @Override
    public boolean handle(PlayStatusPacket packet) {
        if (packet.getStatus() == PlayStatusPacket.Status.PLAYER_SPAWN && !initialized) {
            initialized = true;
            session.sendPacket(ClientPacketFactory.getSetLocalPlayerAsInitializedPacket());
            tasker = new ClientTaskManager(session);
        }
        return true;
    }

    @Override
    public boolean handle(RespawnPacket packet) {
        if (packet.getSpawnState() == RespawnPacket.State.SERVER_SEARCHING) {
            session.sendPacket(ClientPacketFactory.getRespawnPacket2());
        }
        return true;
    }
}
