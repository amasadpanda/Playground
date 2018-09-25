#include <iostream>

#define SPACE 0
#define WALL 1
#define EXIT 2
#define CHAR 3

#define X 20
#define Y 20

#define getrandom(min, max) \
    ((rand()%(int)(((max) + 1)-(min)))+ (min))

int nextX(int i, int x)
{
  switch(i)
  {
    case 0:
      return i;
    case 1:
      if((i+1) >= X)
        break;
      return i+1;
    case 2:
      return i;
    case 3:
      if((i-1) < 0)
        break;
      return i-1;
  }
  return -1;
}

int nextY(int i, int y)
{
  switch(i)
  {
    case 0:
      if((y-1) < 0)
        break;
      return y-1;
    case 1:
      return y;
    case 2:
      if((y+1) >= Y)
        break;
      return y+1;
    case 3:
      return y;
  }
  return -1;
}

void printMaze(int** maze)
{
  char map[4];
  map[0] = ' ';
  map[1] = '#';
  map[2] = 'E';
  map[3] = 'O';

  for(int i = 0; i < X; i++)
  {
    for(int j = 0; j < Y; j++)
    {
      std::cout << map[maze[i][j]];
    }
    std::cout << std::endl;
  }
}

int backtrack(int** maze, int y, int x)
{
  if(x == -1 || y == -1 || maze[y][x] != SPACE)
    return 0;

  if(maze[y][x] == EXIT)
    return 1;

  for(int i = 0; i < 4; i++)
  {
    if(backtrack(maze, nextY(i, y), nextX(i, x)) == 1);
      return 1;
  }
  return 0;
}

int main()
{
  srand((int)time(0));

  int** maze;
  maze = new int*[X];
  for(int i = 0; i < X; i++)
  {
    maze[i] = new int[Y];
    for(int j = 0; j < Y; j++)
    {
      maze[i][j] = getrandom(0,1);
    }
  }

  maze[getrandom(0,X)][getrandom(0,Y)] = EXIT;

  int startX, startY;
  do{
    startX = getrandom(0,X);
    startY = getrandom(0,Y);
  }while(maze[startY][startX] == EXIT);
  maze[startY][startX] = CHAR;

  std::cout << "Starting maze..." << std::endl;
  printMaze(maze);
  std::cout << (backtrack(maze, startY, startX) > 0)?"Found an exit!\n":"Trapped...forever\n";
}
