package appeng.api.me.tiles;

import java.util.List;

/*
 * Somes ME Tiles can be configured, generally via interface, but these let you do this programatically.
 */
public interface IConfigureableTile {
	
	// switch config to next option... ( returns new configuration. )
	String nextConfiguration(String name); 
	
	// get list off all configurations...
	List<String> getConfigurations();
	
	// get options for a particular configuration.
	List<String> getConfiguationOptions( String name );
	
	// set a configuration to a specific option... ( returns the old one. 
	String setConfiguration(String name, String value);
	
	// the the current value for a configuration.
	String getConfiguration(String name);


}
