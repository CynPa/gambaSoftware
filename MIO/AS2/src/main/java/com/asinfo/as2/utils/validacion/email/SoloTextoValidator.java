/*  1:   */ package com.asinfo.as2.utils.validacion.email;
/*  2:   */ 
/*  3:   */ import java.util.regex.Matcher;
/*  4:   */ import java.util.regex.Pattern;
/*  5:   */ import javax.validation.ConstraintValidator;
/*  6:   */ import javax.validation.ConstraintValidatorContext;
/*  7:   */ 
/*  8:   */ public class SoloTextoValidator
/*  9:   */   implements ConstraintValidator<SoloTexto, String>
/* 10:   */ {
/* 11:27 */   private Pattern mask = Pattern.compile("^[A-Za-z_]*$");
/* 12:   */   
/* 13:   */   public void initialize(SoloTexto constraintAnnotation) {}
/* 14:   */   
/* 15:   */   public boolean isValid(String value, ConstraintValidatorContext context)
/* 16:   */   {
/* 17:35 */     if ((value == null) || (value.trim().isEmpty())) {
/* 18:36 */       return true;
/* 19:   */     }
/* 20:39 */     Matcher matcher = this.mask.matcher(value);
/* 21:41 */     if (!matcher.matches()) {
/* 22:42 */       return false;
/* 23:   */     }
/* 24:44 */     return true;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.email.SoloTextoValidator
 * JD-Core Version:    0.7.0.1
 */