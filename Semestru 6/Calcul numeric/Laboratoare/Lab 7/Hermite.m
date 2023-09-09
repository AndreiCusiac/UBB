function [H, dH]=Hermite(x,f,df,X)
  n = length(x);
  c = dif_div_double(x,f,df)(1,:);
  H = c(1);
  dH = 0;
  P = 1;
  dP = 0;
  z = repelem(x,2);
  for i=1:length(z)-1
    dP = dP * (X - z(i)) + P;
    P *= X - z(i);
    H += c(i+1) * P;
    dH += c(i+1) * dP;
  endfor

