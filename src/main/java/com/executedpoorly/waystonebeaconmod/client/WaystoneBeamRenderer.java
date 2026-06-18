package com.executedpoorly.waystonebeaconmod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WaystoneBeamRenderer implements BlockEntityRenderer<BlockEntity, BeaconRenderState> {

    public WaystoneBeamRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public BeaconRenderState createRenderState() {
        return new BeaconRenderState();
    }

    @Override
    public void extractRenderState(BlockEntity blockEntity, BeaconRenderState state, float partialTick,
                                   Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPosition, breakProgress);

        state.animationTime = blockEntity.getLevel() != null
                ? Math.floorMod(blockEntity.getLevel().getGameTime(), 40) + partialTick
                : 0.0F;

        LocalPlayer player = Minecraft.getInstance().player;
        float distance = (float) cameraPosition.subtract(state.blockPos.getCenter()).horizontalDistance();
        state.beamRadiusScale = (player != null && player.isScoping()) ? 1.0F : Math.max(1.0F, distance / 96.0F);
    }

    @Override
    public void submit(BeaconRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        int beamColor = 0xFF33CCFF;
        int beamHeight = 384;

        BeaconRenderer.submitBeaconBeam(
                poseStack,
                submitNodeCollector,
                BeaconRenderer.BEAM_LOCATION,
                1.0F,
                state.animationTime,
                0,
                beamHeight,
                beamColor,
                0.2F * state.beamRadiusScale,
                0.25F * state.beamRadiusScale
        );
    }
}