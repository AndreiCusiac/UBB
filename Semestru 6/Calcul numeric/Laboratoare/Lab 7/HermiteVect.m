function [H, dH]=HermiteVect(x,f,df,X)
  n = length(x);
  c = dif_div_double(x,f,df)(1,:);

  H = X;
  dH = X;
  z = repelem(x,2);

  for j=1:length(X)
    H(j) = c(1);
    dH(j) = 0;
    P = 1;
    dP = 0;
    for i=1:length(z)-1
      dP = dP * (X(j) - z(i)) + P;
      P *= X(j) - z(i);
      H(j) += c(i+1) * P;
      dH(j) += c(i+1) * dP;
    endfor
  endfor


