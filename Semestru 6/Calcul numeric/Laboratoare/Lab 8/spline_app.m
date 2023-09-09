clf;axis equal;axis([0 2 0 1]);
xticks(0:0.2:2);yticks(0:0.2:1);
grid on;hold on;
 [x,y]=ginput(1);
 X=x;Y=y;
 i=1;
 while ~isempty([x,y])
   plot(x,y,'*k','MarkerSize',10);
   text(x+0.02,y+0.02,num2str(i),'fontsize',25);
   [x,y]=ginput(1);
   i=i+1;
   X=[X,x];Y=[Y,y];
 endwhile
nodes=linspace(0,1,length(X));
time=linspace(0,1,1000);
sx=evalsplinecubic(nodes,X,'deBoor',[],time);
sy=evalsplinecubic(nodes,Y,'deBoor',[],time);
plot(sx,sy,'-r','LineWidth',2);