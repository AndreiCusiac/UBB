function x=forward_subs(A,b)
  x=b;
  ...x=zeros(length(b),1);
  n=length(b);
  for i=1:n
    x(i)=(b(i)-A(i,1:i-1)*x(1:i-1))/A(i,i);
  endfor

