package com.executedpoorly.waystonebeaconmod.client;

import com.executedpoorly.waystonebeaconmod.WaystoneBeacon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = WaystoneBeacon.MODID, value = Dist.CLIENT)
public class WaystoneBeamClientSetup {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BuiltInRegistries.BLOCK_ENTITY_TYPE.listElements().forEach(holder -> {
            String path = holder.key().identifier().getPath();
            if (path.contains("waystone")) {
                WaystoneBeacon.LOGGER.info("WAYSTONE BEAM: registering renderer for {}", path);
                register(event, holder.value());
            }
        });
    }

    private static <T extends BlockEntity> void register(EntityRenderersEvent.RegisterRenderers event, BlockEntityType<T> type) {
        event.registerBlockEntityRenderer(type, WaystoneBeamRenderer::new);
    }
}