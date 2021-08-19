package kr.co.greetech.back.util;

import org.springframework.context.annotation.Profile;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.List;

public class ExceptionMsg {

    private ObjectError error;

    public static String bindingMsg(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) return "";

        StringBuilder sb = new StringBuilder("");
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        int size = allErrors.size();
        int index = 0;
        for (ObjectError error : allErrors) {
            // TODO: - 해당 if 문을 profile 구분으로 on/off 가능한지 or 다른 방법은 없는지 알아보기 - 검색 했을 때 잘 안나왔음 추후 해볼 것
            if (error instanceof FieldError) {
                sb.append(((FieldError) error).getField());
                sb.append(": ");
            }
            sb.append(error.getDefaultMessage());
            index++;

            if (index < size) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
