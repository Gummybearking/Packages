package packages.me.max.java;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class DataManager {
			//S_L
			public static String LocationToString(Location l) {
				return l.getWorld() +","+  l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
			}
			public static Location StringToLocation(String s) {
				String split[] = s.split(",");
				if(split.length != 4) return null;
				World w = Bukkit.getWorld(split[0]);
				Block b = w.getBlockAt(toInt(split[1]),toInt(split[2]),toInt(split[3]));
				return b.getLocation();
			}
			//INT
			public static int toInt(String v) {
				return Integer.valueOf(v);
			}
			public int toInt(float v) {
				return Math.round(v);
			}
			public int toInt(double v) {
				return Math.round(Math.round(v));
			}
			public int toInt(long v) {
				return Math.round(v);
			}
			//STRING
			public String toString(int v) {
				return String.valueOf(v);
			}
			public String toString(long v) {
				return String.valueOf(v);
			}
			public String toString(double v) {
				return String.valueOf(v);
			}
			public String toString(float v) {
				return String.valueOf(v);
			}
}
