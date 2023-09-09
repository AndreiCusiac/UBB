function [L,U,P]=DescLup(A)
  [m n] = size(A);
  P = zeros(m,n);
  piv = (1:m)';
  for i=1:m-1
    [pm,kp] = max(abs(A(i:m,i)));
    kp = kp + i - 1;

    if i ~= kp
      A([i,kp],:) = A([kp,i],:);
      piv([i,kp]) = piv([kp,i]);
    endif

    lin = i + 1:m;
    A(lin,i) = A(lin,i) / A(i,i);
    A(lin,lin) = A(lin,lin) - A(lin,i)*A(i,lin);

  endfor

  for i=1:m
    P(i,piv(i)) = 1;
  endfor

  U = triu(A);
  L = tril(A,-1) + eye(m);

