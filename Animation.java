import javafx.animation.AnimationTimer;
/**
 * Write a description of class Animation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Animation extends AnimationTimer
{
    Sprite[] spriteNums;
    Player prefer;
    double time = 0;
    double tmult = 0.016;
    Animation(int[] nums, int imgWidth, int imgHeight, int sizes, Player p){
        spriteNums = new Sprite[nums.length];
        prefer = p;
        for(int i = 0; i < nums.length; i++){
            spriteNums[i] = new Sprite(imgWidth, imgHeight, sizes, nums[i]);
        }
        p.setSprite(spriteNums[0]);
    }
    
    public void handle(long now){
        if(time<spriteNums.length-1){
            time += tmult;
        }
        else{time = 0;}
        prefer.setSprite(spriteNums[(int)time]);
    }
}
