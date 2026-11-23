package com.plusultra.client;

import com.plusultra.PlusUltra;
import com.plusultra.entity.VillainEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class VillainRenderer extends HumanoidMobRenderer<VillainEntity, HumanoidModel<VillainEntity>> {
    private static final ResourceLocation TEXTURE = PlusUltra.id("textures/entity/villain.png");

    public VillainRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(VillainEntity entity) {
        return TEXTURE;
    }
}
