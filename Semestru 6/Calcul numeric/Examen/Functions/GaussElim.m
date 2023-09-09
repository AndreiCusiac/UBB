function x=GaussElim(A,b)
  A=[A b];
  n=length(b);
  for k=1:n-1
    [valmax,pozmax] = max(abs(A(k:end,k)));
    pozpivot = pozmax+k-1;
    if valmax != 0 && pozpivot != k
      A([k,pozpivot],k:end) = A([pozpivot,k],k:end);
    elseif valmax ==0
      disp('NU are solutie unica!');
      return;

    endif

    for i=k+1:n
      A(i,k:end) = A(i,k:end)-A(k,k:end)*A(i,k)/A(k,k);
    endfor

  endfor

  x = backward_subs(A(:,1:n),A(:,n+1));
