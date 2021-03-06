package slime.controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * 
 * <b>
 * This class handles the updating of the current time passed in the
 * song being played, which is used in the GUI.
 * </b>
 * <p>
 * I am not really worried about the clock ticking over 59 minutes and 59 seconds,
 * and as such the display and code only supports 00-59 of both minutes and seconds.
 * </p>
 * <p>
 * Note:	The songDuration argument in the Constructor and the updater method currently
 * 			are not used for anything. Its simply there for use in the future if there is
 * 			a need for it.
 * </p>
 * 
 * @author dMacGit
 */

public class SongTimeController extends JPanel implements ActionListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7152956559593217709L;
	private Timer seconds;
	private TimerTask secondsUpdating;
	private byte songMinutes, songSeconds, pausedSeconds, pausedMinutes;
	private String songTime;
	
	private int songDuration;
	
	private final short H_Size = 20, SONG_TIME_W = 38, SONG_NAME_W = 225, DEFAULT_STRING_LABEL_W = 47, DEFAULT_STRING_LABEL_H = 22;
	
	private final Timer timer = new Timer(1000, this);
	
	//The JLabel where the string is animated on.
    private final JLabel label = new JLabel();
    
	private boolean isPaused = false;
    
	public SongTimeController(int songDuration) 
	{
		this.songDuration = songDuration; 
		
		songMinutes = 0;
		songSeconds = 0;
		songTime = "00:00";
		label.setText(songTime);
		label.setPreferredSize(new Dimension(SONG_TIME_W,DEFAULT_STRING_LABEL_H));
		label.setForeground(Color.WHITE);
		
		//seconds.scheduleAtFixedRate(secondsUpdating, 100, 1000);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		//subject.stateSubjectCallback(getStateObserverName(), playerState);
		this.setBackground(Color.BLACK);
        this.add(label);
        //timer.start();
	}
	
	public void startTimer(){
		timer.start();
	}
	public void stopTimer(){
		timer.stop();
	}
	public void pauseTimer(){
		isPaused = true;
	}
	
	public void reset(){
		songMinutes = 00;
		songSeconds = 00;
		songTime = "00:00";
		label.setText(songTime);
		label.revalidate();
		this.revalidate();
	}
	
	public void newDuration(int duration)
	{
		songDuration = duration;
		reset();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(!isPaused)
        {
            if(songMinutes < 10 && songSeconds < 10)
            {
                songTime = ("0"+songMinutes+":0"+songSeconds);
                
            }
            else if(songMinutes >= 10 && songSeconds >= 10)
            {
                songTime = (songMinutes+":"+songSeconds);
            }
            else if(songMinutes < 10 && songSeconds >= 10)
            {
                songTime = ("0"+songMinutes+":"+songSeconds);
            }
           
            if(songSeconds < 59)
            {
                songSeconds++;
            }
            else
            {
                songSeconds = 0;
                songMinutes++;
            }
            
            label.setText(songTime);
            label.revalidate();
        }
		
	}
}
