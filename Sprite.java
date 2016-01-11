import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.FontMetrics;

/*
 * For intros
 * 
 *the button is reinvented here rather than the swings button to detract 
 *from swings look and feel in order to create my own.
 *
 *this is turning into spaghetti code
 * */

public class Sprite extends JPanel implements Runnable {

	// for mouse positions
	int mousex, mousey;

	// an array list for images
	ArrayList<ImageObjects> im_arr = new ArrayList<ImageObjects>();

	Sprite() {

		// listeners for mouse events are built in
		this.addMouseListener(new MouseListen());
		this.addMouseMotionListener(new MouseMotion());
		setFocusable(true);
	

	}

	// adds a text image with appropriate arguments
	void addTextImage(String text, String id, Font font, Color color1,
			Color color2, int x, int y, int w, int h) {

		im_arr.add(new ImageObjects(text, id, font, color1, color2, x, y, w, h));
	}

	// adds an image with appropriate arguments
	void addImageobject(String id, String address, int x, int y, int w, int h) {

		im_arr.add(new ImageObjects(id, load_image(address), x, y, w, h));
	}

	void addanimation(String id, String address, int framex, int framey, int framew, int frameh, int x, int y, int w, int h) {

		im_arr.add(new ImageObjects(id, load_image(address), framex, framey, framew, frameh, x, y, w, h));
	}
	
	// checks if the ID of the image object has been set to true or false
	boolean button_checked(String id) {

		for (int a = 0; a <= im_arr.size(); a++) {

			if (im_arr.get(a).identity == id) {

				return im_arr.get(a).switched;
			}
		}
		return false;
	}
	
	//if the ID of the image object is being hovered over
	boolean hovered_over(String id) {

		for (int a = 0; a <= im_arr.size(); a++) {

			if (im_arr.get(a).identity == id) {

				return im_arr.get(a).hover;
			}
		}
		return false;
	}
	
	// returns the position in the image array of the ID  
		int id_position(String id) {

			int id_position = 0;
			
			for (int a = 0; a <= im_arr.size()-1; a++) {

				if (im_arr.get(a).identity == id) {

					id_position = a;
				}
			}
			return id_position;
		}

	// this function will be called in run, it will detect if the mouse is over
	// an ImageObject by looping through the array list and checking the most
	// position
	void mouseover() {

		// the first element is the background and should not be affected
		for (int a = 1; a <= im_arr.size() - 1; a++) {

			if (im_arr.get(a).posx < mousex
					&& im_arr.get(a).posx + im_arr.get(a).width > mousex
					&& im_arr.get(a).posy < mousey
					&& im_arr.get(a).posy + im_arr.get(a).height > mousey) {

				im_arr.get(a).hover = true;
			} else {
				im_arr.get(a).hover = false;
			}
		}
	}

	// The argument will initialise Image variables with an image at the path
	// otherwise they will be initialised with a null image
	Image load_image(String p) {

		Image temp = null;
		try {

			temp = ImageIO.read(new File(p));
		} catch (IOException e) {
			System.out.print("Image at" + p + "failed to load" + e.toString());
		}

		return temp;
	}

	// paint is an inbuilt function that is automatically called with repaint in
	// this case
	@Override
	public void paint(Graphics g) {
				
		// loops through the image object array and displays each image if it has a priority set to true
		for (int a = 0; a <= im_arr.size() - 1; a++) {

			if(im_arr.get(a).priority){
			g.drawImage(im_arr.get(a).animate(), im_arr.get(a).posx,
					im_arr.get(a).posy, im_arr.get(a).width,
					im_arr.get(a).height, null);
			}
		}

	}

	@Override
	public void run() {

		while (true) {

			try {

				// each time the mouse is hovering over an alement in an array
				// list or not this function sets the objects image to true or
				// false accordingly
				mouseover();
				Thread.sleep(20);
			} catch (Exception e) {
			}
			repaint();
		}
	}
	
	
	// This builds the desired image for the build image function, its
	// properties allow
	class ImageObjects {

		// the images variables
		int posx, posy, width, height,frames = 1,total;

		Font font;
		String text, identity;

		// the image can potentially know if it has been selected with switch
		// and hover will be switched to true if the mouse is over and priority for the most important image focus
		boolean switched = false, hover = false, istext = false, isanim = false, priority = true;

		Color color, color2;

		Image image, image2;
        Image[] animage;
		
		

		//animated image constructor, works for equal and ordered contact sheets
		ImageObjects(String id, Image i,int framex, int framey, int framew, int frameh, int x, int y, int w, int h ){
		    
			isanim=true;
			posx = x;
			posy = y;
			width = w;
			height = h;
			identity = id;
			
			//find a way to stretch the image to fit again
			animage = new Image[framex*framey+1];
			BufferedImage animate = new BufferedImage(framex*framew,frameh*framey,
					BufferedImage.TYPE_4BYTE_ABGR);
			animate = (BufferedImage) i;
			
			int totalframes = 0;
			
			for(int a = 0; a <= framex-2; a++){
				for(int b = 0; b <= framey; b++){			
					
				totalframes++;
			    animage[totalframes] = animate.getSubimage(framew*b, frameh*a, framew, frameh);
				}				
			}				
			total = animage.length;
		}
		
		
		// text as image constructor
		ImageObjects(String s, String id, Font f, Color c, Color nd, int x,
				int y, int w, int h) {

			font = f;
			color = c;
			color2 = nd;
			text = s;
			posx = x;
			posy = y;
			width = w;
			height = h;
			identity = id;
			istext = true;
			addtextimage();
		}

		// image constructor
		ImageObjects(String id, Image i, int x, int y, int w, int h) {

			// initialising variables
			image = i;
			posx = x;
			posy = y;
			width = w;
			height = h;
			identity = id;
		}

		// creates two images based on the text argument in constructor and will
		// display one or the other if mouse is over,ugly code but effective.
		void addtextimage() {

			
			BufferedImage imagebuffer = new BufferedImage(1500, 1500,
					BufferedImage.TYPE_4BYTE_ABGR);

			Graphics q = imagebuffer.createGraphics();
			
			q.setFont(font);
			q.setColor(color);
						
			FontMetrics font_met = q.getFontMetrics();
			Rectangle2D bounds = font_met.getStringBounds(text, q);

			int string_height = (int)bounds.getCenterX()-((int)bounds.getCenterX()/4);
			int string_width = (int) bounds.getWidth();
			
			q.drawString(text, 0, string_height);
			q.setColor(color2);
			q.drawString(text, string_width, string_height);
			image = imagebuffer.getSubimage(0, 0, string_width, string_height);
			image2 = imagebuffer.getSubimage(string_width, 0, string_width,
					string_height);
			q.dispose();
		}

		// displays images of the object--make this more elegant later
		Image animate() {

			if (istext == true) {

				if (hover == true) {

					return image2;
				} 
			} 
			if(isanim == true && switched == true){
				
				if(frames == total-1){
					frames = 1;
					}else{
						frames++;
					}
				return animage[frames];				
			}
			else if(isanim == true && switched == false){
				
				return animage[frames];
			}else{
				return image;
			}
			
		}

	}

	class MouseMotion implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {

			mousex = arg0.getX();
			mousey = arg0.getY();
		}

	}

	class MouseListen implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			for (int a = 0; a <= im_arr.size() - 1; a++) {

				if (im_arr.get(a).hover == true
						&& im_arr.get(a).switched == false) {

					im_arr.get(a).switched = true;
				} else if (im_arr.get(a).hover == true
						&& im_arr.get(a).switched == true) {

					im_arr.get(a).switched = false;
				}
			}

		}

	}

}