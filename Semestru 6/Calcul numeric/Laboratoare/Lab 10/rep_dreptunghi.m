function arie=rep_dreptunghi(f,a,b,n)
  h = (b-a)/n;
  x_mij = a+h/2:h:b-h/2;
  arie = h*sum(f(x_mij));

% integrala de la 0 la 1 din 4 / (1+x^2) dx
