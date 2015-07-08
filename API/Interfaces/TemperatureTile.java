package Reika.RotaryCraft.API.Interfaces;

/** All TileEntities with a temperature implement this, possibly indirectly. */
public interface TemperatureTile extends BasicMachine {

	/** For fetching the temperature for logic or display */
	public int getTemperature();

	/** For overheating tests. Not all tiles actually fail on overheat; some just clamp the temperature. */
	public int getMaxTemperature();

}