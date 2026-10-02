package game;

import java.awt.*;
import java.awt.Toolkit;
import java.io.*;

public class TileMap
{
	private Image[]  tile;
	
    private String[] map;
    
	
	private int w;
	private int h;
	
	int z;

		
	public TileMap(String filename, int z)
	{
		loadMap(filename);
		
		this.z = z;
	}
	
	public void loadMap(String filename)
	{
		File file = new File(filename);
		
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(file));
			
			int rows = Integer.parseInt(input.readLine());
			
			map = new String[rows];
			
			for(int row = 0; row < map.length; row++)
			{
				map[row] = input.readLine();
			}
			
			w = Integer.parseInt(input.readLine());			
			h = Integer.parseInt(input.readLine());	
			
			int n = Integer.parseInt(input.readLine());
			
			tile = new Image[n];
			
			loadTiles();			

			input.close();
		}
		catch(IOException x) {};
		
	}

	private void loadTiles()
	{
		for(int i = 0; i < tile.length; i++)
		{
			tile[i] = Toolkit.getDefaultToolkit().getImage("winter" + (i+1) + ".png");
		}
	}
	
	
	
	public void draw(Graphics g)
	{
		Image image = null;
		
		
		for(int row = 0; row < map.length; row++)
		{
			for(int col = 0; col < map[row].length(); col++)
			{
				char c = map[row].charAt(col);
				
				if(c != '.')  
				{
					image = tile[c - 65];
					
					g.drawImage(image, col * w - Camera.x/z, row * h - Camera.y/z, w, h, null);
				}
			}
		}
	}

}