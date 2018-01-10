/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteComparativo;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Temporal;
/*  21:    */ import javax.persistence.TemporalType;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="estado_financiero")
/*  28:    */ public class EstadoFinanciero
/*  29:    */   extends EntidadBase
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @Id
/*  33:    */   @TableGenerator(name="estado_financiero", initialValue=0, allocationSize=50)
/*  34:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="estado_financiero")
/*  35:    */   @Column(name="id_estado_financiero")
/*  36:    */   private int idEstadoFinanciero;
/*  37:    */   @Enumerated(EnumType.ORDINAL)
/*  38:    */   @Column(name="tipo_estado_financiero", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private TipoEstadoFinanciero tipoEstadoFinanciero;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idOrganizacion;
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @Column(name="fecha_desde")
/*  46:    */   @NotNull
/*  47:    */   private Date fechaDesde;
/*  48:    */   @Temporal(TemporalType.DATE)
/*  49:    */   @Column(name="fecha_hasta")
/*  50:    */   @NotNull
/*  51:    */   private Date fechaHasta;
/*  52:    */   @Column(name="nota", length=200)
/*  53:    */   @Size(max=200)
/*  54:    */   private String nota;
/*  55:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="estadoFinanciero")
/*  56: 82 */   private List<DetalleEstadoFinanciero> listaDetalleEstadoFinanciero = new ArrayList();
/*  57:    */   @Transient
/*  58:    */   private BigDecimal saldoActivo;
/*  59:    */   @Transient
/*  60:    */   private BigDecimal saldoPasivo;
/*  61:    */   @Transient
/*  62:    */   private BigDecimal saldoPatrimonio;
/*  63:    */   @Transient
/*  64:    */   private BigDecimal saldoIngreso;
/*  65:    */   @Transient
/*  66:    */   private BigDecimal saldoCosto;
/*  67:    */   @Transient
/*  68:    */   private BigDecimal saldoGasto;
/*  69:    */   @Transient
/*  70:    */   private BigDecimal saldoOtro;
/*  71:    */   @Transient
/*  72:    */   private BigDecimal resultadoEjercicio;
/*  73:    */   @Transient
/*  74:    */   private List<ReporteComparativo> listaReporteComparativo;
/*  75:    */   @Transient
/*  76:    */   private boolean indicadorNIIF;
/*  77:    */   @Transient
/*  78:    */   private boolean indicadorCuentaMovimiento;
/*  79:    */   
/*  80:    */   public int getIdEstadoFinanciero()
/*  81:    */   {
/*  82:119 */     return this.idEstadoFinanciero;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdEstadoFinanciero(int idEstadoFinanciero)
/*  86:    */   {
/*  87:123 */     this.idEstadoFinanciero = idEstadoFinanciero;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Date getFechaDesde()
/*  91:    */   {
/*  92:127 */     return this.fechaDesde;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setFechaDesde(Date fechaDesde)
/*  96:    */   {
/*  97:131 */     this.fechaDesde = fechaDesde;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Date getFechaHasta()
/* 101:    */   {
/* 102:135 */     return this.fechaHasta;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFechaHasta(Date fechaHasta)
/* 106:    */   {
/* 107:139 */     this.fechaHasta = fechaHasta;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNota()
/* 111:    */   {
/* 112:143 */     return this.nota;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNota(String nota)
/* 116:    */   {
/* 117:147 */     this.nota = nota;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<DetalleEstadoFinanciero> getListaDetalleEstadoFinanciero()
/* 121:    */   {
/* 122:151 */     return this.listaDetalleEstadoFinanciero;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setListaDetalleEstadoFinanciero(List<DetalleEstadoFinanciero> listaDetalleEstadoFinanciero)
/* 126:    */   {
/* 127:155 */     this.listaDetalleEstadoFinanciero = listaDetalleEstadoFinanciero;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public TipoEstadoFinanciero getTipoEstadoFinanciero()
/* 131:    */   {
/* 132:159 */     return this.tipoEstadoFinanciero;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setTipoEstadoFinanciero(TipoEstadoFinanciero tipoEstadoFinanciero)
/* 136:    */   {
/* 137:163 */     this.tipoEstadoFinanciero = tipoEstadoFinanciero;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public BigDecimal getSaldoActivo()
/* 141:    */   {
/* 142:167 */     return this.saldoActivo;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setSaldoActivo(BigDecimal saldoActivo)
/* 146:    */   {
/* 147:171 */     this.saldoActivo = saldoActivo;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public BigDecimal getSaldoPasivo()
/* 151:    */   {
/* 152:175 */     return this.saldoPasivo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setSaldoPasivo(BigDecimal saldoPasivo)
/* 156:    */   {
/* 157:179 */     this.saldoPasivo = saldoPasivo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public BigDecimal getSaldoPatrimonio()
/* 161:    */   {
/* 162:183 */     return this.saldoPatrimonio;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setSaldoPatrimonio(BigDecimal saldoPatrimonio)
/* 166:    */   {
/* 167:187 */     this.saldoPatrimonio = saldoPatrimonio;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public BigDecimal getSaldoIngreso()
/* 171:    */   {
/* 172:191 */     return this.saldoIngreso;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setSaldoIngreso(BigDecimal saldoIngreso)
/* 176:    */   {
/* 177:195 */     this.saldoIngreso = saldoIngreso;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public BigDecimal getSaldoCosto()
/* 181:    */   {
/* 182:199 */     return this.saldoCosto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setSaldoCosto(BigDecimal saldoCosto)
/* 186:    */   {
/* 187:203 */     this.saldoCosto = saldoCosto;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public BigDecimal getSaldoGasto()
/* 191:    */   {
/* 192:207 */     return this.saldoGasto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setSaldoGasto(BigDecimal saldoGasto)
/* 196:    */   {
/* 197:211 */     this.saldoGasto = saldoGasto;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public BigDecimal getSaldoOtro()
/* 201:    */   {
/* 202:215 */     return this.saldoOtro;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setSaldoOtro(BigDecimal saldoOtro)
/* 206:    */   {
/* 207:219 */     this.saldoOtro = saldoOtro;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public BigDecimal getResultadoEjercicio()
/* 211:    */   {
/* 212:223 */     if (this.resultadoEjercicio == null) {
/* 213:224 */       this.resultadoEjercicio = BigDecimal.ZERO;
/* 214:    */     }
/* 215:227 */     return this.resultadoEjercicio;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setResultadoEjercicio(BigDecimal resultadoEjercicio)
/* 219:    */   {
/* 220:231 */     this.resultadoEjercicio = resultadoEjercicio;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<ReporteComparativo> getListaReporteComparativo()
/* 224:    */   {
/* 225:235 */     return this.listaReporteComparativo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setListaReporteComparativo(List<ReporteComparativo> listaReporteComparativo)
/* 229:    */   {
/* 230:239 */     this.listaReporteComparativo = listaReporteComparativo;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isIndicadorNIIF()
/* 234:    */   {
/* 235:248 */     return this.indicadorNIIF;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setIndicadorNIIF(boolean indicadorNIIF)
/* 239:    */   {
/* 240:258 */     this.indicadorNIIF = indicadorNIIF;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isIndicadorCuentaMovimiento()
/* 244:    */   {
/* 245:267 */     return this.indicadorCuentaMovimiento;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setIndicadorCuentaMovimiento(boolean indicadorCuentaMovimiento)
/* 249:    */   {
/* 250:277 */     this.indicadorCuentaMovimiento = indicadorCuentaMovimiento;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public int getIdOrganizacion()
/* 254:    */   {
/* 255:284 */     return this.idOrganizacion;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setIdOrganizacion(int idOrganizacion)
/* 259:    */   {
/* 260:291 */     this.idOrganizacion = idOrganizacion;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public int getId()
/* 264:    */   {
/* 265:301 */     return getIdEstadoFinanciero();
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.EstadoFinanciero
 * JD-Core Version:    0.7.0.1
 */