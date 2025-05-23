package net.yeoxuhang.capix.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.yeoxuhang.capix.Capix;

public class Cape extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Capix.name("cape"), "main");
	public final ModelPart cape;

	public Cape(ModelPart root) {
        super(RenderType::entityCutout);
        this.cape = root.getChild("cape");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition cape = partdefinition.addOrReplaceChild("cape", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	/**
	 * We use {@link #renderWithAnimation} instead.
	 * **/
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
	}

	public void renderWithAnimation(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color, float tick) {
		float flutter = Mth.cos(tick * 0.015F) * 0.05F;
		this.cape.xRot = flutter;
		cape.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}