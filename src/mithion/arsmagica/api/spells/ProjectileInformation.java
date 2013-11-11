package mithion.arsmagica.api.spells;

/**
 * Projectile render information
 * @author Mithion
 *
 */
public class ProjectileInformation {
	private String projectileTextureName;
	private int idNo;
	private boolean isAnimated;
	private int firstAnimationFrame;
	private int lastAnimationFrame;
	private int animationTicks;
	
	public float scale;
	
	private float colorRed;
	private float colorGreen;
	private float colorBlue;
	
	private int imageSize;
	private int frameSize;
	
	private boolean hasCustomColorSet;
	
	public ProjectileInformation(int id, String textureFile, int firstAnimationFrame, int lastAnimationFrame, int animationTicks){
		isAnimated = true;
		this.firstAnimationFrame = firstAnimationFrame;
		this.lastAnimationFrame = lastAnimationFrame;
		projectileTextureName = textureFile;
		this.animationTicks = animationTicks;
		this.scale = 0.5f;
		hasCustomColorSet = false;
		this.idNo = id;
		this.imageSize = 256;
		this.frameSize = 64;
	}
	
	public ProjectileInformation setImageAndFrameSize(int imageSize, int frameSize){
		this.imageSize = imageSize;
		this.frameSize = frameSize;
		return this;
	}
	
	public int getImageSize(){
		return this.imageSize;
	}
	
	public int getFrameSize(){
		return this.frameSize;
	}
	
	public void setScale(float scale){
		this.scale = scale;
	}
	
	public float getScale(){
		return this.scale;
	}
	
	public ProjectileInformation(int id, String textureFile, int index){
		isAnimated = false;
		this.firstAnimationFrame = index;
		this.lastAnimationFrame = index;
		this.scale = 0.5f;
		projectileTextureName = textureFile;
		this.animationTicks = -1;
		hasCustomColorSet = false;
		this.idNo = id;
	}
	
	public ProjectileInformation setRGB(float red, float green, float blue){
		colorRed = red;
		colorGreen = green;
		colorBlue = blue;
		hasCustomColorSet = true;
		return this;
	}
	
	public int GetID(){
		return this.idNo;
	}
	
	public boolean hasCustomColorSet(){
		return this.hasCustomColorSet;
	}
	
	public float getColorRed(){
		return colorRed;
	}
	
	public float getColorGreen(){
		return colorGreen;
	}
	
	public float getColorBlue(){
		return colorBlue;
	}
	
	public String GetTextureFileName(){
		return this.projectileTextureName;
	}
	
	public boolean IsAnimated(){
		return this.isAnimated;
	}
	
	public int GetFirstAnimationFrame(){
		return this.firstAnimationFrame;
	}
	
	public int GetLastAnimatonFrame(){
		return this.lastAnimationFrame;
	}
	
	public int GetAnimationTicks(){
		return this.animationTicks;
	}
}
