%test

syms x
a=-sym(1)
b=sym(1)
w=sqrt(1+x)
wb = simplify((b-x)*w)
%de aici caut in tabel wb, NU w
%pi2=orto_poly_sym_type('Laguerre',2,sym(0))
pi2=orto_poly_sym_type('Jacobi',2,sym(1),sym(1)/2)
sols=solve(pi2,x)
nodes=[sols' b]
#nodes=[simplify(solve(pi2==0))' b]
coefs=gauss_coefs_sym(w,a,b,nodes)

rest_fara_f=int(pi2^2*wb,a,b)/factorial(5)

#cos(x)

I=eval(coefs*cos(nodes)')
eroare_max = eval(cos(1)*restul_fara_f)
%luam csi maximul din intervalul pe care lucram
abs(I-integral(@(x) cos(x)*sqrt(1+x),-1,1))


