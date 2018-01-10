/*   1:    */ package com.asinfo.as2.utils.validacion.identificacion;
/*   2:    */ 
/*   3:    */ import javax.validation.ConstraintValidator;
/*   4:    */ import javax.validation.ConstraintValidatorContext;
/*   5:    */ import org.apache.log4j.Logger;
/*   6:    */ 
/*   7:    */ public class IdentificacionValidator
/*   8:    */   implements ConstraintValidator<Identificacion, String>
/*   9:    */ {
/*  10: 18 */   private static final Logger LOG = Logger.getLogger(IdentificacionValidator.class);
/*  11:    */   private static final int NUM_PROVINCIAS = 24;
/*  12: 21 */   private static int[] coeficientes = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
/*  13: 22 */   private static int constante = 11;
/*  14:    */   private static final String BIEN = "bien";
/*  15:    */   private static final String MAL = "mal";
/*  16:    */   private static final int LONGITUD_CEDULA = 10;
/*  17:    */   private transient IdentificacionMode cedulaMode;
/*  18:    */   
/*  19:    */   public void initialize(Identificacion constraintAnnotation)
/*  20:    */   {
/*  21: 29 */     this.cedulaMode = constraintAnnotation.value();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean isValid(String object, ConstraintValidatorContext constraintContext)
/*  25:    */   {
/*  26: 34 */     Boolean isValid = Boolean.FALSE;
/*  27:    */     try
/*  28:    */     {
/*  29: 36 */       if ((object.charAt(2) == '0') || (object.charAt(2) == '1') || (object.charAt(2) == '2') || (object.charAt(2) == '3') || (object.charAt(2) == '4') || (object.charAt(2) == '5'))
/*  30:    */       {
/*  31: 37 */         LOG.info("Posicion3 de la cedula o el RUC de persona natural " + object.charAt(2));
/*  32: 38 */         if (validaCedula(object.substring(0, 10)).equals("bien")) {
/*  33: 39 */           isValid = Boolean.valueOf(true);
/*  34:    */         }
/*  35:    */       }
/*  36: 42 */       else if (object.charAt(2) == '6')
/*  37:    */       {
/*  38: 43 */         LOG.info("Posicion3 de la cedula o el RUC de empresa publica " + object.charAt(2));
/*  39: 44 */         if (validaRucEP(object) == "bien") {
/*  40: 45 */           isValid = Boolean.valueOf(true);
/*  41:    */         }
/*  42:    */       }
/*  43: 48 */       else if (object.charAt(2) == '9')
/*  44:    */       {
/*  45: 49 */         LOG.info("Posicion3 de la cedula o el RUC de persona juridica " + object.charAt(2));
/*  46: 50 */         if (validacionRUC(object) == "bien") {
/*  47: 51 */           isValid = Boolean.valueOf(true);
/*  48:    */         }
/*  49:    */       }
/*  50:    */       else
/*  51:    */       {
/*  52: 55 */         isValid = Boolean.valueOf(false);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 58 */       LOG.info("Error al validar RUC o cedula: " + e);
/*  58:    */     }
/*  59: 60 */     return isValid.booleanValue();
/*  60:    */   }
/*  61:    */   
/*  62:    */   private String validaCedula(String cedula)
/*  63:    */   {
/*  64: 66 */     String isValid = "mal";
/*  65:    */     try
/*  66:    */     {
/*  67: 68 */       if ((cedula == null) || (cedula.length() != 10)) {
/*  68: 69 */         return isValid;
/*  69:    */       }
/*  70: 71 */       if (this.cedulaMode == IdentificacionMode.BIEN)
/*  71:    */       {
/*  72: 72 */         int prov = Integer.parseInt(cedula.substring(0, 2));
/*  73: 74 */         if ((prov <= 0) || (prov > 24))
/*  74:    */         {
/*  75: 75 */           LOG.info("Error: cedula ingresada mal");
/*  76: 76 */           return isValid;
/*  77:    */         }
/*  78: 79 */         int[] d = new int[10];
/*  79: 80 */         for (int i = 0; i < d.length; i++) {
/*  80: 81 */           d[i] = Integer.parseInt(cedula.charAt(i) + "");
/*  81:    */         }
/*  82: 84 */         int imp = 0;
/*  83: 85 */         int par = 0;
/*  84: 87 */         for (int i = 0; i < d.length; i += 2)
/*  85:    */         {
/*  86: 88 */           d[i] = (d[i] * 2 > 9 ? d[i] * 2 - 9 : d[i] * 2);
/*  87: 89 */           imp += d[i];
/*  88:    */         }
/*  89: 92 */         for (int i = 1; i < d.length - 1; i += 2) {
/*  90: 93 */           par += d[i];
/*  91:    */         }
/*  92: 96 */         int suma = imp + par;
/*  93:    */         
/*  94: 98 */         int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) + "0") - suma;
/*  95:    */         
/*  96:    */ 
/*  97:101 */         d10 = d10 == 10 ? 0 : d10;
/*  98:103 */         if (d10 == d[9]) {
/*  99:104 */           return isValid = "bien";
/* 100:    */         }
/* 101:106 */         return isValid = "mal";
/* 102:    */       }
/* 103:110 */       return isValid = "mal";
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:113 */       LOG.info("Error al validar cedula o RUC de persona natural: " + e);
/* 108:    */     }
/* 109:115 */     return isValid;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String validaRucEP(String ruc)
/* 113:    */   {
/* 114:121 */     int prov = Integer.parseInt(ruc.substring(0, 2));
/* 115:122 */     String resp = "mal";
/* 116:    */     try
/* 117:    */     {
/* 118:125 */       if ((prov <= 0) || (prov > 24))
/* 119:    */       {
/* 120:126 */         LOG.info("Error: cedula ingresada mal");
/* 121:127 */         return resp;
/* 122:    */       }
/* 123:135 */       int[] d = new int[ruc.length()];
/* 124:137 */       for (int i = 0; i < d.length; i++) {
/* 125:138 */         d[i] = Integer.parseInt(ruc.charAt(i) + "");
/* 126:    */       }
/* 127:141 */       Integer v1 = Integer.valueOf(d[0] * 3);
/* 128:142 */       Integer v2 = Integer.valueOf(d[1] * 2);
/* 129:143 */       Integer v3 = Integer.valueOf(d[2] * 7);
/* 130:144 */       Integer v4 = Integer.valueOf(d[3] * 6);
/* 131:145 */       Integer v5 = Integer.valueOf(d[4] * 5);
/* 132:146 */       Integer v6 = Integer.valueOf(d[5] * 4);
/* 133:147 */       Integer v7 = Integer.valueOf(d[6] * 3);
/* 134:148 */       Integer v8 = Integer.valueOf(d[7] * 2);
/* 135:149 */       Integer v9 = Integer.valueOf(d[8]);
/* 136:    */       
/* 137:151 */       Integer sumatoria = Integer.valueOf(v1.intValue() + v2.intValue() + v3.intValue() + v4.intValue() + v5.intValue() + v6.intValue() + v7.intValue() + v8.intValue());
/* 138:152 */       Integer modulo = Integer.valueOf(sumatoria.intValue() % 11);
/* 139:153 */       Integer digito = Integer.valueOf(11 - modulo.intValue());
/* 140:155 */       if (digito.equals(v9)) {
/* 141:156 */         resp = "bien";
/* 142:    */       } else {
/* 143:158 */         resp = "mal";
/* 144:    */       }
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:161 */       LOG.info("Error al validar el RUC de empresas publicas: " + e);
/* 149:    */     }
/* 150:163 */     return resp;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String validacionRUC(String ruc)
/* 154:    */   {
/* 155:173 */     String resp_dato = "mal";
/* 156:174 */     int prov = Integer.parseInt(ruc.substring(0, 2));
/* 157:    */     try
/* 158:    */     {
/* 159:176 */       if ((prov <= 0) || (prov > 24))
/* 160:    */       {
/* 161:177 */         LOG.info("Error: ruc ingresada mal");
/* 162:178 */         return resp_dato;
/* 163:    */       }
/* 164:181 */       int[] d = new int[10];
/* 165:182 */       int suma = 0;
/* 166:184 */       for (int i = 0; i < d.length; i++) {
/* 167:185 */         d[i] = Integer.parseInt(ruc.charAt(i) + "");
/* 168:    */       }
/* 169:188 */       for (int i = 0; i < d.length - 1; i++)
/* 170:    */       {
/* 171:189 */         d[i] *= coeficientes[i];
/* 172:190 */         suma += d[i];
/* 173:    */       }
/* 174:193 */       LOG.info("Suma es: " + suma);
/* 175:    */       
/* 176:    */ 
/* 177:    */ 
/* 178:197 */       int aux = suma % constante;
/* 179:198 */       int resp = constante - aux;
/* 180:    */       
/* 181:200 */       resp = aux == 0 ? 0 : resp;
/* 182:    */       
/* 183:202 */       LOG.info("Aux: " + aux);
/* 184:203 */       LOG.info("Resp " + resp);
/* 185:204 */       LOG.info("d[9] " + d[9]);
/* 186:206 */       if (resp == d[9]) {
/* 187:207 */         resp_dato = "bien";
/* 188:    */       } else {
/* 189:210 */         resp_dato = "mal";
/* 190:    */       }
/* 191:    */     }
/* 192:    */     catch (Exception e)
/* 193:    */     {
/* 194:213 */       LOG.info("Error al validar el RUC de personas juridicas: " + e);
/* 195:    */     }
/* 196:215 */     return resp_dato;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.validacion.identificacion.IdentificacionValidator
 * JD-Core Version:    0.7.0.1
 */