/*  1:   */ package com.asinfo.as2.utils.validacion.email;
/*  2:   */ 
/*  3:   */ import java.util.regex.Matcher;
/*  4:   */ import java.util.regex.Pattern;
/*  5:   */ import javax.validation.ConstraintValidator;
/*  6:   */ import javax.validation.ConstraintValidatorContext;
/*  7:   */ 
/*  8:   */ public class EmailValidator
/*  9:   */   implements ConstraintValidator<Email, String>
/* 10:   */ {
/* 11:27 */   private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
/* 12:28 */   private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
/* 13:29 */   private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
/* 14:   */   private Pattern pattern;
/* 15:   */   
/* 16:   */   public EmailValidator()
/* 17:   */   {
/* 18:33 */     this.pattern = Pattern.compile("^" + ATOM + "+(\\." + ATOM + "+)*@" + DOMAIN + "|" + IP_DOMAIN + ")$", 2);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void initialize(Email annotation) {}
/* 22:   */   
/* 23:   */   public boolean isValid(String value, ConstraintValidatorContext context)
/* 24:   */   {
/* 25:40 */     if ((value == null) || (value.length() == 0)) {
/* 26:41 */       return true;
/* 27:   */     }
/* 28:43 */     Matcher m = this.pattern.matcher(value);
/* 29:44 */     return m.matches();
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.email.EmailValidator
 * JD-Core Version:    0.7.0.1
 */