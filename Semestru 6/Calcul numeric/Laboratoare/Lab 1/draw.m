function T=draw(f,x,m,k,a,b,n)
  clf; hold on; grid on;
  P = pade_sym(f,m,k,x);
  disp('Pade 1/1:'),disp(P);
  Z = taylor(f,x,0,'order',n+1);
  disp('Taylor:'),disp(Z);
  fh=function_handle(f);
  fplot(fh,[a,b]);
  L={'f'};
  T=pade_sym(f,m,k,x);
  Th = function_handle(T);
  fplot(Th,[a,b]);
  L{end+1}=['Pade ' num2str(m) '/' num2str(k)];
  for i=1:n
    T=taylor(f,x,0,'order',i+1);
    Th = function_handle(T);
    fplot(Th,[a,b]);
    L{end+1}=['T' num2str(i)];
  endfor
  legend(L,'location','northeastoutside');
endfunction

## sqrt((1+x/2)/(1+2*x))
## draw(sqrt((1+x/2)/(1+2*x)),x,1,1,-0.4,1.4,2)
