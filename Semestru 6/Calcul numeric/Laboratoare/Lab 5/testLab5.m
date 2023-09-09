#Testare lab 5
n=10;
A = spdiags ([ones(n,1) 3*ones(n,1) ones(n,1)], [-1 0 1], n, n);
full(A)
b = A * ones(10,1);
x_sys_Jacobi = b;
x_sys_GaussSeidel = b;
x_sys_SOR = b;
#b;

x_sys_Jacobi = model_sys_lin(A,b,'Jacobi');
x_sys_GaussSeidel = model_sys_lin(A,b,'Gauss-Seidel');
x_sys_SOR = model_sys_lin(A,b,'SOR',1.15);
[x_sys_Jacobi x_sys_GaussSeidel x_sys_SOR]
