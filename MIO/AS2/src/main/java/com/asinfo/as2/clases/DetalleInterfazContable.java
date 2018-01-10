/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.Table;
/*   9:    */ 
/*  10:    */ @Entity
/*  11:    */ @Table(name="tmp_detalle_interfaz_contable")
/*  12:    */ public class DetalleInterfazContable
/*  13:    */ {
/*  14:    */   @Id
/*  15:    */   @Column(name="detalle_interfaz_contable")
/*  16:    */   private Integer idDetalleInterfazContable;
/*  17:    */   @Column(name="id_cuenta_contable")
/*  18:    */   private int idCuentaContable;
/*  19:    */   @Column(name="id_centro_costo")
/*  20:    */   private Integer idCentroCosto;
/*  21:    */   @Column(name="id_dimension_contable1")
/*  22:    */   private Integer idDimensionContable1;
/*  23:    */   @Column(name="id_dimension_contable2")
/*  24:    */   private Integer idDimensionContable2;
/*  25:    */   @Column(name="id_dimension_contable3")
/*  26:    */   private Integer idDimensionContable3;
/*  27:    */   @Column(name="id_dimension_contable4")
/*  28:    */   private Integer idDimensionContable4;
/*  29:    */   @Column(name="id_dimension_contable5")
/*  30:    */   private Integer idDimensionContable5;
/*  31:    */   @Column(name="referencia1")
/*  32:    */   private String referencia1;
/*  33:    */   @Column(name="referencia2")
/*  34:    */   private String referencia2;
/*  35:    */   @Column(name="referencia3")
/*  36:    */   private String referencia3;
/*  37:    */   @Column(name="referencia4")
/*  38:    */   private String referencia4;
/*  39:    */   private Integer idFormaPago;
/*  40:    */   @Column(name="valor", precision=12, scale=4)
/*  41:    */   private BigDecimal valor;
/*  42:    */   @Column(name="descripcion")
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="id_agrupacion")
/*  45:    */   private Integer IdAgrupacion;
/*  46:    */   
/*  47:    */   public DetalleInterfazContable() {}
/*  48:    */   
/*  49:    */   public DetalleInterfazContable(Integer idCuentaContable, String referencia1, String referencia2, String referencia3, BigDecimal valor)
/*  50:    */   {
/*  51:111 */     this.idCuentaContable = idCuentaContable.intValue();
/*  52:112 */     this.referencia1 = referencia1;
/*  53:113 */     this.referencia2 = referencia2;
/*  54:114 */     this.referencia3 = referencia3;
/*  55:115 */     this.referencia4 = "";
/*  56:116 */     if (valor != null) {
/*  57:117 */       this.valor = FuncionesUtiles.redondearBigDecimal(valor, 2);
/*  58:    */     } else {
/*  59:119 */       this.valor = valor;
/*  60:    */     }
/*  61:121 */     this.descripcion = "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public DetalleInterfazContable(int idCuentaContable, Integer idCentroCosto, String referencia1, String referencia2, String referencia3, BigDecimal valor)
/*  65:    */   {
/*  66:135 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/*  67:136 */     this.idCentroCosto = idCentroCosto;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public DetalleInterfazContable(int idCuentaContable, Integer idDimensionContable1, Integer idDimensionContable2, Integer idDimensionContable3, Integer idDimensionContable4, Integer idDimensionContable5, String referencia1, String referencia2, String referencia3, BigDecimal valor)
/*  71:    */   {
/*  72:150 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/*  73:151 */     this.idDimensionContable1 = idDimensionContable1;
/*  74:152 */     this.idDimensionContable2 = idDimensionContable2;
/*  75:153 */     this.idDimensionContable3 = idDimensionContable3;
/*  76:154 */     this.idDimensionContable4 = idDimensionContable4;
/*  77:155 */     this.idDimensionContable5 = idDimensionContable5;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public DetalleInterfazContable(Integer idCuentaContable, String referencia1, String referencia2, String referencia3, Integer idFormaPago, BigDecimal valor)
/*  81:    */   {
/*  82:171 */     this(idCuentaContable, referencia1, referencia2, referencia3, valor);
/*  83:    */     
/*  84:173 */     this.idFormaPago = idFormaPago;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public DetalleInterfazContable(int idCuentaContable, String referencia1, String referencia2, String referencia3, String referencia4, BigDecimal valor)
/*  88:    */   {
/*  89:188 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/*  90:189 */     this.referencia4 = referencia4;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public DetalleInterfazContable(int idCuentaContable, String referencia1, String referencia2, String referencia3, String referencia4, Integer idFormaPago, BigDecimal valor)
/*  94:    */   {
/*  95:205 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/*  96:206 */     this.referencia4 = referencia4;
/*  97:207 */     this.idFormaPago = idFormaPago;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public DetalleInterfazContable(int idCuentaContable, String referencia1, String referencia2, String referencia3, BigDecimal valor, String descripcion)
/* 101:    */   {
/* 102:225 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/* 103:226 */     this.descripcion = descripcion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DetalleInterfazContable(int idCuentaContable, String referencia1, String referencia2, String referencia3, BigDecimal valor, Integer idAgrupacion)
/* 107:    */   {
/* 108:243 */     this(Integer.valueOf(idCuentaContable), referencia1, referencia2, referencia3, valor);
/* 109:244 */     this.referencia3 = "";
/* 110:245 */     this.IdAgrupacion = idAgrupacion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIdCuentaContable()
/* 114:    */   {
/* 115:254 */     return this.idCuentaContable;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIdCuentaContable(int idCuentaContable)
/* 119:    */   {
/* 120:264 */     this.idCuentaContable = idCuentaContable;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getReferencia1()
/* 124:    */   {
/* 125:273 */     return this.referencia1;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setReferencia1(String referencia1)
/* 129:    */   {
/* 130:283 */     this.referencia1 = referencia1;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getReferencia2()
/* 134:    */   {
/* 135:292 */     return this.referencia2;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setReferencia2(String referencia2)
/* 139:    */   {
/* 140:302 */     this.referencia2 = referencia2;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public BigDecimal getValor()
/* 144:    */   {
/* 145:311 */     return this.valor;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setValor(BigDecimal valor)
/* 149:    */   {
/* 150:321 */     this.valor = valor;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getDescripcion()
/* 154:    */   {
/* 155:330 */     return this.descripcion;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setDescripcion(String descripcion)
/* 159:    */   {
/* 160:340 */     this.descripcion = descripcion;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getReferencia3()
/* 164:    */   {
/* 165:344 */     return this.referencia3;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setReferencia3(String referencia3)
/* 169:    */   {
/* 170:348 */     this.referencia3 = referencia3;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Integer getIdAgrupacion()
/* 174:    */   {
/* 175:352 */     return this.IdAgrupacion;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setIdAgrupacion(Integer idAgrupacion)
/* 179:    */   {
/* 180:356 */     this.IdAgrupacion = idAgrupacion;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Integer getIdCentroCosto()
/* 184:    */   {
/* 185:360 */     return this.idCentroCosto;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setIdCentroCosto(Integer idCentroCosto)
/* 189:    */   {
/* 190:364 */     this.idCentroCosto = idCentroCosto;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Integer getIdDetalleInterfazContable()
/* 194:    */   {
/* 195:368 */     return this.idDetalleInterfazContable;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setIdDetalleInterfazContable(Integer idDetalleInterfazContable)
/* 199:    */   {
/* 200:372 */     this.idDetalleInterfazContable = idDetalleInterfazContable;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getReferencia4()
/* 204:    */   {
/* 205:376 */     return this.referencia4;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setReferencia4(String referencia4)
/* 209:    */   {
/* 210:380 */     this.referencia4 = referencia4;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Integer getIdFormaPago()
/* 214:    */   {
/* 215:389 */     return this.idFormaPago;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setIdFormaPago(Integer idFormaPago)
/* 219:    */   {
/* 220:399 */     this.idFormaPago = idFormaPago;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Integer getIdDimensionContable1()
/* 224:    */   {
/* 225:403 */     return this.idDimensionContable1;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setIdDimensionContable1(Integer idDimensionContable1)
/* 229:    */   {
/* 230:407 */     this.idDimensionContable1 = idDimensionContable1;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Integer getIdDimensionContable2()
/* 234:    */   {
/* 235:411 */     return this.idDimensionContable2;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setIdDimensionContable2(Integer idDimensionContable2)
/* 239:    */   {
/* 240:415 */     this.idDimensionContable2 = idDimensionContable2;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Integer getIdDimensionContable3()
/* 244:    */   {
/* 245:419 */     return this.idDimensionContable3;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIdDimensionContable3(Integer idDimensionContable3)
/* 249:    */   {
/* 250:423 */     this.idDimensionContable3 = idDimensionContable3;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Integer getIdDimensionContable4()
/* 254:    */   {
/* 255:427 */     return this.idDimensionContable4;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setIdDimensionContable4(Integer idDimensionContable4)
/* 259:    */   {
/* 260:431 */     this.idDimensionContable4 = idDimensionContable4;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Integer getIdDimensionContable5()
/* 264:    */   {
/* 265:435 */     return this.idDimensionContable5;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void setIdDimensionContable5(Integer idDimensionContable5)
/* 269:    */   {
/* 270:439 */     this.idDimensionContable5 = idDimensionContable5;
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.DetalleInterfazContable
 * JD-Core Version:    0.7.0.1
 */