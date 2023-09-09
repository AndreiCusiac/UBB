function ccos=cosred(x)
 ccos=0;
 u=1;
 n=0;
 p=1;
 while abs(u)
   ccos=ccos+u;
   n++;
   #u=u*...
   u=u*x*x*(-1);
   p=1*(n+n-1)*(n+n);
   u=u*(1/p);
 end
 #display(n)
