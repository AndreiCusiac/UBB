function nr_cond=condpol(coefs,xi)
%numarul de cond. a aflarii rad. xi a unei ec. pol.
%coefs=coeficientii pol.
coefs_der=polyder(coefs); %coef. derivatei lui p;
...nr_cond=polyval(...,...)/abs(...*polyval(...,...));
nr_cond=polyval(abs(coefs(2:end)),abs(xi))/abs(xi*polyval(coefs_der,xi));
endfunction
