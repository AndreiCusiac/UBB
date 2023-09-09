function [x,ni,rho]=model_sys_lin(A,b,met='Jacobi',omega,nr_max_it=1e+4,err=1e-14,p=Inf)
  %omega este omis pentru Jacobi si Gauss-Seidel
  #M=...;N=...;T=...;c=...;rho=...; %raza spectrala
  #M=diag(diag(A));
  #M=tril(A);

  switch met
    case 'Jacobi'
      M=diag(diag(A));                    %Jacobi
    case 'Gauss-Seidel'
      M=tril(A);                          %Gauss-Seidel
    case 'SOR'
      M=1/omega*diag(diag(A))+tril(A,-1); %SOR
    otherwise
      M=diag(diag(A));                    %Jacobi
  endswitch

  N=M-A;
  T=M\N;
  c=M\b;
  rho=max(abs(eig(T))); %raza spectrala

  if norm(T,p)>=1
    disp('Abort!')
  endif
  factor=norm(T,p)/(1-norm(T,p));
  x_old=zeros(size(b));
  ni=1;
  x=x_old;
  diff = x - x_old;
  while ni<nr_max_it
    x = c + T * x_old;
    diff = x - x_old;
    if factor * norm(diff,p) <= err
      return;
    else
      x_old = x;
      ++ni;
    endif
  endwhile
  endfunction
