/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoProrrateoEnum;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.EnumType;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.Min;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ import org.hibernate.annotations.ColumnDefault;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="gasto_importacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  25:    */ public class GastoImportacion
/*  26:    */   extends EntidadBase
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 6475052744009077326L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="gasto_importacion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="gasto_importacion")
/*  32:    */   @Column(name="id_gasto_importacion")
/*  33:    */   private int idGastoImportacion;
/*  34:    */   @Column(name="id_organizacion", nullable=false)
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal", nullable=false)
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="codigo", nullable=false, length=10)
/*  39:    */   @Size(min=1, max=10)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=50)
/*  42:    */   @Size(min=3, max=50)
/*  43:    */   private String nombre;
/*  44:    */   @Column(name="tipo_prorrateo", nullable=true)
/*  45:    */   @Enumerated(EnumType.ORDINAL)
/*  46:    */   @NotNull
/*  47:    */   private TipoProrrateoEnum tipoProrrateo;
/*  48:    */   @Column(name="indicador_calculo_automatico", nullable=false)
/*  49:    */   private boolean indicadorCalculoAutomatico;
/*  50:    */   @Column(name="indicador_factura_exterior", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   @ColumnDefault("'0'")
/*  53:    */   private boolean indicadorFacturaExterior;
/*  54:    */   @Column(name="porcentaje", nullable=false, precision=12, scale=2)
/*  55:    */   @Min(0L)
/*  56: 85 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*  57:    */   @Column(name="valor_manual", nullable=false, precision=12, scale=2)
/*  58:    */   @Min(0L)
/*  59: 89 */   private BigDecimal valorManual = BigDecimal.ZERO;
/*  60:    */   @Column(name="descripcion", length=200, nullable=true)
/*  61:    */   @Size(max=200)
/*  62:    */   private String descripcion;
/*  63:    */   @Column(name="activo", nullable=false)
/*  64:    */   private boolean activo;
/*  65:    */   @Column(name="predeterminado", nullable=false)
/*  66:    */   private boolean predeterminado;
/*  67:    */   @ManyToOne
/*  68:    */   @JoinColumn(name="id_tipo_tramite_importacion", nullable=true)
/*  69:    */   private TipoTramiteImportacion tipoTramiteImportacion;
/*  70:    */   @ManyToOne
/*  71:    */   @JoinColumn(name="id_grupo_gasto_importacion", nullable=false)
/*  72:    */   @NotNull
/*  73:    */   private GrupoGastoImportacion grupoGastoImportacion;
/*  74:    */   @Transient
/*  75:122 */   private BigDecimal traValor = BigDecimal.ZERO;
/*  76:    */   @Transient
/*  77:125 */   private BigDecimal traValorManual = BigDecimal.ZERO;
/*  78:    */   @Transient
/*  79:    */   private Date traFecha;
/*  80:    */   
/*  81:    */   public int getId()
/*  82:    */   {
/*  83:146 */     return this.idGastoImportacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdGastoImportacion()
/*  87:    */   {
/*  88:155 */     return this.idGastoImportacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdGastoImportacion(int idGastoImportacion)
/*  92:    */   {
/*  93:165 */     this.idGastoImportacion = idGastoImportacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdOrganizacion()
/*  97:    */   {
/*  98:174 */     return this.idOrganizacion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdOrganizacion(int idOrganizacion)
/* 102:    */   {
/* 103:184 */     this.idOrganizacion = idOrganizacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getIdSucursal()
/* 107:    */   {
/* 108:193 */     return this.idSucursal;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setIdSucursal(int idSucursal)
/* 112:    */   {
/* 113:203 */     this.idSucursal = idSucursal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public TipoProrrateoEnum getTipoProrrateo()
/* 117:    */   {
/* 118:212 */     return this.tipoProrrateo;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setTipoProrrateo(TipoProrrateoEnum tipoProrrateo)
/* 122:    */   {
/* 123:222 */     this.tipoProrrateo = tipoProrrateo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public TipoTramiteImportacion getTipoTramiteImportacion()
/* 127:    */   {
/* 128:231 */     return this.tipoTramiteImportacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTipoTramiteImportacion(TipoTramiteImportacion tipoTramiteImportacion)
/* 132:    */   {
/* 133:241 */     this.tipoTramiteImportacion = tipoTramiteImportacion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public GrupoGastoImportacion getGrupoGastoImportacion()
/* 137:    */   {
/* 138:250 */     return this.grupoGastoImportacion;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setGrupoGastoImportacion(GrupoGastoImportacion grupoGastoImportacion)
/* 142:    */   {
/* 143:260 */     this.grupoGastoImportacion = grupoGastoImportacion;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isIndicadorCalculoAutomatico()
/* 147:    */   {
/* 148:269 */     return this.indicadorCalculoAutomatico;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setIndicadorCalculoAutomatico(boolean indicadorCalculoAutomatico)
/* 152:    */   {
/* 153:279 */     this.indicadorCalculoAutomatico = indicadorCalculoAutomatico;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public BigDecimal getPorcentaje()
/* 157:    */   {
/* 158:288 */     return this.porcentaje;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 162:    */   {
/* 163:298 */     this.porcentaje = porcentaje;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public BigDecimal getValorManual()
/* 167:    */   {
/* 168:307 */     return this.valorManual;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setValorManual(BigDecimal valorManual)
/* 172:    */   {
/* 173:317 */     this.valorManual = valorManual;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getDescripcion()
/* 177:    */   {
/* 178:326 */     return this.descripcion;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setDescripcion(String descripcion)
/* 182:    */   {
/* 183:336 */     this.descripcion = descripcion;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isActivo()
/* 187:    */   {
/* 188:345 */     return this.activo;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setActivo(boolean activo)
/* 192:    */   {
/* 193:355 */     this.activo = activo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean isPredeterminado()
/* 197:    */   {
/* 198:364 */     return this.predeterminado;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setPredeterminado(boolean predeterminado)
/* 202:    */   {
/* 203:374 */     this.predeterminado = predeterminado;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getCodigo()
/* 207:    */   {
/* 208:383 */     return this.codigo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setCodigo(String codigo)
/* 212:    */   {
/* 213:393 */     this.codigo = codigo;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getNombre()
/* 217:    */   {
/* 218:402 */     return this.nombre;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setNombre(String nombre)
/* 222:    */   {
/* 223:412 */     this.nombre = nombre;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public BigDecimal getTraValor()
/* 227:    */   {
/* 228:421 */     return this.traValor;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setTraValor(BigDecimal traValor)
/* 232:    */   {
/* 233:431 */     this.traValor = traValor;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Date getTraFecha()
/* 237:    */   {
/* 238:440 */     return this.traFecha;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setTraFecha(Date traFecha)
/* 242:    */   {
/* 243:450 */     this.traFecha = traFecha;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public BigDecimal getTraValorManual()
/* 247:    */   {
/* 248:454 */     return this.traValorManual;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setTraValorManual(BigDecimal traValorManual)
/* 252:    */   {
/* 253:458 */     this.traValorManual = traValorManual;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean isIndicadorFacturaExterior()
/* 257:    */   {
/* 258:462 */     return this.indicadorFacturaExterior;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setIndicadorFacturaExterior(boolean indicadorFacturaExterior)
/* 262:    */   {
/* 263:466 */     this.indicadorFacturaExterior = indicadorFacturaExterior;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.GastoImportacion
 * JD-Core Version:    0.7.0.1
 */