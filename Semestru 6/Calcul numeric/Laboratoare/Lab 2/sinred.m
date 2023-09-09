function ssin=sinred(x)
 ssin=0;
 u=x;
 n=0;
 p=1;
 while abs(u)
   #disp(u)
   ssin=ssin+u;
   n++;
   #u=u*...
   ...t=u;
   ...t=t*x*x*(-1);
   u=u*x*x*(-1);
   ...p=1/((n+n)*(n+n+1));
   ...p=p*(n+n)*(n+n+1);
   p=(n+n)*(n+n+1);
   u=u*(1/p);
 end
