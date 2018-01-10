/*  1:   */ package com.asinfo.as2.utils.validacion.email;
/*  2:   */ 
/*  3:   */ import java.util.regex.Matcher;
/*  4:   */ import java.util.regex.Pattern;
/*  5:   */ import javax.validation.ConstraintValidator;
/*  6:   */ import javax.validation.ConstraintValidatorContext;
/*  7:   */ 
/*  8:   */ public class EmailsValidator
/*  9:   */   implements ConstraintValidator<Emails, String>
/* 10:   */ {
/* 11:27 */   private Pattern mask = Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,20};{0,1}))*$");
/* 12:   */   
/* 13:   */   public void initialize(Emails constraintAnnotation) {}
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
/* 24:44 */     String[] correos = value.split(";");
/* 25:45 */     for (String correo : correos) {
/* 26:46 */       if (correo.split("@").length != 2) {
/* 27:47 */         return false;
/* 28:   */       }
/* 29:   */     }
/* 30:50 */     return true;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.email.EmailsValidator
 * JD-Core Version:    0.7.0.1
 */