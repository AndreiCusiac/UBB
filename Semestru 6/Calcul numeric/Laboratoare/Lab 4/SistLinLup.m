function x=SistLinLup(A,b)
  [L,U,P] = DescLup(A);
  y = forward_subs(L,P*b);
  x = backward_subs(U,y);

