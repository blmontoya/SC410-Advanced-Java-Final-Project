// Imports necessary packages such as java.awt and javax.swing
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The Panel class inherits properties of the JPanel class while implementing the ActionListener
 */

public class Panel extends JPanel implements ActionListener
{
    // Instantiates two constants for the bounds of the panel
    private final int PANEL_WIDTH;
    private final int PANEL_HEIGHT;

    // Instantiates a timer to incorporate time into the animation and an image to create a background for the panel
    Timer timer;
    Image background;

    // Instantiates 5 ParticlePhysics objects to accompany the 5 particles (Ellipse2D.Double objects)
    // Ellipse2D.Double is so the Ellipse2D is in "double" precision (Cited from: courses.cs.wash.edu)
    ParticlePhysics pP1;
    ParticlePhysics pP2;
    ParticlePhysics pP3;
    ParticlePhysics pP4;
    ParticlePhysics pP5;

    Ellipse2D.Double p1;
    Ellipse2D.Double p2;
    Ellipse2D.Double p3;
    Ellipse2D.Double p4;
    Ellipse2D.Double p5;

    /**
     * This Panel constructor is where teh animation, collisions and demonstration will be taking place
     */
    public Panel()
    {
        // Background is 960x800 pixels, therefore PANEL_WIDTH and PANEL_HEIGHT are set to their respective values
        PANEL_WIDTH = 960;
        PANEL_HEIGHT = 800;

        // Due to pack function in Frame.java
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        // Initializes the Image background to reference the Resources' folder with the background .png file
        background = new ImageIcon("Resources/background.jpeg").getImage();

        // Timer is in ms (milliseconds)
        // After 10ms, actionPerformed occurs, meaning some particle will move (x,y) pixels in 10ms
        // Can pass in "this" as parameter since ActionListener is implemented
        timer = new Timer(10, this);
        timer.start();

        // Initializes the ParticlePhysics objects with the desired properties that the particle would like to have
        // (See ParticlePhysics class for more details on these properties)
        pP1 = new ParticlePhysics(0,0,2,2,100,100);
        pP2 = new ParticlePhysics(200,200,-2,-2,100,100);
        pP3 = new ParticlePhysics(100,400,-2,2,100,100);
        pP4 = new ParticlePhysics(500,100,-2,-2,100,100);
        pP5 = new ParticlePhysics(400,400,5,-10,100,100);

        // Initializes the particles with the dimensions stated in its respective ParticlePhysics object
        p1 = new Ellipse2D.Double(pP1.getX(), pP1.getY(), pP1.getPWidth(), pP1.getPHeight());
        p2 = new Ellipse2D.Double(pP2.getX(),pP2.getY(), pP2.getPWidth(), pP2.getPHeight());
        p3 = new Ellipse2D.Double(pP3.getX(), pP3.getY(), pP3.getPWidth(), pP3.getPHeight());
        p4 = new Ellipse2D.Double(pP4.getX(),pP4.getY(), pP4.getPWidth(), pP4.getPHeight());
        p5 = new Ellipse2D.Double(pP5.getX(), pP5.getY(), pP5.getPWidth(), pP5.getPHeight());
    }

