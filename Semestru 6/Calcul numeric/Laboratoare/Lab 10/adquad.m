function Q=adquad(f,a,b,err)
  c = (a + b)/2;
  fa = f(a);
  fb = f(b);
  fc = f(c);
  Q = quadstep(f,a,b,err,fa,fc,fb);
