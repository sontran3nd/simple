package com.example.admin.keyboard.Utils;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.admin.keyboard.R;

/**
 * Created by Admin on 10/5/2017.
 */

public class SimpleQwertyKeyboard {

    private KeyboardView mKeyboardView;
    private boolean caps = false;
    private Activity mActivity;
    private int keyBoardLayoutId = R.xml.simple_qwerty_keyboard;

    public SimpleQwertyKeyboard(Activity activity, int viewid, int layoutid) {
        mActivity = activity;
        mKeyboardView = (KeyboardView) mActivity.findViewById(viewid);
        keyBoardLayoutId = layoutid;
        mKeyboardView.setKeyboard(new Keyboard(mActivity, keyBoardLayoutId));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public SimpleQwertyKeyboard(Activity activity) {
        mActivity = activity;
        mKeyboardView = new KeyboardView(mActivity, null);
        //mKeyboardView = (KeyboardView) mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mActivity, keyBoardLayoutId));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public SimpleQwertyKeyboard(Activity activity, boolean caps_lock_on) {
        mActivity = activity;
        mKeyboardView = new KeyboardView(mActivity, null);
        //mKeyboardView = (KeyboardView) mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mActivity, keyBoardLayoutId));
        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        caps = caps_lock_on;
        mKeyboardView.setShifted(caps);
        mKeyboardView.invalidateAllKeys();
    }

    public View setUpKeyBoardWithFragment(View RootView) {
        RelativeLayout relativeLayout = new RelativeLayout(mActivity);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(RootView, p);

        RelativeLayout.LayoutParams kbParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        kbParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        kbParams.addRule(RelativeLayout.FOCUSABLE);
        kbParams.addRule(RelativeLayout.FOCUSABLES_TOUCH_MODE);
        relativeLayout.addView(mKeyboardView, kbParams);

        return relativeLayout;
    }

    //use to hide keyboard after press back button on device
    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public KeyboardView getKeyboard() {
        return mKeyboardView;
    }

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }

    public void showCustomKeyboard(View v) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if (v != null)
            ((InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void registerEditText(EditText edittext) {
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showCustomKeyboard(v);
                else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override
            public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type

                Layout layout = ((EditText) v).getLayout();
                if (layout != null) {
                    float x = event.getX() + edittext.getScrollX();
                    int offset = layout.getOffsetForHorizontal(0, x);
                    if (offset > 0)
                        if (x > layout.getLineMax(0))
                            edittext.setSelection(offset);     // touch was at end of text
                        else
                            edittext.setSelection(offset - 1);
                }
                else{
                    ViewTreeObserver vto = edittext.getViewTreeObserver();
                    final MotionEvent eventx = event;
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            Layout layout = edittext.getLayout();
                            if (layout != null)
                            {
                                float x = eventx.getX() + edittext.getScrollX();
                                int offset = layout.getOffsetForHorizontal(0, x);
                                if (offset > 0)
                                    if (x > layout.getLineMax(0))
                                        edittext.setSelection(offset);     // touch was at end of text
                                    else
                                        edittext.setSelection(offset - 1);
                            }
                        }
                    });
                }
//                String currentContent = edittext.getText().toString();
//                if (currentContent.length() > 0)
//                {
//                    edittext.setSelection(currentContent.length());
//                }

                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        EditText edittext = (EditText) mActivity.findViewById(resid);
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) showCustomKeyboard(v);
                else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override
            public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type

                Layout layout = ((EditText) v).getLayout();
                if (layout != null) {
                    float x = event.getX() + edittext.getScrollX();
                    int offset = layout.getOffsetForHorizontal(0, x);
                    if (offset > 0)
                        if (x > layout.getLineMax(0))
                            edittext.setSelection(offset);     // touch was at end of text
                        else
                            edittext.setSelection(offset - 1);
                }
                else{
                    ViewTreeObserver vto = edittext.getViewTreeObserver();
                    final MotionEvent eventx = event;
                    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            Layout layout = edittext.getLayout();
                            if (layout != null)
                            {
                                float x = eventx.getX() + edittext.getScrollX();
                                int offset = layout.getOffsetForHorizontal(0, x);
                                if (offset > 0)
                                    if (x > layout.getLineMax(0))
                                        edittext.setSelection(offset);     // touch was at end of text
                                    else
                                        edittext.setSelection(offset - 1);
                            }
                        }
                    });
                }

//                String currentContent = edittext.getText().toString();
//                if (currentContent.length() > 0) {
//                    edittext.setSelection(currentContent.length());
//                }

                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {

        public final static int CodeClear = 55006;

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
            // Get the EditText and its Editable
            View focusCurrent = mActivity.getWindow().getCurrentFocus();

            if (focusCurrent == null)
                return;

            if (focusCurrent.getClass() != AppCompatEditText.class && focusCurrent.getClass() != EditText.class)
                return;

            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // Apply the key to the edittext
            if (primaryCode == Keyboard.KEYCODE_SHIFT) {
                caps = !caps;
                mKeyboardView.setShifted(caps);
                mKeyboardView.invalidateAllKeys();
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {
                hideCustomKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                if (editable != null && start > 0) editable.delete(start - 1, start);
            } else if (primaryCode == CodeClear) {
                if (editable != null) editable.clear();
            } else { // insert character
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                editable.insert(start, Character.toString(code));
            }
        }

        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };
}
