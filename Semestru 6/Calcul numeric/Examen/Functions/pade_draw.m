function T=pade_draw(f,x,m,k,a,b)
  clf; hold on; grid on;
  fh=function_handle(f);
  fplot(fh,[a,b]);
  L={'f'};
  T=pade_sym(f,m,k,x);
  Th = function_handle(T);
  fplot(Th,[a,b]);
  L{end+1}=['Pade ' num2str(m) '/' num2str(k)];
##  for i=1:n
##    T=pade_sym(f,m,k,x);
##    Th = function_handle(T);
##    fplot(Th,[a,b]);
##    L{end+1}=['T' num2str(i)];
##  endfor
  legend(L,'location','northeastoutside');
endfunction
