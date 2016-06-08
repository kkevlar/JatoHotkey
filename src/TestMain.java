import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;


public class TestMain 
{
	public static void main(String[] args)
	{
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		KeyListener listener = new KeyListener();
		try {
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeKeyListener(listener);
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BasicTest lc = new BasicTest(new KeyInput(Key.constructKey("Left Control", listener)));
		BasicTest rc = new BasicTest(new KeyInput(Key.constructKey("Right Control", listener)));
		OrTest controls = new OrTest(lc,rc);
		BasicTest ralt = new BasicTest(new KeyInput(Key.constructKey("Left Alt", listener)));
		BasicTest lalt = new BasicTest(new KeyInput(Key.constructKey("Right Alt", listener)));
		OrTest alts = new OrTest(ralt,lalt);
		
		AndTest mods = new AndTest(controls,alts);
		
		BasicTest k = new BasicTest(new KeyInput(Key.constructKey("Escape", listener)));
		
		AndTest combined = new AndTest(mods,k);
		
		ChromeAction action = new ChromeAction();
		Event mainEvent = new Event(combined,action);
		
		
		while(true)
		{
			mainEvent.testAndDo();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

	