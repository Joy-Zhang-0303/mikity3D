Func void main(){
Real m,g,a,b,Iequ,Zequ,Z0,Q,delta_z,delta_i,ram1,ram2,temp1,temp2,ts;
Matrix A,B,C,D,K,As,Cs,H,Ac,Bc,Cc,Ad,Bd,Cd;

//物理パラメータ
m=0.227;
g=9.81;
a=54.5;
b=0.193;
Iequ=0.738;
Zequ=10.0/1000;
Z0=b/a;
Q=2*m*g/(a*a);
//極配置
ram1=-50;
ram2=-50;
//サンプリング時間
ts=0.005;

//線形一次式
delta_z=Q*Iequ*Iequ/(m*(Z0+Zequ)*(Z0+Zequ)*(Z0+Zequ));//^3);
delta_i=-Q*Iequ/(m*(Z0+Zequ)*(Z0+Zequ));//^2);

printf("%f\t",delta_z);
printf("%f\n",delta_i);

//状態空間モデル
A=[[0 1][delta_z 0]];
B=[[0][delta_i]];
C=[1 0];

//フィードバックゲイン
temp1=(-(ram1*ram2)-delta_z)/delta_i;
temp2=(ram1+ram2)/delta_i;
K=-[temp1 temp2];

print K;
  
//状態フィードバックを用いたシステム
As=A-B*K;
print As;

//オブザーバゲイン
H=[[-ram1*2-ram2*2][(ram1*2*ram2*2+delta_z)]];
print H;

//オブザーバ＋フィードバック　システム
Ac=A-B*K-H*C;
Bc=H;
Cc=-K;
print Ac;
print Bc;


//離散化
Ad=exp(Ac*ts);
Bd=inv(Ac)*(Ad-[[1 0][0 1]])*Bc;
Cd=Cc;

print Ad;
print Bd;
print Cc;


}
main()