#include <stdio.h>

int determina_minim(int sir[],int n);
int main(){
    FILE *fisier;
    int sir[101],n,i;
    scanf("%d",&n);
    for (i=1;i<=n;i++)
        scanf("%d",&sir[i]);
    int min=determina_minim(sir,n);
    //printf("min=%x",min);
    fisier=fopen("min.txt","w");
    fprintf(fisier,"Minimul este %x",min);
return 0;
}
