import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.util.Pair; 

public class mazeSolver 
{	

	Set<Pair> isVisited = new HashSet<Pair>();
	char[][] outputMaze =  null;
	int noOfCol = 0;
	int noOfRow = 0;
	int[][] inputMaze = null;
	Pair <Integer, Integer> endPair = null;
	boolean isResolvable = false;
	
	public void solveTheMaze(String path) throws IOException
	{		
		
		//Set File Reader
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		//Set width and height
		String[] columnRow = br.readLine().split(" ");
		noOfCol = Integer.parseInt(columnRow[0]);
		noOfRow = Integer.parseInt(columnRow[1]);
		
		//Set start
		String[] startXY = br.readLine().split(" ");
		int startX = Integer.parseInt(startXY[0]); //start column
		int startY = Integer.parseInt(startXY[1]); //start row
		
		//Set end
		String[] endXY = br.readLine().split(" ");
		int endX = Integer.parseInt(endXY[0]);
		int endY = Integer.parseInt(endXY[1]);
		
		//Initialize input array 
		inputMaze  = new int[noOfCol][noOfRow];
		
		//Initialize output array with '#' to prevent null elements
		outputMaze = new char[noOfCol][noOfRow];		
		for (char[] row: outputMaze)
		    Arrays.fill(row, '#');
		
		//Fill input array (input matrix to array)
		for(int i = 0; i< noOfRow; i++)
		{
			String[] currentLine = br.readLine().split(" ");
			for(int l = 0; l<noOfCol; l++)
			{
				inputMaze[l][i] = Integer.parseInt(currentLine[l]);				
			}
		}
		
		//Set the end point
		endPair = new Pair <Integer, Integer> (endX, endY);
		
		//List that holds call trace
		List traceList = new ArrayList<Pair>();
		
		//Depth-First Search algorithm is called for the solution
		dfs(startX, startY, traceList);
		
		//Set start and end points
		outputMaze[startX][startY] = 'S';
		outputMaze[endX][endY] = 'E';
		
		//Output written to the Standard Output/Console
		for(int i = 0; i< noOfRow; i++)
		{
			for(int l = 0; l<noOfCol; l++)
			{
				System.out.print(outputMaze[l][i]);
			}
			System.out.println();
		}
		
		//Alert if the maze is not resolvable
		if(!isResolvable)
			System.out.println("This maze does not have a solution.");
	}
	
	public void dfs(int column, int row, List backTraceList)
	{
		
		Pair <Integer, Integer> colRowPair = new Pair <Integer, Integer> (column, row);
		
		//Copy the call trace and set it into a new array to add the new trace value
		List<Pair> currTraceList = new ArrayList<>(backTraceList);
		
		//if given points are valid and if it is not visited yet check
		if (row < 0 || row > noOfRow || column < 0 || column > noOfCol || isVisited.contains(colRowPair))
		{
			return;
		}
		
		//fill is visited for the current point
		isVisited.add(colRowPair);
		
		//set # for 1s
		if (inputMaze[column][row] == 1)
		{
			outputMaze[column][row] = '#';
		}
		
		//set ' ' for 0s and call search recursively
		if (inputMaze[column][row] == 0)
		{
			outputMaze[column][row] = ' ';
			currTraceList.add(colRowPair);
			dfs (column-1, row, currTraceList);
			dfs (column+1, row, currTraceList);
			dfs (column, row-1, currTraceList);
			dfs (column, row+1, currTraceList);
		}
		
		//end point check
		if (colRowPair.equals(endPair) )
		{
			isResolvable = true;
			for (int i = 0; i<currTraceList.size(); i++)
			{
				Pair XYpair = currTraceList.get(i);
				int x = (int) XYpair.getKey();
				int y = (int) XYpair.getValue();
				outputMaze[x][y] = 'X';
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException 
	{
		mazeSolver ms = new mazeSolver();
		//provide path to the text sample from CMD as a first argument in the form: '/Users/Home/maze_for_candidates/input.txt'
		String Fpath = args[0];
		ms.solveTheMaze(Fpath);
	}

}
