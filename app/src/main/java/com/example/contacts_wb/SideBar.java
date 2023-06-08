package com.example.contacts_wb;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author
 *一个自定义view 实现a-z的竖直绘制，和监听滑动事件
 */
public class SideBar extends View {
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "#" };  //包含 A-Z 和 # 这 27 个字母，用于绘制 SideBar。
	private int choose = -1;  //记录当前选中的字母的下标，初始值为 -1
	private Paint paint = new Paint();  //绘制字母。在 onDraw 方法中设置字母的颜色、大小和样式等属性。

	private TextView mTextDialog;  //显示当前选中的字母。

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	protected void onDraw(Canvas canvas) {
		/**
		 *在 SideBar 上绘制字母。首先获取 SideBar 的高度、宽度和单个字母的高度，
		 *然后循环绘制每个字母。如果当前字母被选中，则字体加粗并改变颜色。
		 */
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;

		for (int i = 0; i < b.length; i++) {
			//设置字体大小和颜色
			paint.setColor(Color.parseColor("#A1A1A2"));
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			if (i == choose) {
				paint.setColor(Color.parseColor("#B8B8B8"));
				paint.setTextSize(40);
				paint.setFakeBoldText(true);
			}
			/*
			* xPos 的计算：首先计算字母在 x 轴上的位置。width / 2 表示 SideBar 宽度的一半，
			* paint.measureText(b[i]) / 2 表示字母宽度的一半，两者相减得到字母在 x 轴上的中心位置。
			* 因为 canvas.drawText 方法默认是以字母的左下角为起点绘制，所以还需要将 xPos 往左移动字母宽度的一半，
			* 使字母的中心与 SideBar 的中心对齐。
			* yPos 的计算：然后计算字母在 y 轴上的位置。singleHeight 表示单个字母的高度，
			* singleHeight * i 表示当前字母在 y 轴上的位置，singleHeight 表示字母在 y 轴上的高度。
			* 因为 canvas.drawText 方法默认是以字母的左下角为起点绘制，所以还需要将 yPos 往下移动字母高度的一半，
			* 使字母的中心与 SideBar 的中心对齐。
			* */
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();//重置画笔,以免影响后续的绘制操作。
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		/*
		* 处理滑动事件。
		* 首先获取手指所在位置的坐标和当前选中的字母下标。
		* 根据手指所在位置计算出当前选中的字母下标，如果选中的字母下标与上次不同，
		* 则回调 OnTouchingLetterChangedListener 接口，并显示选中的字母。
		* 同时将选中的字母标记为当前选中的字母，以便下次判断。
		* 手指离开屏幕时，将选中的字母重置，并隐藏 mTextDialog。
		* */
		final int action = event.getAction();//获取触摸事件类型
		final float y = event.getY();//获取手指所在位置的纵坐标
		final int oldChoose = choose;//choose 记录当前选中的字母的下标，oldChoose 记录上次选中的字母下标，初始值都为 -1
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener; //回调接口，用于通知调用方当前选中的字母。
		final int c = (int) (y / getHeight() * b.length);//根据手指所在位置计算出来的当前选中字母的下标
		//这里只处理ACTION_UP 事件，即手指离开屏幕的事件。
		switch (action) {
		//ACTION_UP时,将 choose 重置为 -1，同时隐藏 mTextDialog，并调用 invalidate() 方法刷新当前 View。
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//将choose重置为-1
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.INVISIBLE);
			}
			break;
		/*否则，如果上次选中字母和当前选中字母不同，并且当前选中字母的下标在合法范围内（即 [0, b.length)），
		则调用 onTouchingLetterChanged 方法通知调用方当前选中的字母，并设置 mTextDialog 显示当前选中的字母。
		最后将 choose 的值更新为当前选中字母的下标，并调用 invalidate() 方法刷新当前 View。
		 */
		default:
			// setBackgroundResource(R.drawable.sidebar_background);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						mTextDialog.setVisibility(View.VISIBLE);
					}
					choose = c;
					invalidate();//刷新View，凸显选中的字母
				}
			}

			break;
		}
		return true;
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}
	//定义一个回调接口，当用户选择字母时，会回调该方法。
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);//用于通知调用方当前选中的字母。
	}

}