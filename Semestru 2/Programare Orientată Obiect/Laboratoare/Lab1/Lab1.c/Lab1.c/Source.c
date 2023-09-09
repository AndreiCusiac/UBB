#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>


int main()
{

    int n, s = 0, nr, i;
    printf("Dati un numar n: ");
    scanf("%d", &n);

    printf("Dati n numere: \n");
    for (i = 0; i < n; i++)
    {
        scanf("%d", &nr);
        s = s + nr;
    }

    printf("Suma numerelor este: %d", s);

    return 0;
}