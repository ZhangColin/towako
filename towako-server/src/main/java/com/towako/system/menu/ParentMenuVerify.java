package com.towako.system.menu;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author colin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParentMenuVerify.Validator.class)
public @interface ParentMenuVerify {
    String message() default "指定的上级分类不正确";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ParentMenuVerify, Long> {

        private final MenuRepository menuRepository;

        public Validator(MenuRepository menuRepository) {
            this.menuRepository = menuRepository;
        }

        @Override
        public boolean isValid(Long value, ConstraintValidatorContext context) {
            if (value.equals(0L)) {
                return true;
            }

            return menuRepository.existsById(value);
        }
    }
}
