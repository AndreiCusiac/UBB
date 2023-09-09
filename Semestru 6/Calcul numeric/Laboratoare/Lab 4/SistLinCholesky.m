function x=SistLinCholesky(A,b)
  R = FactCholesky(A);
  B = triu(R);
  B = B';
  y = forward_subs(B,b);
  B = B';
  x = backward_subs(B,y);

