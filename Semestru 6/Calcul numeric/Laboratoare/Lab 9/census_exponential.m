function y=census_exponential(x,f,Xs)
  y=Xs;
  n=length(Xs);
  log_f = log(f);
  [coef,err] = mcmmp(x,log_f,1);
  #coef
  k = e^coef(2);
  lambda = coef(1);
  #lambda * x(n) + k
  p=@(X) k * e^(lambda * X);
  for i=1:n
    y(i) = p(Xs(i));
  endfor

#census
#census_polinomial(ani,pop,3,[1975 2010])
