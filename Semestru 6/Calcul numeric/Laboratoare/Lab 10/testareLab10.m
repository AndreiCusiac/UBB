f=@(X) (e.^X)./sin(X); % e^x / sin(x)
a = 1;
b = 3;
err = 10^-11;
MyAdaptiveQuad = adquad(f,a,b,err)
OctaveQuad = quad(f,a,b)
OctaveIntegral = integral(f,a,b)
Difference = abs(OctaveIntegral-MyAdaptiveQuad)

syms k
syms lambda
zet = @(x) (k*(k+2*x)) / (4 * (k + x - 1/2 ) * (k + x + 1/2) )
zet(lambda-1/2)
