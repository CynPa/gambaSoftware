package com.asinfo.as2.utils.validacion.identificacion;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy={IdentificacionValidator.class})
@Documented
public @interface Identificacion
{
  String message() default "Error de identificacion";
  
  Class<?>[] groups() default {};
  
  Class<? extends Payload>[] payload() default {};
  
  IdentificacionMode value();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.identificacion.Identificacion
 * JD-Core Version:    0.7.0.1
 */