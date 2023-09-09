function y=mysin(x,digits=1000)
x=reducereper(x,digits);
semn=1;
if x>3*pi/2
    #x=...; semn=...;
    x=2*pi-x; semn=-1;
elseif x>pi
    #x=...; semn=...;
    x=x-pi; semn=-1;
elseif x>pi/2
    #x=...;
    x=pi-x;
end
if x<=pi/4
    #y=...;
    y=sinred(x);
else
    #y=...;
    y=cosred(pi/2-x);
end
y=semn*y;
