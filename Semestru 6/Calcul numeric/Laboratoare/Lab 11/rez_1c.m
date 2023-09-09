function rutina(f,t,lambda)
  syms x
  syms t
  syms lambda
  w=(1-t^2)^(lambda-1/2)
  %de aici caut in tabel wab, NU w
  w = simplify(w);
  pi2=orto_poly_sym_type('Jacobi',k)
  sols=solve(pi2,x)
  nodes=[a sols' b]
  coefs=gauss_coefs_sym(w,a,b,nodes)
  %ia mult timp:(
  restul_fara_f = 1/factorial(6)*int(pi2^2*wab,x,a,b)
  I=eval(coefs*exp(nodes)')
  eroare_max = eval(exp(1)*restul_fara_f)
  %luam csi maximul din intervalul pe care lucram
  abs(I-integral(@(x) exp(x)./sqrt(1-x.^2),-1,1))
