#include <iostream>
#include <fstream>

using namespace std;

ifstream f("date.txt");

void BFS (int i, int n, int a[100][100], int v[100], int d[100], int c[100])
{
    int k, j;
    
    for (k=1;k<=n;k++)
    {
        if (a[i][k] == 1 && v[k] == 0 && i != k)
        {
            v[k] = 1;
            
            j = 1;
            while (c[j] != 0)
            {
                j++;
            }
            c[j] = k;
            
            d[k] = d[i]+1;
        }
    }
}

int main()
{
    int a[100][100], n, i, j, sursa, c[100]={0}, v[100]={0}, d[100]={0};
    
    f>>n;
    
    for (i=1;i<=n;i++)
    {
        for (j=1;j<=n;j++)
        {
            f>>a[i][j];
        }
    }
    
    cout<<"Matricea citita este: "<<endl;
    
    for (i=1;i<=n;i++)
    {
        for (j=1;j<=n;j++)
        {
            cout<<a[i][j]<<" ";
        }
        cout<<endl;
    }
    
    cout<<endl<<"Dati nodul sursa: ";
    cin>>sursa;
    
    c[1] = sursa;
    d[sursa] = 0;
    v[sursa] = 1;
    
    i = 1;

    while (c[i]!=0)
    {
        BFS(c[i], n, a, v, d, c);
        i++;
    }
    
    for (i=1;i<=n;i++)
    {
        if (v[i] == 0)
        {
            d[i] = -999;
        }
    }
    
    cout<<endl<<"Nodurile vizitate au fost: "<<endl;
    
    i = 1;
    
    while (c[i] != 0)
    {
        cout<<c[i]<<", la distanta "<<d[c[i]]<<endl;
        i++;
    }
    
    return 0;
}

