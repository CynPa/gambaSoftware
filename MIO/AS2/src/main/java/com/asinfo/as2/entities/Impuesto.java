/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.ImpuestoListener;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoImpuestoEnum;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EntityListeners;
/*  12:    */ import javax.persistence.EnumType;
/*  13:    */ import javax.persistence.Enumerated;
/*  14:    */ import javax.persistence.FetchType;
/*  15:    */ import javax.persistence.GeneratedValue;
/*  16:    */ import javax.persistence.GenerationType;
/*  17:    */ import javax.persistence.Id;
/*  18:    */ import javax.persistence.JoinColumn;
/*  19:    */ import javax.persistence.JoinTable;
/*  20:    */ import javax.persistence.ManyToMany;
/*  21:    */ import javax.persistence.ManyToOne;
/*  22:    */ import javax.persistence.OneToMany;
/*  23:    */ import javax.persistence.Table;
/*  24:    */ import javax.persistence.TableGenerator;
/*  25:    */ import javax.persistence.Transient;
/*  26:    */ import javax.validation.constraints.NotNull;
/*  27:    */ import javax.validation.constraints.Size;
/*  28:    */ 
/*  29:    */ @Entity
/*  30:    */ @Table(name="impuesto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"}), @javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"}), @javax.persistence.UniqueConstraint(columnNames={"id_impuesto"})})
/*  31:    */ @EntityListeners({ImpuestoListener.class})
/*  32:    */ public class Impuesto
/*  33:    */   extends EntidadBase
/*  34:    */   implements Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -7293866301728626100L;
/*  37:    */   @Id
/*  38:    */   @TableGenerator(name="impuesto", initialValue=0, allocationSize=50)
/*  39:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="impuesto")
/*  40:    */   @Column(name="id_impuesto")
/*  41:    */   private int idImpuesto;
/*  42:    */   @Column(name="id_sucursal", nullable=false)
/*  43:    */   @NotNull
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="id_organizacion", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private int idOrganizacion;
/*  48:    */   @Column(name="codigo", nullable=false, length=10)
/*  49:    */   @Size(min=1, max=10)
/*  50:    */   @NotNull
/*  51:    */   private String codigo;
/*  52:    */   @Column(name="nombre", nullable=false, length=50)
/*  53:    */   @Size(max=50)
/*  54:    */   @NotNull
/*  55:    */   private String nombre;
/*  56:    */   @Column(name="descripcion", nullable=true, length=200)
/*  57:    */   @Size(max=200)
/*  58:    */   private String descripcion;
/*  59:    */   @Column(name="estado", nullable=false)
/*  60:    */   @NotNull
/*  61:    */   private boolean estado;
/*  62:    */   @Column(name="indicador_impuesto_tributario", nullable=false)
/*  63:    */   private boolean indicadorImpuestoTributario;
/*  64:    */   @Column(name="indicador_no_objeto_iva", nullable=false)
/*  65:    */   private boolean indicadorNoObjetoIVA;
/*  66:    */   @ManyToMany(fetch=FetchType.LAZY)
/*  67:    */   @JoinTable(name="impuesto_categoria", joinColumns={@JoinColumn(name="id_impuesto")}, inverseJoinColumns={@JoinColumn(name="id_categoria_impuesto")})
/*  68: 94 */   private List<CategoriaImpuesto> listaCategoriaImpuesto = new ArrayList();
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="impuesto")
/*  70: 98 */   private List<RangoImpuesto> listaRangoImpuesto = new ArrayList();
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_cuenta_contable_venta", nullable=true)
/*  73:    */   private CuentaContable cuentaContableVenta;
/*  74:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  75:    */   @JoinColumn(name="id_cuenta_contable_compra", nullable=true)
/*  76:    */   private CuentaContable cuentaContableCompra;
/*  77:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  78:    */   @JoinColumn(name="id_cuenta_contable_redondeo", nullable=true)
/*  79:    */   private CuentaContable cuentaContableRedondeo;
/*  80:    */   @Column(name="tipo_impuesto", nullable=true)
/*  81:    */   @Enumerated(EnumType.ORDINAL)
/*  82:    */   private TipoImpuestoEnum tipoImpuesto;
/*  83:    */   @Transient
/*  84:    */   private BigDecimal totalImpuesto;
/*  85:    */   
/*  86:    */   public int getId()
/*  87:    */   {
/*  88:122 */     return this.idImpuesto;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Impuesto() {}
/*  92:    */   
/*  93:    */   public Impuesto(int idImpuesto, String codigo, String nombre, String descripcion, boolean estado, boolean indicadorImpuestoTributario)
/*  94:    */   {
/*  95:129 */     this.idImpuesto = idImpuesto;
/*  96:130 */     this.codigo = codigo;
/*  97:131 */     this.nombre = nombre;
/*  98:132 */     this.descripcion = descripcion;
/*  99:133 */     this.estado = estado;
/* 100:134 */     this.indicadorImpuestoTributario = indicadorImpuestoTributario;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Impuesto(int idImpuesto, String codigo, String nombre)
/* 104:    */   {
/* 105:138 */     this.idImpuesto = idImpuesto;
/* 106:139 */     this.codigo = codigo;
/* 107:140 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<String> getCamposAuditables()
/* 111:    */   {
/* 112:144 */     ArrayList<String> lista = new ArrayList();
/* 113:145 */     lista.add("codigo");
/* 114:146 */     lista.add("nombre");
/* 115:147 */     lista.add("indicadorImpuestoTributario");
/* 116:148 */     lista.add("descripcion");
/* 117:149 */     lista.add("estado");
/* 118:    */     
/* 119:151 */     return lista;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getIdImpuesto()
/* 123:    */   {
/* 124:155 */     return this.idImpuesto;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdImpuesto(int idImpuesto)
/* 128:    */   {
/* 129:159 */     this.idImpuesto = idImpuesto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getIdSucursal()
/* 133:    */   {
/* 134:163 */     return this.idSucursal;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setIdSucursal(int idSucursal)
/* 138:    */   {
/* 139:167 */     this.idSucursal = idSucursal;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getIdOrganizacion()
/* 143:    */   {
/* 144:171 */     return this.idOrganizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setIdOrganizacion(int idOrganizacion)
/* 148:    */   {
/* 149:175 */     this.idOrganizacion = idOrganizacion;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getCodigo()
/* 153:    */   {
/* 154:179 */     return this.codigo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setCodigo(String codigo)
/* 158:    */   {
/* 159:183 */     this.codigo = codigo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getNombre()
/* 163:    */   {
/* 164:187 */     return this.nombre;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setNombre(String nombre)
/* 168:    */   {
/* 169:191 */     this.nombre = nombre;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getDescripcion()
/* 173:    */   {
/* 174:195 */     return this.descripcion;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setDescripcion(String descripcion)
/* 178:    */   {
/* 179:199 */     this.descripcion = descripcion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isEstado()
/* 183:    */   {
/* 184:203 */     return this.estado;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setEstado(boolean estado)
/* 188:    */   {
/* 189:207 */     this.estado = estado;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<CategoriaImpuesto> getListaCategoriaImpuesto()
/* 193:    */   {
/* 194:211 */     return this.listaCategoriaImpuesto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setListaCategoriaImpuesto(List<CategoriaImpuesto> listaCategoriaImpuesto)
/* 198:    */   {
/* 199:215 */     this.listaCategoriaImpuesto = listaCategoriaImpuesto;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<RangoImpuesto> getListaRangoImpuesto()
/* 203:    */   {
/* 204:224 */     return this.listaRangoImpuesto;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setListaRangoImpuesto(List<RangoImpuesto> listaRangoImpuesto)
/* 208:    */   {
/* 209:234 */     this.listaRangoImpuesto = listaRangoImpuesto;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public BigDecimal getTotalImpuesto()
/* 213:    */   {
/* 214:238 */     if (this.totalImpuesto == null) {
/* 215:239 */       this.totalImpuesto = BigDecimal.ZERO;
/* 216:    */     }
/* 217:241 */     return this.totalImpuesto;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setTotalImpuesto(BigDecimal totalImpuesto)
/* 221:    */   {
/* 222:245 */     this.totalImpuesto = totalImpuesto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isIndicadorImpuestoTributario()
/* 226:    */   {
/* 227:249 */     return this.indicadorImpuestoTributario;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIndicadorImpuestoTributario(boolean indicadorImpuestoTributario)
/* 231:    */   {
/* 232:253 */     this.indicadorImpuestoTributario = indicadorImpuestoTributario;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String toString()
/* 236:    */   {
/* 237:258 */     return this.nombre;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CuentaContable getCuentaContableVenta()
/* 241:    */   {
/* 242:262 */     return this.cuentaContableVenta;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setCuentaContableVenta(CuentaContable cuentaContableVenta)
/* 246:    */   {
/* 247:266 */     this.cuentaContableVenta = cuentaContableVenta;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public CuentaContable getCuentaContableCompra()
/* 251:    */   {
/* 252:270 */     return this.cuentaContableCompra;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setCuentaContableCompra(CuentaContable cuentaContableCompra)
/* 256:    */   {
/* 257:274 */     this.cuentaContableCompra = cuentaContableCompra;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public CuentaContable getCuentaContableRedondeo()
/* 261:    */   {
/* 262:283 */     return this.cuentaContableRedondeo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setCuentaContableRedondeo(CuentaContable cuentaContableRedondeo)
/* 266:    */   {
/* 267:293 */     this.cuentaContableRedondeo = cuentaContableRedondeo;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public boolean isIndicadorNoObjetoIVA()
/* 271:    */   {
/* 272:300 */     return this.indicadorNoObjetoIVA;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setIndicadorNoObjetoIVA(boolean indicadorNoObjetoIVA)
/* 276:    */   {
/* 277:308 */     this.indicadorNoObjetoIVA = indicadorNoObjetoIVA;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public TipoImpuestoEnum getTipoImpuesto()
/* 281:    */   {
/* 282:312 */     if (this.tipoImpuesto == null) {
/* 283:313 */       this.tipoImpuesto = TipoImpuestoEnum.AFECTA_PRECIO;
/* 284:    */     }
/* 285:315 */     return this.tipoImpuesto;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public void setTipoImpuesto(TipoImpuestoEnum tipoImpuesto)
/* 289:    */   {
/* 290:319 */     this.tipoImpuesto = tipoImpuesto;
/* 291:    */   }
/* 292:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Impuesto
 * JD-Core Version:    0.7.0.1
 */