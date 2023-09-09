function testLab6(nodes,values,col={'r'},m=3)
  clf; hold on; grid on; xlim([-5 5]); xticks(-5:5);axis square;

  X = linspace(-5,5,501);

  plot(nodes,values,'ok','markerfacecolor','g','markersize',10);
  l = {'noduri'};
  plot(X,Lagr_bary(nodes,values,X),'r','linewidth',4)
  l{end+1}=['L cu Lagr Baricentric'];
  plot(X,Lagr_classic(nodes,values,X),'b','linewidth',2)
  l{end+1}=['L cu Lagr Clasic'];

  legend(l,"location", "northeastoutside")
  set(gca,"fontsize", 17)

