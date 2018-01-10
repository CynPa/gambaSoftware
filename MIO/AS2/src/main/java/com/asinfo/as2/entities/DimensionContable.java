/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.TipoAccesoContable;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.EnumType;
/*  11:    */ import javax.persistence.Enumerated;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.OneToMany;
/*  19:    */ import javax.persistence.Table;
/*  20:    */ import javax.persistence.TableGenerator;
/*  21:    */ import javax.persistence.Temporal;
/*  22:    */ import javax.persistence.TemporalType;
/*  23:    */ import javax.persistence.Transient;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ import javax.validation.constraints.Size;
/*  26:    */ 
/*  27:    */ @Entity
/*  28:    */ @Table(name="dimension_contable", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "numero", "codigo"})})
/*  29:    */ public class DimensionContable
/*  30:    */   extends EntidadBase
/*  31:    */   implements Serializable
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @Id
/*  35:    */   @TableGenerator(name="dimension_contable", initialValue=0, allocationSize=50)
/*  36:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="dimension_contable")
/*  37:    */   @Column(name="id_dimension_contable")
/*  38:    */   private int idDimensionContable;
/*  39:    */   @Enumerated(EnumType.ORDINAL)
/*  40:    */   @Column(name="tipo_acceso_contable", nullable=false)
/*  41:    */   private TipoAccesoContable tipoAccesoContable;
/*  42:    */   @Column(name="id_organizacion", nullable=false)
/*  43:    */   private int idOrganizacion;
/*  44:    */   @Column(name="id_sucursal", nullable=false)
/*  45:    */   private int idSucursal;
/*  46:    */   @Column(name="numero", nullable=false, insertable=true, updatable=false)
/*  47:    */   private String numero;
/*  48:    */   @Column(name="codigo", nullable=false, length=20)
/*  49:    */   @NotNull
/*  50:    */   @Size(min=1, max=20)
/*  51:    */   private String codigo;
/*  52:    */   @Column(name="nombre", nullable=false, length=100)
/*  53:    */   @NotNull
/*  54:    */   @Size(min=2, max=100)
/*  55:    */   private String nombre;
/*  56:    */   @Column(name="mascara", nullable=false, length=100)
/*  57:    */   @NotNull
/*  58:    */   @Size(min=2, max=100)
/*  59: 82 */   private String mascara = "";
/*  60:    */   @Column(name="indicador_movimiento", nullable=false)
/*  61:    */   private boolean indicadorMovimiento;
/*  62:    */   @Column(name="descripcion", length=200, nullable=true)
/*  63:    */   @Size(max=200)
/*  64:    */   private String descripcion;
/*  65:    */   @Column(name="predeterminado", nullable=false)
/*  66:    */   private boolean predeterminado;
/*  67:    */   @Column(name="activo", nullable=false)
/*  68:    */   private boolean activo;
/*  69:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  70:    */   @JoinColumn(name="id_dimension_padre", nullable=true)
/*  71:    */   private DimensionContable dimensionPadre;
/*  72:    */   @Temporal(TemporalType.DATE)
/*  73:    */   @Column(name="fecha_desde", nullable=true)
/*  74:    */   private Date fechaDesde;
/*  75:    */   @Temporal(TemporalType.DATE)
/*  76:    */   @Column(name="fecha_hasta", nullable=true)
/*  77:    */   private Date fechaHasta;
/*  78:    */   @Temporal(TemporalType.DATE)
/*  79:    */   @Column(name="fecha_contrato", nullable=true)
/*  80:    */   private Date fechaContrato;
/*  81:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="dimensionContable")
/*  82:116 */   private List<CuentaContable> listaCuentaContable = new ArrayList();
/*  83:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="dimensionContable")
/*  84:119 */   private List<CuentaContableDimensionContable> listaCuentaContableDimensionContable = new ArrayList();
/*  85:    */   @Transient
/*  86:    */   private String nombreDimensionGrupo;
/*  87:    */   
/*  88:    */   public DimensionContable() {}
/*  89:    */   
/*  90:    */   public DimensionContable(int idDimensionContable)
/*  91:    */   {
/*  92:129 */     this.idDimensionContable = idDimensionContable;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public DimensionContable(int idDimensionContable, String numero, String codigo, String nombre)
/*  96:    */   {
/*  97:133 */     this.idDimensionContable = idDimensionContable;
/*  98:134 */     this.numero = numero;
/*  99:135 */     this.codigo = codigo;
/* 100:136 */     this.nombre = nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getId()
/* 104:    */   {
/* 105:141 */     return getIdDimensionContable();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIdDimensionContable()
/* 109:    */   {
/* 110:145 */     return this.idDimensionContable;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setIdDimensionContable(int idDimensionContable)
/* 114:    */   {
/* 115:149 */     this.idDimensionContable = idDimensionContable;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public TipoAccesoContable getTipoAccesoContable()
/* 119:    */   {
/* 120:153 */     return this.tipoAccesoContable;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setTipoAccesoContable(TipoAccesoContable tipoAccesoContable)
/* 124:    */   {
/* 125:157 */     this.tipoAccesoContable = tipoAccesoContable;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getIdOrganizacion()
/* 129:    */   {
/* 130:161 */     return this.idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setIdOrganizacion(int idOrganizacion)
/* 134:    */   {
/* 135:165 */     this.idOrganizacion = idOrganizacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdSucursal()
/* 139:    */   {
/* 140:169 */     return this.idSucursal;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdSucursal(int idSucursal)
/* 144:    */   {
/* 145:173 */     this.idSucursal = idSucursal;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getNumero()
/* 149:    */   {
/* 150:182 */     return this.numero;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setNumero(String numero)
/* 154:    */   {
/* 155:192 */     this.numero = numero;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getCodigo()
/* 159:    */   {
/* 160:196 */     return this.codigo;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCodigo(String codigo)
/* 164:    */   {
/* 165:200 */     this.codigo = codigo;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getNombre()
/* 169:    */   {
/* 170:204 */     return this.nombre;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setNombre(String nombre)
/* 174:    */   {
/* 175:208 */     this.nombre = nombre;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getMascara()
/* 179:    */   {
/* 180:212 */     return this.mascara;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setMascara(String mascara)
/* 184:    */   {
/* 185:216 */     this.mascara = mascara;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean isIndicadorMovimiento()
/* 189:    */   {
/* 190:220 */     return this.indicadorMovimiento;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 194:    */   {
/* 195:224 */     this.indicadorMovimiento = indicadorMovimiento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getDescripcion()
/* 199:    */   {
/* 200:228 */     return this.descripcion;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setDescripcion(String descripcion)
/* 204:    */   {
/* 205:232 */     this.descripcion = descripcion;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isPredeterminado()
/* 209:    */   {
/* 210:236 */     return this.predeterminado;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setPredeterminado(boolean predeterminado)
/* 214:    */   {
/* 215:240 */     this.predeterminado = predeterminado;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean isActivo()
/* 219:    */   {
/* 220:244 */     return this.activo;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setActivo(boolean activo)
/* 224:    */   {
/* 225:248 */     this.activo = activo;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public DimensionContable getDimensionPadre()
/* 229:    */   {
/* 230:252 */     return this.dimensionPadre;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setDimensionPadre(DimensionContable dimensionPadre)
/* 234:    */   {
/* 235:256 */     this.dimensionPadre = dimensionPadre;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Date getFechaDesde()
/* 239:    */   {
/* 240:265 */     return this.fechaDesde;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setFechaDesde(Date fechaDesde)
/* 244:    */   {
/* 245:275 */     this.fechaDesde = fechaDesde;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Date getFechaHasta()
/* 249:    */   {
/* 250:284 */     return this.fechaHasta;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setFechaHasta(Date fechaHasta)
/* 254:    */   {
/* 255:294 */     this.fechaHasta = fechaHasta;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Date getFechaContrato()
/* 259:    */   {
/* 260:301 */     return this.fechaContrato;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setFechaContrato(Date fechaContrato)
/* 264:    */   {
/* 265:309 */     this.fechaContrato = fechaContrato;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List<CuentaContable> getListaCuentaContable()
/* 269:    */   {
/* 270:313 */     return this.listaCuentaContable;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/* 274:    */   {
/* 275:317 */     this.listaCuentaContable = listaCuentaContable;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<CuentaContableDimensionContable> getListaCuentaContableDimensionContable()
/* 279:    */   {
/* 280:321 */     return this.listaCuentaContableDimensionContable;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setListaCuentaContableDimensionContable(List<CuentaContableDimensionContable> listaCuentaContableDimensionContable)
/* 284:    */   {
/* 285:325 */     this.listaCuentaContableDimensionContable = listaCuentaContableDimensionContable;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public String getNombreDimensionGrupo()
/* 289:    */   {
/* 290:329 */     return this.nombreDimensionGrupo;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setNombreDimensionGrupo(String nombreDimensionGrupo)
/* 294:    */   {
/* 295:333 */     this.nombreDimensionGrupo = nombreDimensionGrupo;
/* 296:    */   }
/* 297:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DimensionContable
 * JD-Core Version:    0.7.0.1
 */