function prob_estim_A = rosie(N = 5000)
  %A: cel putin o bila extrasa e rosie
  
  count_A = 0;
  
  for i = 1:N  
    extragere_de_3 = randsample('rrrrraaavv', 3, replacement = false)
    extragere_de_3(1)
  
    if extragere_de_3(1)=='r' || extragere_de_3(2)=='r' || extragere_de_3(3)=='r'
      count_A++;
    endif
    
    
  endfor

  
  prob_estim_A = count_A / N;