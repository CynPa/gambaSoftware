/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.Digits;
/*  13:    */ import javax.validation.constraints.Max;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="forma_pago", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class FormaPago
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="forma_pago", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="forma_pago")
/*  28:    */   @Column(name="id_forma_pago", unique=true, nullable=false)
/*  29:    */   private int idFormaPago;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", length=10, nullable=false)
/*  35:    */   @Size(min=2, max=10)
/*  36:    */   @NotNull
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="codigo_alterno", length=10, nullable=true)
/*  39:    */   @Size(min=2, max=10)
/*  40:    */   private String codigoAlterno;
/*  41:    */   @Column(name="nombre", length=50)
/*  42:    */   @Size(min=2, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="descripcion", length=200)
/*  45:    */   @Size(max=200)
/*  46:    */   private String descripcion;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="predeterminado", nullable=false)
/*  50:    */   private boolean predeterminado;
/*  51:    */   @Column(name="indicador_retencion_fuente", nullable=false)
/*  52:    */   private boolean indicadorRetencionFuente;
/*  53:    */   @Column(name="indicador_retencion_iva", nullable=false)
/*  54:    */   private boolean indicadorRetencionIva;
/*  55:    */   @Column(name="indicador_deposito_automatico", nullable=false)
/*  56:    */   private boolean indicadorDepositoAutomatico;
/*  57:    */   @Column(name="indicador_cheque", nullable=false)
/*  58:    */   private boolean indicadorCheque;
/*  59:    */   @Column(name="indicador_tarjeta_credito", nullable=false)
/*  60:    */   private boolean indicadorTarjetaCredito;
/*  61:    */   @Column(name="porcentaje_retencion", nullable=false, precision=5, scale=2)
/*  62:    */   @NotNull
/*  63:    */   @Digits(integer=5, fraction=2)
/*  64:    */   @Min(0L)
/*  65:    */   @Max(100L)
/*  66: 80 */   private BigDecimal porcentajeRetencion = BigDecimal.ZERO;
/*  67:    */   @Column(name="porcentaje_retencion_renta", precision=5, scale=2)
/*  68:    */   @Digits(integer=2, fraction=2)
/*  69:    */   @Min(0L)
/*  70:    */   @Max(100L)
/*  71: 87 */   private BigDecimal porcentajeRetencionRenta = BigDecimal.ZERO;
/*  72:    */   @Column(name="porcentaje_retencion_iva", precision=5, scale=2)
/*  73:    */   @Digits(integer=2, fraction=2)
/*  74:    */   @Min(0L)
/*  75:    */   @Max(100L)
/*  76: 93 */   private BigDecimal porcentajeRetencionIva = BigDecimal.ZERO;
/*  77:    */   
/*  78:    */   public FormaPago() {}
/*  79:    */   
/*  80:    */   public FormaPago(int idFormaPago, String codigo, String nombre)
/*  81:    */   {
/*  82:110 */     this.idFormaPago = idFormaPago;
/*  83:111 */     this.codigo = codigo;
/*  84:112 */     this.nombre = nombre;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getId()
/*  88:    */   {
/*  89:117 */     return this.idFormaPago;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdFormaPago()
/*  93:    */   {
/*  94:121 */     return this.idFormaPago;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdFormaPago(int idFormaPago)
/*  98:    */   {
/*  99:125 */     this.idFormaPago = idFormaPago;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdOrganizacion()
/* 103:    */   {
/* 104:129 */     return this.idOrganizacion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdOrganizacion(int idOrganizacion)
/* 108:    */   {
/* 109:133 */     this.idOrganizacion = idOrganizacion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getIdSucursal()
/* 113:    */   {
/* 114:137 */     return this.idSucursal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setIdSucursal(int idSucursal)
/* 118:    */   {
/* 119:141 */     this.idSucursal = idSucursal;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getNombre()
/* 123:    */   {
/* 124:145 */     return this.nombre;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setNombre(String nombre)
/* 128:    */   {
/* 129:149 */     this.nombre = nombre;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean getActivo()
/* 133:    */   {
/* 134:153 */     return this.activo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setActivo(boolean activo)
/* 138:    */   {
/* 139:157 */     this.activo = activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getDescripcion()
/* 143:    */   {
/* 144:161 */     return this.descripcion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setDescripcion(String descripcion)
/* 148:    */   {
/* 149:165 */     this.descripcion = descripcion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean getPredeterminado()
/* 153:    */   {
/* 154:169 */     return this.predeterminado;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setPredeterminado(boolean predeterminado)
/* 158:    */   {
/* 159:173 */     this.predeterminado = predeterminado;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getCodigo()
/* 163:    */   {
/* 164:177 */     return this.codigo;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setCodigo(String codigo)
/* 168:    */   {
/* 169:181 */     this.codigo = codigo;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isIndicadorRetencionFuente()
/* 173:    */   {
/* 174:190 */     return this.indicadorRetencionFuente;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setIndicadorRetencionFuente(boolean indicadorRetencionFuente)
/* 178:    */   {
/* 179:200 */     this.indicadorRetencionFuente = indicadorRetencionFuente;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isIndicadorRetencionIva()
/* 183:    */   {
/* 184:209 */     return this.indicadorRetencionIva;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setIndicadorRetencionIva(boolean indicadorRetencionIva)
/* 188:    */   {
/* 189:219 */     this.indicadorRetencionIva = indicadorRetencionIva;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isIndicadorDepositoAutomatico()
/* 193:    */   {
/* 194:228 */     return this.indicadorDepositoAutomatico;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setIndicadorDepositoAutomatico(boolean indicadorDepositoAutomatico)
/* 198:    */   {
/* 199:238 */     this.indicadorDepositoAutomatico = indicadorDepositoAutomatico;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isIndicadorCheque()
/* 203:    */   {
/* 204:247 */     return this.indicadorCheque;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setIndicadorCheque(boolean indicadorCheque)
/* 208:    */   {
/* 209:257 */     this.indicadorCheque = indicadorCheque;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String toString()
/* 213:    */   {
/* 214:267 */     return this.nombre;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public BigDecimal getPorcentajeRetencion()
/* 218:    */   {
/* 219:276 */     return this.porcentajeRetencion;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setPorcentajeRetencion(BigDecimal porcentajeRetencion)
/* 223:    */   {
/* 224:286 */     this.porcentajeRetencion = porcentajeRetencion;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isIndicadorTarjetaCredito()
/* 228:    */   {
/* 229:295 */     return this.indicadorTarjetaCredito;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIndicadorTarjetaCredito(boolean indicadorTarjetaCredito)
/* 233:    */   {
/* 234:305 */     this.indicadorTarjetaCredito = indicadorTarjetaCredito;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getCodigoAlterno()
/* 238:    */   {
/* 239:309 */     return this.codigoAlterno;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setCodigoAlterno(String codigoAlterno)
/* 243:    */   {
/* 244:313 */     this.codigoAlterno = codigoAlterno;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public BigDecimal getPorcentajeRetencionRenta()
/* 248:    */   {
/* 249:317 */     return this.porcentajeRetencionRenta;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setPorcentajeRetencionRenta(BigDecimal porcentajeRetencionRenta)
/* 253:    */   {
/* 254:321 */     this.porcentajeRetencionRenta = porcentajeRetencionRenta;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public BigDecimal getPorcentajeRetencionIva()
/* 258:    */   {
/* 259:325 */     return this.porcentajeRetencionIva;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setPorcentajeRetencionIva(BigDecimal porcentajeRetencionIva)
/* 263:    */   {
/* 264:329 */     this.porcentajeRetencionIva = porcentajeRetencionIva;
/* 265:    */   }
/* 266:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FormaPago
 * JD-Core Version:    0.7.0.1
 */