package com.app.delock.delockApplication.utils;

import android.content.Context;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.add_item.AddItemActivity;
import com.thejuki.kformmaster.helper.EmailEditTextBuilder;
import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.helper.HeaderBuilder;
import com.thejuki.kformmaster.helper.MultiLineEditTextBuilder;
import com.thejuki.kformmaster.helper.NumberEditTextBuilder;
import com.thejuki.kformmaster.helper.SingleLineEditTextBuilder;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;

import java.util.List;

/**
 * Created by Marky on 30/04/2018.
 */

public class AddItemFormUtils {

    public enum Tag {
        Title,
        Location,
        Deposit,
        Price,
        Description,
        Date,
        Time,
        DateTime
    }

    public static List<BaseFormElement<?>> addEditTexts(Context mContext, List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(mContext.getString(R.string.ItemDetails)).build());

        SingleLineEditTextBuilder title = new SingleLineEditTextBuilder((Tag.Title.ordinal()));
        title.setTitle(mContext.getString(R.string.Title));
        title.setHint(mContext.getString(R.string.EnterTitle));
        elements.add(title.build());

        NumberEditTextBuilder deposit = new NumberEditTextBuilder(Tag.Deposit.ordinal());
        deposit.setTitle(mContext.getString(R.string.Deposit));
        deposit.setValue("0");
        elements.add(deposit.build());

        NumberEditTextBuilder price = new NumberEditTextBuilder(Tag.Price.ordinal());
        price.setTitle(mContext.getString(R.string.Price));
        price.setValue("0");
        elements.add(price.build());;

        MultiLineEditTextBuilder textArea = new MultiLineEditTextBuilder(Tag.Description.ordinal());
        textArea.setTitle(mContext.getString(R.string.Description));
        textArea.setValue("");
        textArea.setHint(mContext.getString(R.string.EnterDescription));
        elements.add(textArea.build());
        return elements;
    }
}
