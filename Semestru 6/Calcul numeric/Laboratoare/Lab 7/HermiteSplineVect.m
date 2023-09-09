function [H, dH]=HermiteSplineVect(x,f,df,X)
  H = X;
  dH = X;
  n = length(x);
  for i=1:length(X)
    if X(i) == x(n)
      H(i) = f(n);
      dH(i) = df(n);
    else
      poz=find(x>X(i),1)-1;
      poz;
      [x(poz) x(poz+1)];
      new_nodes = [x(poz) x(poz+1)];
      new_f = [f(poz) f(poz+1)];
      new_df = [df(poz) df(poz+1)];

      [H(i), dH(i)] = HermiteSpline(new_nodes,new_f,new_df,X(i));
    endif
  endfor

