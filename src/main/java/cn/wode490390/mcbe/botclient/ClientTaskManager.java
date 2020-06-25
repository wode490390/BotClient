package cn.wode490390.mcbe.botclient;

import com.nukkitx.protocol.bedrock.BedrockClientSession;
import java.util.Timer;
import java.util.TimerTask;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientTaskManager {

    private final Timer timer;

    private final BedrockClientSession session;

    public ClientTaskManager(BedrockClientSession session) {
        this.session = session;
        this.timer = new Timer(true);

        this.timer.schedule(new RequestTask(), 9000, 1000); //9,1
        //Must be spawned
        this.timer.schedule(new ResponseTask(), 10000, 10000); //10,10
//        this.timer.schedule(new ChatTask(), 10000, 10000); //10,10
        this.timer.schedule(new EatTask(), 10000, 10000); //10,10
        this.timer.schedule(new AnimateTask(), 10000, 10000); //10,10
        this.timer.schedule(new CommandTask(), 10000, 10000); //10,10
        this.timer.schedule(new ActionTask(), 10000, 10000); //10,10
        this.timer.schedule(new RotateTask(), 10000, 10000); //10,10
        //Must be dead
        this.timer.schedule(new RespawnTask(), 40000, 38000);//(5~10)25~30,31~40
    }

    public Timer getTimer() {
        return this.timer;
    }

    class RespawnTask extends TimerTask {

        RespawnTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("RespawnTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.getPlayerActionPacket7());
        }
    }

    class ChatTask extends TimerTask {

        ChatTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("ChatTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomTextPacket1());
        }
    }

    class EatTask extends TimerTask {

        EatTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("EatTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomEntityEventPacket57());
        }
    }

    class AnimateTask extends TimerTask {

        AnimateTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("AnimateTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomAnimatePacket());
        }
    }

    class ResponseTask extends TimerTask {

        ResponseTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("ResponseTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.getResourcePackClientResponsePacket3());
        }
    }

    class RequestTask extends TimerTask {

        RequestTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("RequestTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.getServerSettingsRequestPacket());
            log.debug("ActionTask14::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.getPlayerActionPacket14());
        }
    }

    class CommandTask extends TimerTask {

        CommandTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("CommandTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomCommandRequestPacket());
        }
    }

    class ActionTask extends TimerTask {

        ActionTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("ActionTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomPlayerActionPacket());
            log.debug("ActionTask1Or2::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomPlayerActionPacket1Or2());
        }
    }

    class RotateTask extends TimerTask {

        RotateTask() {
            super();
        }

        @Override
        public void run() {
            log.debug("RotateTask::run");
            ClientTaskManager.this.session.sendPacket(ClientPacketFactory.randomMovePlayerPacketR());
        }
    }
}
