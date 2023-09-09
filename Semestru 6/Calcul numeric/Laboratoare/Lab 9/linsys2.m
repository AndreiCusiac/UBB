function [c,err]=linsys2(A,f)
  [Q,R] = qr(A);
  [n,m] = size(A);
  b = Q' * f;
  c = R(1:m,:)\b(1:m);
  err = norm(b(m+1:n));
