package dev.lazurite.grabz.core.entity.render;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import dev.lazurite.grabz.core.entity.GrabbedBlockEntity;
import dev.lazurite.rayon.core.impl.util.math.QuaternionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Quaternion;
import net.minecraft.world.World;

import java.util.Random;

public class GrabbedBlockEntityRenderer extends EntityRenderer<GrabbedBlockEntity> {
    public GrabbedBlockEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    public void render(GrabbedBlockEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = fallingBlockEntity.getBlockState();

        if (blockState != null) {
            World world = fallingBlockEntity.getEntityWorld();
            BoundingBox box = fallingBlockEntity.getRigidBody().getFrame().getBox(new BoundingBox(), g);
            Vector3f bounds = box.getExtent(new Vector3f()).multLocal(-1);
            Quaternion rot = QuaternionHelper.bulletToMinecraft(fallingBlockEntity.getPhysicsRotation(new com.jme3.math.Quaternion(), g));
            BlockPos blockPos = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
            BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();

            matrixStack.push();
            matrixStack.multiply(rot);
            matrixStack.translate(bounds.x, bounds.y, bounds.z);
            blockRenderManager.getModelRenderer().render(world, blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, new Random(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
            matrixStack.pop();

            super.render(fallingBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
        }
    }

    @Override
    public Identifier getTexture(GrabbedBlockEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}