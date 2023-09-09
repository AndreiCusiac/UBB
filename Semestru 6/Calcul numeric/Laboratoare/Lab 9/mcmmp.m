function [coef,err]=mcmmp(x,f,k)
  A = vander(x,k+1);
  [coef,err] = linsys2(A,f');
