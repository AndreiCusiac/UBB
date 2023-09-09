#
clf;
hold on;
f = @(x) tan(x) + tanh(x);
ft = @(x) tan(x);
fh = @(x) -tanh(x);
#diff(f, x);
fplot(ft,[0,15,-8,8])
fplot(fh,[0,15,-8,8])
y1 = @(x) x*pi
fplot(y1,[0,15,-8,8])

for i=1:4
  y1 = @(x) x*pi = 0;
  y2 = @(x) (x-1/2)*pi = 0;
endfor


