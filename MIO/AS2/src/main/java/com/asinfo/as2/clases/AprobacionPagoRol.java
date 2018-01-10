/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class AprobacionPagoRol
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 1L;
/*  11:    */   private int idPagoRolEmpleado;
/*  12:    */   private String identificacion;
/*  13:    */   private String apellidos;
/*  14:    */   private String nombres;
/*  15:    */   private String cargo;
/*  16:    */   private int diasTrabajados;
/*  17:    */   private BigDecimal sueldo;
/*  18:    */   private BigDecimal totalIngresost_2;
/*  19:    */   private BigDecimal totalIngresost_1;
/*  20:    */   private BigDecimal totalIngresos;
/*  21:    */   private BigDecimal totalEgresos;
/*  22:    */   private boolean indicadorAprobado;
/*  23:    */   private boolean indicadorAprobadoAnterior;
/*  24:    */   private boolean indicadorCobrado;
/*  25:    */   private boolean indicadorVariaciont_2;
/*  26:    */   private boolean indicadorVariaciont_1;
/*  27:    */   private BigDecimal[] ingresos;
/*  28:    */   private BigDecimal[] egresos;
/*  29:    */   private List<String> cabecerasIngresos;
/*  30:    */   private List<String> cabecerasEgresos;
/*  31:    */   
/*  32:    */   public AprobacionPagoRol(int idPagoRolEmpleado, String identificacion, String apellidos, String nombres, String cargo, int diasTrabajados, BigDecimal sueldo, boolean indicadorAprobado, boolean indicadorCobrado)
/*  33:    */   {
/*  34: 65 */     this.idPagoRolEmpleado = idPagoRolEmpleado;
/*  35: 66 */     this.identificacion = identificacion;
/*  36: 67 */     this.apellidos = apellidos;
/*  37: 68 */     this.nombres = nombres;
/*  38: 69 */     this.cargo = cargo;
/*  39: 70 */     this.diasTrabajados = diasTrabajados;
/*  40: 71 */     this.sueldo = sueldo;
/*  41: 72 */     this.indicadorAprobado = indicadorAprobado;
/*  42: 73 */     this.indicadorAprobadoAnterior = indicadorAprobado;
/*  43: 74 */     this.indicadorCobrado = indicadorCobrado;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getIdPagoRolEmpleado()
/*  47:    */   {
/*  48: 83 */     return this.idPagoRolEmpleado;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setIdPagoRolEmpleado(int idPagoRolEmpleado)
/*  52:    */   {
/*  53: 93 */     this.idPagoRolEmpleado = idPagoRolEmpleado;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getIdentificacion()
/*  57:    */   {
/*  58:102 */     return this.identificacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdentificacion(String identificacion)
/*  62:    */   {
/*  63:112 */     this.identificacion = identificacion;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getApellidos()
/*  67:    */   {
/*  68:121 */     return this.apellidos;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setApellidos(String apellidos)
/*  72:    */   {
/*  73:131 */     this.apellidos = apellidos;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getNombres()
/*  77:    */   {
/*  78:140 */     return this.nombres;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setNombres(String nombres)
/*  82:    */   {
/*  83:150 */     this.nombres = nombres;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCargo()
/*  87:    */   {
/*  88:159 */     return this.cargo;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCargo(String cargo)
/*  92:    */   {
/*  93:169 */     this.cargo = cargo;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getDiasTrabajados()
/*  97:    */   {
/*  98:178 */     return this.diasTrabajados;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setDiasTrabajados(int diasTrabajados)
/* 102:    */   {
/* 103:188 */     this.diasTrabajados = diasTrabajados;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public BigDecimal getSueldo()
/* 107:    */   {
/* 108:197 */     return this.sueldo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setSueldo(BigDecimal sueldo)
/* 112:    */   {
/* 113:207 */     this.sueldo = sueldo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public BigDecimal getTotalIngresost_2()
/* 117:    */   {
/* 118:216 */     return this.totalIngresost_2;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTotalIngresost_2(BigDecimal totalIngresost_2)
/* 122:    */   {
/* 123:226 */     this.totalIngresost_2 = totalIngresost_2;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public BigDecimal getTotalIngresost_1()
/* 127:    */   {
/* 128:235 */     return this.totalIngresost_1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTotalIngresost_1(BigDecimal totalIngresost_1)
/* 132:    */   {
/* 133:245 */     this.totalIngresost_1 = totalIngresost_1;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public BigDecimal getTotalIngresos()
/* 137:    */   {
/* 138:254 */     return this.totalIngresos;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setTotalIngresos(BigDecimal totalIngresos)
/* 142:    */   {
/* 143:264 */     this.totalIngresos = totalIngresos;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public BigDecimal getTotalEgresos()
/* 147:    */   {
/* 148:273 */     return this.totalEgresos;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setTotalEgresos(BigDecimal totalEgresos)
/* 152:    */   {
/* 153:283 */     this.totalEgresos = totalEgresos;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isIndicadorAprobado()
/* 157:    */   {
/* 158:292 */     return this.indicadorAprobado;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setIndicadorAprobado(boolean indicadorAprobado)
/* 162:    */   {
/* 163:302 */     this.indicadorAprobado = indicadorAprobado;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal[] getIngresos()
/* 167:    */   {
/* 168:311 */     return this.ingresos;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setIngresos(BigDecimal[] ingresos)
/* 172:    */   {
/* 173:321 */     this.ingresos = ingresos;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public BigDecimal[] getEgresos()
/* 177:    */   {
/* 178:330 */     return this.egresos;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setEgresos(BigDecimal[] egresos)
/* 182:    */   {
/* 183:340 */     this.egresos = egresos;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<String> getCabecerasIngresos()
/* 187:    */   {
/* 188:349 */     return this.cabecerasIngresos;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setCabecerasIngresos(List<String> cabecerasIngresos)
/* 192:    */   {
/* 193:359 */     this.cabecerasIngresos = cabecerasIngresos;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public List<String> getCabecerasEgresos()
/* 197:    */   {
/* 198:368 */     return this.cabecerasEgresos;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setCabecerasEgresos(List<String> cabecerasEgresos)
/* 202:    */   {
/* 203:378 */     this.cabecerasEgresos = cabecerasEgresos;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean isIndicadorAprobadoAnterior()
/* 207:    */   {
/* 208:387 */     return this.indicadorAprobadoAnterior;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setIndicadorAprobadoAnterior(boolean indicadorAprobadoAnterior)
/* 212:    */   {
/* 213:397 */     this.indicadorAprobadoAnterior = indicadorAprobadoAnterior;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean isIndicadorVariaciont_2()
/* 217:    */   {
/* 218:406 */     return this.indicadorVariaciont_2;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setIndicadorVariaciont_2(boolean indicadorVariaciont_2)
/* 222:    */   {
/* 223:416 */     this.indicadorVariaciont_2 = indicadorVariaciont_2;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isIndicadorVariaciont_1()
/* 227:    */   {
/* 228:425 */     return this.indicadorVariaciont_1;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIndicadorVariaciont_1(boolean indicadorVariaciont_1)
/* 232:    */   {
/* 233:435 */     this.indicadorVariaciont_1 = indicadorVariaciont_1;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isIndicadorCobrado()
/* 237:    */   {
/* 238:444 */     return this.indicadorCobrado;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setIndicadorCobrado(boolean indicadorCobrado)
/* 242:    */   {
/* 243:454 */     this.indicadorCobrado = indicadorCobrado;
/* 244:    */   }
/* 245:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.AprobacionPagoRol
 * JD-Core Version:    0.7.0.1
 */