import javafx.scene.image.*;
import java.io.File;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private int x, y;
    private Image model;
    private Sprite sprite;
    private Animation anim;
    public misc.ErrorCode err;
    Player(int SpawnX, int SpawnY, String cModel){
        x = SpawnX;
        y = SpawnY;
        try{
            model = new Image(cModel);
            anim = new Animation(new int[]{1,1,2,1,3,0},(int)model.getWidth(), (int)model.getHeight(), 64, this);
        }catch(IllegalArgumentException e){
            sprite = new Sprite(); 
            model = null;
            File temp = new File(cModel);
            if(temp.exists()){err = misc.ErrorCode.Code_X01;}
            else{err = misc.ErrorCode.Code_X04;}
        }
        WritableImage temp = new WritableImage((int)model.getWidth(), (int)model.getHeight());
        PixelReader r = model.getPixelReader();
        PixelWriter w = temp.getPixelWriter();
        for(int i = 0; i < model.getHeight(); i++){
            for(int j = 0; j < model.getWidth(); j++){
                int argb = r.getArgb(j,i);
                if((argb & 0xFFFFFF) == 0xB6519E){
                    w.setArgb(j,i, 0x00000000);
                }
                else{
                    w.setArgb(j,i, argb);
                }
            }
        }
        model = temp;
    }

    public boolean checkAnim(){if(sprite.getSprX() < 0){return false;}return true;}

    public int[] getImgProperties(){
        int[] get = sprite.getImgProps();
        return new int[]{get[2]*x,get[2]*y,get[0],get[1],get[2]};
    }

    public Image getImage(){return model;}

    public int getX(){return x;}

    public int getY(){return y;}
    
    public void animate(){
        anim.start();
    }
    
    public void setSprite(Sprite s){
        sprite = s;
        inputManager.RequestPaint(this,getImgProperties());
    }
    
    public void move(Direction d){
        switch(d){
            case UP:y--;break;
            case DOWN:y++;break;
            case LEFT:x--;break;
            case RIGHT:x++;break;
        }
        inputManager.RequestPaint(this,getImgProperties());
    }
}
