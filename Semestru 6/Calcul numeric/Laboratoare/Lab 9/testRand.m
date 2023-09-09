clf; hold on;
x = 10 * rand(1,200);
f = 1 + 25 * (x.^2 + 10 * rand(1,200));
plot(x,f,'or','markerfacecolor','r','markersize',3)
[coef,err] = mcmmp(x,f,2)
p=@(X) polyval(coef,X);
fplot(p,[0,10],'-b','linewidth',2)
