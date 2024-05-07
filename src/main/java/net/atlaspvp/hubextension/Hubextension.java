package net.atlaspvp.hubextension;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.utils.binary.BinaryWriter;

import java.io.IOException;
import java.nio.file.Path;

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
        ic.setChunkLoader(new AnvilLoader("world"));

        //ic.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.AIR));
        //ic.setBlock(0, 40, 0, Block.GRASS_BLOCK);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(ic);
            player.setItemInMainHand(teleporter());
            player.setRespawnPoint(new Pos(-28, 118, -18));
        });

        globalEventHandler.addListener(PlayerUseItemEvent.class, event -> {
            final Player player = event.getPlayer();
            System.out.println("The listener works bitch");
            if (event.getItemStack().getMaterial() == Material.COMPASS) {
                System.out.println("if statement true");
                // Send a plugin message to the AJQueue plugin to add the player to a queue
                BinaryWriter writer = new BinaryWriter();
                writer.writeSizedString("Connect");
                writer.writeSizedString("hub");

                player.sendPluginMessage("BungeeCord", writer.toByteArray());
            } else {
                System.out.println("The listener false bitch");
            }
        });
    }

    public static ItemStack teleporter() {
        return ItemStack.of(Material.COMPASS);
    }

}
