function y=census_polinomial(x,f,k,Xs)
  y=Xs;
  n=length(Xs);
  [coef,err] = mcmmp(x,f,k);
  #coef
  #coef(1)
  p=@(X) polyval(coef,X);
  for i=1:n
    y(i) = p(Xs(i));
  endfor

#census
#census_polinomial(ani,pop,3,[1975 2010])
