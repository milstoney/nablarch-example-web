package com.nablarch.example.app.entity.core.validation.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import nablarch.core.util.StringUtil;

import com.nablarch.example.app.entity.core.validation.validator.MoneyRange.MoneyRangeValidator;

/**
 * 金額範囲のバリデーションを行うクラス。
 *
 * @author Nabu Rakutaro
 */
@Documented
@Constraint(validatedBy = MoneyRangeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface MoneyRange {

    /**
     * グループを取得する。
     *
     * @return グループ
     */
    Class<?>[] groups() default { };

    /**
     * バリデーションエラー発生時に設定するメッセージ。
     *
     * @return メッセージ
     */
    String message() default "{com.nablarch.example.app.entity.core.validation.validator.MoneyRange.message}";

    /**
     * Payloadを取得する。
     *
     * @return Payload
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * 最小値を取得する。
     *
     * @return 最小値
     */
    long min() default 0;

    /**
     * 最大値を取得する。
     *
     * @return 最大値
     */
    long max() default Long.MAX_VALUE;

    /**
     * 指定された整数の範囲の金額であることを検証するバリデータ。
     */
    @SuppressWarnings("PublicInnerClass")
    class MoneyRangeValidator implements ConstraintValidator<MoneyRange, String> {

        /**
         * 最小値
         */
        private long min;

        /**
         * 最大値
         */
        private long max;

        /**
         * 最小値・最大値を初期化する。
         *
         * @param constraintAnnotation 対象プロパティに付与されたアノテーション
         */
        @Override
        public void initialize(MoneyRange constraintAnnotation) {
            max = constraintAnnotation.max();
            min = constraintAnnotation.min();
        }

        /**
         * 対象の値が{@code min}～{@code max}で指定する範囲内であるか検証する。
         *
         * @param value 対象の値
         * @param context バリデーションコンテキスト
         * @return 範囲内である場合{@code true}
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtil.isNullOrEmpty(value)) {
                return true;
            }

            long number;
            try {
                number = Long.valueOf(value);
            } catch (NumberFormatException ignored) {
                return false;
            }

            return number >= min && number <= max;
        }
    }
}
