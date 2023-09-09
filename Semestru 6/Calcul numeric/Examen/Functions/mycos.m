function y=mycos(x)
x=reducereper(x);
semn=1;
if x>3*pi/2
    #x=...;
    x=2*pi-x;
elseif x>pi
    #x=...; semn=...;
    x=x-pi; semn=-1;
elseif x>pi/2
    #x=...; semn=...;
    x=pi-x; semn=-1;
end
if x<=pi/4
    #y=...;
    y=cosred(x);
else
    #y=...;
    y=sinred(pi/2-x);
end
y=semn*y;
