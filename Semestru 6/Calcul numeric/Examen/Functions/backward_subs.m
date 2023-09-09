function x=backward_subs(A,b)
  x=b;
  ...x=zeros(length(b),1);
  n=length(b);
  for i=n:-1:1
    x(i)=(b(i)-A(i,i+1:n)*x(i+1:n))/A(i,i);
  endfor

