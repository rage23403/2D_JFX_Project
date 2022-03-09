import javafx.scene.paint.Color;
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game
{
    static boolean game = true;

    public static boolean held = false;
    public static boolean left = false;
    public static boolean up = false;
    public static boolean right = false;
    public static boolean down = false;
    public static boolean a = false;
    public static Thread loop = new Thread(){
            public void run(){
                try{
                Thread.sleep(2500);
            }catch(Exception e){}
                gLoop();
            }
        };
    private static void gLoop(){
        Background b = new Background(600,600,"ugly.png");
        Player p = new Player(5,5,"sprite_sheet.png");
        inputManager.RequestBackgroundPainted(b);
        inputManager.RequestPaint(p, p.getImgProperties());
        //p.animate();
        while(game){
            if(!held){
                if(left){
                    inputManager.RequestBackgroundPainted(b);
                    p.move(Direction.LEFT);
                    held = true;
                }
                if(right){
                    inputManager.RequestBackgroundPainted(b);
                    p.move(Direction.RIGHT);
                    held = true;
                }
                if(up){
                    inputManager.RequestBackgroundPainted(b);
                    p.move(Direction.UP);
                    held = true;
                }
                if(down){
                    inputManager.RequestBackgroundPainted(b);
                    p.move(Direction.DOWN);
                    held = true;
                }
                if(a){
                    held = true;
                }
            }
            try{Thread.sleep(16);}catch(Exception e){}
        }
    }
}
