function w=coefs_bary(x, type='none')
  m = length(x) - 1;
  w = x;
  switch type
    case 'none'
      for j=0:m
        w(j+1) = 1/prod(x(j+1) - x([1:j,j+2:end]));
      endfor

    case 'echidistante'
      for j=0:m
        w(j+1) = (-1)^j * nchoosek(m,j);
      endfor

    case 'Cebisev1'
      j=0:m;
      w=(-1).^j.*sin((2*j+1)*pi/(2*m+2));

    case 'Cebisev2'
      j=0:m;
      w=(-1).^j;
      w(1) = w(1)/2;
      w(end) = w(end)/2;
  endswitch
