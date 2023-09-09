function [H, dH]=HermiteSpline(x,f,df,X)
  n = length(x);
  if x(n) == X
    H = f(n);
    dH = df(n);
  else
    poz=find(x>X,1)-1;
    #poz
    new_nodes = [x(poz) x(poz+1)];
    new_f = [f(poz) f(poz+1)];
    new_df = [df(poz) df(poz+1)];

    [H, dH] = Hermite(new_nodes,new_f,new_df,X)
  endif


