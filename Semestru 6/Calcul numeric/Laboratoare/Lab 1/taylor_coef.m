function c=taylor_coef(f,x,n,a=0)
  if n<0
    c=sym(0);
    return;
  else
    ...c=...;
    der = diff(f,x,n)/factorial(n);
    derH = function_handle(der);
    c=derH(a);
  end
 endfunction
