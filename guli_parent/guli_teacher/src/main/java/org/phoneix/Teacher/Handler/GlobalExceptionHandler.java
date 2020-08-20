package org.phoneix.Teacher.Handler;

import com.baomidou.mybatisplus.extension.api.R;
import org.phoneix.guli.common.EduException;
import org.phoneix.guli.common.Result;
import org.phoneix.guli.common.ResultCode;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
