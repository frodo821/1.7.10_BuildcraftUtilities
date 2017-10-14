package net.mysdbox.bcutil.client;

import buildcraft.core.lib.render.IInventoryRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import buildcraft.core.lib.engines.RenderEngine;

import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_S;

public class CommonRenderer
extends TileEntitySpecialRenderer
implements IInventoryRenderer {
	ModelBase model = new ModelBase() { };
	public ResourceLocation baseTexture;
	public ModelRenderer renderer;
	
	public CommonRenderer(ResourceLocation res) {
		baseTexture = res;
		renderer = new ModelRenderer(model);
		renderer.addBox(0f, 0f, 0f, 16, 16, 16);
		renderer.rotationPointX = 8f;
		renderer.rotationPointY = 8f;
		renderer.rotationPointZ = 8f;
	}
	
	@Override
	public void inventoryRender(double x, double y, double z, float f, float f1) {
		render((float)x, (float)y, (float)z, 0.0f, 0.0f, 1.5707964f);
	}

	@Override
	public void renderTileEntityAt(
			TileEntity tent,
			double x,
			double y,
			double z,
			float f) {
		render((float)x, (float)y, (float)z);
	}
	
	private void render(float renderX, float renderY, float renderZ){
		render(renderX, renderY, renderZ, 0f, 0f, 0f);
	}
	
	protected void render(float renderX, float renderY, float renderZ, float rotX, float rotY, float rotZ){
		glPushMatrix();
		glPushAttrib(GL_S);
		//lighting and face only enable
		glEnable(GL_LIGHTING);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glDisable(GL_BLEND);
		
		//set rendering original coordination
		glTranslatef(renderX, renderY, renderZ);
		
		//set rendering texture
		bindTexture(baseTexture);
		renderer.rotateAngleX = rotX;
		renderer.rotateAngleY = rotY;
		renderer.rotateAngleZ = rotZ;
		
		renderer.render(0);
		
		glPopAttrib();
		glPopMatrix();
	}
}
