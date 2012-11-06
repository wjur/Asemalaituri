//import java.net.*;
import java.io.*;

// import java.nio.*;
// import java.nio.channels.*;
// import java.util.*;


//import com.sun.opengl.util.BufferUtil;
import com.jogamp.common.nio.Buffers;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

//import JautOGL.*;

/**
 * Image loading class that converts BufferedImages into a data
 * structure that can be easily passed to OpenGL.
 * @author Pepijn Van Eeckhoudt
 */


// Uses the class GLModel from JautOGL to load and display obj files.
public class ModelLoaderOBJ {

///   protected TextureManager texture_manager;
  protected GLModel model1;
  protected GLModel model2;


  // Load some models.
  public void init( GL2 gl ) {

    System.out.println("ModelLoaderOBJ init() start..."); //ddd

///       texture_manager = TextureManager.getInstance( gl, glu );
       gl.glEnable( GL2.GL_TEXTURE_2D );
       gl.glShadeModel( GL2.GL_SMOOTH );

       //Paths of the 3D models.
       //String path1 = "models/formula.obj";
       //String path1 = "models/heli.obj";
       String path1 = "W:\\nauka\\msc\\gk\\asemalaituri\\spot.obj";
       ////String path2 = "models/formula.obj";

   //here the needed textures for the race-ground are loaded
       try{
///               texture_manager.createManagedTexture
///                       ("road", "textures/roads/road.jpg", 
///                        GL.GL_TEXTURE_2D, GL.GL_RGB, GL.GL_RGB, GL.GL_LINEAR,
///                        GL.GL_LINEAR, true, true );
                       // ...
                       //in the same way we load each texture is needed...
                       //...

               //a file input stream reads the data and stores them in 
               //a buffer reader for each 3D model
               FileInputStream r_path1 = new FileInputStream(path1);
               BufferedReader b_read1 =
                    new BufferedReader(new InputStreamReader(r_path1));
               model1 = new GLModel(b_read1, true, "W:\\nauka\\msc\\gk\\asemalaituri\\spot.mtl", gl);
               r_path1.close();
               b_read1.close();

//                FileInputStream r_path2 = new FileInputStream(path2);
//                BufferedReader b_read2 =
//                     new BufferedReader(new InputStreamReader(r_path2));
//                model2 = new GLModel(b_read2, true, "models/formula.mtl", gl);
//                r_path2.close();
//                b_read2.close();
       }
       catch( Exception e ){
               System.out.println("LOADING ERROR" +  e);
       }

       System.out.println("ModelLoaderOBJ init() done"); //ddd
   }


  public void draw( GL2 gl ) 
    {

          gl.glPushMatrix();

           //gl.glTranslatef(car1_x, car1_y, car1_z);
           //gl.glScalef(0.0005F, 0.0005F, 0.0005F); //TOO BIG
           //gl.glRotatef(-90.0f, 0.0F, 1.0F, 0.0F);
           //gl.glRotatef((float)Math.toDegrees(movement_angle),0.0f,1.0f,0.0f);

           //draws the model
           model1.opengldraw(gl);

           gl.glPopMatrix();

  //          //***CAR2***
//            gl.glPushMatrix();

//            gl.glTranslated(car2_x, car2_y, car2_z);
//            gl.glScalef(0.0005F, 0.0005F, 0.0005F); //TOO BIG
//            gl.glRotatef(-90.0f, 0.0F, 1.0F, 0.0F);
//            gl.glRotatef((float)Math.toDegrees(movement_angle2),0.0f,1.0f,0.0f);

//            //draws the model
//            model2.opengldraw(gl);

//            gl.glPopMatrix();

    }
}
