# SudokuSolver
See how the program solves the given Sudoku through the GUI for the given algorithm.  
The main purpose of the program is to use Design Patterns in the code.  

The user can input the Sudoku to be solved in a file with the first line giving the number of rows in the Sudoku and the second line containing the characters involved.  

Example:  

16  
1 2 3 4 5 6 7 8 9 A B C D E F G  
7 1 - - A - E C - 3 2 - 6 - - 8  
6 - 2 - - - - - - - E - D C - 9  
E 8 - B - 3 - - 1 - G - - 4 5 -  
- 3 F - B - - - - 6 D - - - A -  
- E - - F C B - - 9 - D - 3 - -  
1 - 3 - 4 - 8 - A - - - - 9 - -  
- 7 C - - G - - - B F - A - - -  
- - - - - - 5 - 2 4 8 - C D 6 -  
- - - - - - C - 6 8 9 - 1 2 G -  
- G 8 - - 6 - - - 5 7 - 3 - - -  
C - E - 3 - A - B - - - - 7 - -  
- 4 - - 5 8 F - - G - C - 6 - -  
- C 9 - G - - - - F A - - - 2 -  
B A - E - 2 - - G - 5 - - 1 9 -  
4 - 6 - - - - - - - B - 8 E - G  
5 D - - 1 - 3 8 - E 4 - 7 - - B  


Choice of algorithms:  
- DFS  
- Forward Checking (FC)  
- AC3  