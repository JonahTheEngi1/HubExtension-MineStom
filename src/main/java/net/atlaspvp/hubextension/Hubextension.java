package net.atlaspvp.hubextension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;

public final class Hubextension {
     public static int port = 27005;
     public static String ip = "0.0.0.0"; //"172.18.0.1"

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();
        initWorld();
        VelocityProxy.enable("klendglerbnty");
        minecraftServer.start(ip, port);

        
    }

    public static void initWorld() {
        InstanceManager im = MinecraftServer.getInstanceManager();
        InstanceContainer ic = im.createInstanceContainer();

        ic.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.AIR));
        ic.setBlock(0, 40, 0, Block.GRASS_BLOCK);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(ic);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });
    }
}
