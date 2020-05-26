package com.example.calculatorMCX;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static java.lang.Math.abs;

public class MainActivity extends Activity implements OnClickListener{
	// 组件
	private Button btn0; 				// 数字0～9
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btnplus; 			// 加法
	private Button btnminus; 			// 减法
	private Button btnmultiply; 		// 乘法
	private Button btndivide;  			// 除法
	private Button btnclear;  			// 清除
	private Button btndel;  			// 删除一位
	private Button btndot;  			// 小数点
	private Button btnequal; 			// 等于
	private EditText et_input;  		// 输入和结果显示框

	private boolean finish = false; // 是否完成一次计算


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindAndSetListener();     // 绑定组件并设置监听函数
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_main, container,
					false);
		}
	}

	private void bindAndSetListener() {
		// 绑定按键
		btn0 = findViewById(R.id.btn_0); 				// 数字0～9
		btn1 = findViewById(R.id.btn_1);
		btn2 = findViewById(R.id.btn_2);
		btn3 = findViewById(R.id.btn_3);
		btn4 = findViewById(R.id.btn_4);
		btn5 = findViewById(R.id.btn_5);
		btn6 = findViewById(R.id.btn_6);
		btn7 = findViewById(R.id.btn_7);
		btn8 = findViewById(R.id.btn_8);
		btn9 = findViewById(R.id.btn_9);
		btnplus = findViewById(R.id.btn_plus); 			// 加法
		btnminus = findViewById(R.id.btn_minus); 		// 减法
		btnmultiply = findViewById(R.id.btn_mutiply); 	// 乘法
		btndivide = findViewById(R.id.btn_divide);  	// 除法
		btnclear = findViewById(R.id.btn_clear);  		// 清除
		btndel = findViewById(R.id.btn_del);  			// 删除一位
		btndot = findViewById(R.id.btn_dot);  			// 小数点
		btnequal = findViewById(R.id.btn_equal); 		// 等于
		et_input= findViewById(R.id.et_input);			// 显示框

		// 设置监听函数
		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btnplus.setOnClickListener(this);
		btnminus.setOnClickListener(this);
		btnmultiply.setOnClickListener(this);
		btndivide.setOnClickListener(this);
		btnclear.setOnClickListener(this);
		btndel.setOnClickListener(this);
		btndot.setOnClickListener(this);
		btnequal.setOnClickListener(this);
	}

	// 监听函数
	@Override
	public void onClick(View v) {
		String string=et_input.getText().toString(); // 获取显示框的信息
		switch (v.getId()) {
			case R.id.btn_0:
			case R.id.btn_1:
			case R.id.btn_2:
			case R.id.btn_3:
			case R.id.btn_4:
			case R.id.btn_5:
			case R.id.btn_6:
			case R.id.btn_7:
			case R.id.btn_8:
			case R.id.btn_9:
			case R.id.btn_dot:
				string = et_input.getText().toString();
				if (finish || string.equals("Error")) {  // 若果已完成一次计算或出现错误则重新设置显示框内容
					et_input.setText(((Button)v).getText());
					finish = false;
				}
				else {
					String str = string + ((Button)v).getText();
					et_input.setText(str);
				}
				break;
			case R.id.btn_plus:
			case R.id.btn_minus:
			case R.id.btn_mutiply:
			case R.id.btn_divide:
				finish = false;
				getResult();
				string = et_input.getText().toString();
				if (string.equals("Error"))
					return;
				String str = string + " "+ ((Button)v).getText() + " ";
				et_input.setText(str);
				break;
			case R.id.btn_clear:
				et_input.setText("");
				break;
			case R.id.btn_del:  // 退一位
				if(!string.isEmpty()){
					et_input.setText(string.substring(0, string.length()-1));
				}
				break;
			case R.id.btn_equal:
				getResult();
				finish = true; // 完成一次计算
				break;
			default:
				break;
		}
	}

	// 获取计算结果
	private void getResult(){
		double result = 0;
		int flag = 1;  // 出错标记
		String expString = et_input.getText().toString(); // 计算表达式
		if(expString.isEmpty() || !expString.contains(" "))
			return;
		String s1 = expString.substring(0, expString.indexOf(" "));  // 操作数1
		String op = expString.substring(expString.indexOf(" ")+1, expString.indexOf(" ")+2); // 运算符
		String s2 = expString.substring(expString.indexOf(" ")+3);  // 操作数2

		if(!s1.isEmpty() && !s2.isEmpty()){ // 两个操作数都存在
			try{
				double d1 = Double.parseDouble(s1);
				double d2 = Double.parseDouble(s2);
				switch (op) {
					case "+":
						result = d1 + d2;
						break;
					case "-":
						result = d1 - d2;
						break;
					case "X":
						result = d1 * d2;
						break;
					case "÷":
						if (abs(d2) < 0.000001) {  // 除数为0
							flag = 0;   // 标记出错
						} else {
							result = d1 / d2;
						}
						break;
				}
				if(flag == 0){
					et_input.setText(R.string.error);
				}
				else if(!s1.contains(".")&&!s2.contains(".")&&!op.equals("÷")){ // 整数加减乘
					int r = (int)result;
					et_input.setText(String.valueOf(r));
				}
				else {
					String res = String.valueOf(result);
					if (res.length() > 7)
						res = String.format("%.7f", result);
					et_input.setText(res);
				}

			} catch(NumberFormatException e){
					et_input.setText(R.string.error);
			}
		}
		else if(!s1.isEmpty()){  // 只有操作数1
			et_input.setText(expString);
		}
		else if(!s2.isEmpty()){ // 只有操作数2
			try{
				double d2=Double.parseDouble(s2);
				switch (op) {
					case "+":
						result = d2;
						break;
					case "-":
						result = 0 - d2;
						break;
					default:
						flag = 0;
						break;
				}
				if(flag==0){
					et_input.setText(R.string.error);
				}
				else if(!s2.contains(".")){
					int r=(int)result;
					et_input.setText(String.valueOf(r));
				}
				else {
					et_input.setText(String.valueOf(result));
				}
			}catch(NumberFormatException e){
				et_input.setText(R.string.error);
			}
		}
		else{
			et_input.setText(expString);
		}
	}
}
