#include <iostream>
#include <vector>
#include <string>
#include <cmath>

using namespace std;

int lol(int cat[], int parent[], bool isLastCat, int index)
{
  if(index <= 1)
    return 1;

  if(cat[index] && isLastCat)
    return 0;

  return lol(cat, parent, cat[index], parent[index]);
}

int main()
{
  int nodes, maxCats;
  cin >> nodes >> maxCats;

  int cat[nodes];
  for(int i = 0; i < nodes; i++)
    cin >> cat[i];

  int parent[nodes+1] = {0};
  bool isLeaf[nodes+1] = {true};
  isLeaf[0] = false;
  for(int i = 0; i < nodes-1; i++)
  {
    int paren, child;
    cin >> paren >> child;
    parent[child] = paren;
    isLeaf[paren] = false;
  }

  int count = 0;
  for(int i = nodes ; i >= 0; i--)
    if(isLeaf[i])
     count += lol(cat, parent, false, i);

  cout << count << endl;
  return 0;
}
