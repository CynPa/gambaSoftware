/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.TipoArticuloServicioEnum;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.validation.constraints.Max;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ import javax.validation.constraints.Size;
/*  24:    */ 
/*  25:    */ @Entity
/*  26:    */ @Table(name="articulo_servicio")
/*  27:    */ public class ArticuloServicio
/*  28:    */   extends EntidadBase
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -7395751966461372311L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="articulo_servicio", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="articulo_servicio")
/*  34:    */   @Column(name="id_articulo_servicio")
/*  35:    */   private int idArticuloServicio;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   private int idSucursal;
/*  40:    */   @Column(name="codigo", nullable=false, length=20)
/*  41:    */   @NotNull
/*  42:    */   @Size(min=1, max=10)
/*  43:    */   private String codigo;
/*  44:    */   @Column(name="nombre", nullable=false, length=50)
/*  45:    */   @NotNull
/*  46:    */   @Size(min=2, max=50)
/*  47:    */   private String nombre;
/*  48:    */   @Column(name="descripcion", nullable=false, length=100)
/*  49:    */   @NotNull
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="numero_serie", length=50, nullable=true)
/*  53:    */   @Size(max=50)
/*  54:    */   private String numeroSerie;
/*  55:    */   @Column(name="numero_parte", length=50, nullable=true)
/*  56:    */   @Size(max=50)
/*  57:    */   private String numeroParte;
/*  58:    */   @Column(name="codigo_barras", length=20, nullable=true)
/*  59:    */   @Size(max=50)
/*  60:    */   private String codigoBarras;
/*  61:    */   @Column(name="vida_util", nullable=false)
/*  62:    */   @NotNull
/*  63:    */   @Min(1L)
/*  64:    */   @Max(999999999L)
/*  65:    */   private int vitaUtil;
/*  66:    */   @Enumerated(EnumType.ORDINAL)
/*  67:    */   @Column(name="tipo_articulo_servicio", nullable=false)
/*  68:    */   @NotNull
/*  69:    */   private TipoArticuloServicioEnum tipoArticuloServicio;
/*  70:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  71:    */   @JoinColumn(name="id_grupo_articulo_servicio", nullable=false)
/*  72:    */   private GrupoArticuloServicio grupoArticuloServicio;
/*  73:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  74:    */   @JoinColumn(name="id_tipo_ciclo_operacion", nullable=false)
/*  75:    */   private TipoCicloOperacion tipoCicloOperacion;
/*  76:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  77:    */   @JoinColumn(name="id_categoria_articulo_servicio", nullable=false)
/*  78:    */   private CategoriaArticuloServicio categoriaArticuloServicio;
/*  79:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="articuloServicioPadre")
/*  80:120 */   private List<HistoricoArticuloServicio> listaHistoricoArticuloServicioHijo = new ArrayList();
/*  81:    */   
/*  82:    */   public int getIdArticuloServicio()
/*  83:    */   {
/*  84:147 */     return this.idArticuloServicio;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdArticuloServicio(int idArticuloServicio)
/*  88:    */   {
/*  89:157 */     this.idArticuloServicio = idArticuloServicio;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdOrganizacion()
/*  93:    */   {
/*  94:166 */     return this.idOrganizacion;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdOrganizacion(int idOrganizacion)
/*  98:    */   {
/*  99:176 */     this.idOrganizacion = idOrganizacion;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getIdSucursal()
/* 103:    */   {
/* 104:185 */     return this.idSucursal;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setIdSucursal(int idSucursal)
/* 108:    */   {
/* 109:195 */     this.idSucursal = idSucursal;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getCodigo()
/* 113:    */   {
/* 114:204 */     return this.codigo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setCodigo(String codigo)
/* 118:    */   {
/* 119:214 */     this.codigo = codigo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getNombre()
/* 123:    */   {
/* 124:223 */     return this.nombre;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setNombre(String nombre)
/* 128:    */   {
/* 129:233 */     this.nombre = nombre;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getDescripcion()
/* 133:    */   {
/* 134:242 */     return this.descripcion;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setDescripcion(String descripcion)
/* 138:    */   {
/* 139:252 */     this.descripcion = descripcion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNumeroSerie()
/* 143:    */   {
/* 144:261 */     return this.numeroSerie;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNumeroSerie(String numeroSerie)
/* 148:    */   {
/* 149:271 */     this.numeroSerie = numeroSerie;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getNumeroParte()
/* 153:    */   {
/* 154:280 */     return this.numeroParte;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setNumeroParte(String numeroParte)
/* 158:    */   {
/* 159:290 */     this.numeroParte = numeroParte;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getCodigoBarras()
/* 163:    */   {
/* 164:299 */     return this.codigoBarras;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setCodigoBarras(String codigoBarras)
/* 168:    */   {
/* 169:309 */     this.codigoBarras = codigoBarras;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int getVitaUtil()
/* 173:    */   {
/* 174:318 */     return this.vitaUtil;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setVitaUtil(int vitaUtil)
/* 178:    */   {
/* 179:328 */     this.vitaUtil = vitaUtil;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public TipoArticuloServicioEnum getTipoArticuloServicio()
/* 183:    */   {
/* 184:337 */     return this.tipoArticuloServicio;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setTipoArticuloServicio(TipoArticuloServicioEnum tipoArticuloServicio)
/* 188:    */   {
/* 189:347 */     this.tipoArticuloServicio = tipoArticuloServicio;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public GrupoArticuloServicio getGrupoArticuloServicio()
/* 193:    */   {
/* 194:356 */     return this.grupoArticuloServicio;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setGrupoArticuloServicio(GrupoArticuloServicio grupoArticuloServicio)
/* 198:    */   {
/* 199:366 */     this.grupoArticuloServicio = grupoArticuloServicio;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public TipoCicloOperacion getTipoCicloOperacion()
/* 203:    */   {
/* 204:375 */     return this.tipoCicloOperacion;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setTipoCicloOperacion(TipoCicloOperacion tipoCicloOperacion)
/* 208:    */   {
/* 209:385 */     this.tipoCicloOperacion = tipoCicloOperacion;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<HistoricoArticuloServicio> getListaHistoricoArticuloServicioHijo()
/* 213:    */   {
/* 214:394 */     return this.listaHistoricoArticuloServicioHijo;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setListaHistoricoArticuloServicioHijo(List<HistoricoArticuloServicio> listaHistoricoArticuloServicioHijo)
/* 218:    */   {
/* 219:404 */     this.listaHistoricoArticuloServicioHijo = listaHistoricoArticuloServicioHijo;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public CategoriaArticuloServicio getCategoriaArticuloServicio()
/* 223:    */   {
/* 224:413 */     return this.categoriaArticuloServicio;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setCategoriaArticuloServicio(CategoriaArticuloServicio categoriaArticuloServicio)
/* 228:    */   {
/* 229:423 */     this.categoriaArticuloServicio = categoriaArticuloServicio;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public int getId()
/* 233:    */   {
/* 234:453 */     return this.idArticuloServicio;
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio
 * JD-Core Version:    0.7.0.1
 */