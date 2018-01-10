/*  1:   */ package com.asinfo.as2.utils.validacion.horario;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.regex.Matcher;
/*  7:   */ import java.util.regex.Pattern;
/*  8:   */ import javax.validation.ConstraintValidator;
/*  9:   */ import javax.validation.ConstraintValidatorContext;
/* 10:   */ 
/* 11:   */ public class HorarioValidator
/* 12:   */   implements ConstraintValidator<Horario, String>
/* 13:   */ {
/* 14:28 */   private Pattern mask = Pattern.compile("([0-9][0-9][:][0-9][0-9][-][0-9][0-9][:][0-9][0-9];{0,1})*");
/* 15:   */   
/* 16:   */   public void initialize(Horario constraintAnnotation) {}
/* 17:   */   
/* 18:   */   public boolean isValid(String value, ConstraintValidatorContext context)
/* 19:   */   {
/* 20:36 */     if ((value == null) || (value.trim().isEmpty())) {
/* 21:37 */       return true;
/* 22:   */     }
/* 23:40 */     Matcher matcher = this.mask.matcher(value);
/* 24:42 */     if (!matcher.matches()) {
/* 25:43 */       return false;
/* 26:   */     }
/* 27:46 */     String[] a = value.split(";");
/* 28:47 */     HashMap<Integer, String> hmNoRepetirHoras = new HashMap();
/* 29:48 */     for (int i = 0; i < a.length; i++)
/* 30:   */     {
/* 31:49 */       if (i == 0)
/* 32:   */       {
/* 33:50 */         hmNoRepetirHoras.put(Integer.valueOf(i), a[i].substring(0, 2) + "--" + a[i].substring(6, 8));
/* 34:   */       }
/* 35:   */       else
/* 36:   */       {
/* 37:54 */         for (String noRepetidos : hmNoRepetirHoras.values())
/* 38:   */         {
/* 39:56 */           String[] al = noRepetidos.split("--");
/* 40:57 */           int x = Integer.parseInt(al[0]);
/* 41:58 */           int y = Integer.parseInt(al[1]);
/* 42:60 */           if ((x <= Integer.parseInt(a[i].substring(0, 2))) && (y >= Integer.parseInt(a[i].substring(0, 2)))) {
/* 43:61 */             return false;
/* 44:   */           }
/* 45:   */         }
/* 46:65 */         hmNoRepetirHoras.put(Integer.valueOf(i), a[i].substring(0, 2) + "--" + a[i].substring(6, 8));
/* 47:   */       }
/* 48:69 */       Object horas = new ArrayList();
/* 49:70 */       String[] b = a[i].split("-");
/* 50:71 */       for (int j = 0; j < b.length; j++)
/* 51:   */       {
/* 52:72 */         String[] c = b[j].split(":");
/* 53:73 */         for (int k = 0; k < c.length; k++)
/* 54:   */         {
/* 55:74 */           if (k == 0)
/* 56:   */           {
/* 57:75 */             int x = Integer.parseInt(c[k]);
/* 58:76 */             ((List)horas).add(Integer.valueOf(x));
/* 59:77 */             if (x > 23) {
/* 60:78 */               return false;
/* 61:   */             }
/* 62:   */           }
/* 63:81 */           if (k == 1)
/* 64:   */           {
/* 65:82 */             int x = Integer.parseInt(c[k]);
/* 66:83 */             if (x > 59) {
/* 67:84 */               return false;
/* 68:   */             }
/* 69:   */           }
/* 70:   */         }
/* 71:   */       }
/* 72:89 */       if (((Integer)((List)horas).get(0)).intValue() > ((Integer)((List)horas).get(1)).intValue()) {
/* 73:90 */         return false;
/* 74:   */       }
/* 75:   */     }
/* 76:94 */     return true;
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.horario.HorarioValidator
 * JD-Core Version:    0.7.0.1
 */