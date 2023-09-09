function arie=rep_Simpson(f,a,b,n)
  h = (b-a)/n;
  x = a+h:h:b-h;
  x_mij = a+h/2:h:b-h/2;
  arie = h/6 * (f(a) + 2*sum(f(x)) + 4 * sum(f(x_mij)) + f(b));

% integrala de la 0 la 1 din 4 / (1+x^2) dx
% vezi formula resturilor
