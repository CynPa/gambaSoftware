/*   1:    */ package com.asinfo.as2.nomina.procesos.servicio;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.PagoRol;
/*   5:    */ import com.asinfo.as2.entities.PagoRolEmpleado;
/*   6:    */ import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
/*   7:    */ import com.asinfo.as2.entities.Rubro;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  10:    */ import com.asinfo.as2.nomina.procesos.compilador.Evaluador;
/*  11:    */ import com.asinfo.as2.utils.ServiceLocator;
/*  12:    */ import com.asinfo.as2.utils.formula.IdentificadorClase;
/*  13:    */ import java.lang.annotation.Annotation;
/*  14:    */ import java.lang.reflect.InvocationTargetException;
/*  15:    */ import java.lang.reflect.Method;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import javax.naming.NamingException;
/*  18:    */ 
/*  19:    */ public class FuncionRol
/*  20:    */ {
/*  21:    */   private static ServicioPagoRolEmpleado servicioPagoRolEmpleado;
/*  22:    */   private static ServicioRubroEmpleado servicioRubroEmpleado;
/*  23:    */   private static ServicioHistoricoEmpleado servicioHistoricoEmpleado;
/*  24:    */   
/*  25:    */   @IdentificadorClase(label="SUELDO", value="a")
/*  26:    */   public static BigDecimal sueldo(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  27:    */   {
/*  28: 48 */     return getServicioPagoRolEmpleado().obtenerSueldoPorEmpleado(pagoRolEmpleadoRubro);
/*  29:    */   }
/*  30:    */   
/*  31:    */   @IdentificadorClase(label="DECIMO_TERCERO", value="b")
/*  32:    */   public static BigDecimal decimoTercero(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  33:    */     throws ExcepcionAS2Nomina
/*  34:    */   {
/*  35: 62 */     return getServicioPagoRolEmpleado().calcularDecimoTercero(pagoRolEmpleadoRubro);
/*  36:    */   }
/*  37:    */   
/*  38:    */   @IdentificadorClase(label="APORTE_PERSONAL", value="c")
/*  39:    */   public static BigDecimal aportePersonal(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  40:    */   {
/*  41: 73 */     return getServicioPagoRolEmpleado().obtenerAportePersonal(pagoRolEmpleadoRubro);
/*  42:    */   }
/*  43:    */   
/*  44:    */   @IdentificadorClase(label="APORTE_PATRONAL", value="d")
/*  45:    */   public static BigDecimal aportePatronal(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  46:    */   {
/*  47: 84 */     return getServicioPagoRolEmpleado().obtenerAportePatronal(pagoRolEmpleadoRubro);
/*  48:    */   }
/*  49:    */   
/*  50:    */   @IdentificadorClase(label="DECIMO_CUARTO", value="e")
/*  51:    */   public static BigDecimal decimoCuarto(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  52:    */     throws ExcepcionAS2Nomina
/*  53:    */   {
/*  54: 89 */     return getServicioPagoRolEmpleado().calcularDecimoCuarto(pagoRolEmpleadoRubro);
/*  55:    */   }
/*  56:    */   
/*  57:    */   @IdentificadorClase(label="PROVISION_DC", value="f")
/*  58:    */   public static BigDecimal provisionDecimoCuarto(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  59:    */     throws ExcepcionAS2Nomina
/*  60:    */   {
/*  61: 94 */     return getServicioPagoRolEmpleado().calcularProvisionDecimoCuarto(pagoRolEmpleadoRubro);
/*  62:    */   }
/*  63:    */   
/*  64:    */   @IdentificadorClase(label="PROVISION_DT", value="g")
/*  65:    */   public static BigDecimal provisionDecimoTercero(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  66:    */   {
/*  67: 99 */     return getServicioPagoRolEmpleado().calcularProvisionDecimoTercero(pagoRolEmpleadoRubro);
/*  68:    */   }
/*  69:    */   
/*  70:    */   @IdentificadorClase(label="IMPUESTO_RENTA", value="h")
/*  71:    */   public static BigDecimal impuestoRenta(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  72:    */     throws ExcepcionAS2Nomina
/*  73:    */   {
/*  74:104 */     return getServicioPagoRolEmpleado().calcularImpuestoRenta(pagoRolEmpleadoRubro);
/*  75:    */   }
/*  76:    */   
/*  77:    */   @IdentificadorClase(label="PRESTAMO", value="i")
/*  78:    */   public static BigDecimal cuotaPrestamo(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  79:    */   {
/*  80:116 */     return getServicioPagoRolEmpleado().descuentoPrestamo(pagoRolEmpleadoRubro);
/*  81:    */   }
/*  82:    */   
/*  83:    */   @IdentificadorClase(label="FONDO_RESERVA", value="j")
/*  84:    */   public static BigDecimal fondoReserva(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  85:    */     throws ExcepcionAS2Nomina
/*  86:    */   {
/*  87:121 */     return getServicioPagoRolEmpleado().calcularFondoReserva(pagoRolEmpleadoRubro);
/*  88:    */   }
/*  89:    */   
/*  90:    */   @IdentificadorClase(label="SUBSIDIO", value="k")
/*  91:    */   public static BigDecimal subsidio(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  92:    */   {
/*  93:133 */     return getServicioPagoRolEmpleado().subsidio(pagoRolEmpleadoRubro);
/*  94:    */   }
/*  95:    */   
/*  96:    */   @IdentificadorClase(label="SALARIO_ASIGNADO", value="l")
/*  97:    */   public static BigDecimal salarioAsignado(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/*  98:    */   {
/*  99:143 */     return getServicioRubroEmpleado().obtenerSueldoPorEmpleado(pagoRolEmpleadoRubro);
/* 100:    */   }
/* 101:    */   
/* 102:    */   @IdentificadorClase(label="ANIOS_ANTIGUEDAD", value="m")
/* 103:    */   public static BigDecimal aniosAntiguedad(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 104:    */   {
/* 105:154 */     Rubro rubro = pagoRolEmpleadoRubro.getRubro();
/* 106:    */     
/* 107:156 */     BigDecimal valorAntiguedad = BigDecimal.valueOf(getServicioHistoricoEmpleado().obtenerAnioAntiguedad(pagoRolEmpleadoRubro
/* 108:157 */       .getPagoRolEmpleado().getEmpleado(), pagoRolEmpleadoRubro.getPagoRolEmpleado().getPagoRol().getFecha()));
/* 109:159 */     if ((rubro.getValorMaximo().compareTo(BigDecimal.ZERO) == 1) && (valorAntiguedad.compareTo(rubro.getValorMaximo()) == 1)) {
/* 110:160 */       return rubro.getValorMaximo();
/* 111:    */     }
/* 112:162 */     return valorAntiguedad;
/* 113:    */   }
/* 114:    */   
/* 115:    */   @IdentificadorClase(label="RUBRO_FORMULA1", value="n")
/* 116:    */   public static BigDecimal obtenerRubroEmpleado1(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 117:    */   {
/* 118:175 */     return getServicioPagoRolEmpleado().obtenerRubroPorEmpleado(pagoRolEmpleadoRubro, 1);
/* 119:    */   }
/* 120:    */   
/* 121:    */   @IdentificadorClase(label="RUBRO_FORMULA2", value="o")
/* 122:    */   public static BigDecimal obtenerRubroEmpleado2(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 123:    */   {
/* 124:185 */     return getServicioPagoRolEmpleado().obtenerRubroPorEmpleado(pagoRolEmpleadoRubro, 2);
/* 125:    */   }
/* 126:    */   
/* 127:    */   @IdentificadorClase(label="RUBRO_FORMULA3", value="p")
/* 128:    */   public static BigDecimal obtenerRubroEmpleado3(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 129:    */   {
/* 130:195 */     return getServicioPagoRolEmpleado().obtenerRubroPorEmpleado(pagoRolEmpleadoRubro, 3);
/* 131:    */   }
/* 132:    */   
/* 133:    */   @IdentificadorClase(label="RUBRO_FORMULA4", value="q")
/* 134:    */   public static BigDecimal obtenerRubroEmpleado4(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 135:    */   {
/* 136:205 */     return getServicioPagoRolEmpleado().obtenerRubroPorEmpleado(pagoRolEmpleadoRubro, 4);
/* 137:    */   }
/* 138:    */   
/* 139:    */   @IdentificadorClase(label="RUBRO_FORMULA5", value="r")
/* 140:    */   public static BigDecimal obtenerRubroEmpleado5(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 141:    */   {
/* 142:215 */     return getServicioPagoRolEmpleado().obtenerRubroPorEmpleado(pagoRolEmpleadoRubro, 5);
/* 143:    */   }
/* 144:    */   
/* 145:    */   @IdentificadorClase(label="CARGAS_FAMILIARES_ACTIVAS", value="s")
/* 146:    */   public static BigDecimal obtenerNumeroCargasFamiliaresActivas(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 147:    */   {
/* 148:225 */     return BigDecimal.valueOf(pagoRolEmpleadoRubro.getPagoRolEmpleado().getEmpleado().getNumeroCargasActivas());
/* 149:    */   }
/* 150:    */   
/* 151:    */   @IdentificadorClase(label="VALOR_APORTABLE", value="t")
/* 152:    */   public static BigDecimal calcularValoresAportables(PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 153:    */   {
/* 154:230 */     return getServicioPagoRolEmpleado().calcularValoresAportables(pagoRolEmpleadoRubro);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static Double evaluarFuncion(String formula, PagoRolEmpleadoRubro pagoRolEmpleadoRubro)
/* 158:    */     throws ExcepcionAS2
/* 159:    */   {
/* 160:236 */     Object[] parametros = { pagoRolEmpleadoRubro };
/* 161:237 */     Double resultado = new Double(0.0D);
/* 162:238 */     Evaluador evaluador = new Evaluador();
/* 163:239 */     evaluador.vAnaliza(formula);
/* 164:240 */     for (int i = 0; i < formula.length(); i++)
/* 165:    */     {
/* 166:241 */       Class clase = FuncionRol.class;
/* 167:242 */       for (Method method : clase.getDeclaredMethods()) {
/* 168:243 */         for (Annotation annotation : method.getAnnotations()) {
/* 169:244 */           if ((annotation instanceof IdentificadorClase))
/* 170:    */           {
/* 171:245 */             IdentificadorClase identificadorClase = (IdentificadorClase)annotation;
/* 172:246 */             if (formula.charAt(i) == identificadorClase.value().charAt(0)) {
/* 173:    */               try
/* 174:    */               {
/* 175:248 */                 BigDecimal valor = BigDecimal.ZERO;
/* 176:249 */                 valor = (BigDecimal)method.invoke(method.getName(), parametros);
/* 177:250 */                 evaluador.fVariables[formula.charAt(i)] = valor.doubleValue();
/* 178:    */               }
/* 179:    */               catch (IllegalArgumentException e)
/* 180:    */               {
/* 181:253 */                 e.printStackTrace();
/* 182:    */               }
/* 183:    */               catch (IllegalAccessException e)
/* 184:    */               {
/* 185:256 */                 e.printStackTrace();
/* 186:    */               }
/* 187:    */               catch (InvocationTargetException e)
/* 188:    */               {
/* 189:259 */                 e.printStackTrace();
/* 190:    */               }
/* 191:    */               catch (Exception e)
/* 192:    */               {
/* 193:261 */                 if ((e instanceof ExcepcionAS2)) {
/* 194:262 */                   throw new ExcepcionAS2(e);
/* 195:    */                 }
/* 196:    */               }
/* 197:    */             }
/* 198:    */           }
/* 199:    */         }
/* 200:    */       }
/* 201:    */     }
/* 202:270 */     resultado = Double.valueOf(evaluador.fEvaluar());
/* 203:271 */     if (evaluador.ERRORMATEMATICO != 0) {
/* 204:272 */       return new Double(0.0D);
/* 205:    */     }
/* 206:274 */     return resultado;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static ServicioPagoRolEmpleado getServicioPagoRolEmpleado()
/* 210:    */   {
/* 211:284 */     if (servicioPagoRolEmpleado == null) {
/* 212:    */       try
/* 213:    */       {
/* 214:287 */         ServiceLocator serviceLocator = ServiceLocator.getInstance();
/* 215:    */         
/* 216:289 */         servicioPagoRolEmpleado = (ServicioPagoRolEmpleado)serviceLocator.getEJB("ServicioPagoRolEmpleadoImpl/local");
/* 217:    */       }
/* 218:    */       catch (NamingException e)
/* 219:    */       {
/* 220:292 */         e.printStackTrace();
/* 221:    */       }
/* 222:    */     }
/* 223:295 */     return servicioPagoRolEmpleado;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static ServicioRubroEmpleado getServicioRubroEmpleado()
/* 227:    */   {
/* 228:304 */     if (servicioRubroEmpleado == null) {
/* 229:    */       try
/* 230:    */       {
/* 231:307 */         ServiceLocator serviceLocator = ServiceLocator.getInstance();
/* 232:    */         
/* 233:309 */         servicioRubroEmpleado = (ServicioRubroEmpleado)serviceLocator.getEJB("ServicioRubroEmpleadoImpl/local");
/* 234:    */       }
/* 235:    */       catch (NamingException e)
/* 236:    */       {
/* 237:312 */         e.printStackTrace();
/* 238:    */       }
/* 239:    */     }
/* 240:315 */     return servicioRubroEmpleado;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static ServicioHistoricoEmpleado getServicioHistoricoEmpleado()
/* 244:    */   {
/* 245:324 */     if (servicioHistoricoEmpleado == null) {
/* 246:    */       try
/* 247:    */       {
/* 248:327 */         ServiceLocator serviceLocator = ServiceLocator.getInstance();
/* 249:    */         
/* 250:329 */         servicioHistoricoEmpleado = (ServicioHistoricoEmpleado)serviceLocator.getEJB("ServicioHistoricoEmpleadoImpl/local");
/* 251:    */       }
/* 252:    */       catch (NamingException e)
/* 253:    */       {
/* 254:332 */         e.printStackTrace();
/* 255:    */       }
/* 256:    */     }
/* 257:335 */     return servicioHistoricoEmpleado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static void setServicioPagoRolEmpleado(ServicioPagoRolEmpleado servicioPagoRolEmpleado)
/* 261:    */   {
/* 262:345 */     servicioPagoRolEmpleado = servicioPagoRolEmpleado;
/* 263:    */   }
/* 264:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.FuncionRol
 * JD-Core Version:    0.7.0.1
 */