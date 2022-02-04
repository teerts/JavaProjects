import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {    
    // Variable Declarations
    private final int topPositionOfMatrix = 0; // Aware of the initial starting position and not apart of matrix
    private final int offSetForBottomPositionOfMatrix = 1; // This position is not apart of the matrix but a endpoint.
    private final int firstPositionOfMatrix = 1;
    private final int startAndEndPositionCount = 2;
    private final int positionOffsetByOne = 1;   
    private int bottomPositionOfMatrix; // Unaware of the final point and not apart of the matrix  
    private int sizeOfNbyNGrid; // The size of the grid (if n=3, 3x3 grid!)
    private int openedSites = 0; // The number of sites that have been opened in our grid
    private boolean[][] openedSite; // A boolean array to keep track of which site has been opened
    private WeightedQuickUnionUF weightedQuickUnionUF; // Presents methods to be used from Algs4 library package    

    // Constructor: creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {          
        if (n <= 0)
            throw new IllegalArgumentException();
        
        sizeOfNbyNGrid = n; 

        // Intention: want to have all our sites that are possibly connected to point to this position which 
        // means we need to take the size of the matrix 
        bottomPositionOfMatrix = sizeOfNbyNGrid * sizeOfNbyNGrid + offSetForBottomPositionOfMatrix;

        // Instantiate the library helper class
        // Add +2 since the top position and bottom position of the matrix of opened sites are not apart of the matrix.
        // They are just a starting and endpoint where the top of matrix row entries should point to the Start while the 
        // bottom of matrix row entries should point to the End.
        weightedQuickUnionUF = new WeightedQuickUnionUF(sizeOfNbyNGrid * sizeOfNbyNGrid + startAndEndPositionCount);

        // Instantiate array that holds True if the site was opened
        openedSite = new boolean[sizeOfNbyNGrid][sizeOfNbyNGrid];      
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {   
        isMatrixBoundryExceeded(row, col);     

        int currentMatrixPosition = 0;            

        // want to set the initialize openedSite matrix to row col 
        openedSite[row - offSetForBottomPositionOfMatrix][col - offSetForBottomPositionOfMatrix] = true;               

        // increment opened counter for UI tracker
        openedSites++;

        // base case: if row = 1 
        currentMatrixPosition = getMatrixPosition(row, col);
        if (row == firstPositionOfMatrix)
            weightedQuickUnionUF.union(currentMatrixPosition, topPositionOfMatrix);

        // base case: if row is equal to the size of the grid then we are at the last row of our matrix; connect the bottom to this position
        if (row == sizeOfNbyNGrid)
            weightedQuickUnionUF.union(currentMatrixPosition, bottomPositionOfMatrix);

        // If a middle site then check every side if there is an opened site. Remember we opened the site above already!
        CheckTopPositionFromCurrent(row, col);             
        CheckBottomPositionFromCurrent(row, col);
        CheckLeftPositionFromCurrent(row, col);
        CheckRightPositionFromCurrent(row, col);         
    }

    // Check if the row is not the top most row (discluding the Start point) 
    // and then check if the site on top has not been connected to this site.
    // tl;dr Check the site to the top of our current matrix position. If not connected lets connect them!
    private void CheckTopPositionFromCurrent(int row, int col)
    {
        if (row > firstPositionOfMatrix && isOpen(row - positionOffsetByOne, col))                     
        weightedQuickUnionUF.union(getMatrixPosition(row, col), getMatrixPosition(row-1, col));  
    }

    private void CheckBottomPositionFromCurrent(int row, int col)
    {
        if (row < sizeOfNbyNGrid && isOpen(row + positionOffsetByOne, col))
            weightedQuickUnionUF.union(getMatrixPosition(row, col), getMatrixPosition(row+1, col));
    }

    private void CheckLeftPositionFromCurrent(int row, int col)
    {
        if (col > positionOffsetByOne && isOpen(row, col - positionOffsetByOne))
        weightedQuickUnionUF.union(getMatrixPosition(row, col), getMatrixPosition(row, col-1));
    }

    private void CheckRightPositionFromCurrent(int row, int col)
    {
        if (col < sizeOfNbyNGrid && isOpen(row, col + positionOffsetByOne))
        weightedQuickUnionUF.union(getMatrixPosition(row, col), getMatrixPosition(row, col+1));     }

    private int getMatrixPosition(int row, int col)
    {
        return ((row-offSetForBottomPositionOfMatrix) * sizeOfNbyNGrid) + col; 
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {        
        return openedSite[row-offSetForBottomPositionOfMatrix][col-offSetForBottomPositionOfMatrix];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {   
        if ((row > 0 && row <= sizeOfNbyNGrid) && (col > 0 && col <= sizeOfNbyNGrid))
        {
            int currentMatrixPosition = getMatrixPosition(row, col);

            return weightedQuickUnionUF.find(topPositionOfMatrix) == weightedQuickUnionUF.find(currentMatrixPosition);                        
        } 
        else 
            throw new IllegalArgumentException();               
    }

    // Add check exception code if matrix boundaries for row or column are exceeded are below 
    // if it does throw new IllegalArgumentException()
    private void isMatrixBoundryExceeded(int row, int col)
    {    
        if (row <= 0 || row > sizeOfNbyNGrid || col <= 0 || col > sizeOfNbyNGrid)
            throw new IllegalArgumentException();
    }   

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openedSites;
    }

    // does the system percolate? 
    public boolean percolates()
    {
        return weightedQuickUnionUF.find(topPositionOfMatrix) == weightedQuickUnionUF.find(bottomPositionOfMatrix);
    }    
}