    /**
     * This method paints the graphics onto the screen, which will be the particles and background
     * Note that antialiasing is also incorporated into this method
     * @param g Graphics object
     */
    public void paint(Graphics g)
    {
        // Call super method on paint to paint the background in frame
        super.paint(g);

        // Casts the graphics object to be a 2D graphics object for more features
        Graphics2D g2d = (Graphics2D)g;

        // Incorporates antialiasing, which blends the color of the particle with its surroundings so that
        // the particle appears smooth rather than pixelated on screen
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        // Due to the fact these particles are being painted in the paint method, they must be re-initialized in
        // the paint method even though they have been initialized in the constructor. This is so that the frame can
        // be repainted over to create an animation.
        p1 = new Ellipse2D.Double(pP1.getX(), pP1.getY(), pP1.getPWidth(), pP1.getPHeight());
        p2 = new Ellipse2D.Double(pP2.getX(),pP2.getY(), pP2.getPWidth(), pP2.getPHeight());
        p3 = new Ellipse2D.Double(pP3.getX(), pP3.getY(), pP3.getPWidth(), pP3.getPHeight());
        p4 = new Ellipse2D.Double(pP4.getX(),pP4.getY(), pP4.getPWidth(), pP4.getPHeight());
        p5 = new Ellipse2D.Double(pP5.getX(), pP5.getY(), pP5.getPWidth(), pP5.getPHeight());

        // Produces the image in the frame, acting as the background since it is called before the particles are
        // painted. Therefore, the particles take priority and are painted on top of the background
        g2d.drawImage(background, 0, 0, null);

        // Fills in the particles with a gradient that adapts to where the particle is
        // (See more in pShading() method)
        pShading(pP1, p1, g2d, Color.yellow);
        pShading(pP2, p2, g2d, Color.cyan);
        pShading(pP3, p3, g2d, Color.magenta);
        pShading(pP4, p4, g2d, Color.red);
        pShading(pP5, p5, g2d, Color.green);
    }

    /**
     * This method is called upon every 10ms (as stated in the constructor), where the frame will be repainted
     * over to create an animation
     * @param e ActionEvent object
     */
    public void actionPerformed(ActionEvent e)
    {
        // pAnimation is called for each particle to animate them each respectively
        // pAnimation uses the ParticlePhysics object of the particle to animate it, since
        // the particle's (x,y) values are dependent on its ParticlePhysics object
        pAnimation(pP1);
        pAnimation(pP2);
        pAnimation(pP3);
        pAnimation(pP4);
        pAnimation(pP5);

        // Must repaint window every time actionPerformed is called
        repaint();
    }

    /**
     * pAnimation takes in a ParticlePhysics object and changes the velocities and positions of the particle
     * depending on where it is and what it is doing
     * @param bP ParticlePhysics object
     */
    private void pAnimation(ParticlePhysics bP)
    {
        // Adapts to window size (Window size is set so background will always be in frame)

        // If the ball is not within the bounds, it will have a negative velocity and bounce off of the boundary
        if (bP.getX() >= PANEL_WIDTH-bP.getPWidth() || bP.getX() < 0)
        {
            bP.setNegXVelocity();

            // Since java is not a language used for animation, these if statements are used as correction
            // in the case that the ball is outside of bounds and cannot get back in due to the fact its
            // velocity oscillates since it is multiplied by -1
            // The correction sets the ball at the boundary line when its velocity is multiplied by -1
            if (bP.getX() > PANEL_WIDTH-bP.getPWidth())
            {
                bP.setX(PANEL_WIDTH-bP.getPWidth());
            }
            if (bP.getX() < -1)
            {
                bP.setX(0);
            }
        }

        // The new X value is set afterwards
        bP.setX(bP.getXVelocity() + bP.getX());

        // The same case as above for x-values is applicable here as well
        if (bP.getY() >= PANEL_HEIGHT-bP.getPHeight() || bP.getY() < 0)
        {
            bP.setNegYVelocity();

            if (bP.getY() > PANEL_HEIGHT-bP.getPHeight())
            {
                bP.setY(PANEL_HEIGHT-bP.getPHeight());
            }
            if (bP.getY() < -1)
            {
                bP.setY(0);
            }
        }

        // The new X value is set afterwards
        bP.setY(bP.getYVelocity() + bP.getY());

        // Finally, the method checks if there is a collision using checkCollision
        // (See more in checkCollision() method)
        checkCollision(pP1, pP2, p1);
        checkCollision(pP1, pP3, p1);
        checkCollision(pP1, pP4, p1);
        checkCollision(pP1, pP5, p1);
        checkCollision(pP2, pP3, p2);
        checkCollision(pP2, pP4, p2);
        checkCollision(pP2, pP5, p2);
        checkCollision(pP3, pP4, p3);
        checkCollision(pP3, pP5, p3);
        checkCollision(pP4, pP5, p4);
    }

