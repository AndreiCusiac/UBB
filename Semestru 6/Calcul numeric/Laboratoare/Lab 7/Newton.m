function L=Newton(x,f,X)
  n = length(x);
  c = dif_div(x,f)(1,:);
  L = c(1);
  P = 1;
  for i=1:n-1
    P *= X - x(i);
    L += c(i+1) * P;
  endfor

