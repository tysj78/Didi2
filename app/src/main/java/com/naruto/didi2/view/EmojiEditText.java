package com.naruto.didi2.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import com.naruto.didi2.util.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 2021/10/25.
 */

@SuppressLint("AppCompatCustomView")
public class EmojiEditText extends EditText {
    // 输入表情前的光标位置
    private int cursorPos;
    // 输入表情前EditText中的文本
    private String inputAfterText;
    // 是否重置了EditText的内容
    private boolean resetText;
    private Context mContext;


    public EmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }


    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }


    public EmojiEditText(Context context, AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    } // 初始化edittext 控件


    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                }


            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                try {
                    if (!resetText) {
                        CharSequence input = s.subSequence(cursorPos, cursorPos
                                + count);
                        if (count >= 2) {// 表情符号的字符长度最小为2
                            if (containsEmoji(input.toString())) {
                                //暂不支持输入Emoji表情符号
                                Toast.makeText(mContext, "暂不支持输入表情符号", Toast.LENGTH_SHORT).show();
                                reset();
                                return;
                            }
                        }

                        if (isContainSpecialChar(input.toString())) {
                            Toast.makeText(mContext, "暂不支持输入特殊符号", Toast.LENGTH_SHORT).show();
                            reset();
                        }

                    } else {
                        resetText = false;
                    }
                } catch (Exception e) {
                    LogUtils.e("" + e.toString());
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    private void reset() {
        resetText = true;
        // 是表情符号就将文本还原为输入表情符号之前的内容
        setText(inputAfterText);
        CharSequence text = getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 校验是否包含特殊字符
     *
     * @param str
     * @return
     */
    public boolean isContainSpecialChar(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        boolean b = matcher.find();
        return b;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}
