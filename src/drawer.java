
// Add the following imports to your tree

/*
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
*/

// Put this stuff into your lovely tree
// Love Ariel and Shaked

/*
	private BufferedImage paintImage;
	private int XDIM, YDIM;
	private Graphics2D display;
	public void init_draw(int x, int y)
	{
		XDIM = x;  
		YDIM = y;	
		paintImage = new BufferedImage(XDIM,YDIM, BufferedImage.TYPE_INT_RGB);
		display = paintImage.createGraphics(); 
	}
	public int depth(RBNode N)  // find max depth of tree
	{
	if (N == null) return 0;
		int l = depth(N.left);
		int r = depth(N.right);
		if (l > r) return l+1; else return r+1;
	}
	private int bheight = 50; // branch height
	private int yoff = 30;  // static y-offset

	// l is level, lb,rb are the bounds (position of left and right child)
	private void drawnode(RBNode N,int l, int lb, int rb)
	{
		if (N == null) return;
		if (N.isRed)
		{
			display.setColor(Color.red);
			
		}
		else
		{
			display.setColor(Color.black);

		}
		display.fillOval(((lb + rb) / 2) - 10,yoff + (l * bheight), 30, 30);
		display.setColor(Color.BLACK);
		display.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		display.drawString(N.key+"",((lb + rb) / 2) + 25,yoff + 15 + (l * bheight));
		
		display.setColor(Color.blue); // draw branches
		if (N.left != null)
		{
		   display.drawLine((lb + rb) / 2, yoff + 10 + (l * bheight),
			((3 * lb + rb) / 4), yoff+(l * bheight + bheight));
			   drawnode(N.left, l + 1, lb, (lb + rb) / 2);
		}
		if (N.right != null)
		{
			display.drawLine((lb + rb) / 2, yoff + 10 + (l * bheight),
			((3 * rb + lb) / 4), yoff + (l * bheight + bheight));
			drawnode(N.right, l + 1,(lb + rb) / 2, rb);
		}
	}
	public void drawtree()
	{
		RBNode T = this.root;
		if (T == null) return;
		int d = depth(T);
		bheight = (YDIM / d);
		display.setColor(Color.white);
		display.fillRect(0, 0, XDIM,YDIM);  // clear background
		drawnode(T, 0, 0, XDIM);
		
	}
	public void save(String name) throws IOException
	{
		display.dispose();
		ImageIO.write(paintImage, "PNG", new File(name + ".png"));
	}
*/