function x=rutina(f,k,lambda)
  syms x
  syms k
  syms lambda
  w=(1-x^2)^(lambda-1/2)

  w = simplify(w);
  pi2=orto_poly_sym_type('Jacobi',k)
  sols=solve(pi2,x)
  nodes=[sols']
  coefs=gauss_coefs_sym(w,a,b,nodes)

  I=eval(coefs*f(nodes)')
