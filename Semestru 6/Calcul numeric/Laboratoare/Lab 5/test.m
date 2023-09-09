n=10;
A=diag(5*ones(n,1),0) + diag(-ones(n-1,1),1) + diag(-ones(n-1,1),-1) + diag(-ones(n-3,1),3) + diag(-ones(n-3,1),-3);
A
b=[3;2;2;ones(n-6,1);2;2;3];
b;
x_J = Jacobi(A,b,10);
x_GS = GaussSeidel(A,b,10);
x_SOR = SOR(A,b,1.15,10);
x_sys = model_sys_lin(A,b,1.15);
[x_J, x_GS, x_SOR, x_sys]

#de facut tema:
# 3 programe, ce am primit pe teams completat pentru Jacobi, GS si SOR
#
#
#
#
