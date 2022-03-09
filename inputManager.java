import javafx.application.Application;
import javafx.application.Platform;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import misc.Error;
import misc.ErrorCode;
import misc.ErrorType;

/**
 * Write a description of JavaFX class inputManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class inputManager extends Application
{
    static int canWidth=640,canHeight=640;
    Canvas can;
    static GraphicsContext g;
    private final int BrushSize = 10;
    private static boolean parsingError = false;
    @Override
    public void start(Stage stage)
    {
        // Create a new grid pane
        can = new Canvas(canWidth,canHeight);
        GridPane pane = new GridPane();
        g = can.getGraphicsContext2D();
        Button b = new Button("lol");
        b.setFocusTraversable(false);
        pane.add(can,0,0);
        pane.add(b,1,0);
        Scene scene = new Scene(pane, 800,700);
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);

        // Show the Stage (window)
        stage.setOnCloseRequest(event -> {System.exit(1);});
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent e){
                    switch(e.getCode()){
                        case UP:Game.up = true;break;
                        case DOWN:Game.down = true;break;
                        case LEFT:Game.left = true;break;
                        case RIGHT:Game.right = true;break;
                        case A:Game.a = true;break;
                    }
                }
            });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent e){
                    Game.held = false;
                    switch(e.getCode()){
                        case UP:Game.up = false;break;
                        case DOWN:Game.down = false;break;
                        case LEFT:Game.left = false;break;
                        case RIGHT:Game.right = false;break;
                        case A:Game.a = false;break;
                    }
                }
            });
        Game.loop.start();
        stage.show();
    }

    public static void RequestPaint(Player sprite, int[] imgProps){
        if(!parsingError){
            Platform.runLater(() ->{   
                    try{ //image, x offset, y offset, size, size, canvas x, canvas y, size, size
                        PaintCan(sprite.getImage(), imgProps[2], imgProps[3], imgProps[4], imgProps[4], imgProps[0], imgProps[1], imgProps[4], imgProps[4]);
                    }catch(ArrayIndexOutOfBoundsException e){
                        ErrorCode c;
                        if(imgProps.length < 1){c = ErrorCode.Code_X02;}
                        else if(imgProps.length > 5){c = ErrorCode.Code_X01;}
                        else if(1 < imgProps.length && imgProps.length < 5){c = ErrorCode.Code_X03;}
                        else{c = ErrorCode.Code_X04;}
                        Error err = new Error(ErrorType.Code, c);
                        PaintError(err);
                    }catch(NullPointerException e){
                        Error err;
                        if(sprite.err != null){
                            err = new Error(ErrorType.IO, sprite.err);
                        }
                        else{err = new Error(ErrorType.IO, ErrorCode.Code_X04);}
                        PaintError(err);
                    }
                });
        }
    }

    public static void RequestBackgroundPainted(Background image){
        if(!parsingError){
            Platform.runLater(() ->{
                    Image check = image.getImg();
                    if(check == null){
                        PaintError(new Error("paint",4));
                    }else{
                        g.drawImage(check, 0, 0, image.getWidth(), image.getHeight());
                    }
                });
        }
    }

    public static void PaintCan(Image sprite, int imgX, int imgY, int imgW, int imgH, int canX, int canY, int canW, int canH) throws NullPointerException{
        if(sprite == null){throw new NullPointerException("requested image is null");} 
        else if(!parsingError){
            g.drawImage(sprite, imgX, imgY, imgW, imgH, canX, canY, canW, canH);
        }
    }

    public static void PaintError(Error err){
        parsingError = true;
        int mult = 4;
        int h = 60;
        int w = 20;
        g.setFill(Color.BLACK);
        g.fillRect(0,0,canWidth,canHeight);
        g.setFill(Color.RED);
        g.fillRect((w+20)*mult,(20+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+20)*mult,(30+h)*mult,(10)*mult,(20)*mult);
        g.fillRect((w+30)*mult,(35+h)*mult,(10)*mult,(10)*mult);
        g.fillRect((w+20)*mult,(50+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+60)*mult,(20+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+60)*mult,(30+h)*mult,(10)*mult,(30)*mult);
        g.fillRect((w+60)*mult,(35+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+80)*mult,(30+h)*mult,(10)*mult,(5)*mult);
        g.fillRect((w+70)*mult,(45+h)*mult,(10)*mult,(5)*mult);
        g.fillRect((w+70)*mult,(50+h)*mult,(15)*mult,(5)*mult);
        g.fillRect((w+75)*mult,(55+h)*mult,(15)*mult,(5)*mult);
        g.fillRect((w+100)*mult,(20+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+100)*mult,(30+h)*mult,(10)*mult,(30)*mult);
        g.fillRect((w+100)*mult,(35+h)*mult,(30)*mult,(10)*mult);
        g.fillRect((w+120)*mult,(30+h)*mult,(10)*mult,(5)*mult);
        g.fillRect((w+110)*mult,(45+h)*mult,(10)*mult,(5)*mult);
        g.fillRect((w+110)*mult,(50+h)*mult,(15)*mult,(5)*mult);
        g.fillRect((w+115)*mult,(55+h)*mult,(15)*mult,(5)*mult);
        g.setFill(Color.WHITE);
        g.setTextAlign(TextAlignment.CENTER);
        g.fillText(err.toString(),(w+70)*mult,(80+h)*mult);
        Game.loop.suspend();
    }
}
