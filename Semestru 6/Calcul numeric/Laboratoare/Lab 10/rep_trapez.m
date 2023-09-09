function arie=rep_trapez(f,a,b,n)
  h = (b-a)/n;
  x = a+h:h:b-h;
  arie = h/2* (f(a) + 2*sum(f(x)) + f(b));

% integrala de la 0 la 1 din 4 / (1+x^2) dx
