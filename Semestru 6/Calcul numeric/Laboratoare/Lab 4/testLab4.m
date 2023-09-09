#Testare lab 4
n=10;
A = spdiags ([ones(n,1) 3*ones(n,1) ones(n,1)], [-1 0 1], n, n);
full(A)
b = A * ones(10,1);
x_LUP = SistLinLup(A,b);
x_CHL = SistLinCholesky(A,b);
[x_LUP x_CHL]
