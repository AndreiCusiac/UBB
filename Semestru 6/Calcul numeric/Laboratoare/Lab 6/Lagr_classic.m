function LX=Lagr_classic(x,f,X)
  ...w=coefs_bary(x,type);
  LX=X;
  m = length(x) - 1;

  for i=1:length(X)
    x_poz = find(X(i)==x);
    if isempty(x_poz)
      ...LX(i)=sum(w./(X(i)-x).*f)/sum(w./(X(i)-x));

      lk = x;
      for k=0:m
        lk(k+1) = prod(X(i)-x([1:k,k+2:end]))/prod(x(k+1)-x([1:k,k+2:end]));
      endfor

      LX(i)=sum(lk.*f);
    else
      LX(i)=f(x_poz);
    end
  endfor

