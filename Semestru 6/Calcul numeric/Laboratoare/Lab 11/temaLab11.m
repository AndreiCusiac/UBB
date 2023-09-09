syms x
a=sym(0)
b=sym(Inf)
w=e^(-x)
wa = simplify((x-a)*w)
%de aici caut in tabel wa, NU w
%pi2=orto_poly_sym_type('Laguerre',2,sym(0))
pi2=orto_poly_sym_type('Laguerre',2,sym(1))
sols=solve(pi2,x)
nodes=[a sols']
coefs=gauss_coefs_sym(w,a,b,nodes)

%%%
%ia mult timp:(
restul_fara_f = 1/factorial(5)*int(pi2^2*wa,x,a,b)
I=eval(coefs*exp(nodes)')
eroare_max = eval(exp(-Inf)*restul_fara_f)
%luam csi maximul din intervalul pe care lucram
abs(I-integral(@(x) log(1+exp(-x)),0,Inf))
%%%

integrala=eval(simplify(subs(log(1+exp(-x))*exp(x),nodes)*coefs'))
int_octave=integral(function_handle(log(1+exp(-x))),0,Inf)
error=abs(integrala-int_octave)
int_octave2=quad(function_handle(log(1+exp(-x))),0,Inf)

rest_fara_f=int(pi2^2*wa,a,b)/factorial(5)

f=log(1+exp(-x))*exp(x);

df5=simplify(diff(f,x,5)-f)+f
syms y %facem subst exp(x)=1/y <=> x=-log(y); x>0 <=> 0<y<1;
df5y=subs(df5,exp(x),1/y);
fplot(function_handle(df5y),[0,1])
