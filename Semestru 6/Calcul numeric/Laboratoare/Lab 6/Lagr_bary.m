function LX=Lagr_bary(x,f,X,type='none')
  w=coefs_bary(x,type);
  LX=X;
  for i=1:length(X)
    x_poz = find(X(i)==x);
    if isempty(x_poz)
      LX(i)=sum(w./(X(i)-x).*f)/sum(w./(X(i)-x));
    else
      LX(i)=f(x_poz);
    end
  endfor

