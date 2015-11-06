Func void main(){
Real m,g,a,b,Iequ,Zequ,Z0,Q,delta_z,delta_i,ram1,ram2,temp1,temp2,ts;
Matrix A,B,C,D,K,As,Cs,H,Ac,Bc,Cc,Ad,Bd,Cd;

//�����p�����[�^
m=0.227;
g=9.81;
a=54.5;
b=0.193;
Iequ=0.738;
Zequ=10.0/1000;
Z0=b/a;
Q=2*m*g/(a*a);
//�ɔz�u
ram1=-50;
ram2=-50;
//�T���v�����O����
ts=0.005;

//���`�ꎟ��
delta_z=Q*Iequ*Iequ/(m*(Z0+Zequ)*(Z0+Zequ)*(Z0+Zequ));//^3);
delta_i=-Q*Iequ/(m*(Z0+Zequ)*(Z0+Zequ));//^2);

printf("%f\t",delta_z);
printf("%f\n",delta_i);

//��ԋ�ԃ��f��
A=[[0 1][delta_z 0]];
B=[[0][delta_i]];
C=[1 0];

//�t�B�[�h�o�b�N�Q�C��
temp1=(-(ram1*ram2)-delta_z)/delta_i;
temp2=(ram1+ram2)/delta_i;
K=-[temp1 temp2];

print K;
  
//��ԃt�B�[�h�o�b�N��p�����V�X�e��
As=A-B*K;
print As;

//�I�u�U�[�o�Q�C��
H=[[-ram1*2-ram2*2][(ram1*2*ram2*2+delta_z)]];
print H;

//�I�u�U�[�o�{�t�B�[�h�o�b�N�@�V�X�e��
Ac=A-B*K-H*C;
Bc=H;
Cc=-K;
print Ac;
print Bc;


//���U��
Ad=exp(Ac*ts);
Bd=inv(Ac)*(Ad-[[1 0][0 1]])*Bc;
Cd=Cc;

print Ad;
print Bd;
print Cc;


}
main()