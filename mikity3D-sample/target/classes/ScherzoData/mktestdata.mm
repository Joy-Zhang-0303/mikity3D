//
// Scherzo 用テストデータ作成
//

Func void main()
{
  Array t, th1;
  Real endtime, dt;

  /* 時間軸終了時刻 */
  endtime = 5.0;
  /* 時間軸の刻み */
  dt = 0.01;
  /* 時間軸 */
  t = linspace(0.0, endtime, Integer(endtime/dt)+1);

  /* 変数の初期化 */
  th1 = Z(t);

  th1 = linspace(0.0, 2*PI, Integer(endtime/dt)+1);

  print [[t][th1]] >> "data.mat";
}
