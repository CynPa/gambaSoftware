package com.asinfo.as2.util.seguridad;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityConstraint
{
  PermissionType[] permissions() default {};
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.util.seguridad.SecurityConstraint
 * JD-Core Version:    0.7.0.1
 */