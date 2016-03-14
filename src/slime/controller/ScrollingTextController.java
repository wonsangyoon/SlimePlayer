package slime.controller;

import javax.swing.*;

import slime.media.SongTag;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class ScrollingTextController extends JLabel 
{
	private String text;
	private Point current_pos, defaultPoint = new Point(0, 12);
	private Timer timer;
	private int componentWidth;
	private BufferedImage image = null;
	//private BufferedImage copyImage = null;
	private volatile boolean pauseScrolling = false;
	private Font font;
	private BufferedImage defaultPosImage;
	private SongTag currentScrollingTag;
	private boolean pauseAnimationReady = false;

	public ScrollingTextController(String text, int startX, int componentWidth) 
	{
		super(text);
		this.startAnimation();
		this.componentWidth = componentWidth;
		this.text = text;
		this.current_pos = new Point(0, 12);
		BufferedImage tp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		font = tp.getGraphics().getFont();
		defaultPosImage = new BufferedImage(225, 15, BufferedImage.TYPE_INT_RGB);
		defaultPosImage.getGraphics().setFont(font);
		defaultPosImage.getGraphics().setColor(Color.BLACK);
		defaultPosImage.getGraphics().drawString(text, current_pos.x,current_pos.y);
		setDoubleBuffered(true);

	}
	public ScrollingTextController(SongTag song,int componentWidth) 
	{
		super(craetedPrintedSongString(song));
		this.text = craetedPrintedSongString(song);
		this.startAnimation();
		this.componentWidth = componentWidth;
		this.current_pos = new Point(0, 12);
		BufferedImage tp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		font = tp.getGraphics().getFont();
		defaultPosImage = new BufferedImage(225, 15, BufferedImage.TYPE_INT_RGB);
		defaultPosImage.getGraphics().setFont(font);
		defaultPosImage.getGraphics().setColor(Color.BLACK);
		defaultPosImage.getGraphics().drawString(text, current_pos.x,current_pos.y);
		setDoubleBuffered(true);

	}
	
	private static String craetedPrintedSongString(SongTag song)
	{
		String Album = song.getRecordingTitle();
        if(Album.compareTo("") == 0)
        {
            Album = song.getSongTitle();
        }
        return new String(song.getArtist()+"   :   "+song.getSongTitle()+"     "+Album+"     "+song.getYear());
	    
	}

	class Handler implements ActionListener 
	{
		public void actionPerformed(ActionEvent evt) 
		{
			if (!pauseScrolling) 
			{
				pauseAnimationReady = false;
				current_pos = new Point(current_pos.x - 1, current_pos.y);
				image = new BufferedImage(225, 15, BufferedImage.TYPE_INT_RGB);
				image.getGraphics().setFont(font);
				image.getGraphics().setColor(Color.BLACK);
				image.getGraphics().drawString(text, current_pos.x,	current_pos.y);
				setIcon(new ImageIcon(image));
				setImage(image);
				if (current_pos.x > -text.length() * 6)
				{
					current_pos.setLocation(current_pos.x - 1, current_pos.y);
				}
				else 
				{
					current_pos.setLocation(componentWidth, 12);
				}
			} 
			else 
			{
				pauseAnimationReady = false;
				current_pos = defaultPoint;
				setIcon(new ImageIcon(defaultPosImage));
				setImage(defaultPosImage);
				image = defaultPosImage;
				pauseAnimationReady = true;
			}
		}
	}
	
	public void changeTag(SongTag tag)
	{
		pauseAnimation();
		this.text = craetedPrintedSongString(tag);
		BufferedImage tp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		font = tp.getGraphics().getFont();
		defaultPosImage = new BufferedImage(225, 15, BufferedImage.TYPE_INT_RGB);
		defaultPosImage.getGraphics().setFont(font);
		defaultPosImage.getGraphics().setColor(Color.BLACK);
		defaultPosImage.getGraphics().drawString(text, current_pos.x,current_pos.y);
		startAnimation();
	}
	
	public boolean getPauseAnimationReady(){
		return this.pauseAnimationReady;
	}
	
	public void setImage(BufferedImage theLabel) 
	{
		image = theLabel;
	}

	public boolean checkImage() 
	{
		if (image == null) 
		{
			return false;
		}
		return true;
	}

	public BufferedImage getImage() 
	{
		return image;
	}

	public void startAnimation() 
	{
		if (!pauseScrolling)
		{
			
			this.timer = new Timer(20, new Handler());
			this.timer.start();
		}
		else
		{
			pauseScrolling = false;
		}
	}
	
	public void resetAnimation(){
		setText("");
		this.current_pos = this.defaultPoint;
	}
	public void resetTimer(){
		this.timer.stop();
	}

	public boolean pauseAnimation()
	{
		pauseScrolling = true;
		return true;
	}
	public void stopAnimation(){
		this.timer.stop();
		setText("");
	}
}