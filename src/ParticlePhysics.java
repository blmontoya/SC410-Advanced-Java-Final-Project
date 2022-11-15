/**
 * This ParticlePhysics class stored the properties of a respective particle, such as (x,y) position and velocity,
 * as well as height
 */
public class ParticlePhysics
{
    // Instantiates the (x,y) position, (x,y) velocities and the height and width of its boundary
    int x;
    int y;
    int xVelocity;
    int yVelocity;
    int pWidth;
    int pHeight;

    /**
     * This constructor initializes the parameters sent in with the instantiated values above to store them
     * @param x int value for x position
     * @param y int value for y position
     * @param xVelocity int value for velocity on x-axis
     * @param yVelocity int value for velocity on y-axis
     * @param pWidth int value for width of boundary
     * @param pHeight int value for height of boundary
     */
    public ParticlePhysics(int x, int y, int xVelocity, int yVelocity, int pWidth, int pHeight)
    {
        this.x = x;
        this.y = y;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
    }

    // Returns xVelocity
    public int getXVelocity()
    {
        return xVelocity;
    }

    // Sets xVelocity
    public void setXVelocity(int v){ xVelocity = v; }

    // Set xVelocity to be the negative of its current value
    public void setNegXVelocity()
    {
        xVelocity *= -1;
    }

    // Returns yVelocity
    public int getYVelocity()
    {
        return yVelocity;
    }

    // Sets yVelocity
    public void setYVelocity(int v){ yVelocity = v; }

    // Set YVelocity to be the negative of its current value
    public void setNegYVelocity()
    {
        yVelocity *= -1;
    }

    // Returns x position
    public int getX()
    {
        return x;
    }

    // Sets x position
    public void setX(int dX)
    {
        x = dX;
    }

    // Returns y position
    public int getY() { return y;}

    // Sets y position
    public void setY(int dY)
    {
        y = dY;
    }

    // Returns boundary width
    public int getPWidth()
    {
        return pWidth;
    }

    // Returns boundary height
    public int getPHeight()
    {
        return pHeight;
    }
}