    /**
     * This method is used to shade the desired particle in with a desired color
     * The method takes the particle along with its respective ParticlePhysics object, a Graphics2D object
     * to fill the particle in and the desired color of the particle.
     * @param bP ParticlePhysics object
     * @param p Ellipse2D.Double object
     * @param g2d Graphics2D object
     * @param color Color object
     */
    private void pShading(ParticlePhysics bP, Ellipse2D.Double p, Graphics2D g2d, Color color)
    {
        // A new gradient is created based on the particle's position
        // This is so that the gradient moves with the particle
        GradientPaint gp = new GradientPaint(bP.getX()-10, bP.getY()-10, color, bP.getX()+150, bP.getY()+150, Color.black, true);

        // The gradient is set to the Graphics2D object and used to fill the particle in
        g2d.setPaint(gp);
        g2d.fill(p);
    }

    /**
     * This method checks to see if there is a collision between two particles
     * This is done by passing the ParticlePhysics object of the two particles and passing ond of the particles
     * in as parameters.
     * If the two particles collide, they will have their respective velocities swapped with each other
     * @param pPA ParticlePhysics object (for 1st particle)
     * @param pPB ParticlePhysics object (for 2nd particle)
     * @param pA Ellipse2D.Double object (is the 1st particle)
     */
    private void checkCollision(ParticlePhysics pPA, ParticlePhysics pPB, Ellipse2D.Double pA)
    {
        // Once the particle is within the bounds of another, it is checked to see if the particle should be colliding
        if (pA.intersects(pPB.getX(), pPB.getY(), 100, 100))
        {
            // This if statement checks if the two particles should be colliding with each other
            // This is determined by taking the distance between the upper left rectangular boundary of the
            // particle and calculating if this distance is <= 100, since the diameter of each particle is 100 pixels
            if (Math.sqrt(Math.pow(Math.abs(pPA.getY()-pPB.getY()),2)+Math.pow(Math.abs(pPA.getY()-pPB.getY()),2)) <= 100)
            {
                // Once it is determined that the particles are colliding, the respective (x,y) velocities are swapped
                // by using a temporary int value t hold the velocities of one of the particles
                int tempXVelocity = pPB.getXVelocity();
                int tempYVelocity = pPB.getYVelocity();

                pPB.setXVelocity(pPA.getXVelocity());
                pPB.setYVelocity(pPA.getYVelocity());

                pPA.setXVelocity(tempXVelocity);
                pPA.setYVelocity(tempYVelocity);

                // Once the velocities are swapped, the (x,y) positions of the particles are adjusted accordingly
                pPB.setX(pPB.getXVelocity() + pPB.getX());
                pPA.setX(pPA.getXVelocity() + pPA.getX());
                pPB.setY(pPB.getYVelocity() + pPB.getY());
                pPA.setY(pPA.getYVelocity() + pPA.getY());

                // These if statements are used as corrections in the case that two circles overlap
                // These corrections make it so that the velocities do not combine to create higher velocities
                // rather than just exchanging
                // This correction is done by checking if a particle's top left bound and comparing it to
                // the second particle to make the correction
                // This correction is done by a single pixel to minimize any erratic movement the ball might have
                // when correcting itself
                if (pPA.getX() <= pPB.getX())
                {
                    pPB.setX(pPB.getX() + 1);
                }
                if (pPB.getX() < pPA.getX())
                {
                    pPB.setX(pPB.getX() - 1);
                }
                if (pPA.getY() <= pPB.getY())
                {
                    pPB.setY(pPB.getY() + 1);
                }
                if (pPB.getY() < pPA.getY())
                {
                    pPB.setY(pPB.getY() - 1);
                }
            }
        }
    }
}