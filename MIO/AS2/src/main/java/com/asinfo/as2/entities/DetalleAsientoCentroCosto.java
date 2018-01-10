/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.persistence.Transient;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_asiento_centro_costo")
/*  22:    */ public class DetalleAsientoCentroCosto
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_asiento_centro_costo", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_asiento_centro_costo")
/*  30:    */   @Column(name="id_detalle_asiento_centro_costo", unique=true, nullable=false)
/*  31:    */   private int idDetalleAsientoCentroCostos;
/*  32:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  33:    */   @JoinColumn(name="id_detalle_asiento", nullable=true)
/*  34:    */   private DetalleAsiento detalleAsiento;
/*  35:    */   @Transient
/*  36:    */   private CentroCosto centroCosto;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   private int idOrganizacion;
/*  39:    */   @Column(name="id_sucursal", nullable=false)
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="valor", precision=12, scale=2)
/*  42:    */   @Digits(integer=12, fraction=2)
/*  43:    */   @Min(0L)
/*  44: 62 */   private BigDecimal valor = BigDecimal.ZERO;
/*  45:    */   @Transient
/*  46:    */   private Date traFecha;
/*  47:    */   @Transient
/*  48:    */   private String traCodigoPartidaPresupuestaria;
/*  49:    */   @Transient
/*  50:    */   private String traNombrePartidaPresupuestaria;
/*  51:    */   @Transient
/*  52:    */   private String traNumero;
/*  53:    */   @Transient
/*  54:    */   private String traTipoAsiento;
/*  55:    */   @Transient
/*  56:    */   private String traNombreCentroCosto;
/*  57:    */   @Transient
/*  58:    */   private String traCodigoCentroCosto;
/*  59:    */   @Transient
/*  60:    */   private BigDecimal traHaber;
/*  61:    */   
/*  62:    */   public DetalleAsientoCentroCosto() {}
/*  63:    */   
/*  64:    */   public int getId()
/*  65:    */   {
/*  66: 97 */     return this.idDetalleAsientoCentroCostos;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DetalleAsientoCentroCosto(int idDetalleAsientoCentroCostos, BigDecimal valor, String traNombreCentroCosto, String traCodigoCentroCosto)
/*  70:    */   {
/*  71:101 */     this.idDetalleAsientoCentroCostos = idDetalleAsientoCentroCostos;
/*  72:102 */     this.valor = valor;
/*  73:103 */     this.traNombreCentroCosto = traNombreCentroCosto;
/*  74:104 */     this.traCodigoCentroCosto = traCodigoCentroCosto;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public DetalleAsientoCentroCosto(DetalleAsiento detalleAsiento, CentroCosto centroCosto, BigDecimal valor)
/*  78:    */   {
/*  79:113 */     this.detalleAsiento = detalleAsiento;
/*  80:114 */     this.centroCosto = centroCosto;
/*  81:115 */     this.valor = valor;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public DetalleAsientoCentroCosto(Date traFecha, String traCodigoPartidaPresupuestaria, String traNombrePartidaPresupuestaria, String traNumero, String traTipoAsiento, String traNombreCentroCosto, String traCodigoCentroCosto, BigDecimal valor, BigDecimal traHaber)
/*  85:    */   {
/*  86:130 */     this.traFecha = traFecha;
/*  87:131 */     this.traCodigoPartidaPresupuestaria = traCodigoPartidaPresupuestaria;
/*  88:132 */     this.traNombrePartidaPresupuestaria = traNombrePartidaPresupuestaria;
/*  89:133 */     this.traNumero = traNumero;
/*  90:134 */     this.traTipoAsiento = traTipoAsiento;
/*  91:135 */     this.traNombreCentroCosto = traNombreCentroCosto;
/*  92:136 */     this.traCodigoCentroCosto = traCodigoCentroCosto;
/*  93:137 */     this.valor = valor;
/*  94:138 */     this.traHaber = traHaber;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getIdDetalleAsientoCentroCostos()
/*  98:    */   {
/*  99:142 */     return this.idDetalleAsientoCentroCostos;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setIdDetalleAsientoCentroCostos(int idDetalleAsientoCentroCostos)
/* 103:    */   {
/* 104:146 */     this.idDetalleAsientoCentroCostos = idDetalleAsientoCentroCostos;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public DetalleAsiento getDetalleAsiento()
/* 108:    */   {
/* 109:150 */     return this.detalleAsiento;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setDetalleAsiento(DetalleAsiento detalleAsiento)
/* 113:    */   {
/* 114:154 */     this.detalleAsiento = detalleAsiento;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public CentroCosto getCentroCosto()
/* 118:    */   {
/* 119:158 */     if (this.centroCosto == null) {
/* 120:159 */       this.centroCosto = new CentroCosto();
/* 121:    */     }
/* 122:161 */     return this.centroCosto;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setCentroCosto(CentroCosto centroCosto)
/* 126:    */   {
/* 127:165 */     this.centroCosto = centroCosto;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getIdOrganizacion()
/* 131:    */   {
/* 132:169 */     return this.idOrganizacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setIdOrganizacion(int idOrganizacion)
/* 136:    */   {
/* 137:173 */     this.idOrganizacion = idOrganizacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getIdSucursal()
/* 141:    */   {
/* 142:177 */     return this.idSucursal;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setIdSucursal(int idSucursal)
/* 146:    */   {
/* 147:181 */     this.idSucursal = idSucursal;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getValor()
/* 151:    */   {
/* 152:185 */     return this.valor;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setValor(BigDecimal valor)
/* 156:    */   {
/* 157:189 */     this.valor = valor;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getTraNombreCentroCosto()
/* 161:    */   {
/* 162:193 */     return this.traNombreCentroCosto;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setTraNombreCentroCosto(String traNombreCentroCosto)
/* 166:    */   {
/* 167:197 */     this.traNombreCentroCosto = traNombreCentroCosto;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getTraCodigoCentroCosto()
/* 171:    */   {
/* 172:201 */     return this.traCodigoCentroCosto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTraCodigoCentroCosto(String traCodigoCentroCosto)
/* 176:    */   {
/* 177:205 */     this.traCodigoCentroCosto = traCodigoCentroCosto;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getNombreCompletoCentroCosto()
/* 181:    */   {
/* 182:209 */     return this.traCodigoCentroCosto + " | " + this.traNombreCentroCosto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getNombreCompletoPartidaPresupuestaria()
/* 186:    */   {
/* 187:213 */     return this.traCodigoPartidaPresupuestaria + " | " + this.traNombrePartidaPresupuestaria;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Date getTraFecha()
/* 191:    */   {
/* 192:222 */     return this.traFecha;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setTraFecha(Date traFecha)
/* 196:    */   {
/* 197:232 */     this.traFecha = traFecha;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getTraNumero()
/* 201:    */   {
/* 202:241 */     return this.traNumero;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setTraNumero(String traNumero)
/* 206:    */   {
/* 207:251 */     this.traNumero = traNumero;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getTraTipoAsiento()
/* 211:    */   {
/* 212:260 */     return this.traTipoAsiento;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setTraTipoAsiento(String traTipoAsiento)
/* 216:    */   {
/* 217:270 */     this.traTipoAsiento = traTipoAsiento;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getTraHaber()
/* 221:    */   {
/* 222:279 */     return this.traHaber;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setTraHaber(BigDecimal traHaber)
/* 226:    */   {
/* 227:289 */     this.traHaber = traHaber;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getTraCodigoPartidaPresupuestaria()
/* 231:    */   {
/* 232:298 */     return this.traCodigoPartidaPresupuestaria;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setTraCodigoPartidaPresupuestaria(String traCodigoPartidaPresupuestaria)
/* 236:    */   {
/* 237:308 */     this.traCodigoPartidaPresupuestaria = traCodigoPartidaPresupuestaria;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getTraNombrePartidaPresupuestaria()
/* 241:    */   {
/* 242:317 */     return this.traNombrePartidaPresupuestaria;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setTraNombrePartidaPresupuestaria(String traNombrePartidaPresupuestaria)
/* 246:    */   {
/* 247:327 */     this.traNombrePartidaPresupuestaria = traNombrePartidaPresupuestaria;
/* 248:    */   }
/* 249:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleAsientoCentroCosto
 * JD-Core Version:    0.7.0.1
 */